package net.hycollege.ljl.sharefood.utils;

import net.hycollege.ljl.sharefood.bean.Config;
import net.hycollege.ljl.sharefood.bean.FoodDetailBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//食物网络请求
public interface ICookbookService {

    //通过分类ID搜索菜谱
    @POST(Config.BYCLASSID)
    @FormUrlEncoded
    Call<FoodDetailBean> cookBookById(@Field("classid") int classid
            , @Field("start") int start
            , @Field("num") int num
            , @Field("appkey") String appkey);

    //按名字搜索菜谱
    @POST(Config.SEARCH)
    @FormUrlEncoded
    Call<FoodDetailBean> cookBookSearch(@Field("keyword") String keyword
            , @Field("num") int num
            , @Field("appkey") String appkey);
    //按食物ID搜索菜谱
    @POST(Config.DETAIL)
    @FormUrlEncoded
    Call<String> cookBookDetail(@Field("id") int id, @Field("appkey") String appkey);
}
