package xkh.hzp.xkh.com.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.io.File;
import java.util.List;
import java.util.Map;

import xkh.hzp.xkh.com.R;
import xkh.hzp.xkh.com.base.utils.DimentUtils;

/**
 * 自定义通用的GridView添加图片适配器
 *
 * @packageName xkh.hzp.xkh.com.base.adapter
 * @FileName GridViewAddImgesAdpter
 * @Author tangyang
 * @DATE 2018/5/4
 **/
public class GridViewAddImgesAdpter extends BaseAdapter {
    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    private List<String> datas;
    private Context context;
    private int numOfRow;
    private LayoutInflater inflater;
    /**
     * 可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
     * 默认9张
     */
    private int maxImages = 9;

    public GridViewAddImgesAdpter(List<String> datas, Context context, int numOfRow) {
        this.datas = datas;
        this.context = context;
        this.numOfRow = numOfRow;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 获取最大上传张数
     *
     * @return
     */
    public int getMaxImages() {
        return maxImages;
    }

    /**
     * 设置最大上传张数
     *
     * @param maxImages
     */
    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    /**
     * 让GridView中的数据数目加1最后一个显示+号
     *
     * @return 返回GridView中的数量
     */
    @Override
    public int getCount() {
        int count = datas == null ? 1 : datas.size() + 1;
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void notifyDataSetChanged(List<String> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_publish_grida, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int screenWidth = DimentUtils.getScreenWidth(context);
        ViewGroup.LayoutParams layoutParams = viewHolder.ivimage.getLayoutParams();
        layoutParams.width = (screenWidth - DimentUtils.dip2px(context, numOfRow * 5)) / numOfRow;
        layoutParams.height = (screenWidth - DimentUtils.dip2px(context, numOfRow * 5)) / numOfRow;
        viewHolder.ivimage.setLayoutParams(layoutParams);

        if (datas != null && position < datas.size()) {
            final File file = new File(datas.get(position).toString());
            Glide.with(context)
                    .load(file)
                    .priority(Priority.HIGH)
                    .into(viewHolder.ivimage);
            viewHolder.btdel.setVisibility(View.VISIBLE);
            viewHolder.btdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (file.exists()) {
                        file.delete();
                    }
                    datas.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {
            Glide.with(context)
                    .load(R.drawable.img_add_img)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .into(viewHolder.ivimage);
            viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.btdel.setVisibility(View.GONE);
        }

        return convertView;

    }

    public class ViewHolder {
        public final ImageView ivimage;
        public final Button btdel;
        public final View root;

        public ViewHolder(View root) {
            ivimage = root.findViewById(R.id.itemAddImg);
            btdel = root.findViewById(R.id.itemDelBtn);
            this.root = root;
        }
    }
}
