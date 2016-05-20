package com.zhicai.byteera.activity.community.group;

import android.widget.GridView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;


public class SelelctGroupActivity extends BaseActivity {
    @Bind(R.id.first_grid_view) GridView firstGridView;
    @Bind(R.id.sec_grid_view) GridView secGridView;
    @Bind(R.id.third_grid_view) GridView thirdGridView;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.selelct_group_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {
        List<GroupEntity> firstGroups = new ArrayList<>();
        List<GroupEntity> secGroups = new ArrayList<>();
        List<GroupEntity> thirdGroups = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GroupEntity item = new GroupEntity();
            item.setPeopleCnt(10);
            item.setAvatarUrl("http://v1.qzone.cc/avatar/201404/13/11/12/534a00b62633e072.jpg!200x200.jpg");
            item.setName("你是猪吗");
            firstGroups.add(item);
        }
        for (int i = 0; i < 11; i++) {
            GroupEntity item = new GroupEntity();
            item.setPeopleCnt(10);
            item.setAvatarUrl("http://v1.qzone.cc/avatar/201305/17/22/59/519645dd2f5f2562.jpg!200x200.jpg");
            item.setName("脚印还很干净");
            secGroups.add(item);
        }
        for (int i = 0; i < 6; i++) {
            GroupEntity item = new GroupEntity();
            item.setPeopleCnt(10);
            item.setAvatarUrl("http://img5q.duitang.com/uploads/item/201403/05/20140305105850_V3mVx.jpeg");
            item.setName("无形之刃");
            thirdGroups.add(item);
        }

        SelelctGroupAdapter firstAdapter = new SelelctGroupAdapter(baseContext, firstGroups);
        SelelctGroupAdapter secAdapter = new SelelctGroupAdapter(baseContext, secGroups);
        SelelctGroupAdapter thirdAdapter = new SelelctGroupAdapter(baseContext, thirdGroups);

        firstGridView.setAdapter(firstAdapter);
        secGridView.setAdapter(secAdapter);
        thirdGridView.setAdapter(thirdAdapter);
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {

    }
}
