package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.adapter.JoinGroupAdapter;
import com.zhicai.byteera.activity.community.dynamic.adapter.PhotoGridAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.ImageItem;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.PublishDynamicActivityIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.PublishDynamicActivityPre;
import com.zhicai.byteera.activity.community.dynamic.view.ShowSelectPhotoPopWindow;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Bimp;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ImageUtils;
import com.zhicai.byteera.commonutil.LoadingDialogShow;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.dynamic.DynamicGroupV2;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.NoScrollGridView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;


public class PublishDynamicActivity extends BaseActivity implements PublishDynamicActivityIV {

    @Bind(R.id.noScrollgridview) NoScrollGridView noScrollgridview;
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.et_comment) EditText etComment;
    @Bind(R.id.ll_parent) View popParent;
    @Bind(R.id.tv_right) View tvSend;
    //selectionOptions
    @Bind(R.id.rl_postions) RelativeLayout rlOptions;
    @Bind(R.id.listview) ListView mListView;
    @Bind(R.id.tv_select) TextView tvSelect;
    @Bind(R.id.tv_option) TextView tvOptions;

    private JoinGroupAdapter mAdapter;
    private List<GroupEntity> groupList;
    private PublishDynamicActivityPre publishDynamicActivityPre;
    private PhotoGridAdapter adapter;
    private ShowSelectPhotoPopWindow pop;
    private PhotoGetBroadcastReceiver mReceiver;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.public_dynamic_activity);
        ButterKnife.bind(this);
        publishDynamicActivityPre = new PublishDynamicActivityPre(this);
    }

    /**是否显示选择同步小组的条件**/
    public void isShowSlectionOptions(boolean isSelected){
        if (isSelected){
            rlOptions.setVisibility(View.VISIBLE);
            mAdapter = new JoinGroupAdapter(this);
            mListView.setAdapter(mAdapter);
            getJoinLicaiquan_grouplist();
        }else{
            rlOptions.setVisibility(View.GONE);
        }
    }


    @OnItemClick(R.id.listview)
    public void itemClickListener(int position){
        final GroupEntity groupEntity = groupList.get(position);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvSelect.setVisibility(View.GONE);
                tvOptions.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.INVISIBLE);
                tvOptions.setText(groupEntity.getName());
                tvOptions.setTextColor(Color.parseColor("#157db6"));
                publishDynamicActivityPre.setGroupId(groupEntity.getGroupId());
            }
        });
    }

    @OnClick({R.id.tv_select,R.id.tv_option})
    public void clickListener(View v){
        switch (v.getId()){
            case R.id.tv_select:
                mListView.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_option:
                tvOptions.setVisibility(View.GONE);
                tvSelect.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.VISIBLE);

                break;
        }
    }

    /**获取已加入理财圈小组的列表**/
    public void getJoinLicaiquan_grouplist() {
        DynamicGroupV2.LicaiquanGroupGetJoinedReq sec = DynamicGroupV2.LicaiquanGroupGetJoinedReq.newBuilder().setUserId(MyApp.getInstance().getUserId()).build();
        TiangongClient.instance().send("chronos", "licaiquan_group_get_joined", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {

                    final DynamicGroupV2.LicaiquanGroupGetJoinedResponse response = DynamicGroupV2.LicaiquanGroupGetJoinedResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                groupList = ModelParseUtil.JoinGroupEntityParse(response.getLicaiquanGroupsList());
                                mAdapter.setData(groupList);
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initData() {
        initView();
        publishDynamicActivityPre.getIntentData();
    }


    @Override
    protected void updateUI() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //清空被选中的图片
        publishDynamicActivityPre.clearPhotos();
    }


    @OnItemClick(R.id.noScrollgridview)
    public void onItemClick(int position){
        if (position == Bimp.tempSelectBitmap.size()) {
            pop.showAtLocation(popParent, Gravity.BOTTOM, 0, 0);
            UIUtils.hideKeyboard(baseContext);
        } else {
            publishDynamicActivityPre.intentToGallery(position);
        }
    }

    @Override
    protected void processLogic() {
        mHeadView.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override public void onRightTextClick() {
                if (!TextUtils.isEmpty(etComment.getText().toString().trim()) || Bimp.tempSelectBitmap.size() != 0) {
                    publishDynamicActivityPre.pushDynamic();
                } else {
                    ToastUtil.showToastText("发布动态内容不能为空");
                }
            }
        });
        mHeadView.setLeftTextClickListener(new HeadViewMain.LeftTextClickListener() {
            @Override public void onLeftTextClick() {
                UIUtils.hideKeyboard(baseContext);
                ActivityUtil.finishActivity(baseContext);
                publishDynamicActivityPre.clearPhotos();
            }
        });
    }

    public void initView() {
        registReceiver();
        pop = new ShowSelectPhotoPopWindow(baseContext, new ShowSelectPhotoPopWindow.ButtonClickListener() {
            @Override public void onCameraButtonClick() {
                publishDynamicActivityPre.photo();
            }

            @Override public void onPhotoButtonClick() {
                ActivityUtil.startActivity(baseContext, new Intent(baseContext, AlbumActivity.class));
            }
        });
        adapter = new PhotoGridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
    }

    private void registReceiver() {
        mReceiver = new PhotoGetBroadcastReceiver();
        IntentFilter filter = new IntentFilter("update_photo");
        this.registerReceiver(mReceiver, filter);
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PublishDynamicActivityPre.TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < Constants.PHOTO_NUM && resultCode == RESULT_OK) {
                    File cameraFile = publishDynamicActivityPre.getCaptureFile();
                    if (cameraFile != null && cameraFile.exists()) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        options.inSampleSize = 10;
                        options.inJustDecodeBounds = false;
                        Log.e("path:", cameraFile.getAbsolutePath());
                        Bitmap bm = BitmapFactory.decodeFile(cameraFile.getAbsolutePath(), options);
                        Bitmap bitmap = ImageUtils.compressImage(bm);
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bitmap);
                        Bimp.tempSelectBitmap.add(takePhoto);
                    }
                }
                break;
        }
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void setSendEnabled(boolean b) {
        tvSend.setEnabled(b);
    }

    @Override public String getContent() {
        return etComment.getText().toString();
    }

    @Override public void showPushDialog() {
        dialog.setMessage("发布中...");
        dialog.show();
    }

    @Override public void showPushSuccessDialog() {
        dialog.setResultStatusDrawable(true, "分布成功");
    }

    @Override public void showPushFialeDialog() {
        dialog.setResultStatusDrawable(false, "发布失败");
    }

    @Override public void hidePushDialog() {
        dialog.dismiss();
    }

    public class PhotoGetBroadcastReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                adapter.notifyDataSetChanged();
            } else {
                Bimp.max += 1;
                adapter.notifyDataSetChanged();
            }
        }
    }
}
