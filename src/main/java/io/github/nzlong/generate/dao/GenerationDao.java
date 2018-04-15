package io.github.nzlong.generate.dao;

import com.blade.ioc.annotation.Bean;
import io.github.nzlong.generate.entity.Column;
import io.github.nzlong.generate.entity.PageVO;
import io.github.nzlong.generate.entity.TableInfo;
import io.github.nzlong.generate.kit.TypeKit;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 01 上午9:40
 */
@Bean
public class GenerationDao {

    /**
     * 获取表名和注释信息
     *
     * @param connection
     * @param pageVO
     * @param schema
     * @throws SQLException
     */
    public void getTables(Connection connection, PageVO pageVO, String schema) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet schemas = metaData.getTables(schema, null, null, null);
        List<TableInfo> tableInfos = new ArrayList<>();
        while (schemas.next()) {
            String tableName = schemas.getString("TABLE_NAME");
            String tableComment = schemas.getString("REMARKS");
            TableInfo tableInfo = new TableInfo();
            tableInfos.add(tableInfo.setTableName(tableName)
                                    .setTableComment(tableComment));
        }
        pageVO.setData(tableInfos);
    }

    /**
     * 获取数据库名称
     *
     * @param connection
     */
    public List<String> getSchemas(Connection connection) throws SQLException {
        List<String> schemaNames = new ArrayList<>();
        ResultSet schemas = connection.prepareStatement("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA").executeQuery();
        while (schemas.next()) {
            schemaNames.add(schemas.getString("SCHEMA_NAME"));
        }
        return schemaNames;
    }

    /**
     * 根据表名获取所有列的属性
     *
     * @param table
     * @return
     * @throws SQLException
     */
    public List<Column> getColumns(String schema, String table, Connection connection) throws SQLException {
        List<Column> columnList = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(schema, null, table, null);
        while (columns.next()) {
            columnList.add(Column.getInstance()
                                 .setColumnName(columns.getString("COLUMN_NAME"))
                                 .setRemark(columns.getString("REMARKS"))
                                 .setType(TypeKit.jdbcType2JavaType(columns.getString("TYPE_NAME"))));
        }
        return columnList;
    }
}
