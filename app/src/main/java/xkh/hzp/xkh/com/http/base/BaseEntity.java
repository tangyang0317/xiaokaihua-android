package xkh.hzp.xkh.com.http.base;

/**
 * HttpResponse基类
 * Created by tangyang on 18/4/21.
 */

public class BaseEntity<T> {

    private String msg;
    private int code;
    private boolean success;
    private T result;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
