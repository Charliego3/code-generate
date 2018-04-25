package io.github.nzlong.generate.service;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import io.github.nzlong.generate.GenerationApplication;
import io.github.nzlong.generate.dao.GenerationDao;
import io.github.nzlong.generate.entity.Column;
import io.github.nzlong.generate.entity.ConnectionReqVO;
import io.github.nzlong.generate.entity.GenerateReqVO;
import io.github.nzlong.generate.entity.PageVO;
import io.github.nzlong.generate.exception.GeneraterException;
import io.github.nzlong.generate.kit.BaseKit;
import io.github.nzlong.generate.kit.ConnKit;
import io.github.nzlong.generate.kit.Const;
import io.github.nzlong.generate.kit.SysCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 01 上午9:36
 */
@Bean
public class GenerationService {

    @Inject
    private GenerationDao generationDao;

    private Connection connection;

    private String removePrefix = "";

    /**
     * 获取所有表信息
     * @param connectionReqVO
     * @param pageVO
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void getTables(ConnectionReqVO connectionReqVO, PageVO pageVO) throws SQLException, ClassNotFoundException {
        if (connectionReqVO == null) {
            throw new GeneraterException(SysCode.CONNECT_ERROR);
        }
        Connection conn = ConnKit.getConnection(connectionReqVO);
        generationDao.getTables(conn, pageVO, connectionReqVO.getSchema());
    }

    /**
     * 获取数据库名称信息
     *
     * @param connectionReqVO
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<String> getSchemas(ConnectionReqVO connectionReqVO) throws SQLException, ClassNotFoundException {
        if (connectionReqVO == null) {
            throw new GeneraterException(SysCode.CONNECT_ERROR);
        }
        Connection conn = ConnKit.getConnection(connectionReqVO);
        return generationDao.getSchemas(conn);
    }

    /**
     * 构造实体
     *
     * @param generateReqVO
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void generateStart(GenerateReqVO generateReqVO) throws SQLException, ClassNotFoundException {
        if (generateReqVO.getTables().length <= 0) {
            throw new GeneraterException(SysCode.ERROR_TABLES_IS_EMPTY);
        }
        if (generateReqVO.getConnectionReqVO() == null) {
            throw new GeneraterException(SysCode.CONNECT_ERROR);
        }
        if (StringKit.isBlank(generateReqVO.getPackageName())) {
            throw new GeneraterException(SysCode.ERROR_PACKAGE_PATH_IS_EMPTY);
        }
        Connection connection = ConnKit.getConnection(generateReqVO.getConnectionReqVO());
        this.connection = connection;
        String schema = generateReqVO.getConnectionReqVO().getSchema();
        Stream.of(generateReqVO.getTables())
              .filter(tableName -> StringKit.isNotBlank(tableName))
              .forEach(tableName -> {
                  try {
                      StringBuilder clazz = new StringBuilder();
                      boolean isAnnotation = generateReqVO.getSelection()[0].getValue();
                      System.out.println(String.format("[DEBUG] Start generate table [%s]", tableName));
                      clazz.append(buildPackage(generateReqVO.getPackageName()));
                      List<Column> columnList = generationDao.getColumns(schema, tableName, connection);
                      clazz.append(buildImport(columnList));
                      clazz.append(buildAnnotation(getTableComment(tableName)));
                      clazz.append(String.format(Const.PUBLIC_CLASS, convertToCamelPrefixUpper(tableName)));
                      clazz.append(buildField(columnList, isAnnotation));
                      clazz.append(buildEmptyStrucutre(tableName, isAnnotation));
                      clazz.append(buildStrucutre(columnList, tableName, isAnnotation));
                      clazz.append(buildInstance(tableName));
                      clazz.append(buildSetter(columnList, tableName));
                      clazz.append(buildGetter(columnList));
                      clazz.append(buildToString(columnList, tableName));
                      clazz.append(Const.CLASS_END);
                      writeToFile(clazz.toString(), generateReqVO.getPath(), tableName);
                      System.out.println(String.format("[DEBUG] End generate table [%s]", tableName));
                  } catch (Exception ex) {
                      ex.printStackTrace();
                  }
              });
    }

    /**
     * 写入内容到文件
     *
     * @param content
     * @param path
     * @param fileName
     * @throws FileNotFoundException
     */
    protected void writeToFile(String content, String path, String fileName) throws FileNotFoundException {
        if (StringKit.isNotBlank(path)) {
            path = GenerationApplication.class.getResource("").getPath().concat("production").concat("entity");
        }
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File clazz = new File(path.concat(File.separator).concat(convertToCamelPrefixUpper(fileName)).concat(Const.FILE_TYPE));
        PrintStream ps = new PrintStream(clazz);
        ps.println(content);
    }

    /**
     * 构建toString()
     *
     * @param columnList
     * @param tableName
     * @return
     */
    public String buildToString(List<Column> columnList, String tableName) {
        StringBuilder toString = new StringBuilder();
        toString.append("\t@Override\n")
                .append("\tpublic String toString() {\n")
                .append("\t\treturn \"")
                .append(convertToCamelPrefixUpper(tableName))
                .append(" {\"");
        if (BaseKit.isNotEmptyOrNull(columnList)) {
            columnList.forEach(column -> {
                String columName = convertToCamelPrefixLower(column.getColumnName());
                toString.append("\n\t\t\t\t+ \"")
                        .append(columName)
                        .append(" = \'\" + ")
                        .append(columName)
                        .append(" + \"' ,\"");
            });
        }
        String toStringStr = toString.toString();
        if (toStringStr.contains(",")) {
            toStringStr = toString.substring(0, toString.lastIndexOf("+"));
            toStringStr += "+ \"'\"";
        }
        toStringStr += "\n\t\t\t\t+ \"}\";\n\t}\n";
        return toStringStr;
    }

    /**
     * 构造getter函数
     *
     * @param columnList
     * @return
     */
    protected String buildGetter(List<Column> columnList) {
        StringBuilder getter = new StringBuilder();
        if (BaseKit.isNotEmptyOrNull(columnList)) {
            columnList.forEach(column ->
                getter.append("\tpublic ")
                      .append(column.getType())
                      .append(" get")
                      .append(convertToCamelPrefixUpper(column.getColumnName()))
                      .append("() {\n")
                      .append("\t\treturn this.")
                      .append(convertToCamelPrefixLower(column.getColumnName()))
                      .append(";\n")
                      .append("\t}\n\n")

            );
        }
        return getter.toString();
    }

    /**
     * 构造setter函数
     *
     * @param columnList
     * @param table
     * @return
     */
    protected String buildSetter(List<Column> columnList, String table) {
        StringBuilder setter = new StringBuilder();
        if (BaseKit.isNotEmptyOrNull(columnList)) {
            columnList.forEach(column -> {
                String columnNameLower = convertToCamelPrefixLower(column.getColumnName());
                setter.append("\tpublic ")
                      .append(convertToCamelPrefixUpper(table))
                      .append(" set")
                      .append(convertToCamelPrefixUpper(column.getColumnName()))
                      .append("(")
                      .append(column.getType())
                      .append(" ")
                      .append(columnNameLower)
                      .append(") {\n")
                      .append("\t\tthis.")
                      .append(columnNameLower)
                      .append(" = ")
                      .append(columnNameLower)
                      .append(";\n")
                      .append("\t\treturn this;\n")
                      .append("\t}\n\n");
            });
        }
        return setter.toString();
    }

    /**
     * 获取实例
     *
     * @param table
     * @return
     */
    protected String buildInstance(String table) {
        StringBuilder instance = new StringBuilder();
        String convert = convertToCamelPrefixUpper(table);
        instance.append("\tpublic static ".concat(convert)
                                          .concat(" getInstance() {\n")
                                          .concat("\t\treturn new ")
                                          .concat(convert)
                                          .concat("();\n\t}\n\n"));
        return instance.toString();
    }

    /**
     * 构建字段
     *
     * @param columnList
     * @param isAnnotation
     * @return
     */
    protected String buildField(List<Column> columnList, boolean isAnnotation) {
        StringBuilder fields = new StringBuilder();
        if (BaseKit.isNotEmptyOrNull(columnList)) {
            columnList.forEach(column -> {
                if (isAnnotation) {
                    fields.append(String.format(Const.FIELD_ANNOTATION, column.getRemark()));
                }
                fields.append(String.format(Const.FIELD_PRIVATE, column.getType(), convertToCamelPrefixLower(column.getColumnName())));
            });
        }
        return fields.toString();
    }

    /**
     * 构造全参构造函数
     *
     * @param columnList
     * @param strucutre
     * @return
     */
    protected String buildStrucutre(List<Column> columnList, String strucutre, boolean isAnnotation) {
        StringBuilder stru = new StringBuilder();
        if (BaseKit.isNotEmptyOrNull(columnList)) {
            if (isAnnotation) {
                stru.append(String.format(Const.STRUCUTRE_ANNOTATION, Const.ALL_STRUCUTRE));
            }
            stru.append(String.format(Const.STRUCUTRE, convertToCamelPrefixUpper(strucutre)).concat("("));
            StringBuilder paramBu = new StringBuilder();
            columnList.forEach(column ->
                paramBu.append(column.getType().concat(" ").concat(convertToCamelPrefixLower(column.getColumnName())).concat(", "))
            );
            String param = paramBu.toString();
            param = param.substring(0, param.lastIndexOf(","));
            stru.append(param);
            stru.append(") {\n");
            columnList.forEach(column ->
                stru.append("\t\tthis.".concat(convertToCamelPrefixLower(column.getColumnName()))
                                       .concat(" = ")
                                       .concat(convertToCamelPrefixLower(column.getColumnName()))
                                       .concat(";\n"))
            );
            stru.append("\t}\n\n");
        }
        return stru.toString();
    }

    /**
     * 构造空参构造函数
     *
     * @param strucutre
     * @return
     */
    protected String buildEmptyStrucutre(String strucutre, boolean isAnnotation) {
        String re = "";
        if (isAnnotation) {
            re = String.format(Const.STRUCUTRE_ANNOTATION, Const.EMPTY_STRUCUTRE);
        }
        re += String.format(Const.STRUCUTRE, convertToCamelPrefixUpper(strucutre)).concat("() {\n\t}\n\n");
        return re;
    }

    /**
     * 将下划线转为首字母小写的驼峰格式
     *
     * @param takeConvert
     * @return
     */
    protected String convertToCamelPrefixLower(String takeConvert) {
        if (StringKit.isBlank(takeConvert)) {
            return "";
        }
        String convert = underlineToCamel(takeConvert, false);
        StringBuilder reSb = new StringBuilder();
        reSb.append(Character.toLowerCase(convert.charAt(0)));
        reSb.append(convert.substring(1));
        return reSb.toString();
    }

    /**
     * 将下划线转换为首字母大写的驼峰格式
     *
     * @param takeConvert
     * @return
     */
    protected String convertToCamelPrefixUpper(String takeConvert) {
        if (StringKit.isBlank(takeConvert)) {
            return "";
        }
        StringBuilder reSb = new StringBuilder();
        int prefixLength = removePrefix == null ? 0 : removePrefix.length();
        String cutTakeCon = takeConvert.substring(prefixLength);
        String convert = underlineToCamel(cutTakeCon, false);
        reSb.append(Character.toUpperCase(convert.charAt(0)));
        reSb.append(convert.substring(1));
        return reSb.toString();
    }

    /**
     * 将家划线转换为驼峰
     *
     * @param columnName
     * @return
     */
    public String underlineToCamel(String columnName, boolean isConvert) {
        if (StringKit.isBlank(columnName)) {
            return "";
        }
        int len = columnName.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(columnName.charAt(0));

        for(int i = 1; i < len; ++i) {
            char c = columnName.charAt(i);
            if (c == '_') {
                ++i;
                if (i < len) {
                    sb.append(Character.toUpperCase(columnName.charAt(i)));
                }
            } else {
                if (isConvert) {
                    c = Character.toLowerCase(c);
                }
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 获取表注释
     *
     * @param table
     * @return
     * @throws SQLException
     */
    protected String getTableComment(String table) throws SQLException {
        String tableName = "";
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(connection.getCatalog(), null, table, null);
        while (tables.next()) {
            tableName = tables.getString("REMARKS");
        }
        return tableName;
    }

    /**
     * 构造类注释
     *
     * @return
     */
    protected String buildAnnotation(String tableRemark) {
        String userName = System.getProperty("user.name");
        String osName = System.getProperty("os.name");
        String date = DateKit.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
        String encoding = System.getProperty("sun.jnu.encoding");
        return String.format(Const.CLASS_ANNOTATION, userName, osName, tableRemark, encoding, date);
    }

    /**
     * 构造导包数据
     *
     * @return
     */
    protected String buildImport(List<Column> columns) {
        StringBuilder importStr = new StringBuilder();
        importStr.append("\n");
        columns.forEach(column -> {
            if (StringKit.isNotBlank(column.getType()) && !importStr.toString().contains(Const.IMPORT_BIGDECIMAL) && column.getType().equalsIgnoreCase("BigDecimal")) {
                importStr.append(Const.IMPORT_BIGDECIMAL);
            }
            if (StringKit.isNotBlank(column.getType()) && !importStr.toString().contains(Const.IMPORT_DATE) && column.getType().equalsIgnoreCase("Date")) {
                importStr.append(Const.IMPORT_DATE);
            }
            if (StringKit.isNotBlank(column.getType()) && !importStr.toString().contains(Const.IMPORT_TIMESTAMP) && column.getType().equalsIgnoreCase("Timestamp")) {
                importStr.append(Const.IMPORT_TIMESTAMP);
            }
        });
        return importStr.toString().equalsIgnoreCase("\n") ? "" : importStr.toString();
    }

    /**
     * 包路径
     *
     * @param packageName
     * @return
     */
    protected String buildPackage(String packageName) {
        return "package " + packageName + ";\n";
    }

}
