package com.zhicai.byteera.activity.community.dynamic.presenter;

import android.app.Activity;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.UserFocusActivityIV;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.Collections;
import java.util.List;

/** Created by bing on 2015/6/30. */
public class UserFocusActivityPre {
    private Activity mContext;
    private UserFocusActivityIV userFocusActivityIV;

    public UserFocusActivityPre(UserFocusActivityIV userFocusActivityIV) {
        this.userFocusActivityIV = userFocusActivityIV;
        this.mContext = userFocusActivityIV.getContext();
    }

    public void getDataFromNet(String hisUserId) {
        UserAttribute.GetUserRelationUserReq sec = UserAttribute.GetUserRelationUserReq.newBuilder().setUserId(hisUserId)
                .setRelation(UserAttribute.GetUserRelationUserReq.Relation.Watched).build();
        TiangongClient.instance().send("chronos", "get_user_relation_user", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final UserAttribute.GetUserRelationUserResponse response = UserAttribute.GetUserRelationUserResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            if (response.getErrorno() == 0) {
                                if (response.getUserCount() == 0) {
                                    userFocusActivityIV.showEmpty();
                                } else {
                                    userFocusActivityIV.hideEmpty();
                                    List<UserInfoEntity> userInfoEntities = ModelParseUtil.UserInfoEntityParse(response.getUserList());
                                    Collections.sort(userInfoEntities);
                                    userFocusActivityIV.setData(userInfoEntities);
                                }
                            } else {
                               ToastUtil.showToastText("获取数据失败");
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
