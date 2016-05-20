package com.zhicai.byteera.activity.traincamp;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.DayTask;
import com.zhicai.byteera.activity.event.DayTaskEvent;
import com.zhicai.byteera.activity.traincamp.adapter.DayTaskAdapter;
import com.zhicai.byteera.activity.traincamp.component.DaggerDayTaskComponent;
import com.zhicai.byteera.activity.traincamp.component.DayTaskComponent;
import com.zhicai.byteera.activity.traincamp.module.DayTaskModule;
import com.zhicai.byteera.activity.traincamp.presenter.DayTaskPresenter;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;
import java.util.List;

import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

@SuppressWarnings("unused")
public class DailyTaskActivity extends BaseActivity {

    @Bind(R.id.head_title) HeadViewMain headTitle;
    @Bind(R.id.lv_daytask) ListView lv_daytask;
    @Bind(R.id.tv_coin) TextView tvCoin;
    @Bind(R.id.tv_ranking) TextView tvRanking;
    @Bind(R.id.tv_logindays) TextView tvLogingDays;

    @Inject DayTaskPresenter presenter;
    @Inject DayTaskAdapter dayTaskAdapter;
    DayTaskComponent component;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.day_task_activity);
        ButterKnife.bind(this);
        component = DaggerDayTaskComponent.builder().appComponent(MyApp.getInstance().getComponent())
                .dayTaskModule(new DayTaskModule(this)).build();
        component.injectActivity(this);
    }

    @Override
    protected void initData() {
        initListView();
    }

    public void initListView(){
        lv_daytask.setAdapter(dayTaskAdapter);
        presenter.getDailyTaskList();
    }

    @OnItemClick(R.id.lv_daytask)
    void onItemClick(AdapterView<?> parent, int position){
        DayTask task = (DayTask) parent.getAdapter().getItem(position);
        if (DetermineIsLogin()) return;
        if (task.getStatus()==2){
            dialog.show();
            presenter.taskRewardDoing(task.getId());
            dialog.setResultStatusDrawable(true,"领取成功");
        }
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        headTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }


    public void setDialyList(List taskList) {
        dayTaskAdapter.setAdapter(taskList);
    }


    public void getDailyTask(final DayTask dayTask) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvCoin.setText("拥有" + dayTask.getUser_coin() + "财币");
                tvRanking.setText("好友中排名"+dayTask.getUser_rank()+"名");
                tvLogingDays.setText("连续登陆"+dayTask.getCon_login()+"天");
                MyApp.getInstance().setCoinCnt(dayTask.getUser_coin());
                EventBus.getDefault().post(new DayTaskEvent(true));
            }
        });
    }
}
