package net.hycollege.ljl.sharefood.View.Menu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import net.hycollege.ljl.sharefood.FoodBaseActivity;
import net.hycollege.ljl.sharefood.MainActivity;
import net.hycollege.ljl.sharefood.R;
import net.hycollege.ljl.sharefood.adapter.FoodMaterialListAdapter;
import net.hycollege.ljl.sharefood.adapter.StepListViewAdapter;
import net.hycollege.ljl.sharefood.bean.Config;
import net.hycollege.ljl.sharefood.bean.FoodCollection;
import net.hycollege.ljl.sharefood.bean.FoodDetailBean;
import net.hycollege.ljl.sharefood.share.AppShare;
import net.hycollege.ljl.sharefood.utils.CookbookModel;
import net.hycollege.ljl.sharefood.utils.L;
import net.hycollege.ljl.sharefood.utils.StringUtils;
import net.hycollege.ljl.sharefood.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.List;


public class FoodDetailActivity extends FoodBaseActivity implements View.OnClickListener {
    //菜谱相关网络请求
    CookbookModel cookbookModel;
    //@BindView()
    ImageView mIvImage,fooddetail_back,fooddetail_image;
    //@BindView(R.id.fooddetail_title)
    TextView mFooddetailTitle,fooddetail_gohome;
   // @BindView(R.id.step_listview)
    NoScrollListView mFooddetailListview;
    //@BindView(R.id.fooddetail_tv_describe)
    TextView mFooddetailTvDescribe;
   // @BindView(R.id.fooddetail_tv_tags)
    TextView mFooddetailTvTags;
   // @BindView(R.id.ingredients_listview)
    NoScrollListView mIngredientsListview;
   // @BindView(R.id.burden_listview)
    NoScrollListView mBurdenListview;
   // @BindView(R.id.fooddetail_fab)
    FloatingActionButton mFooddetailFab,fooddetail_fab;
    //图片地址
    private String imageUrl;
    //暂时模拟 食谱是否收藏
    private boolean isCollect = false;
    private List<FoodCollection> collections;
    //食谱收藏 的数据库操作
// ==   private FoodCollectDao foodCollectDao;
    private FoodCollection mCollection;
    private FoodDetailBean.ResultBean.ListBean mListBean;

    @Override
    protected void initView() {
        mIvImage=findViewById(R.id.fooddetail_image);
        mFooddetailTitle=findViewById(R.id.fooddetail_title);
        mFooddetailListview=findViewById(R.id.step_listview);
        mFooddetailTvDescribe=findViewById(R.id.fooddetail_tv_describe);
        mFooddetailTvTags=findViewById(R.id.fooddetail_tv_tags);
        mIngredientsListview=findViewById(R.id.ingredients_listview);
        mBurdenListview=findViewById(R.id.burden_listview);
        mFooddetailFab=findViewById(R.id.fooddetail_fab);
        fooddetail_back=findViewById(R.id.fooddetail_back);
        fooddetail_image=findViewById(R.id.fooddetail_image);
        //回首页
        fooddetail_gohome=findViewById(R.id.fooddetail_gohome);
        cookbookModel = CookbookModel.getInstance();
        mListBean = AppShare.getInstance().getListBean();
// ==       foodCollectDao = new FoodCollectDao(this);
        mCollection = new FoodCollection();
        //存储数据的集合
// ==       collections = AppShare.getInstance().getFoodCollection();
        L.e(TAG,"--------全局变量--"+ mListBean);
       /* for (FoodCollection collection : collections) {
            if (mListBean.getId().equals(collection.getId())) {
                isCollect = true;
            }
        }*/
        //如果已经搜藏
        /*if (isCollect) {
            mFooddetailFab.setImageResource(R.mipmap.ic_collection);
        } else {
            mFooddetailFab.setImageResource(R.mipmap.ic_collection_no);
        }*/
        mFooddetailFab.setImageResource(R.mipmap.ic_collection_no);
        setDataToView();
        initEvent();
    }
    //设置点击监听
    private void initEvent() {
        fooddetail_back.setOnClickListener(this);
        fooddetail_gohome.setOnClickListener(this);
        fooddetail_image.setOnClickListener(this);
        fooddetail_back.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fooddetail;
    }

    /**
     * 将数据设置到View上
     */
    private void setDataToView() {

        //图片地址
        imageUrl = mListBean.getPic();
        //设置标题名字
        String foodName = mListBean.getName();

        //设置用于收藏所需要的食物信息 数据库存储
        mCollection.setId(mListBean.getId());
        mCollection.setImage(imageUrl);
        mCollection.setName(foodName);

        //设置图片
        /*Glide.with(FoodDetailActivity.this)
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .placeholder(R.color.colorAccent)
                .into(mIvImage);*/
        Glide.with(FoodDetailActivity.this).load(imageUrl).into(mIvImage);
        mFooddetailTitle.setText(foodName);
        //设置菜品描述内容
        mFooddetailTvDescribe.setText(StringUtils.removeOtherStr(mListBean.getContent()));
        //设置菜品标签
        mFooddetailTvTags.setText(mListBean.getTag());
        //设置菜品主材料明细内容
        //type 材料类型 0辅料 1主料
        List<FoodDetailBean.ResultBean.ListBean.MaterialBean> mainMaterial = getFoodMaterialData("1");
        FoodMaterialListAdapter materialAdapter = new FoodMaterialListAdapter(FoodDetailActivity.this, mainMaterial);
        mIngredientsListview.setAdapter(materialAdapter);
        //设置菜品辅材料明细内容
        List<FoodDetailBean.ResultBean.ListBean.MaterialBean> relishMaterial = getFoodMaterialData("0");
        materialAdapter = new FoodMaterialListAdapter(FoodDetailActivity.this, relishMaterial);
        mBurdenListview.setAdapter(materialAdapter);
        //设置菜品制作步骤
        List<FoodDetailBean.ResultBean.ListBean.ProcessBean> list = mListBean.getProcess();
        StepListViewAdapter adapter = new StepListViewAdapter(FoodDetailActivity.this, list);
        mFooddetailListview.setAdapter(adapter);

        //ListView设置子项点击事件
        mFooddetailListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(FoodDetailActivity.this, StepActivity.class);
                //传递当前点击的步骤 位置
                intent.putExtra("POSITION", String.valueOf(position));
                startActivity(intent);*/
            }
        });
    }


    /**
     * 得到材料的数据
     *
     * @param type 主料 还是辅料
     * @return
     */
    private List<FoodDetailBean.ResultBean.ListBean.MaterialBean> getFoodMaterialData(String type) {
        List<FoodDetailBean.ResultBean.ListBean.MaterialBean> list = new ArrayList<>();
        for (FoodDetailBean.ResultBean.ListBean.MaterialBean materialBean : mListBean.getMaterial()) {
            if (materialBean.getType().equals(type)) {
                list.add(materialBean);
            }
        }
        return list;
    }

    /**
     * 菜谱收藏的事件处理
     */
    private void collectFood() {
        //如果当前没收藏 则收藏，如收藏了，则删除数据库数据
        if (!isCollect) {
            showToast("收藏成功");
            //存入数据
       //==     foodCollectDao.addFoodCollection(mCollection);
            Log.e(TAG,"--------------搜藏了" + mCollection.getId() + mCollection.getName());
            //同步全局共享变量
        //==    AppShare.getInstance().setFoodCollections(foodCollectDao.getFoodCollection());
            mFooddetailFab.setImageResource(R.mipmap.ic_collection);
        } else {
            //删除数据
            showToast("取消收藏");
         //==   foodCollectDao.deleteFodCollection(mListBean.getId());
            //同步全局共享变量
        //==    AppShare.getInstance().setFoodCollections(foodCollectDao.getFoodCollection());
            mFooddetailFab.setImageResource(R.mipmap.ic_collection_no);
        }
        isCollect = !isCollect;
        //发送广播，通知数据已改变
        dataChange();
    }

    /**
     * 数据改变时的处理
     */
    private void dataChange() {
        //发送广播，通知数据已改变
        Intent intent = new Intent();
        intent.setAction(Config.BroadcastAction);
        sendBroadcast(intent);
        Log.e(TAG,"-------------------发送广播没？");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fooddetail_back:
                //返回上一页
                onBackPressed();
                break;
            case R.id.fooddetail_gohome:
                //跳转主界面
//                startActivity(MainActivity.class);
                break;
            case R.id.fooddetail_image:
                //跳转显示图片的界面
                /*Intent intent = new Intent(FoodDetailActivity.this, ImageShowActivity.class);
                intent.putExtra("FOOD_IMAGE", imageUrl);
                startActivity(intent);*/
                break;
            //收藏按钮
            case R.id.fooddetail_fab:
                collectFood();
                break;
        }
    }
}
