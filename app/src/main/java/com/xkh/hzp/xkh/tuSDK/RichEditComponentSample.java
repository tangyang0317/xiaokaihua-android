package com.xkh.hzp.xkh.tuSDK;

import android.app.Activity;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.struct.TuSdkSize;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.core.utils.TuSdkWaterMarkOption;
import org.lasque.tusdk.core.utils.sqllite.ImageSqlHelper;
import org.lasque.tusdk.core.utils.sqllite.ImageSqlInfo;
import org.lasque.tusdk.geev2.TuSdkGeeV2;
import org.lasque.tusdk.geev2.impl.components.TuRichEditComponent;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.modules.components.TuSdkComponent;

import java.util.ArrayList;
import java.util.List;

import xkh.hzp.xkh.com.base.utils.DimentUtils;

/**
 * 多图编辑组件
 * Created by tangyang on 17/11/22.
 */

public class RichEditComponentSample extends TuBase implements TuSdkComponent.TuSdkComponentDelegate {

    private TuMutipleHandle handle;

    @Override
    public void showSample(Activity activity) {
        showSample(activity, 9);
    }

    public void setTuMutipleHandle(TuMutipleHandle handle) {
        this.handle = handle;
    }

    @Override
    public void showSample(Activity activity, int maxSelection) {
        TuRichEditComponent comp = TuSdkGeeV2.richEditCommponent(activity, this);
        // 组件选项配置
        // 设置是否启用图片编辑 默认 true
        comp.componentOption().setEnableEditMultiple(true);
        // 相机组件配置
        // 设置拍照后是否预览图片 默认 true
        comp.componentOption().cameraOption().setEnablePreview(true);
        //图片是否存储为临时文件
        comp.componentOption().cameraOption().setSaveToTemp(false);
        comp.componentOption().cameraOption().setSaveToAlbumName("xiaokaihua");
        //图片是否保存到相册
        comp.componentOption().cameraOption().setSaveToAlbum(true);
        // 开启滤镜配置选项(默认：true)
        comp.componentOption().cameraOption().setEnableFilterConfig(false);
        // 多选相册组件配置
        // 设置相册最大选择数量
        comp.componentOption().albumMultipleComponentOption().albumListOption().setMaxSelection(maxSelection);
        TuSdkWaterMarkOption option = new TuSdkWaterMarkOption();
        comp.componentOption().editMultipleComponentOption().editMultipleOption().setWaterMarkOption(option);
        // 多功能编辑组件配置项
        // 设置最大编辑数量
        comp.componentOption().editMultipleComponentOption().setMaxEditImageCount(maxSelection);
        // 设置焦距初始值(默认：0, 0-getMaxZoom())
        comp.componentOption().cameraOption().setFocalDistanceScale(0);
        // 开启调节焦距 (默认：true)
        comp.componentOption().cameraOption().setEnableFocalDistance(false);
        // 设置没有改变的图片是否保存(默认 false)
        comp.componentOption().editMultipleComponentOption().setEnableAlwaysSaveEditResult(false);
        // 设置编辑时是否支持追加图片 默认 true
        comp.componentOption().editMultipleComponentOption().setEnableAppendImage(true);
        // 设置照片排序方式
        comp.componentOption().albumMultipleComponentOption().albumListOption().setPhotosSortDescriptor(ImageSqlHelper.PhotoSortDescriptor.Date_Added);
        // 设置最大支持的图片尺寸 默认：8000 * 8000
        comp.componentOption().albumMultipleComponentOption().albumListOption().setMaxSelectionImageSize(new TuSdkSize(8000, 8000));
        comp.componentOption().editMultipleComponentOption().editMultipleOption().setLimitSideSize(DimentUtils.getScreenHeight(activity) / 2);
        // 操作完成后是否自动关闭页面
        comp.setAutoDismissWhenCompleted(true)
                // 显示组件
                .showComponent();
    }

    @Override
    public void onComponentFinished(TuSdkResult tuSdkResult, Error error, TuFragment tuFragment) {
        TLog.d("PackageComponentSample onComponentFinished: %s | %s", tuSdkResult, error);
        if (tuSdkResult.images != null && tuSdkResult.images.size() > 0) {
            List<String> imagePath = new ArrayList<>();
            for (ImageSqlInfo info : tuSdkResult.images) {
                imagePath.add(info.path);
            }
            handle.onMultipleTuSuccess(imagePath);
        }
    }
}
