package net.hycollege.ljl.sharefood.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import net.hycollege.ljl.sharefood.R;
import net.hycollege.ljl.sharefood.View.PopMenu.PopMenu;
import net.hycollege.ljl.sharefood.View.PopMenu.PopMenuItem;
import net.hycollege.ljl.sharefood.View.PopMenu.PopMenuItemListener;
import net.hycollege.ljl.sharefood.adapter.ExpandableItemAdapter;
import net.hycollege.ljl.sharefood.entity.Level0Item;
import net.hycollege.ljl.sharefood.entity.Level1Item;
import net.hycollege.ljl.sharefood.entity.Person;

import java.util.ArrayList;
import java.util.Random;

import razerdp.github.com.lib.base.BaseFragment;
import razerdp.github.com.ui.base.BaseTitleBarFragment;

/**
 * 菜谱分享盆友圈
 */
public class getFragmentMenu extends BaseFragment {

     GridLayoutManager manager;

    private PopMenu mPopMenu;

    ArrayList<MultiItemEntity> list;
    ExpandableItemAdapter adapter;
    RecyclerView mRecyclerView;

    //实现MultiItemEntity列表
    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 3;
        int lv1Count = 3;
        int personCount = 6;
        String[] titleList = { "收藏美味菜式", "收到爱心点赞","我分享的菜式"};
        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce", "Fruce"};
        Random random = new Random();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item(titleList[i],i+"");
            for (int j = 0; j < lv1Count; j++) {
                Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
                for (int k = 0; k < personCount; k++) {
                    lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
//        res.add(new  Level0Item("This is " + lv0Count + "th item in Level 0", "subtitle of " + lv0Count));
        return res;
    }

    @Override
    public int getLayoutResId() {
        return  R.layout.fragmentmenu;
    }

    @Override
    protected void onInitData() {
        manager = new GridLayoutManager(getActivity(), 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == ExpandableItemAdapter.TYPE_PERSON ? 1 : manager.getSpanCount();
            }
        });
        list = generateData();
        adapter = new ExpandableItemAdapter(list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(manager);
        adapter.expand(2);
    }

    @Override
    protected void onInitView(View rootView) {

        mRecyclerView = findView(R.id.rv);
        mPopMenu = new PopMenu.Builder().attachToActivity(getActivity())
                .addMenuItem(new PopMenuItem("文字", getResources().getDrawable(R.mipmap.tabbar_compose_idea)))
                .addMenuItem(new PopMenuItem("照片/视频", getResources().getDrawable(R.mipmap.tabbar_compose_photo)))
                .addMenuItem(new PopMenuItem("头条文章", getResources().getDrawable(R.mipmap.tabbar_compose_headlines)))
                .addMenuItem(new PopMenuItem("签到", getResources().getDrawable(R.mipmap.tabbar_compose_lbs)))
                .addMenuItem(new PopMenuItem("点评", getResources().getDrawable(R.mipmap.tabbar_compose_review)))
                .addMenuItem(new PopMenuItem("更多", getResources().getDrawable(R.mipmap.tabbar_compose_more)))
                .setOnItemClickListener(new PopMenuItemListener() {
                    @Override
                    public void onItemClick(PopMenu popMenu, int position) {
                        Toast.makeText(getActivity(), "你点击了第" + position + "个位置", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
                findView(R.id.buttonFloat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPopMenu.isShowing()) {
                    mPopMenu.show();
                }
            }
        });
    }
}
