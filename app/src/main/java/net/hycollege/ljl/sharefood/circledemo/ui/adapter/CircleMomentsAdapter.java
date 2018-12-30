package net.hycollege.ljl.sharefood.circledemo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.razerdp.github.com.common.entity.MomentsInfo;
import net.hycollege.ljl.sharefood.circledemo.mvp.presenter.MomentPresenter;
import net.hycollege.ljl.sharefood.circledemo.ui.viewholder.CircleBaseViewHolder;
import java.util.List;
import razerdp.github.com.ui.base.adapter.BaseMultiRecyclerViewAdapter;
import razerdp.github.com.ui.base.adapter.BaseRecyclerViewHolder;

/**
 * Created by 大灯泡 on 2016/11/1.
 * <p>
 * 朋友圈adapter
 */

public class CircleMomentsAdapter extends BaseMultiRecyclerViewAdapter<CircleMomentsAdapter, MomentsInfo> {
    private MomentPresenter momentPresenter;
    public CircleMomentsAdapter(@NonNull Context context, @NonNull List<MomentsInfo> datas, MomentPresenter presenter) {
        super(context, datas);
        this.momentPresenter = presenter;
    }

    @Override
    protected void onInitViewHolder(BaseRecyclerViewHolder holder) {
        if (holder instanceof CircleBaseViewHolder) {
            ((CircleBaseViewHolder) holder).setPresenter(momentPresenter);
        }
    }
}
