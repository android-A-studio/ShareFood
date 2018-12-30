package net.hycollege.ljl.sharefood.circledemo.ui.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import com.razerdp.github.com.common.entity.MomentsInfo;

import net.hycollege.ljl.sharefood.R;

import razerdp.github.com.ui.base.adapter.LayoutId;


/**
 * Created by 大灯泡 on 2016/11/3.
 * <p>
 * 網頁vh
 * @see
 */

@LayoutId(id = R.layout.moments_web)
public class WebMomentsVH extends CircleBaseViewHolder {


    public WebMomentsVH(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void onFindView(@NonNull View rootView) {

    }

    @Override
    public void onBindDataToView(@NonNull MomentsInfo data, int position, int viewType) {

    }
}
