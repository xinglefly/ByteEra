package com.zhicai.byteera.activity.myhome.presenter;

import android.app.Activity;
import android.graphics.Bitmap;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.myhome.interfaceview.ModifyUserInfoActivityIV;
import com.zhicai.byteera.commonutil.FormatTools;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

/** Created by bing on 2015/7/1. */
public class ModifyUserInfoActivityPre {
    private Activity mContext;
    private ModifyUserInfoActivityIV modifyUserInfoActivityIV;

    public ModifyUserInfoActivityPre(ModifyUserInfoActivityIV modifyUserInfoActivityIV) {
        this.modifyUserInfoActivityIV = modifyUserInfoActivityIV;
        this.mContext = modifyUserInfoActivityIV.getContext();
    }

    public void setUserInfo() {
        ZCUser userInfo = PreferenceUtils.getInstance(mContext).readUserInfo();
        modifyUserInfoActivityIV.setUserInfo(userInfo);
    }

    public void changeUserInfo(final Object content, final String type) {
        final ZCUser userInfo = PreferenceUtils.getInstance(mContext).readUserInfo();
        final String userId = userInfo.getUser_id();
        final UserAttribute.ModifyUserAttrReq.Builder builder = UserAttribute.ModifyUserAttrReq.newBuilder();
        builder.setUserId(userId);
        switch (type) {
            case "nick_name":
                builder.setNickname((String) content);
                break;
            case "sex":
                builder.setSex((Common.SexType) content);
                break;
            case "birthday":
                builder.setBirthday((String) content);
                break;
            case "card":
                builder.setIdentifyCard((String) content);
                break;
            case "city":
                builder.setCity((String) content);
                break;

        }
        UserAttribute.ModifyUserAttrReq req = builder.build();
        TiangongClient.instance().send("chronos", "modify_user_attr", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    UserAttribute.ModifyUserAttrResponse response = UserAttribute.ModifyUserAttrResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                ToastUtil.showToastText( "修改成功");
                                switch (type) {
                                    case "nick_name":
                                        modifyUserInfoActivityIV.setTextNickName((String) content);
                                        userInfo.setNickname((String) content);
                                        break;
                                    case "sex":
                                        modifyUserInfoActivityIV.setTextSex(setText((Common.SexType) content));
                                        userInfo.setSex(setText((Common.SexType) content));
                                        break;
                                    case "birthday":
                                        modifyUserInfoActivityIV.setTextBirthday(modifyUserInfoActivityIV.getDialogBirtyday());
                                        userInfo.setBirthday(modifyUserInfoActivityIV.getDialogBirtyday());
                                        break;
                                    case "card":
                                        modifyUserInfoActivityIV.setTextCard((String) content);
                                        userInfo.setIdentifyCard((String) content);
                                        break;
                                    case "city":
                                        modifyUserInfoActivityIV.setTextCity((String) content);
                                        userInfo.setCity((String) content);
                                        break;
                                }
                                PreferenceUtils.getInstance(mContext).setUserInfo(userInfo);
                            }
                        });
                    } else {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                ToastUtil.showToastText( "修改失败");
                            }
                        });
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public String setText(Common.SexType type) {
        if (type == Common.SexType.FEMALE) {
            return "女";
        } else if (type == Common.SexType.MALE) {
            return "男";
        }
        return null;
    }

    public void changeIcon(final Bitmap photo) {
        final ZCUser userInfo = PreferenceUtils.getInstance(mContext).readUserInfo();
        UserAttribute.UploadHeadPortraitReq.ImageFile imageFile = UserAttribute.UploadHeadPortraitReq.ImageFile.newBuilder()
                .setName(System.currentTimeMillis() + ".jpg")
                .setData(ByteString.copyFrom(FormatTools.Bitmap2Bytes(photo))).build();
        UserAttribute.UploadHeadPortraitReq req = UserAttribute.UploadHeadPortraitReq.newBuilder().setUserId(userInfo.getUser_id()).setFile(imageFile).build();
        TiangongClient.instance().send("chronos", "upload_head_portrait", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final UserAttribute.UploadHeadPortraitResponse page = UserAttribute.UploadHeadPortraitResponse.parseFrom(buffer);
                    if (page.getErrorno() == 0) {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                               ToastUtil.showToastText("修改成功");
                                modifyUserInfoActivityIV.setIcon(photo);
                                String imgUrl = page.getHeadPortrait();
                                userInfo.setHeader(imgUrl);     //修改本地的保存值
                                PreferenceUtils.getInstance(mContext).setUserInfo(userInfo);
                            }
                        });
                    } else {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                               ToastUtil.showToastText("修改失败");
                            }
                        });
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
