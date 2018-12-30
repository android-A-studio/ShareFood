package net.hycollege.ljl.sharefood.share;

import android.app.Activity;
import android.app.Application;

import com.razerdp.github.com.common.manager.LocalHostManager;

import net.hycollege.ljl.sharefood.bean.FoodDetailBean;
import net.hycollege.ljl.sharefood.circledemo.config.Define;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import razerdp.github.com.lib.api.AppContext;
import razerdp.github.com.lib.manager.localphoto.LocalPhotoManager;

/**
 * Created by 大灯泡 on 2016/10/26.
 * <p>
 * app
 * 注册bmob类
 */

public class AppShare extends Application {
    private static AppShare instance;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.initARouter();
        initBmob();
        initLocalHostInfo();
        LocalPhotoManager.INSTANCE.registerContentObserver(null);
        instance = this;
        //初始化第三方SDK
        //MOB短信SDK初始化
    }

    private void initBmob() {
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId(Define.BMOB_APPID)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(15)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(1800)
                .build();
        //注册bmob类
        Bmob.initialize(config);
    }

    private void initLocalHostInfo() {
        //本地用户管理
        LocalHostManager.INSTANCE.init();
    }
    /****************************************全局共享的数据*****************************************/
    // //自定义的日历对象
//    private LocalCalendar mLocalCalendar;
//
//    public LocalCalendar getLocalDate() {
//        return mLocalCalendar;
//    }
//
//    public void setLocalDate(LocalCalendar localDate) {
//        mLocalCalendar = localDate;
//    }
//
//    //食谱收藏 集合
//    private List<FoodCollection> foodCollection;
//
//    public List<FoodCollection> getFoodCollection() {
//        return foodCollection;
//    }
//
//    public void setFoodCollections(List<FoodCollection> foodCollection) {
//        this.foodCollection = foodCollection;
//    }

    /**
     * 得到AppShare对象
     *
     * @return
     */
    public static AppShare getInstance() {
        return instance;
    }

    /**
     * 存储Activity的集合
     */
    private static List<Activity> activities = new ArrayList<>();

    /**
     * 添加Activity进集合
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 将Activity移除集合
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 销毁所有的Actiityv
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    //一份食物的详细介绍
    private FoodDetailBean.ResultBean.ListBean mListBean;

    public FoodDetailBean.ResultBean.ListBean getListBean() {
        return mListBean;
    }

    public void setListBean(FoodDetailBean.ResultBean.ListBean listBean) {
        mListBean = listBean;
    }

}
