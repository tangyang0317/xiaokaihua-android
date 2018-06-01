package com.xkh.hzp.xkh.event;

/**
 * @packageName com.xkh.hzp.xkh.event
 * @FileName LoginEvent
 * @Author tangyang
 * @DATE 2018/5/31
 **/
public class LoginEvent {
    private boolean success;

    public LoginEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
