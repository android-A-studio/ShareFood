package net.hycollege.ljl.sharefood.circledemo.ui.viewholder;
import android.support.annotation.NonNull;
import android.view.View;
import com.razerdp.github.com.common.entity.MomentsInfo;
import net.hycollege.ljl.sharefood.R;
import razerdp.github.com.ui.base.adapter.LayoutId;


/**
 * Created by 大灯泡 on 2016/11/3.
 *
 * 衹有文字的vh
 *
 * @see MomentsType
 */

@LayoutId(id =  R.layout.moments_only_text)
public class TextOnlyMomentsVH extends CircleBaseViewHolder {

    public TextOnlyMomentsVH(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void onFindView(@NonNull View rootView) {

    }

    @Override
    public void onBindDataToView(@NonNull MomentsInfo data, int position, int viewType) {

    }
}
