package io.github.nzlong.generate;

import com.blade.kit.StringKit;
import io.github.nzlong.generate.dao.GenerationDao;
import io.github.nzlong.generate.entity.ConnectionReqVO;
import io.github.nzlong.generate.entity.BaseRespVO;
import io.github.nzlong.generate.kit.ConnKit;
import io.github.nzlong.generate.kit.Const;
import io.github.nzlong.generate.kit.SysCode;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 03 31 下午7:29
 */
public class ConnectionTest {

    @Test
    public void connection() throws Exception {
        Class.forName(Const.DRIVER_CLASS_NAME);
        String url = "jdbc:mysql://127.0.0.1:3307/HOOP?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
        String userName = "root";
        String passward = "root";
        Connection connection = DriverManager.getConnection(url, userName, passward);
        System.out.println(connection);
    }

    @Test
    public void testConnectionTest() throws SQLException, ClassNotFoundException {
        ConnectionReqVO connectionVO = new ConnectionReqVO();
        connectionVO.setHost("localhost")
                    .setPort(3306)
                    .setSchema("HOOP")
                    .setUserName("root")
                    .setpassword("root");
        BaseRespVO b = ConnKit.testConnection(connectionVO);
        System.out.println(b);

//        DatabaseMetaData metaData = ConnKit.getConnection().getMetaData();
//        System.out.println(metaData);
    }

    @Test
    public void getTbles() throws SQLException, ClassNotFoundException {
        testConnectionTest();
//        Connection connection = ConnKit.getConnection();
//        DatabaseMetaData metaData = connection.getMetaData();
//        System.out.println(connection.getCatalog());
//        ResultSet schemas = metaData.getTables(connection.getCatalog(), null, null, null);
//        while (schemas.next()) {
//            System.out.println("======================== " + schemas.getString("TABLE_NAME") + " =========================");
//            System.out.println(String.format("table_CAT : [%s]", schemas.getString("TABLE_CAT")));
//            System.out.println(String.format("table_SCHEM : [%s]", schemas.getString("TABLE_SCHEM")));
//            System.out.println(String.format("table_name : [%s]", schemas.getString("TABLE_NAME")));
////            String table_comment = new GenerationDao().getTableComment(schemas.getString("TABLE_NAME"));
////            System.out.println(String.format("table_comment : [%s]", table_comment));
//            System.out.println(String.format("table_TYPE : [%s]", schemas.getString("TABLE_TYPE")));
//            System.out.println(String.format("table_REMARKS : [%s]", schemas.getString("REMARKS")));
//            System.out.println(String.format("TYPE_CAT : [%s]", schemas.getString("TYPE_CAT")));
//            System.out.println(String.format("TYPE_SCHEM : [%s]", schemas.getString("TYPE_SCHEM")));
//            System.out.println(String.format("TYPE_NAME : [%s]", schemas.getString("TYPE_NAME")));
//            System.out.println("\n\n");
//        }
    }

    @Test
    public void getPayoutCoinRecordTest() throws Exception {
        //        url = websiteUrl + "getPayoutCoinRecord" + "?currencyId=7baSBb678ZV&userId=7bK5SMOLQfZ";
        //        String s = HttpUtils.doPost(host, url, null);
        //        soutResData("获取提币申请记录", s);

        Class.forName("org.mariadb.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
        DatabaseMetaData metaData = connection.getMetaData();
//        ResultSet schemas = connection.prepareStatement("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA").executeQuery();
//        while (schemas.next()) {
//            System.out.println(schemas.getString("SCHEMA_NAME"));
//        }
        ResultSet tables = metaData.getTables("hoop", null, "USER", null);
        while (tables.next()) {
            String table_name = tables.getString("TABLE_NAME");
            String remarks = tables.getString("REMARKS");
            System.out.println("tableName: " + table_name + ",\t\t\t\tremark: " + remarks);
            ResultSet columns = metaData.getColumns(null, null, table_name, null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME").toUpperCase();
                String columnRemark = columns.getString("REMARKS");
                int len = columns.getInt("COLUMN_SIZE");
                int precision = columns.getInt("DECIMAL_DIGITS");
                if (columnName == null || "".equals(columnName)) {
                    continue;
                }
                System.out.println("列名: " + underlineToCamel(columnName) + ",\t字段类型: " + columnType + ",\t字段注释: " + columnRemark + ",\t字段长度: " + len + ",\t字段精度: " + precision);
            }
            System.out.println("====================================================================================================================================================================");
        }
    }

    public String underlineToCamel(String columnName) {
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
                sb.append(c);
            }
        }

        return sb.toString();
    }

    @Test
    public void properties() {
        Properties properties = System.getProperties();
        System.out.println(properties);
    }

}
