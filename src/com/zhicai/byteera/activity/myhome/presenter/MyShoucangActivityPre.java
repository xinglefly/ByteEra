package com.zhicai.byteera.activity.myhome.presenter;

import android.app.Activity;
import android.view.View;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.myhome.interfaceview.MyShoucangActivityIV;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.information.InformationSecondary;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import me.drakeet.materialdialog.MaterialDialog;

/** Created by bing on 2015/6/29. */
public class MyShoucangActivityPre {
    private Activity mContext;
    private MyShoucangActivityIV myShoucangActivityIV;
    private InformationSecondary.GetUserCollectResponse response;

    public MyShoucangActivityPre(MyShoucangActivityIV myShoucangActivityIV) {
        this.myShoucangActivityIV = myShoucangActivityIV;
        this.mContext = myShoucangActivityIV.getContext();
    }

    public void getDataFromNet() {
        final ZCUser zcUser = PreferenceUtils.getInstance(mContext).readUserInfo();
        String userId = zcUser.getUser_id();
        final InformationSecondary.GetUserCollectReq req = InformationSecondary.GetUserCollectReq.newBuilder().setUserId(userId).build();
        TiangongClient.instance().send("chronos", "zixun_get_user_collect", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    response = InformationSecondary.GetUserCollectResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            myShoucangActivityIV.setData(response.getZixunList());
                            //修改我本地保存的收藏量
                            zcUser.setCollectCnt(response.getZixunList().size());
                            PreferenceUtils.getInstance(mContext).setUserInfo(zcUser);
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
        materialDialog.setTitle("删除收藏").setMessage("您确定要删除这条收藏吗?").setPositiveButton("确定", new View.OnClickListener() {
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
        InformationSecondary.UndoCollectReq req = InformationSecondary.UndoCollectReq.newBuilder().setUserId(userId)
                .setZixunId(response.getZixun(position).getZixunId()).build();
        TiangongClient.instance().send("chronos", "zixun_undo_collect", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final InformationSecondary.UndoCollectResponse page = InformationSecondary.UndoCollectResponse.parseFrom(buffer);
                    if (page.getErrorno() == 0) {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                ToastUtil.showToastText("删除成功");
                                dialog.dismiss();
                                getDataFromNet();
                            }
                        });
                    } else {
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
