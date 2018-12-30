package net.hycollege.ljl.sharefood.circledemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.razerdp.github.com.common.MomentsType;
import com.razerdp.github.com.common.entity.CommentInfo;
import com.razerdp.github.com.common.entity.LikesInfo;
import com.razerdp.github.com.common.entity.MomentsInfo;
import com.razerdp.github.com.common.entity.UserInfo;
import com.razerdp.github.com.common.manager.LocalHostManager;
import com.razerdp.github.com.common.request.MomentsRequest;
import com.socks.library.KLog;
import net.hycollege.ljl.sharefood.R;
import net.hycollege.ljl.sharefood.circledemo.mvp.presenter.MomentPresenter;
import net.hycollege.ljl.sharefood.circledemo.mvp.view.IMomentView;
import net.hycollege.ljl.sharefood.circledemo.ui.adapter.CircleMomentsAdapter;
import net.hycollege.ljl.sharefood.circledemo.ui.viewholder.EmptyMomentsVH;
import net.hycollege.ljl.sharefood.circledemo.ui.viewholder.MultiImageMomentsVH;
import net.hycollege.ljl.sharefood.circledemo.ui.viewholder.TextOnlyMomentsVH;
import net.hycollege.ljl.sharefood.circledemo.ui.viewholder.WebMomentsVH;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.exception.BmobException;
import razerdp.github.com.lib.manager.KeyboardControlMnanager;
import razerdp.github.com.lib.network.base.OnResponseListener;
import razerdp.github.com.lib.utils.ToolUtil;
import razerdp.github.com.ui.base.BaseTitleBarFragment;
import razerdp.github.com.ui.imageloader.ImageLoadMnanger;
import razerdp.github.com.ui.widget.commentwidget.CommentBox;
import razerdp.github.com.ui.widget.commentwidget.CommentWidget;
import razerdp.github.com.ui.widget.common.TitleBar;
import razerdp.github.com.ui.widget.pullrecyclerview.CircleRecyclerView;
import razerdp.github.com.ui.widget.pullrecyclerview.interfaces.OnRefreshListener2;

public class FriendCircleFragmentDemo extends BaseTitleBarFragment implements OnRefreshListener2, IMomentView, CircleRecyclerView.OnPreDispatchTouchListener {
    //request
    private MomentsRequest momentsRequest;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
    }
    @Override
    protected void onInitData() {

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onInitView(View rootView) {
        //定义盆友圈列表
        momentsInfoList = new ArrayList<>();
        //朋友圈时间线请求==重点
        momentsRequest = new MomentsRequest();
        //初始化评论和键盘
        initKeyboardHeightObserver();
        initView(rootView);
    }
    private HostViewHolder hostViewHolder;
    private CircleViewHelper mViewHelper;
    private CircleRecyclerView circleRecyclerView;
    private RelativeLayout mTipsLayout;
    private MomentPresenter presenter;
    private CircleMomentsAdapter adapter;
    private TextView mServiceTipsView;
    private CommentBox commentBox;
    private ImageView mCloseImageView;
    //列表
    private List<MomentsInfo> momentsInfoList;
    private void initView(View rootView) {
        if (mViewHelper == null) {
            mViewHelper = new CircleViewHelper(getActivity());
        }
        setTitle("美食圈");
        setTitleMode(TitleBar.MODE_BOTH);
        setTitleRightIcon(R.drawable.ic_camera);
        setTitleLeftText("发现");
        setTitleLeftIcon(R.drawable.back_left);
        presenter = new MomentPresenter(this);
        //实例化 消息通知,假如有人点赞、照片墙
        hostViewHolder = new HostViewHolder(getContext());
        circleRecyclerView = findView(R.id.recycler);
        circleRecyclerView.setOnRefreshListener(this);
        circleRecyclerView.setOnPreDispatchTouchListener(this);
        //加载照片墙
        circleRecyclerView.addHeaderView(hostViewHolder.getView());

        mTipsLayout = findView(R.id.tips_layout);
        mServiceTipsView = findView(R.id.service_tips);
        mCloseImageView = findView(R.id.iv_close);

        commentBox = findView(R.id.widget_comment);
        //服务器消息检查，非项目所需↑
       // commentBox.setOnCommentSendClickListener(onCommentSendClickListener);
        //盆友圈列表
        adapter = new CircleMomentsAdapter(getContext(), momentsInfoList, presenter);
        adapter.addViewHolder(EmptyMomentsVH.class, MomentsType.EMPTY_CONTENT)
                .addViewHolder(MultiImageMomentsVH.class, MomentsType.MULTI_IMAGES)
                .addViewHolder(TextOnlyMomentsVH.class, MomentsType.TEXT_ONLY)
                .addViewHolder(WebMomentsVH.class, MomentsType.WEB);
        circleRecyclerView.setAdapter(adapter);
        circleRecyclerView.autoRefresh();

    }
    /**
     *消息通知,假如有人点赞
     *头部,照片墙之类
     */
    private static class HostViewHolder {
        private View rootView;
        private ImageView friend_wall_pic;
        private ImageView friend_avatar;
        private ImageView message_avatar;
        private TextView message_detail;
        private TextView hostid;

        public HostViewHolder(Context context) {
            this.rootView = LayoutInflater.from(context).inflate(R.layout.circle_host_header, null);
            this.hostid = (TextView) rootView.findViewById(R.id.host_id);
            this.friend_wall_pic = (ImageView) rootView.findViewById(R.id.friend_wall_pic);
            this.friend_avatar = (ImageView) rootView.findViewById(R.id.friend_avatar);
            this.message_avatar = (ImageView) rootView.findViewById(R.id.message_avatar);
            this.message_detail = (TextView) rootView.findViewById(R.id.message_detail);
        }
        //载入数据,用户签名
        public void loadHostData(UserInfo hostInfo) {
            if (hostInfo == null) return;
            ImageLoadMnanger.INSTANCE.loadImage(friend_wall_pic, hostInfo.getCover());
            ImageLoadMnanger.INSTANCE.loadImage(friend_avatar, hostInfo.getAvatar());
            hostid.setText(String.format("您的测试ID为: %s\n您的测试用户名为: %s", hostInfo.getUserid(), hostInfo.getNick()));
        }
        //返回基础视图
        public View getView() {
            return rootView;
        }

    }
    private void initKeyboardHeightObserver() {
        //观察键盘弹出与消退
        KeyboardControlMnanager.observerKeyboardVisibleChange(getActivity(), new KeyboardControlMnanager.OnKeyboardStateChangeListener() {
            View anchorView;

            @Override
            public void onKeyboardChange(int keyboardHeight, boolean isVisible) {
                int commentType = commentBox.getCommentType();
                if (isVisible) {
                    //定位评论框到view
                    anchorView = mViewHelper.alignCommentBoxToView(circleRecyclerView, commentBox, commentType);
                } else {
                    //定位到底部
                    commentBox.dismissCommentBox(false);
                    mViewHelper.alignCommentBoxToViewWhenDismiss(circleRecyclerView, commentBox, commentType, anchorView);
                }
            }
        });
    }
    @Override
    public boolean onPreTouch(MotionEvent ev) {
        return false;
    }
    private static final int REQUEST_REFRESH = 0x10;
    private static final int REQUEST_LOADMORE = 0x11;
    //下拉刷新
    @Override
    public void onRefresh() {
        momentsRequest.setOnResponseListener(momentsRequestCallBack);
        momentsRequest.setRequestType(REQUEST_REFRESH);
        momentsRequest.setCurPage(0);
        momentsRequest.execute();
    }
    //request
    //List<MomentsInfo>列表,==重点
    //==============================================
    private OnResponseListener.SimpleResponseListener<List<MomentsInfo>> momentsRequestCallBack = new OnResponseListener.SimpleResponseListener<List<MomentsInfo>>() {
        @Override
        public void onSuccess(List<MomentsInfo> response, int requestType) {
            circleRecyclerView.compelete();
            switch (requestType) {
                case REQUEST_REFRESH:
                    if (!ToolUtil.isListEmpty(response)) {
                        KLog.i("firstMomentid", "第一条动态ID   >>>   " + response.get(0).getMomentid());
                        hostViewHolder.loadHostData(LocalHostManager.INSTANCE.getLocalHostUser());
                        adapter.updateData(response);
                    }
                    break;
                case REQUEST_LOADMORE:
                    adapter.addMore(response);
                    break;
            }
        }

        @Override
        public void onError(BmobException e, int requestType) {
            super.onError(e, requestType);
            circleRecyclerView.compelete();
        }
    };
    //加载更多
    @Override
    public void onLoadMore() {
        momentsRequest.setOnResponseListener(momentsRequestCallBack);
        momentsRequest.setRequestType(REQUEST_LOADMORE);
        momentsRequest.execute();
    }


    @Override
    public void onTitleDoubleClick() {
        super.onTitleDoubleClick();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onLikeChange(int itemPos, List<LikesInfo> likeUserList) {

    }

    @Override
    public void onCommentChange(int itemPos, List<CommentInfo> commentInfoList) {

    }

    @Override
    public void showCommentBox(@Nullable View viewHolderRootView, int itemPos, String momentid, CommentWidget commentWidget) {

    }

    @Override
    public void onDeleteMomentsInfo(@NonNull MomentsInfo momentsInfo) {

    }
}
