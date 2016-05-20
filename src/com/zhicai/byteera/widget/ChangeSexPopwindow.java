package com.zhicai.byteera.widget;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.zhicai.byteera.R;
import com.zhicai.byteera.service.Common;

import java.util.ArrayList;

/** Created by lieeber on 15/8/7. */
public class ChangeSexPopwindow {

    private final SweetSheet mSweetSheet;

    public interface SelectNanListener {
        void selectNan(Common.SexType item);
    }

    public interface SelectNvListener {
        void selectNv(Common.SexType item);
    }

    private SelectNanListener selectNanListener;
    private SelectNvListener selectNvListener;

    public SelectNanListener getSelectNanListener() {
        return selectNanListener;
    }

    public void setSelectNanListener(SelectNanListener selectNanListener) {
        this.selectNanListener = selectNanListener;
    }

    public SelectNvListener getSelectNvListener() {
        return selectNvListener;
    }

    public void setSelectNvListener(SelectNvListener selectNvListener) {
        this.selectNvListener = selectNvListener;
    }

    public void show() {
        mSweetSheet.show();
    }

    public ChangeSexPopwindow(Activity mContext, RelativeLayout ll) {
        final ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.drawable.male_female;
        menuEntity1.title = "请选择性别";
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.title = "男";
        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.title = "女";
        MenuEntity menuEntity3 = new MenuEntity();
        menuEntity3.title = "取消";
        list.add(menuEntity1);
        list.add(menuEntity);
        list.add(menuEntity2);
        list.add(menuEntity3);

        mSweetSheet = new SweetSheet(ll);
        //设置数据源 (数据源支持设置 list 数组,也支持从menu 资源中获取)
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        //根据设置不同Effect来设置背景效果:BlurEffect 模糊效果.DimEffect 变暗效果,NoneEffect 没有效果.
        mSweetSheet.setBackgroundEffect(new DimEffect(8));
        //设置菜单点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override public boolean onItemClick(int position, MenuEntity menuEntity1) {
                switch (position) {
                    case 0:
                        return false;
                    case 1:
                        if (selectNanListener != null) {
                            selectNanListener.selectNan(getSexType("男"));
                        }
                        return true;
                    case 2:
                        if (selectNvListener != null) {
                            selectNvListener.selectNv(getSexType("女"));
                        }
                        return true;
                    case 3:
                        return true;

                }

                return true;
            }
        });
    }

    public Common.SexType getSexType(String sex) {
        if ("男".equals(sex)) {
            Common.SexType type = Common.SexType.MALE;
            return type;
        } else if ("女".equals(sex)) {
            Common.SexType type = Common.SexType.FEMALE;
            return type;
        }
        return null;
    }
}
