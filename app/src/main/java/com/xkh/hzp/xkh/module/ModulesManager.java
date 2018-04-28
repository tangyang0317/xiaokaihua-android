package com.xkh.hzp.xkh.module;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xkh.hzp.xkh.com.base.Global;
import xkh.hzp.xkh.com.base.base.ModuleBase;
import xkh.hzp.xkh.com.base.utils.AbAppUtil;

/**
 * @packageName xkh.hzp.xkh.com.module
 * @FileName ModulesManager
 * @Author tangyang
 * @DATE 2018/4/27
 **/
public class ModulesManager {

    private static ModulesManager ins;

    private ConcurrentHashMap<String, ModuleBase> modules = new ConcurrentHashMap<>();

    public static ModulesManager getIns() {
        if (ins == null) {
            synchronized (ModulesManager.class) {
                if (ins == null) {
                    ins = new ModulesManager();
                }
            }
        }
        return ins;
    }


    /***
     * 配置需要使用的模块
     * @param moduleBases
     */
    public void configModule(ModuleBase... moduleBases) {
        for (ModuleBase moduleBase : moduleBases) {
            modules.put(moduleBase.getModuleName(), moduleBase);
        }
    }


    /**
     * 初始化所有模块
     */
    public void initModules() {
        String processName = AbAppUtil.getProcessName(Global.app, android.os.Process.myPid());
        if (processName != null) {
            boolean defaultProcess = processName.equals(Global.appPackageName);
            if (defaultProcess) {
                for (Map.Entry<String, ModuleBase> entry : modules.entrySet()) {
                    entry.getValue().initModule(Global.app);
                }
            }
        }
    }


    /**
     * 销毁所有模组
     */
    public void destroyAll() {
        for (Map.Entry<String, ModuleBase> entry :
                modules.entrySet()) {
            entry.getValue().destoryModule();
        }
        modules.clear();
    }


}
