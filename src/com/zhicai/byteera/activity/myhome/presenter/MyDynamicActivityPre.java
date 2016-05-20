package com.zhicai.byteera.activity.myhome.presenter;

import android.app.Activity;
import android.view.View;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.myhome.interfaceview.MyDynamicActivityIV;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/** Created by bing on 2015/6/29. */
public class MyDynamicActivityPre {
    private Activity mContext;
    private MyDynamicActivityIV myDynamicActivityIV;
    private Dynamic.GetUserMsgResponse response;

    public MyDynamicActivityPre(MyDynamicActivityIV myDynamicActivityIV) {
        this.myDynamicActivityIV = myDynamicActivityIV;
        this.mContext = myDynamicActivityIV.getContext();
    }

    public void getDataFromNet() {
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        Dynamic.GetUserMsg req = Dynamic.GetUserMsg.newBuilder().setUserId(userId).build();
        TiangongClient.instance().send("chronos", "licaiquan_get_usermsg", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    response = Dynamic.GetUserMsgResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            List<DynamicEntity> dynamicEntities = ModelParseUtil.DynamicEntityParse(response, userId);
                            myDynamicActivityIV.setData(dynamicEntities);
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showDeleteDialog(final int position) {
        final MaterialDialog materialDialog = new MaterialDialog(mContext);
        materialDialog.setTitle("删除动态").setMessage("您确定要删除这条动态吗?").setPositiveButton("确定", new View.OnClickListener() {
            @Override public void onClick(View v) {
                deleteCommment(position, materialDialog);
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override public void onClick(View v) {
                materialDialog.dismiss();
            }
        }).show();
    }

    private void deleteCommment(int position, final MaterialDialog dialog) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        String msgId = null;
        String msgId1 = response.getItem(position).getM1().getCommonField().getMsgId();
        String msgId2 = response.getItem(position).getM2().getCommonField().getMsgId();
        String msgId3 = response.getItem(position).getM3().getCommonField().getMsgId();
        if (msgId1 != null) {
            msgId = msgId1;
        } else if (msgId2 != null) {
            msgId = msgId2;
        } else if (msgId3 != null) {
            msgId = msgId3;
        }
        Dynamic.DeleteMyMsg sec = Dynamic.DeleteMyMsg.newBuilder().setUserId(userId)
                .setMsgId(msgId).build();
        TiangongClient.instance().send("chronos", "licaiquan_delete_mymsg", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Dynamic.DeleteMyMsgResponse response = Dynamic.DeleteMyMsgResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            if (response.getErrorno() == 0) {
                                ToastUtil.showToastText( "删除动态成功");
                                dialog.dismiss();
                                getDataFromNet();
                            } else {
                                ToastUtil.showToastText( "删除动态失败");

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
