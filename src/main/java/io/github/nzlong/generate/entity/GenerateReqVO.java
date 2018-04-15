package io.github.nzlong.generate.entity;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 14 下午7:53
 */
public class GenerateReqVO {

    private String[] tables;

    private String path;

    private Select[] selection;

    private String packageName;

    private ConnectionReqVO conn;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public ConnectionReqVO getConnectionReqVO() {
        return conn;
    }

    public void setConnectionReqVO(ConnectionReqVO connectionReqVO) {
        this.conn = connectionReqVO;
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Select[] getSelection() {
        return selection;
    }

    public void setSelection(Select[] selection) {
        this.selection = selection;
    }

}
