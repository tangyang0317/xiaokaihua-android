package com.xkh.hzp.xkh.event;

/**
 * @packageName com.xkh.hzp.xkh.event
 * @FileName LogoutEvent
 * @Author tangyang
 * @DATE 2018/5/31
 **/
public class LogoutEvent {
    private boolean logoutSuccess;

    public LogoutEvent(boolean logoutSuccess) {
        this.logoutSuccess = logoutSuccess;
    }

    public boolean isLogoutSuccess() {
        return logoutSuccess;
    }

    public void setLogoutSuccess(boolean logoutSuccess) {
        this.logoutSuccess = logoutSuccess;
    }
}
