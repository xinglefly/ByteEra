package com.zhicai.byteera.activity.community.dynamic.presenter;

import android.app.Activity;
import android.content.Intent;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.dynamic.activity.UserHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.UserFansActivityIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.List;

/** Created by bing on 2015/6/30. */
public class UserFansActivityPre {
    private Activity mContext;
    private UserFansActivityIV userFansActivityIV;
    private String userId;

    public UserFansActivityPre(UserFansActivityIV userFansActivityIV) {
        this.userFansActivityIV = userFansActivityIV;
        this.mContext = userFansActivityIV.getContext();
    }

    public void intentToUserHome(String userId) {
        Intent intent = new Intent(mContext, UserHomeActivity.class);
        intent.putExtra("other_user_id", userId);
        ActivityUtil.startActivity(mContext, intent);
    }

    public void getDataFromNet() {
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
                                userFansActivityIV.showEmpty();
                            } else {
                                userFansActivityIV.goneEmpty();
                                List<UserInfoEntity> userInfoEntities = ModelParseUtil.UserInfoEntityParse(response.getUserList());
                                userFansActivityIV.setData(userInfoEntities);
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getIntentData() {
        userId = mContext.getIntent().getStringExtra("user_id");
    }
}
