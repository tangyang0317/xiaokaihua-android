package xkh.hzp.xkh.com.base.base;

import android.app.Application;

/**
 * @packageName xkh.hzp.xkh.com.base.base
 * @FileName ModuleBase
 * @Author tangyang
 * @DATE 2018/4/27
 **/
public abstract class ModuleBase {

    public ModuleBase(String moduleName) {
        this.moduleName = moduleName;
    }

    private String moduleName;


    public abstract void initModule(Application application);


    public abstract void destoryModule();

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
