package net.hycollege.ljl.sharefood.fragment;

import android.app.Fragment;
import android.util.SparseArray;

import net.hycollege.ljl.sharefood.R;
import net.hycollege.ljl.sharefood.circledemo.FriendCircleFragmentDemo;

import razerdp.github.com.lib.base.BaseFragment;
import razerdp.github.com.ui.base.BaseTitleBarFragment;

/**
 * 生产Fragment的工厂
 *
 * @author GaoWeiJing
 */
public class FragmentFactory {

    public static final int CircleFragment = 0;
    public static final int SetFragment = 2;
    public static final int MyMenuFragment = 1;

    public static final int AutoStartFragment = 6;
    public static int curFrgment = AutoStartFragment;
    // 提高效率
    private static SparseArray<BaseFragment> fragmentMap = new SparseArray<>();
    /**
     * 在Fragment工厂中获取一个Fragment
     *
     * @param position
     */
    public static BaseFragment getFragmentIntance(int position) {
        curFrgment = position;
        BaseFragment fragment = null;
        BaseFragment newFragment = fragmentMap.get(position);
        if (newFragment != null) {
            fragment = newFragment;
            return fragment;
        }
        switch (position) {
            case CircleFragment:
                fragment=new FriendCircleFragmentDemo();
                break;
            case MyMenuFragment:
                //显示设置页面
                fragment=new getFragmentMe();
                break;
            case SetFragment:
                fragment= new getFragmentMenu();
                break;
        }
        fragmentMap.put(position, fragment);
        return fragment;
    }

}
