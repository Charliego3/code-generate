package io.github.nzlong.generate.entity;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 14 下午9:57
 */
public class Column {

    private String columnName;
    private String type;
    private String remark;

    public static Column getInstance() {
        return new Column();
    }

    public String getColumnName() {
        return columnName;
    }

    public Column setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getType() {
        return type;
    }

    public Column setType(String type) {
        this.type = type;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Column setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public String toString() {
        return "Column {"
                + "columnName = '" + columnName + "' ,"
                + "type = '" + type + "' ,"
                + "remark = '" + remark + "'"
                + "}";
    }

}
