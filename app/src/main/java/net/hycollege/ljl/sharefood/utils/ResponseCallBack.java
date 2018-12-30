package net.hycollege.ljl.sharefood.utils;


import net.hycollege.ljl.sharefood.share.AppShare;

import retrofit2.Call;
import retrofit2.Callback;


public abstract class ResponseCallBack<T> implements Callback<T> {
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        SimpleToast.show(AppShare.getInstance(), "暂无数据，敬请期待");
        DialogUtils.dismiss();
    }
}
