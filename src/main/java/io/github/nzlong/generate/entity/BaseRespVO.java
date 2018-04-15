package io.github.nzlong.generate.entity;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 03 31 下午7:48
 */
public class BaseRespVO {

    private boolean success;

    private String reason;

    public BaseRespVO() {
    }

    public BaseRespVO(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public BaseRespVO setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public BaseRespVO setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public String toString() {
        return "ConnectionRespVo{" + "success=" + success + ", reason='" + reason + '\'' + '}';
    }

}
