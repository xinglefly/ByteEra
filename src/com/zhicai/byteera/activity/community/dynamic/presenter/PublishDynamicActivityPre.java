package com.zhicai.byteera.activity.community.dynamic.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.dynamic.activity.GalleryActivity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.PublishDynamicActivityIV;
import com.zhicai.byteera.activity.community.eventbus.PublishGroupDynamicEvent;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Bimp;
import com.zhicai.byteera.commonutil.FileUtils;
import com.zhicai.byteera.commonutil.FormatTools;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;

/** Created by bing on 2015/6/30. */
public class PublishDynamicActivityPre {
    private static final String TAG = PublishDynamicActivityPre.class.getSimpleName();

    public static final int TAKE_PICTURE = 0x000001;
    private PublishDynamicActivityIV publishDynamicActivityIV;
    private Activity mContext;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    private File captureFile;

    private String groupId; //发表动态时关联小组
    private String organizationId;
    private boolean showJoinLicaiquan;//获取加入理财圈的列表

    public PublishDynamicActivityPre(PublishDynamicActivityIV publishDynamicActivityIV) {
        this.publishDynamicActivityIV = publishDynamicActivityIV;
        this.mContext = publishDynamicActivityIV.getContext();
    }

    public void getIntentData() {
        showJoinLicaiquan = mContext.getIntent().getBooleanExtra("showJoinLicaiquan", false);
        if (showJoinLicaiquan){
            publishDynamicActivityIV.isShowSlectionOptions(showJoinLicaiquan);
        }

        if (!TextUtils.isEmpty(mContext.getIntent().getStringExtra("group_id"))){
            groupId = mContext.getIntent().getStringExtra("group_id");
            setGroupId(groupId);
        }
        if (!TextUtils.isEmpty(mContext.getIntent().getStringExtra("organization_id"))){
            organizationId = mContext.getIntent().getStringExtra("organization_id");
        }
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void photo() {
        if (!FileUtils.hasSdcard()) {
           ToastUtil.showToastText("SD卡不存在，不能拍照");
            return;
        }
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureFile = FileUtils.getCaptureFile();
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(captureFile));
        mContext.startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public File getCaptureFile() {
        return captureFile;
    }

    public void pushDynamic() {
        publishDynamicActivityIV.setSendEnabled(false);
        publishDynamicActivityIV.showPushDialog();
        executorService.execute(new Runnable() {        //发布动态  最好是放到子线程中去执行
            @Override
            public void run() {
                String userId = PreferenceUtils.getInstance(mContext).getUserId();
                if (TextUtils.isEmpty(userId)) {
                    ActivityUtil.startActivity(mContext, new Intent(mContext, LoginActivity.class));
                }

                Dynamic.PublishNormalMsg.Builder msg = Dynamic.PublishNormalMsg.newBuilder();
                msg.setUserId(userId).setContent(publishDynamicActivityIV.getContent());
                for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                    Dynamic.PublishNormalMsg.ImageFile imageFile = Dynamic.PublishNormalMsg.ImageFile.newBuilder().setName(System.currentTimeMillis() + ".jpg")
                            .setData(ByteString.copyFrom(FormatTools.Bitmap2Bytes(Bimp.tempSelectBitmap.get(i).getBitmap()))).build();
                    msg.addImageFile(imageFile);
                }
                if (!TextUtils.isEmpty(groupId)) {
                    msg.setGroupId(groupId);
                    Log.d(TAG, "group_id-->" + groupId);
                }
                if (!TextUtils.isEmpty(organizationId)) {
                    msg.setInstituteUserId(organizationId);
                    Log.d(TAG, "organiztionId-->" + organizationId);
                }
                final Dynamic.PublishNormalMsg build = msg.build();
                Log.d(TAG,"conditions-->"+msg.toString());
                TiangongClient.instance().send("chronos", "licaiquan_pub_normal_msg", build.toByteArray(), new BaseHandlerClass() {
                    public void onSuccess(byte[] buffer) {
                        try {
                            final Dynamic.PublishNormalMsgResponse response = Dynamic.PublishNormalMsgResponse.parseFrom(buffer);
                            Log.d(TAG, "res-->" + response.toString());
                            MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                                @Override
                                public void run() {
                                    publishDynamicActivityIV.setSendEnabled(true);
                                    if (response.getErrorno() == 0) {
                                        ToastUtil.showToastText("发布成功");
                                        publishDynamicActivityIV.hidePushDialog();
                                        //TODO 每日任务，发表过一次动态

                                        UIUtils.hideKeyboard(mContext);
                                        clearPhotos();  //清空要发布的图片
                                        //动态发布完成之后返回到上一个界面,并使页面得到更新
                                        ActivityUtil.finishActivity(mContext);
                                        EventBus.getDefault().post(new PublishGroupDynamicEvent(true));
                                    } else {
                                        publishDynamicActivityIV.showPushFialeDialog();
                                    }
                                }
                            });
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void clearPhotos() {
        Bimp.tempSelectBitmap.clear();
    }

    public void intentToGallery(int position) {
        Intent intent = new Intent(mContext, GalleryActivity.class);
        intent.putExtra("ID", position);
        ActivityUtil.startActivity(mContext, intent);
    }



}
