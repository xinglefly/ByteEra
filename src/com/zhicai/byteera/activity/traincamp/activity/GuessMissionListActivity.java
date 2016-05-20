package com.zhicai.byteera.activity.traincamp.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.GuessDb;
import com.zhicai.byteera.activity.bean.GuessSmallMissionInfo;
import com.zhicai.byteera.activity.traincamp.adapter.GuessMissionListAdapter;
import com.zhicai.byteera.activity.traincamp.util.GuessUtils;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.SDLog;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


@SuppressWarnings("unused")
public class GuessMissionListActivity extends BaseActivity {

    public static final String TAG = GuessMissionListActivity.class.getSimpleName();

    @Bind(R.id.img_back) ImageView back;
    @Bind(R.id.img_question) ImageView question;
    @Bind(R.id.rel_guesssec) RelativeLayout rel_guesssec;
    @Bind(R.id.tv_coincount) TextView tv_coincount;
    @Bind(R.id.gv_guess_mission) GridView gv_guess_mission;

    private GuessMissionListAdapter gv_guess_mission_adapter;
    private List<GuessSmallMissionInfo> missionlist;

    private String filepath;
    private String filename;
    private int mission_id;
    private int leveid;
    private int Recordposition=1;

    private View customView;

    private TextView tv_missionstatus;

    private MyBroadCastRecieve recieve;



    private static final int MSG_GAMECOIN_SUCC = 0;
    private static final int MSG_GAMECOIN_FAIL = 1;
    private static final int MSG_GAMECOIN_ERROR = 2;
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GAMECOIN_SUCC:
                    int entity_id = (Integer)msg.obj;
                    if(entity_id==missionlist.size()+1){
                       ToastUtil.showToastText("当前最后一关,解锁下一关卡！");
                        finish();
                        return;
                    }else{
                        SDLog.d(TAG, "-game entity_id true->" + entity_id);
                        try {
                            //记录关卡的进度
                            GuessDb guessDb = db.findFirst(Selector.from(GuessDb.class).where("level_id", "=", leveid).and("mission_id", "=", mission_id));
                            if(guessDb!=null){
                                GuessDb guess= new GuessDb(guessDb.getId(),leveid,mission_id,entity_id);
                                db.update(guess, "level_id", "mission_id", "game_id");
                            }

                            //记录关卡的进度进行判断
                            GuessSmallMissionInfo missionInfo = new GuessSmallMissionInfo(entity_id,leveid,mission_id,1);
                            db.update(missionInfo, "levelId", "missionid", "missionstatus");
                            SDLog.d(TAG, "-game update db mission->" + missionInfo.toString());
                            baseContext.sendBroadcast(new Intent(Constants.GUESSPICGAMEACTION));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                    Recordposition = entity_id;
                    updateUI();
                    initData();
                case MSG_GAMECOIN_FAIL:
                    ToastUtil.showToastText("您没有足够的金币");
                    break;
                case MSG_GAMECOIN_ERROR:
                    ToastUtil.showToastText("服务器异常...");
                    break;
            }
        }
    };

    @Override protected void loadViewLayout() {
        setContentView(R.layout.activity_guess_mission);
        ButterKnife.bind(this);
        recieve = new MyBroadCastRecieve();
        IntentFilter filter = new IntentFilter(Constants.GUESSPICGAMEACTION);
        baseContext.registerReceiver(recieve, filter);
    }

    @Override protected void initData() {
        rel_guesssec.setVisibility(View.VISIBLE);

        filepath = getIntent().getExtras().getString("filepath");
        filename = getIntent().getExtras().getString("filename");
        mission_id = getIntent().getExtras().getInt("missionid");
        leveid = getIntent().getExtras().getInt("leveid");
        SDLog.d(TAG, "guessSmall getintent--leveid>" + leveid + ",filepath->" + filepath + ",filename->" + filename + ",mission_id->" + mission_id);

        // 处理压缩包
        DealWithZip();

        try {
            missionlist = db.findAll(Selector.from(GuessSmallMissionInfo.class).where("levelId", "=",leveid).and("missionid", "=", mission_id));
        } catch (DbException e) {
            e.printStackTrace();
        }

        gv_guess_mission.setSelection(Recordposition);  //跳转到记录的position

        if(missionlist==null){
            return;
        }

        gv_guess_mission_adapter = new GuessMissionListAdapter(this, missionlist);
        gv_guess_mission.setAdapter(gv_guess_mission_adapter);

        gv_guess_mission.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                tv_missionstatus = ViewHolder.get(view, R.id.tv_missionstatus);
                int missionstatus = Integer.parseInt(tv_missionstatus.getText().toString());

                GuessSmallMissionInfo entity = missionlist.get(position);
                SDLog.d(TAG, "position item-->" + position + ",entity id-->" + entity.getId());

                switch (missionstatus) {
                    case Constants.MISSION_LOCKED:  //0
                        try {
                            if (db.tableIsExist(GuessDb.class)) {
                                GuessDb guessDb = db.findFirst(Selector.from(GuessDb.class).where("level_id", "=", leveid).and("mission_id", "=", mission_id));
                                if (guessDb != null) {
                                    SDLog.d(TAG, "guessdb-->" + guessDb.getGame_id() + "-->" + guessDb.toString());
                                    if (position == (guessDb.getGame_id() == 1 ? 1 : guessDb.getGame_id())) {
                                        showDialogConsume(entity.getId());
                                    } else {
                                       ToastUtil.showToastText("只能解锁第" + (guessDb.getGame_id() + 1) + "关卡");
                                    }
                                } else {
                                   ToastUtil.showToastText("请先解锁未完成关卡");
                                }
                            } else {
                               ToastUtil.showToastText("请先解锁未完成关卡");
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        break;
                    case Constants.MISSION_PASSED:  //1
                        //解锁状态
                        Intent intent = new Intent(baseContext, GuessGameActivity.class);
                        Bundle mBundle = new Bundle();
                        intent.putExtra("entity_id", entity.getId());
                        intent.putExtra("gridview_id", entity.getGridviewid());
                        intent.putExtra("mission_id", mission_id);
                        intent.putExtra("leveid", leveid);
                        intent.putExtra("filepath", filepath);
                        intent.putExtra("guesssmallsize", missionlist.size());
                        ActivityUtil.startActivity(baseContext, intent);
                        break;
                    case Constants.MISSION_FAILED:  //2
                        break;
                }
            }
        });
    }


    /**
     * 显示话费财币的dialog
     */
    private void showDialogConsume(final int entity_id) {
        Builder builder = myDialogBuilder(R.layout.dialog_consume_coin);
        final AlertDialog dialog = builder.show();

        TextView tv_consumecoin = (TextView) customView.findViewById(R.id.tv_consumecoin);
        tv_consumecoin.setText("需要答对前面的关卡或者花费200财币解锁本关卡，是否付费开启呢？");

        Button btn_consume_true = (Button) customView.findViewById(R.id.btn_consume_true);
        btn_consume_true.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                //TODO 这里要做判断，只能判断是保存数据的下一关才可以解锁
                consumeCoin(entity_id, -200);
            }
        });
        Button btn_cancel = (Button) customView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void updateUI() {
        tv_coincount.setText(MyApp.getInstance().getCoinCnt() + "");
    }

    @Override
    protected void processLogic() {
    }

    /**
     * 解压zip
     */
    private void DealWithZip() {
        int gameposition = 0;
        try {
            if (!Constants.GUESSGAMEIS_ISDOWN){
                if (db.tableIsExist(GuessSmallMissionInfo.class)) {
                    SDLog.d(TAG,"guessSmallmission table is exist!");
                    GuessSmallMissionInfo mission = db.findFirst(Selector.from(GuessSmallMissionInfo.class).where("levelId","=",leveid).and("missionid", "=", mission_id));
                    if (mission != null)
                        return;
                }
            }else{
                db.deleteAll(GuessSmallMissionInfo.class);
                SDLog.d(TAG,"delete data guessSmallMissionInfo is succ");
                 GuessDb guessDb = db.findFirst(Selector.from(GuessDb.class).where("level_id", "=", leveid).and("mission_id", "=", mission_id));
                 if (guessDb!=null){
                     SDLog.d(TAG, "guessdb 复写数据库-->"+ guessDb.toString());
                     gameposition = guessDb.getGame_id();
                 }
            }

            // 解压
            File zipFile = new File(GuessUtils.GUESS_DOWNLOAD_PATH + filename);
            try {
                if (!zipFile.exists()) {
                   ToastUtil.showToastText("没有找到对应的资源文件！");
                    return;
                }
                GuessUtils.upZipFile(zipFile, GuessUtils.GUESS_RESOURCE_PATH);
            } catch (ZipException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 存入数据库
            String filename = this.filepath + File.separator + "filelist.json";
            String jsonfile = GuessUtils.readSDFile(filename);

            JSONArray jsonarray = new JSONArray(jsonfile);
            missionlist = new ArrayList<GuessSmallMissionInfo>();

            SDLog.d(TAG, "-jsonarray->" + jsonarray.length());

            //TODO 保存数据使用批处理，在通过服务器比对zip包大小对其数据库进行复写
            if (!Constants.GUESSGAMEIS_ISDOWN){
                for (int i = 0; i < jsonarray.length(); i++) {
                    String json = jsonarray.optString(i);
                    if(i==0) {
                        missionlist.add(new GuessSmallMissionInfo(i+1,leveid, mission_id, json, Constants.MISSION_PASSED));
                    }else {
                        missionlist.add(new GuessSmallMissionInfo(i+1,leveid, mission_id, json, Constants.MISSION_LOCKED));
                    }
                }
                SDLog.d(TAG,"sava database succ");
            }else{
                for (int i = 0; i < jsonarray.length(); i++) {
                    String json = jsonarray.optString(i);
                    if(i<gameposition) {
                        missionlist.add(new GuessSmallMissionInfo(i+1,leveid, mission_id, json, Constants.MISSION_PASSED));
                    }else {
                        missionlist.add(new GuessSmallMissionInfo(i+1,leveid, mission_id, json, Constants.MISSION_LOCKED));
                    }
                }
                Constants.GUESSGAMEIS_ISDOWN = false;
                SDLog.d(TAG,"repalce database succ!");
            }

            db.saveOrUpdateAll(missionlist);
            SDLog.d(TAG,"missionlist save ok");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_back})
    public void clickListener(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }


    protected Builder myDialogBuilder(int dialog_xml) {
        final LayoutInflater inflater = this.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        customView = inflater.inflate(dialog_xml, null);
        return builder.setView(customView);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        if(null!=recieve)
            baseContext.unregisterReceiver(recieve);
    }

    class MyBroadCastRecieve extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            missionlist.clear();
            initData();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            SDLog.d(TAG,"onFinish()");
            finish();
        }
        return  false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }


    /**
     * 财币加减
     */
    private void consumeCoin(final int position,int coin) {
        String userId = PreferenceUtils.getInstance(this).getUserId();
        SDLog.d(TAG,"-game userid->"+userId+",coin->"+coin);
        if(!isStartLoginActivity()){
            UserAttribute.CoinDeltaReq req = UserAttribute.CoinDeltaReq.newBuilder().setUserId(userId).setValue(coin).build();
            TiangongClient.instance().send("chronos", "coindelta", req.toByteArray(), new BaseHandlerClass() {
                @Override
                public void onSuccess(byte[] buffer) {
                    try {
                        UserAttribute.CoinDeltaResponse res = UserAttribute.CoinDeltaResponse.parseFrom(buffer);
                        SDLog.d(TAG, "coin-->" + res.toString());
                        if (res.getErrorno() == 0) {
                            MyApp.getInstance().setCoinCnt(res.getCoinCount());
                            Message msg = mHandler.obtainMessage();
                            msg.obj = position;
                            msg.what = MSG_GAMECOIN_SUCC;
                            mHandler.sendMessage(msg);
                        } else if(res.getErrorno()==500){
                            mHandler.sendEmptyMessage(MSG_GAMECOIN_ERROR);
                        }else{
                            mHandler.sendEmptyMessage(MSG_GAMECOIN_FAIL);
                        }
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
