package net.hycollege.ljl.sharefood.circledemo.mvp.call;


import com.razerdp.github.com.common.entity.CommentInfo;

/**
 * Created by 大灯泡 on 2016/12/9.
 * <p>
 * 评论Callback
 */

public interface OnCommentChangeCallback {

    void onAddComment(CommentInfo response);

    void onDeleteComment(String commentid);

}
