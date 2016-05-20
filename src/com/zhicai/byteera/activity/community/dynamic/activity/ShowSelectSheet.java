package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.util.ArrayList;

/** Created by lieeber on 15/8/17. */
public class ShowSelectSheet {
    private SweetSheet mSweetSheet;
    private ButtonClickListener listener;

    public interface ButtonClickListener {
        void onCameraButtonClick();

        void onPhotoButtonClick();

    }

    public void setItemClickListener(ButtonClickListener listener) {
        this.listener = listener;
    }

    public ShowSelectSheet(Activity activity, RelativeLayout ll) {
        final ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
    //    menuEntity1.iconId = R.drawable.male_female;
        menuEntity1.title = "拍照";
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.title = "从相册选取";
        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.title = "取消";
        list.add(menuEntity1);
        list.add(menuEntity);
        list.add(menuEntity2);
        mSweetSheet = new SweetSheet(ll);
        mSweetSheet.setMenuList(list);
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        mSweetSheet.setBackgroundEffect(new DimEffect(8));
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override public boolean onItemClick(int position, MenuEntity menuEntity1) {
                switch (position) {
                    case 0:
                        if (listener != null) {
                            listener.onCameraButtonClick();
                        }
                        return false;
                    case 1:
                        if (listener != null) {
                            listener.onPhotoButtonClick();
                        }
                        return true;


                }
                return true;
            }
        });
    }

    public void show() {
        mSweetSheet.show();
    }

    public void dismiss(){
        mSweetSheet.dismiss();
    }
}
