package xkh.hzp.xkh.com.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 管理设备相关信息
 *
 * @author Leo
 */
public class AbDevice {
    public static String appPackageName = null;
    public static String appVersionName = null;
    public static int appTargetSdkVersion;
    public static int appVersionCode = 0;

    //移动设备国际识别码，移动设备的唯一识别号码
    public static String IMEI = null;
    //国际移动用户识别码储存在SIM卡中
    public static String IMSI = null;
    //用户手机号码
    public static String PHONE_NUMBER = null;
    //手机型号
    public static String MOBILE_MODEL = null;
    //终端类型
    public static String PLATFORM = "android";
    //显示属性
    public static DisplayMetrics DM = null;
    //屏幕宽高
    public static int SCREEN_WIDTH_PX = 0; // 屏幕宽（像素，如：480px）
    public static int SCREEN_HEIGHT_PX = 0; // 屏幕宽（像素，如：480px）
    public static int SCREEN_WIDTH_DP = 0; // 屏幕宽（像素，如：480px）
    public static int SCREEN_HEIGHT_DP = 0; // 屏幕宽（像素，如：480px）

    //系统版本级别
    public static int OS_LEVEL = 0;
    public static boolean isExternalStorageAvailable = false;
    public static boolean isExternalStorageWriteable = false;

    public static void init(Application app) {
        TelephonyManager tm = (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);

        /**需要 READ_PHONE_STATE 动态权限
         IMEI = tm.getDeviceId();
         IMEI = IMEI != null ? IMEI : "";


         IMSI = tm.getSubscriberId();
         IMSI = IMSI != null ? IMSI : "";
         */

        MOBILE_MODEL = Build.MODEL;
        MOBILE_MODEL = MOBILE_MODEL != null ? MOBILE_MODEL : "";

        OS_LEVEL = Build.VERSION.SDK_INT;

        String state = Environment.getExternalStorageState();

        //检查外部存储
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            isExternalStorageAvailable = isExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            isExternalStorageAvailable = true;
            isExternalStorageWriteable = false;
        } else {
            isExternalStorageAvailable = isExternalStorageWriteable = false;
        }

        initDisplay();

        initAppInfo();

        Log.d("xkh", "[Device] Inited! ***********************************************\n" +
                "VersionName:(" + appVersionName + "); VersionCode:(" + appVersionCode + ")\n" +
                "IMEI:(" + IMEI + ") \nIMSI:(" + IMSI + ") \nMOBILE_MODEL;(" + MOBILE_MODEL + ")\n" +
                "ScreenWidth:(" + SCREEN_WIDTH_PX + "); screenHeight;(" + SCREEN_HEIGHT_PX + ");\n" +
                "DisplayMetrics xdpi:(" + DM.xdpi + "); ydpi;(" + DM.ydpi + ");\n" +
                "DisplayMetrics density:(" + DM.density + "); densityDPI:(" + DM.densityDpi + ");\n"
        );
    }

    public static void initDisplay() {
        DM = Global.app.getResources().getDisplayMetrics();
        SCREEN_WIDTH_PX = DM.widthPixels; // 屏幕宽（像素，如：480px）
        SCREEN_WIDTH_DP = (int) (DM.widthPixels / DM.density);
        SCREEN_HEIGHT_PX = DM.heightPixels; // 屏幕高（像素，如：800px）
        SCREEN_HEIGHT_DP = (int) (DM.heightPixels / DM.density);
    }

    /**
     * 初始化安装包信息。
     */
    public static void initAppInfo() {
        appPackageName = Global.app.getPackageName();
        PackageManager pm = Global.app.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(appPackageName, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            appVersionName = info.versionName;
            appVersionCode = info.versionCode;
            appTargetSdkVersion = info.applicationInfo.targetSdkVersion;
        }
    }

    /**
     * 缓存路径, 优先外部存储
     *
     * @return
     */
    public static String getCachePath() {
        String path = "";
        if (isExternalStorageAvailable && isExternalStorageWriteable && Global.app.getExternalCacheDir() != null) {
            path = Global.app.getExternalCacheDir().getAbsolutePath() + "/";
        } else {
            path = Global.app.getCacheDir().getAbsolutePath() + "/";
        }
        return path;
    }

    public static DisplayMetrics getDM() {
        return DM;
    }

}
