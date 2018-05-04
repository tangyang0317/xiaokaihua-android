package com.xkh.hzp.xkh.tuSDK;

import android.app.Activity;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.struct.TuSdkSize;
import org.lasque.tusdk.core.utils.sqllite.ImageSqlHelper;
import org.lasque.tusdk.core.utils.sqllite.ImageSqlInfo;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuAlbumMultipleComponent;
import org.lasque.tusdk.modules.components.TuSdkComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * 多图选择相册，带拍照
 * Created by tangyang on 17/12/10.
 */

public class AlbumMultipleComponent extends TuBase implements TuSdkComponent.TuSdkComponentDelegate {

    @Override
    public void showSample(Activity activity) {
        showSample(activity);
    }

    @Override
    public void showSample(Activity activity, int maxSelection) {
        if (activity == null) return;
        TuAlbumMultipleComponent comp = TuSdkGeeV1.albumMultipleCommponent(activity, this, maxSelection);
        comp.componentOption();
        comp.componentOption().albumListOption();
        comp.componentOption().cameraOption();
        comp.componentOption().cameraOption().setSaveToTemp(false);
        comp.componentOption().cameraOption().setSaveToAlbumName("xiaokaihua");
        comp.componentOption().cameraOption().setSaveToAlbum(true);
        // 设置相册照片排序方式
        comp.componentOption().albumListOption().setPhotosSortDescriptor(ImageSqlHelper.PhotoSortDescriptor.Date_Modified);
        // 设置最大支持的图片尺寸 默认：8000 * 8000
        comp.componentOption().albumListOption().setMaxSelectionImageSize(new TuSdkSize(8000, 8000));
        // 在组件执行完成后自动关闭组件
        comp.setAutoDismissWhenCompleted(true)
                // 显示组件
                .showComponent();
    }

    @Override
    public void onComponentFinished(TuSdkResult tuSdkResult, Error error, TuFragment tuFragment) {
        if (tuSdkResult.images != null && tuSdkResult.images.size() > 0) {
            List<String> imagePath = new ArrayList<>();
            for (ImageSqlInfo info : tuSdkResult.images) {
                imagePath.add(info.path);
            }
            handle.onMultipleTuSuccess(imagePath);
        } else if (tuSdkResult.imageSqlInfo != null) {
            List<String> imagePath = new ArrayList<>();
            imagePath.add(tuSdkResult.imageSqlInfo.path);
            if (handle != null) {
                handle.onMultipleTuSuccess(imagePath);
            }
        }
    }


    public void setHandle(TuMutipleHandle handle) {
        this.handle = handle;
    }

    private TuMutipleHandle handle;
}
