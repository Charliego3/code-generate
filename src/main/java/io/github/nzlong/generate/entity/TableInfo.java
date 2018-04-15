package io.github.nzlong.generate.entity;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 01 上午11:42
 */
public class TableInfo {

    private String tableName;
    private String tableComment;

    public String getTableName() {
        return tableName;
    }

    public TableInfo setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableComment() {
        return tableComment;
    }

    public TableInfo setTableComment(String tableComment) {
        this.tableComment = tableComment;
        return this;
    }

    @Override
    public String toString() {
        return "TableInfo{" + "tableName='" + tableName + '\'' + ", tableComment='" + tableComment + '\'' + '}';
    }

}
