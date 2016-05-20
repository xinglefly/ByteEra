package com.zhicai.byteera.activity.myhome.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.OrganizationHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.UserHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.myhome.adapter.MyFriendAdapter;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.HeadViewMain.LeftImgClickListner;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;


@SuppressWarnings("unused")
public class MyFansActivity extends BaseActivity {
    @Bind(R.id.list_view) ListView mListView;
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.rl_empty) RelativeLayout mRlEmpty;
    private String userId;
    private MyFriendAdapter mAdapter;
    private List<UserInfoEntity> userInfoEntities;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.my_fans_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {
        userId = PreferenceUtils.getInstance(this).getUserId();
        mAdapter = new MyFriendAdapter(this);
        mListView.setAdapter(mAdapter);
        getDataFromNet();
    }

    private void getDataFromNet() {
        UserAttribute.GetUserRelationUserReq sec = UserAttribute.GetUserRelationUserReq.newBuilder().setUserId(userId)
                .setRelation(UserAttribute.GetUserRelationUserReq.Relation.Fans).build();
        TiangongClient.instance().send("chronos", "get_user_relation_user", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final UserAttribute.GetUserRelationUserResponse response = UserAttribute.GetUserRelationUserResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getUserCount() == 0) {
                                mRlEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mRlEmpty.setVisibility(View.GONE);
                                userInfoEntities = ModelParseUtil.UserInfoEntityParse(response.getUserList());
                                Collections.sort(userInfoEntities);
                                MyApp.getMainThreadHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.updateListView(userInfoEntities);
                                    }
                                });
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });

    }

    @OnItemClick(R.id.list_view)
    public void itemClickListener(int position){
        UserInfoEntity userInfoEntity = userInfoEntities.get(position);
        if (userInfoEntity.getUserType() == 1) {
            Intent intent = new Intent(baseContext, UserHomeActivity.class);
            intent.putExtra("other_user_id", userInfoEntity.getUserId());
            ActivityUtil.startActivity(baseContext, intent);
        } else if (userInfoEntity.getUserType() == 2) {
            Intent intent = new Intent(baseContext, OrganizationHomeActivity.class);
            intent.putExtra("other_user_id", userInfoEntity.getUserId());
            ActivityUtil.startActivity(baseContext, intent);
        }
    }
}