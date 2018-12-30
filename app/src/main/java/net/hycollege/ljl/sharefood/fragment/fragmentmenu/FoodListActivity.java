package net.hycollege.ljl.sharefood.fragment.fragmentmenu;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.hycollege.ljl.sharefood.FoodBaseActivity;
import net.hycollege.ljl.sharefood.R;
import net.hycollege.ljl.sharefood.View.Menu.FoodDetailActivity;
import net.hycollege.ljl.sharefood.adapter.FoodListRecyclerAdapter;
import net.hycollege.ljl.sharefood.bean.FoodDetailBean;
import net.hycollege.ljl.sharefood.interfaces.BeanCallBack;
import net.hycollege.ljl.sharefood.utils.CookbookModel;
import net.hycollege.ljl.sharefood.utils.DialogUtils;
import net.hycollege.ljl.sharefood.utils.NetUtils;
import net.hycollege.ljl.sharefood.widget.RecyclerRefreshLayout;

public class FoodListActivity extends FoodBaseActivity implements RecyclerRefreshLayout.SuperRefreshLayoutListener {

    LinearLayout mLinearLayout;
    TextView mFoodlistTitle;
    RecyclerView mRecyclerview;
    RecyclerRefreshLayout mRefreshLayout;

    //菜谱相关网络请求
    CookbookModel cookbookModel;
    FoodListRecyclerAdapter adapter;
    ImageView foodlist_back;
    private static int cid;
    private int start;
    @Override
    protected void initView() {
        mLinearLayout=findViewById(R.id.ll_food_list);
        mFoodlistTitle=findViewById(R.id.foodlist_title);
        mRecyclerview=findViewById(R.id.foodlist_recyclerview);
        mRefreshLayout= findViewById(R.id.recyclerrefreshlayout);
        foodlist_back= findViewById(R.id.foodlist_back);
        foodlist_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cookbookModel = CookbookModel.getInstance();
        //得到主页界面传来的菜谱 菜系信息
        String[] cuisine = (String[]) getIntent().getCharSequenceArrayExtra("FOOD");
        //获得菜系名字
        String name = cuisine[0];
        //获得菜系ID
        cid = Integer.parseInt(cuisine[1]);
        //标题设置菜系名字
        mFoodlistTitle.setText(name);
        //设置可加载更多
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mRefreshLayout.setCanLoadMore(true);
        mRefreshLayout.setEnabled(false);
        //设置RecyclerView的模式
        mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //请求数据
        if (NetUtils.isConnected(this)) {
            requestData(cid, 0, 30);
        } else {
            mLinearLayout.setBackgroundResource(R.mipmap.no_net);
            showToast("网络未连接");
        }
    }

    /**
     * 请求数据
     * @param classid
     * @param start
     * @param num
     */
    private void requestData(int classid, int start, int num) {
        //进度条
        DialogUtils.showProgressDialog(this);
        cookbookModel.cookBookById(classid, start, num, new BeanCallBack<FoodDetailBean>() {
            @Override
            public void onSucceed(final FoodDetailBean foodDetailBean) {
                adapter = new FoodListRecyclerAdapter(FoodListActivity.this,foodDetailBean.getResult().getList());
                //设置适配器
                mRecyclerview.setAdapter(adapter);
                DialogUtils.dismiss();
                //设置RecyclerView子Item的点击事件
                adapter.setOnItemClickListener(new FoodListRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick() {
                        //跳转到菜谱详情
                        startActivity(FoodDetailActivity.class);
                    }
                });
            }

            @Override
            public void onError(String msg) {
                showToast(msg);
                DialogUtils.dismiss();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_foodlist;
    }


    @Override
    public void onRefreshing() {

    }

    @Override
    public void onLoadMore() {
        if (NetUtils.isConnected(this)) {
            //进度条
            DialogUtils.showProgressDialog(this);
            start = start + 30;
            cookbookModel.cookBookById(cid, start, 30, new BeanCallBack<FoodDetailBean>() {
                @Override
                public void onSucceed(FoodDetailBean foodDetailBean) {
                    adapter.addMoreItem(foodDetailBean.getResult().getList());
                    mRefreshLayout.onComplete();
                    DialogUtils.dismiss();
                }

                @Override
                public void onError(String msg) {
                    showToast(msg);
                    DialogUtils.dismiss();
                }
            });
        } else {
            showToast("网络未连接");
        }
    }
}
