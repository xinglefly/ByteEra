package com.zhicai.byteera.activity.community.dynamic.activity;


import android.os.Bundle;
import android.widget.GridView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragmentActivity;
import com.zhicai.byteera.activity.community.dynamic.adapter.FolderAdapter;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.ButterKnife;
import butterknife.Bind;

/** 这个类主要是用来进行显示包含图片的文件夹 */
public class ImageFileActivity extends BaseFragmentActivity {

    @Bind(R.id.head_view) HeadViewMain mHeadView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_camera_image_file);
        ButterKnife.bind(this);

        mHeadView.setLeftTextClickListener(new HeadViewMain.LeftTextClickListener() {
            @Override public void onLeftTextClick() {
                //清空选择的图片
//                Bimp.tempSelectBitmap.clear();
                ActivityUtil.finishActivity(ImageFileActivity.this);
            }
        });
        GridView gridView = (GridView) findViewById(R.id.fileGridView);
        FolderAdapter folderAdapter = new FolderAdapter(this);
        gridView.setAdapter(folderAdapter);
    }

}
