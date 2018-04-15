package io.github.nzlong.generate.kit;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 01 下午4:23
 */
public enum SysCode {

    CONNECT_ERROR(9001, "连接参数为空"),
    ERROR_TABLES_IS_EMPTY(9002, "没有选择要生成的表"),
    ERROR_PACKAGE_PATH_IS_EMPTY(9003, "包路径为空")

    ;

    SysCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    private int code;
    private String value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
