package net.hycollege.ljl.sharefood.circledemo.ui.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.razerdp.github.com.common.MomentsType;
import com.razerdp.github.com.common.entity.MomentsInfo;
import com.razerdp.github.com.common.entity.PhotoBrowseInfo;
import com.razerdp.github.com.common.entity.PhotoInfo;
import com.razerdp.github.com.util.BmobUrlUtil;
import com.socks.library.KLog;
import net.hycollege.ljl.sharefood.R;
import net.hycollege.ljl.sharefood.circledemo.ActivityLauncher;

import java.util.ArrayList;
import java.util.List;
import razerdp.github.com.ui.base.adapter.LayoutId;
import razerdp.github.com.ui.imageloader.ImageLoadMnanger;
import razerdp.github.com.ui.widget.imageview.ForceClickImageView;
import razerdp.github.com.widget.PhotoContents;
import razerdp.github.com.widget.adapter.PhotoContentsBaseAdapter;

/**
 * Created by 大灯泡 on 2016/11/3.
 * <p>
 * 九宮格圖片的vh
 *
 * @see MomentsType
 */

@LayoutId(id = R.layout.moments_multi_image)
public class MultiImageMomentsVH extends CircleBaseViewHolder implements PhotoContents.OnItemClickListener {


    private PhotoContents imageContainer;
    private InnerContainerAdapter adapter;

    public MultiImageMomentsVH(View itemView, int viewType) {
        super(itemView, viewType);
    }


    @Override
    public void onFindView(@NonNull View rootView) {
        imageContainer = (PhotoContents) findView(imageContainer, R.id.circle_image_container);
        if (imageContainer.getmOnItemClickListener() == null) {
            imageContainer.setmOnItemClickListener(this);
        }
    }
    /**
    *点赞后执行到这里
    */
    @Override
    public void onBindDataToView(@NonNull MomentsInfo data, int position, int viewType) {
        if (adapter == null) {
            adapter = new InnerContainerAdapter(getContext(), data.getContent().getPics());
            imageContainer.setAdapter(adapter);
        } else {
            KLog.i("update image" + data.getAuthor().getNick() + "     :  " + data.getContent().getPics().size());
            adapter.updateData(data.getContent().getPics());
        }
    }

    @Override
    public void onItemClick(ImageView imageView, int i) {
        PhotoBrowseInfo info = PhotoBrowseInfo.create(adapter.getPhotoUrls(), imageContainer.getContentViewsDrawableRects(), i);
        ActivityLauncher.startToPhotoBrosweActivity((Activity) getContext(), info);
    }


    private class InnerContainerAdapter extends PhotoContentsBaseAdapter {


        private Context context;
        private List<PhotoInfo> datas;

        InnerContainerAdapter(Context context, List<PhotoInfo> datas) {
            this.context = context;
            this.datas = new ArrayList<>();
            this.datas.addAll(datas);
        }

        @Override
        public ImageView onCreateView(ImageView convertView, ViewGroup parent, int position) {
            if (convertView == null) {
                convertView = new ForceClickImageView(context);
                convertView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            return convertView;
        }
        //加载数据,照片
        @Override
        public void onBindData(int position, @NonNull ImageView convertView) {
            int width = convertView.getWidth();
            int height = convertView.getHeight();
            PhotoInfo img = datas.get(position);
            if (img.getWidth() != 0 && img.getHeight() != 0) {
                imageContainer.setSingleAspectRatio(img.getWidth() / img.getHeight());
            }
            String imageUrl = img.getUrl();
            if (width > 0 && height > 0) {
                imageUrl = BmobUrlUtil.getThumbImageUrl(imageUrl, width, height);
            } else {
                imageUrl = BmobUrlUtil.getThumbImageUrl(imageUrl, 25);
            }
            KLog.i("处理的url  >>>  " + imageUrl);
            ImageLoadMnanger.INSTANCE.loadImage(convertView, imageUrl);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        public void updateData(List<PhotoInfo> datas) {
            this.datas.clear();
            this.datas.addAll(datas);
            notifyDataChanged();
        }

        public List<String> getPhotoUrls() {
            List<String> result = new ArrayList<>();
            for (PhotoInfo data : datas) {
                result.add(data.getUrl());
            }
            return result;
        }

    }
}
