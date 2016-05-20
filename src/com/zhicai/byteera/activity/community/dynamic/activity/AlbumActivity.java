package com.zhicai.byteera.activity.community.dynamic.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragmentActivity;
import com.zhicai.byteera.activity.community.dynamic.adapter.AlbumGridViewAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.ImageBucket;
import com.zhicai.byteera.activity.community.dynamic.entity.ImageItem;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.AlbumHelper;
import com.zhicai.byteera.commonutil.Bimp;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.widget.HeadViewMain;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Bind;

public class AlbumActivity extends BaseFragmentActivity {

    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.myGrid) GridView gridView;
    @Bind(R.id.preview) Button preview;
    @Bind(R.id.ok_button) Button okButton;
    @Bind(R.id.myText) TextView tv;

    private AlbumGridViewAdapter gridImageAdapter;
    private ArrayList<ImageItem> dataList;
    public static List<ImageBucket> contentList;


    public static Bitmap bitmap;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_camera_album);
        ButterKnife.bind(this);
        opreateReceiver();
        initView();
        initListener();
        isShowOkBt();
        processLogic();
    }

    private void opreateReceiver() {
        //注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plugin_camera_no_pictures);
    }

    private void initView() {
        AlbumHelper helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        contentList = helper.getImagesBucketList(false);
        dataList = new ArrayList<ImageItem>();
        for (int i = 0; i < contentList.size(); i++) {
            dataList.addAll(contentList.get(i).imageList);
        }
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList, Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
        gridView.setEmptyView(tv);
        preview.setOnClickListener(new PreviewListener());
        okButton.setText(getResources().getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + Constants.PHOTO_NUM + ")");
    }

    private void initListener() {
        gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final ToggleButton toggleButton, int position, boolean isChecked, Button chooseBt) {
                if (Bimp.tempSelectBitmap.size() >= Constants.PHOTO_NUM) {
                    toggleButton.setChecked(false);
                    chooseBt.setVisibility(View.GONE);
                    if (!removeOneData(dataList.get(position))) {
                        ToastUtil.showToastText(R.string.only_choose_num);
                    }
                    return;
                }
                if (isChecked) {
                    chooseBt.setVisibility(View.VISIBLE);
                    Bimp.tempSelectBitmap.add(dataList.get(position));
                    okButton.setText(getResources().getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + Constants.PHOTO_NUM + ")");
                } else {
                    Bimp.tempSelectBitmap.remove(dataList.get(position));
                    chooseBt.setVisibility(View.GONE);
                    okButton.setText(getResources().getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + Constants.PHOTO_NUM + ")");
                }
                isShowOkBt();
            }
        });

        okButton.setOnClickListener(new AlbumSendListener());

    }

    private void processLogic() {
        mHeadView.setLeftTextClickListener(new HeadViewMain.LeftTextClickListener() {
            @Override
            public void onLeftTextClick() {
                Bimp.tempSelectBitmap.clear();
                ActivityUtil.finishActivity(AlbumActivity.this);
            }
        });
        mHeadView.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override
            public void onRightTextClick() {
                ActivityUtil.startActivity(AlbumActivity.this, new Intent(AlbumActivity.this, ImageFileActivity.class));
            }
        });
    }


    private class PreviewListener implements OnClickListener {
        public void onClick(View v) {
            if (Bimp.tempSelectBitmap.size() > 0) {
                ActivityUtil.startActivity(AlbumActivity.this, new Intent(AlbumActivity.this, GalleryActivity.class));
            }
        }
    }

    private class AlbumSendListener implements OnClickListener {
        public void onClick(View v) {
            ActivityUtil.finishActivity(AlbumActivity.this);
        }
    }

    private boolean removeOneData(ImageItem imageItem) {
        if (Bimp.tempSelectBitmap.contains(imageItem)) {
            Bimp.tempSelectBitmap.remove(imageItem);
            okButton.setText(getResources().getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + Constants.PHOTO_NUM + ")");
            return true;
        }
        return false;
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


    @Override
    protected void onRestart() {
        isShowOkBt();
        super.onRestart();
    }



    @Override protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            gridImageAdapter.notifyDataSetChanged();
        }
    };
}
