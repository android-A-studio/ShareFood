package net.hycollege.ljl.sharefood.interfaces;

/**
 * 转换Bean的回调接口
 */

public interface BeanCallBack<T> {

    void onSucceed(T t);

    void onError(String msg);
}
