package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.view.ShowGroupWindow;
import com.zhicai.byteera.activity.community.eventbus.GroupEvent;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.dynamic.DynamicGroupV2;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class GroupHomeSecondActivity extends BaseActivity{

    @Bind(R.id.rl_group_second) RelativeLayout rlRroupSecond;
    @Bind(R.id.title_view) HeadViewMain mTitle;
    @Bind(R.id.tv_name) TextView tvName;
    @Bind(R.id.tv_member) TextView tvMember;
    @Bind(R.id.tv_description) TextView tvDes;

    private ShowGroupWindow pop;
    private GroupEntity groupEntity;
    private boolean isAttetion;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.group_second_page);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        groupEntity = (GroupEntity) getIntent().getSerializableExtra("group");
        isAttetion = getIntent().getBooleanExtra("isAttetion", false);
        tvName.setText(groupEntity.getName());
        tvMember.setText(groupEntity.getPeopleCnt()+"人");
        tvDes.setText(groupEntity.getDescription());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.isSHowRightImg(isAttetion);

    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });


        mTitle.setRightImgClickListener(new HeadViewMain.RightImgClickListner() {
            @Override
            public void onRightImgClick() {
                View rootView = getWindow().getDecorView();
                rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                pop = new ShowGroupWindow(baseContext, itemsOnclick);
                pop.showAtLocation(rlRroupSecond, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });



    }


    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pop.dismiss();
            switch (v.getId()){
                case R.id.tv_cancle_joined:
                    toLeaved();
                break;
            }
        }
    };

    /**取消关注小组**/
    public void toLeaved() {
        final DynamicGroupV2.LicaiquanGroupLeaveReq req = DynamicGroupV2.LicaiquanGroupLeaveReq.newBuilder().setMyUserId(MyApp.getInstance().getUserId()).setLicaiquanGroupId(groupEntity.getGroupId()).build();
        TiangongClient.instance().send("chronos", "licaiquan_group_leave", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final DynamicGroupV2.LicaiquanGroupLeaveResponse response = DynamicGroupV2.LicaiquanGroupLeaveResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTitle.isSHowRightImg(false);
                                ToastUtil.showToastText("取消关注成功!");
                                EventBus.getDefault().post(new GroupEvent(false));
                            }
                        });
                    }else{
                        ToastUtil.showToastText("取消关注失败!");
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
