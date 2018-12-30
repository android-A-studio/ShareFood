package net.hycollege.ljl.sharefood.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import net.hycollege.ljl.sharefood.R;
import net.hycollege.ljl.sharefood.adapter.FoodCategoryListViewAdapter;
import net.hycollege.ljl.sharefood.adapter.GridViewAdapter;
import net.hycollege.ljl.sharefood.bean.FoodCategoryBean;
import net.hycollege.ljl.sharefood.fragment.fragmentme.LocalJsonResolutionUtils;

import razerdp.github.com.lib.base.BaseFragment;

/**
 * 菜谱页面类
 */
public class getFragmentMe extends BaseFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.fragmenthome;
    }

    @Override
    protected void onInitData() {

    }

    @Override
    protected void onInitView(View rootView) {

        getCookBookData();
    }
    ListView mListView;
    GridView mGridView;
    /**
     * 请求网络得到数据
     */
    private void getCookBookData() {
        mListView=findView(R.id.foodcategory_lv_left);
        mGridView=findView(R.id.foodcategory_gv_right);
        //得到本地json文本内容
        String fileName = "food.json";
        String foodJson = LocalJsonResolutionUtils.getJson(mActivity, fileName);
        //转换为对象
        FoodCategoryBean foodCategoryBean= LocalJsonResolutionUtils.JsonToObject(foodJson, FoodCategoryBean.class);
        //ListView适配器
         final FoodCategoryListViewAdapter listViewAdapter = new FoodCategoryListViewAdapter(mActivity, foodCategoryBean.getResult());
        mListView.setAdapter(listViewAdapter);
        //GridView适配器 默认加载第一个
        final GridViewAdapter gridViewAdapter = new GridViewAdapter(mActivity, foodCategoryBean.getResult().get(0).getList());
        mGridView.setAdapter(gridViewAdapter);
        //ListView item点击事件
        final FoodCategoryBean finalFoodCategoryBean = foodCategoryBean;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //改变GridView中需要显示的数据
                gridViewAdapter.setList(finalFoodCategoryBean.getResult().get(position).getList());
                //改变CurrentItem值，用于改变 字体颜色
                listViewAdapter.setCurrentItem(position);
                //通知改变
                listViewAdapter.notifyDataSetChanged();
            }
        });
    }

}
