package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.ToggleButton;


import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragmentActivity;
import com.zhicai.byteera.activity.community.dynamic.adapter.AlbumGridViewAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.ImageItem;
import com.zhicai.byteera.commonutil.ActivityController;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Bimp;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.widget.HeadViewMain;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;


/**
 * 这个是显示一个文件夹里面的所有图片时的界面
 */
public class ShowAllPhotoActivity extends BaseFragmentActivity {
    @Bind(R.id.head_view) HeadViewMain mHeadView;

    private AlbumGridViewAdapter gridImageAdapter;
    private Button okButton;
    private Button preview;
    private Intent intent;
    public static ArrayList<ImageItem> dataList = new ArrayList<ImageItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_camera_show_all_photo);
        ButterKnife.bind(this);
        mHeadView.setLeftTextClickListener(new HeadViewMain.LeftTextClickListener() {
            @Override public void onLeftTextClick() {
                //清空选择的图片
                finishManyActivity();
            }
        });

        preview = (Button) findViewById(R.id.showallphoto_preview);
        okButton = (Button) findViewById(R.id.showallphoto_ok_button);

        this.intent = getIntent();
        String folderName = intent.getStringExtra("folderName");
        if (folderName.length() > 8) {
            folderName = folderName.substring(0, 9) + "...";
        }
        mHeadView.setTitleName(folderName);
        preview.setOnClickListener(new PreviewListener());
        init();
        initListener();
        isShowOkBt();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    private class PreviewListener implements OnClickListener {
        public void onClick(View v) {
            if (Bimp.tempSelectBitmap.size() > 0) {
                intent.putExtra("position", "2");
                intent.setClass(ShowAllPhotoActivity.this, GalleryActivity.class);
                ActivityUtil.startActivity(ShowAllPhotoActivity.this, intent);
            }
        }
    }

    private void init() {
        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.showallphoto_progressbar);
        progressBar.setVisibility(View.GONE);
        GridView gridView = (GridView) findViewById(R.id.showallphoto_myGrid);
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList, Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
        okButton = (Button) findViewById(R.id.showallphoto_ok_button);
    }

    private void initListener() {
        gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
            public void onItemClick(final ToggleButton toggleButton, int position, boolean isChecked, Button button) {
                if (Bimp.tempSelectBitmap.size() >= Constants.PHOTO_NUM && isChecked) {
                    button.setVisibility(View.GONE);
                    toggleButton.setChecked(false);
                    ToastUtil.showToastText(R.string.only_choose_num);
                    return;
                }

                if (isChecked) {
                    button.setVisibility(View.VISIBLE);
                    Bimp.tempSelectBitmap.add(dataList.get(position));
                    okButton.setText(getResources().getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + Constants.PHOTO_NUM + ")");
                } else {
                    button.setVisibility(View.GONE);
                    Bimp.tempSelectBitmap.remove(dataList.get(position));
                    okButton.setText(getResources().getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + Constants.PHOTO_NUM + ")");
                }
                isShowOkBt();
            }
        });
        okButton.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                okButton.setClickable(false);
                finishManyActivity();
            }
        });

    }

    private void finishManyActivity() {
        List<Activity> activitys = ActivityController.getActivitys();
        for (int i = 0; i < activitys.size(); i++) {
            if (activitys.get(i) instanceof ImageFileActivity || activitys.get(i) instanceof AlbumActivity) {
                activitys.get(i).finish();
                ActivityController.remove(activitys.get(i));
            }
        }
        ActivityUtil.finishActivity(ShowAllPhotoActivity.this);
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            okButton.setText(getResources().getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + Constants.PHOTO_NUM + ")");
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            okButton.setTextColor(Color.WHITE);
            preview.setTextColor(Color.WHITE);
        } else {
            okButton.setText(getResources().getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + Constants.PHOTO_NUM + ")");
            preview.setPressed(false);
            preview.setClickable(false);
            okButton.setPressed(false);
            okButton.setClickable(false);
            okButton.setTextColor(Color.parseColor("#E1E0DE"));
            preview.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //不用把照片给清空了 用户其实并不想按返回键就清空
            finishManyActivity();
        }

        return false;

    }

    @Override
    protected void onRestart() {
        isShowOkBt();
        super.onRestart();
    }

}
