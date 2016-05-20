package com.zhicai.byteera.activity.message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.traincamp.activity.WealthTaskActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;

public class SelectAddPopupWindow extends PopupWindow implements View.OnClickListener {

    private View mMenuView;
    LinearLayout group_chat, addFriends, dailyTasks, feedBack;
    private Context context;

    public SelectAddPopupWindow(final Activity context) {
        super(context);
        this.context = context;
        mMenuView =  LayoutInflater.from(context).inflate(R.layout.pop_add, null);

        group_chat = (LinearLayout) mMenuView.findViewById(R.id.linear_group_chat);
        addFriends = (LinearLayout) mMenuView.findViewById(R.id.linear_add_friends);
        dailyTasks = (LinearLayout) mMenuView.findViewById(R.id.linear_daily_tasks);
        feedBack = (LinearLayout) mMenuView.findViewById(R.id.linear_feedback);
        group_chat.setOnClickListener(this);
        addFriends.setOnClickListener(this);
        dailyTasks.setOnClickListener(this);
        feedBack.setOnClickListener(this);

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置按钮监听
        // 设置SelectAddPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectAddPopupWindow弹出窗体的宽
        this.setWidth(w / 3);
        // 设置SelectAddPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectAddPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectAddPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.mystyle);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 设置SelectAddPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.linear_group_chat: // 发起群聊
                ActivityUtil.startActivity((Activity) context, new Intent(context, GroupPickContactsActivity.class));
                dismiss();
                break;
            case R.id.linear_add_friends: // 添加好友
//                ActivityUtil.startActivity((Activity) context, new Intent(context, AddFriendsActivity.class));
                dismiss();
                break;
            case R.id.linear_daily_tasks: // 每日任务
                ActivityUtil.startActivity((Activity) context, new Intent(context, WealthTaskActivity.class));
                dismiss();
                break;
            case R.id.linear_feedback: // 意见反馈
//                ActivityUtil.startActivity((Activity) context, new Intent(context, FeedBackActivity.class));
                dismiss();
                break;
        }
    }
}
