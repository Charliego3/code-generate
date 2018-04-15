package io.github.nzlong.generate.exception;

import io.github.nzlong.generate.kit.SysCode;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 01 下午4:21
 */
public class GeneraterException extends RuntimeException {

    private int code;

    public GeneraterException(int code, String message) {
        super(message);
        this.code = code;
    }

    public GeneraterException(String message) {
        super(message);
    }

    public GeneraterException(SysCode sysCode) {
        super(sysCode.getValue());
        this.code = sysCode.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
