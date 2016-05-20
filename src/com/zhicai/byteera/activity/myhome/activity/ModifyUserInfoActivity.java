package com.zhicai.byteera.activity.myhome.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.myhome.interfaceview.ModifyUserInfoActivityIV;
import com.zhicai.byteera.activity.myhome.presenter.ModifyUserInfoActivityPre;
import com.zhicai.byteera.activity.myhome.widget.GetPhotoDialog;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.FileUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.widget.ChangeBirthdayPopwindow;
import com.zhicai.byteera.widget.ChangeSexPopwindow;
import com.zhicai.byteera.widget.HeadViewMain;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/** Created by bing on 2015/5/7. */
public class ModifyUserInfoActivity extends BaseActivity implements ModifyUserInfoActivityIV {
    @Bind(R.id.head_view) HeadViewMain meadView;
    @Bind(R.id.icon) CircleImageView icon;
    @Bind(R.id.tv_nickname) TextView tvNickName;
    @Bind(R.id.tv_sex) TextView tvSex;
    @Bind(R.id.tv_city) TextView tvCity;
    @Bind(R.id.tv_birthday) TextView tvBirthday;
    @Bind(R.id.tv_card) TextView tvCard;
    @Bind(R.id.ll) RelativeLayout ll;

    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    private static final int CLIP_PHOTO = 2;
    private static final int GET_CITY = 4;
    private static final int CHANGE_CARD = 5;

    private Uri captureFilePath;
    private GetPhotoDialog getPhotoDialog;

    private ModifyUserInfoActivityPre modifyUserInfoActivityPre;
    private ChangeSexPopwindow changeSexPopwindow;
    private ChangeBirthdayPopwindow changeBirthdayPopwindow;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.modify_userinfo_activity);
        ButterKnife.bind(this);
        this.modifyUserInfoActivityPre = new ModifyUserInfoActivityPre(this);
    }

    private void initDialog() {
        getPhotoDialog = new GetPhotoDialog(this);
    }

    @Override
    protected void initData() {
        if (!isStartLoginActivity()) {
            initDialog();
            modifyUserInfoActivityPre.setUserInfo();
        }
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        meadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });

        getPhotoDialog.setCameraClickListener(new GetPhotoDialog.OnCameraClikListener() {
            @Override public void onCamerClick() {
                //拍照获取图片
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用,存储缓存图片
                if (FileUtils.hasSdcard()) {
                    captureFilePath = FileUtils.getCaptureFilePath();
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, captureFilePath);
                }
                startActivityForResult(intentFromCapture, FROM_CAMERA);
            }
        });

        getPhotoDialog.setPhotoClickListener(new GetPhotoDialog.OnPhotoClickListener() {
            @Override public void onPhotoClick() {
                Intent intentFromGallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intentFromGallery, FROM_ALBUM);
            }
        });
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 11 && requestCode == 11) {
            modifyUserInfoActivityPre.changeUserInfo(data.getStringExtra("name"), "nick_name");
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case FROM_CAMERA:
                    startPhotoZoom(captureFilePath);
                    break;
                case FROM_ALBUM:
                    if (data != null) {
                        startPhotoZoom(data.getData());
                    }
                    break;
                case CLIP_PHOTO:
                    getPhotoDialog.dismiss();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        Bitmap photo = extras.getParcelable("data");
                        changIcon(photo);       //通过网络修改图片
                        try {
                            if (captureFilePath != null) {
                                //删除本地图片
                                File photoFile = new File(new URI(captureFilePath.toString()));
                                photoFile.delete();
                                captureFilePath = null;
                            }
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case GET_CITY:
                    String provinceAndCity = data.getStringExtra("city");
                    String[] split = provinceAndCity.split(", ");
                    String province = split[0].split("=")[1];
                    String city = split[1].split("=")[1];
                    //修改城市
                    changeCity(province, city);
                    break;
                case CHANGE_CARD:
                    changeCard(data);
                    break;
            }
        }
    }

    private void changeCard(final Intent data) {
        modifyUserInfoActivityPre.changeUserInfo(data.getStringExtra("card"), "card");
    }

    private void changeCity(final String province, final String city) {
        modifyUserInfoActivityPre.changeUserInfo(province + " " + city, "city");
    }

    private void changIcon(final Bitmap photo) {
        modifyUserInfoActivityPre.changeIcon(photo);
    }

    private void startPhotoZoom(Uri captureFilePath) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(captureFilePath, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CLIP_PHOTO);
    }

    @OnClick({R.id.ll_photo, R.id.ll_nickname, R.id.ll_sex, R.id.ll_select_city, R.id.ll_select_birthday, R.id.ll_change_pwd, R.id.ll_card})
    public void clickListener(View view) {
        switch (view.getId()) {
            case R.id.ll_photo:
                getPhotoDialog.show();
                break;
            case R.id.ll_nickname:
                changNickName();
                break;
            case R.id.ll_sex:
                showChangePopdow();
                break;
            case R.id.ll_select_city:
                ActivityUtil.startActivityForResult(this, new Intent(this, SelectCityAcitivity.class), GET_CITY);
                break;
            case R.id.ll_select_birthday:
                showChangeBirthdayPopwindow();
                break;
            case R.id.ll_change_pwd:
                ActivityUtil.startActivity(this, new Intent(this, ChangePwdActivity.class));
                break;
            case R.id.ll_card:
                if (TextUtils.isEmpty(tvCard.getText().toString())) {
                    Intent itent = new Intent(baseContext, ChangeCardActivity.class);
                    itent.putExtra("card", tvCard.getText().toString());
                    ActivityUtil.startActivityForResult(baseContext, itent, CHANGE_CARD);
                }
                break;
        }
    }

    private void changNickName() {
        Intent intent = new Intent(baseContext, ChangeNickNameActivity.class);
        intent.putExtra("name", tvNickName.getText().toString());
        ActivityUtil.startActivityForResult(baseContext, intent, 11);
    }

    private void showChangeBirthdayPopwindow() {
        if (changeBirthdayPopwindow == null) {
            changeBirthdayPopwindow = new ChangeBirthdayPopwindow(this);
            changeBirthdayPopwindow.setChangeOkListener(new ChangeBirthdayPopwindow.ChangeOkListener() {
                @Override public void onOkClick() {
                    modifyUserInfoActivityPre.changeUserInfo(changeBirthdayPopwindow.getDate(), "birthday");
                }
            });
        }
        String birthday = tvBirthday.getText().toString();
        if (!TextUtils.isEmpty(birthday)) {
            changeBirthdayPopwindow.setBirthday(birthday.split("-"));
        }
        changeBirthdayPopwindow.showAtLocation(meadView);


    }

    private void showChangePopdow() {
        if (changeSexPopwindow == null) {
            changeSexPopwindow = new ChangeSexPopwindow(this, ll);
            changeSexPopwindow.setSelectNanListener(new ChangeSexPopwindow.SelectNanListener() {
                @Override public void selectNan(Common.SexType item) {
                    modifyUserInfoActivityPre.changeUserInfo(item, "sex");
                }
            });

            changeSexPopwindow.setSelectNvListener(new ChangeSexPopwindow.SelectNvListener() {
                @Override public void selectNv(Common.SexType item) {
                    modifyUserInfoActivityPre.changeUserInfo(item, "sex");
                }
            });
        }
        changeSexPopwindow.show();
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void setUserInfo(ZCUser userInfo) {
        tvNickName.setText(userInfo.getNickname());
        tvCity.setText(userInfo.getCity());
        tvSex.setText(userInfo.getSex());
        tvCard.setText(userInfo.getIdentifyCard());
        tvBirthday.setText(userInfo.getBirthday());
        ImageLoader.getInstance().displayImage(userInfo.getHeader(), icon);
    }


    @Override public void setTextNickName(String dialogNickName) {
        tvNickName.setText(dialogNickName);
    }


    @Override public void setTextSex(String dialogSex) {
        tvSex.setText(dialogSex);
    }

    @Override public String getDialogBirtyday() {
        return changeBirthdayPopwindow.getDate();
    }

    @Override public void setTextBirthday(String dialogBirtyday) {
        tvBirthday.setText(dialogBirtyday);
    }

    @Override public void setTextCard(String content) {
        tvCard.setText(content);
    }

    @Override public void setTextCity(String content) {
        tvCity.setText(content);
    }

    @Override public void setIcon(Bitmap photo) {
        icon.setImageBitmap(photo);
    }
}
