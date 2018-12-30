package net.hycollege.ljl.sharefood;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import net.hycollege.ljl.sharefood.fragment.FragmentFactory;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //创建底部选项栏
    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction transaction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化方法
        init();
    }

    private void init() {
        //从布局文件里面查找到控件对应的id
        bottomNavigationView = findViewById(R.id.bottomNavigation1);
        //设置点击选择监听
        bottomNavigationView.setOnNavigationItemSelectedListener(NavigationItemSelectedListener);
        //设置默认选择的位置
        //bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(1).getItemId());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //异步类,用来实现数据刷新
                showFragment(0);
            }
        }, 500);
    }

    /**
     * 显示需要显示的Fragment
     *
     * @param position
     */
    private void showFragment(int position) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentview, FragmentFactory.getFragmentIntance(position));
        transaction.commit();
    }

    //点击底部选项栏实体
    BottomNavigationView.OnNavigationItemSelectedListener NavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_menu:
                    showFragment(0);
                    return true;
                case R.id.item_control:
                    showFragment(1);
                    return true;
                case R.id.item_me:
                    showFragment(2);
                    return true;
            }
            return false;
        }
    };
}
