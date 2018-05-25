package xkh.hzp.xkh.com.base.view.sectorMenu;

/**
 * @packageName xkh.hzp.xkh.com.base.view.sectorMenu
 * @FileName QuickClickChecker
 * @Author tangyang
 * @DATE 2018/5/23
 **/
public class QuickClickChecker {

    private int threshold;
    private long lastClickTime = 0;

    public QuickClickChecker(int threshold) {
        this.threshold = threshold;
    }

    public boolean isQuick() {
        boolean isQuick = System.currentTimeMillis() - lastClickTime <= threshold;
        lastClickTime = System.currentTimeMillis();
        return isQuick;
    }
}
