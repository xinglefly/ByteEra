package com.zhicai.byteera.activity.traincamp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.GuessEntity;
import com.zhicai.byteera.activity.bean.GuessPicLevel;
import com.zhicai.byteera.activity.bean.GuessPicMisson;
import com.zhicai.byteera.activity.bean.GuessSmallMissionInfo;
import com.zhicai.byteera.activity.traincamp.adapter.GuessListAdapter;
import com.zhicai.byteera.activity.traincamp.downloader.DownloadService;
import com.zhicai.byteera.activity.traincamp.util.GuessUtils;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.SDLog;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.service.train_camp.GuessPic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * 猜图主页
 */
@SuppressWarnings("unused")
public class GuessPicActivity extends BaseActivity {

    private static final String TAG = GuessPicActivity.class.getSimpleName();

    public static final String ACTION_DOWNLOAD_PROGRESS = "guess_download_progress";
    public static final String ACTION_DOWNLOAD_SUCCESS = "guess_download_success";
    public static final String ACTION_DOWNLOAD_FAIL = "guess_download_fail";

    //header
    @Bind(R.id.img_back) ImageView back;
    @Bind(R.id.img_question) ImageView question;
    @Bind(R.id.rel_guessfirst) RelativeLayout rel_guessfirst;

    @Bind(R.id.rel_composite) RelativeLayout rel_composite;
    @Bind(R.id.tv_composite) TextView tv_composite;
    @Bind(R.id.gv_composite) GridView gv_composite;

    @Bind(R.id.rel_primary) RelativeLayout rel_primary;
    @Bind(R.id.tv_primary) TextView tv_primary;
    @Bind(R.id.gv_primary) GridView gv_primary;

    @Bind(R.id.rel_middle) RelativeLayout rel_middle;
    @Bind(R.id.tv_middle) TextView tv_middle;
    @Bind(R.id.gv_middle) GridView gv_middle;

    @Bind(R.id.rel_high) RelativeLayout rel_high;
    @Bind(R.id.tv_high) TextView tv_high;
    @Bind(R.id.gv_high) GridView gv_high;

    private GuessListAdapter gvAdapterComposite;
    private GuessListAdapter gvAdapterPrimary;
    private GuessListAdapter gvAdapterMidlle;
    private GuessListAdapter gvAdapterHight;


    //	private List<GuessPicMisson> missionlist; //查询数据库对其，初级和中级逻辑判断
    private MyReceiver receiver;


    private List<GuessPicLevel> guessLevelLists;
    private List<GuessPicMisson> guessMissionList;
    private static final int MSG_MISSION_SUC = 0;
    private static final int MSG_MISSION_FAILED = 1;
    private final Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_MISSION_SUC:

                    List<GuessPic.MissionLevel> guess_levels = (List<GuessPic.MissionLevel>) msg.obj;

                    //TODO 存储数据有误
                    guessLevelLists = new ArrayList<GuessPicLevel>();
                    guessMissionList = new ArrayList<GuessPicMisson>();

                    for (int i = 0; i < guess_levels.size(); i++) {
                        GuessPic.MissionLevel guess_level = guess_levels.get(i);
                        GuessPicLevel guesslevelinfo = new GuessPicLevel(i + 1, guess_level.getLevelName(), guess_level.getBackgroundImage(), guess_level.getMissionCount());
//                            SDLog.d(TAG,"guess levle-->"+guesslevelinfo.toString());


                        List<GuessPic.Mission> missionlist = guess_level.getMissionList();

                        for (int j = 0; j < missionlist.size(); j++) {
                            GuessPic.Mission mission = missionlist.get(j);
                            GuessPicMisson info = new GuessPicMisson(j + 1, i + 1, mission.getTitle(), mission.getImage(), Constants.MISSION_LOCKED, mission.getUrl(), mission.getFilesize());
                            SDLog.d(TAG, "guess mission-->" + info.toString());
                            guessMissionList.add(info);
                        }
                        guessLevelLists.add(guesslevelinfo);
//                            SDLog.d(TAG, "guesslevel size-->"+guessLevelLists.size()+"--guessmission size>"+guessMissionList.size());
                    }
                    downLoadZip();
                    levleData();
                    break;
                case MSG_MISSION_FAILED:
                    ToastUtil.showToastText("获取信息失败");
                    break;
            }
        }
    };
    private GuessSmallMissionInfo missionInfo;


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_guess);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        rel_guessfirst.setVisibility(View.VISIBLE);
        ParseData();
    }


    /**
     * 解析数据
     */
    private void ParseData() {
        GuessPic.MissionPageReq req = GuessPic.MissionPageReq.newBuilder().build();
        TiangongClient.instance().send("chronos", "mission", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    GuessPic.MissionPage res = GuessPic.MissionPage.parseFrom(buffer);
                    if (res.getErrorno() == 0) {
                        List<GuessPic.MissionLevel> missionlevellist = res.getMissionLevelList();

                        Message msg = mHandler.obtainMessage();
                        msg.what = MSG_MISSION_SUC;
                        msg.obj = missionlevellist;
                        mHandler.sendMessage(msg);
                    } else {
                        mHandler.sendEmptyMessage(MSG_MISSION_FAILED);
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_DOWNLOAD_PROGRESS);
        filter.addAction(ACTION_DOWNLOAD_SUCCESS);
        filter.addAction(ACTION_DOWNLOAD_FAIL);
        registerReceiver(receiver, filter);
    }


    @OnClick({R.id.img_back})
    public void clickListener(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }


    private void levleData() {
        if (guessLevelLists != null && guessLevelLists.size() > 0) {
            for (GuessPicLevel guess_level : guessLevelLists) {
                SDLog.d(TAG, "-guess_level->" + guess_level.toString());
                switch (guess_level.getId()) {
                    //TODO 改为服务器显示什么数据就填什么
                    case 1://
                        rel_primary.setVisibility(View.VISIBLE);
                        tv_primary.setText(guess_level.getLevelname());
                        initLevelData(guess_level, 1);
                        break;
                    case 2://
                        rel_middle.setVisibility(View.VISIBLE);
                        tv_middle.setText(guess_level.getLevelname());
                        initLevelData(guess_level, 2);
                        break;
                    case 3://
                        rel_high.setVisibility(View.VISIBLE);
                        tv_high.setText(guess_level.getLevelname());
                        initLevelData(guess_level, 3);
                        break;
                }
            }
        }
    }

    /**
     * 数据的展示及下载
     *
     * @param guess_level
     * @param levelid
     */
    private void initLevelData(GuessPicLevel guess_level, int levelid) {
        final List<GuessEntity> guessEntity = new ArrayList<>();
        String levelimg = guess_level.getLevelimg();

        if (guessMissionList != null && !guessMissionList.isEmpty()) {
            for (GuessPicMisson guessMissionInfo : guessMissionList) {
                if (guessMissionInfo.getLevelid() == levelid) {
                    //Constructor
                    GuessEntity guess = new GuessEntity(guessMissionInfo.getLevelid(), guessMissionInfo.getId()
                            , levelimg, guessMissionInfo.getMissionimg(), guessMissionInfo.getMissiontitle(), guessMissionInfo.getMissionurl());
                    SDLog.d(TAG, "-initLevelData guessentity->" + guess.toString());
                    guessEntity.add(guess);
                }
            }
        }

        switch (levelid) {
            case 1: //初级
                gvAdapterPrimary = new GuessListAdapter(this, guessEntity);
                gv_primary.setAdapter(gvAdapterPrimary);
                gv_primary.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int poisition, long arg3) {
                        onItemClickContent(view, poisition, guessEntity, 1);
                    }
                });
                break;
            case 2://中级
                gvAdapterMidlle = new GuessListAdapter(this, guessEntity);
                gv_middle.setAdapter(gvAdapterMidlle);
                gv_middle.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int poisition, long arg3) {
                        onItemClickContent(view, poisition, guessEntity, 2);
                    }
                });
                break;
            case 3://高级
                gvAdapterHight = new GuessListAdapter(this, guessEntity);
                gv_high.setAdapter(gvAdapterHight);
                gv_high.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int poisition, long arg3) {
                        onItemClickContent(view, poisition, guessEntity, 3);
                    }
                });
                break;
            default:
                break;
        }
    }


    /**
     * 对其综合，初级，中级，高级业务逻辑分开处理
     *
     * @param view
     * @param position
     * @param guessEntity
     * @param leveid
     */
    private void onItemClickContent(View view, int position, List<GuessEntity> guessEntity, int leveid) {
        TextView tv_levelname = ViewHolder.get(view, R.id.tv_levelname);
        String levelname = tv_levelname.getText().toString();
        SDLog.d(TAG, "position-->" + position);
        try {
            if (position == 0) {
                downLoadZip();
                isStartGuessMissionListActivity(position, guessEntity, leveid);
            } else {
                List<GuessSmallMissionInfo> list = db.findAll(Selector.from(GuessSmallMissionInfo.class).where("levelId", "=", leveid).and("missionid", "=", position));
                if (list != null && !list.isEmpty()) {
                    SDLog.d(TAG, "-db->" + list.size());
                    missionInfo = list.get(list.size() == 0 ? 0 : list.size() - 1);
                    if (missionInfo.getMissionstatus() == 1) {
                        isStartGuessMissionListActivity(position, guessEntity, leveid);
                    } else {
                        ToastUtil.showToastText("请先解锁上一关卡中的关卡");
                    }
                } else {
                    ToastUtil.showToastText("请先解锁上一关卡中的关卡");
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void isStartGuessMissionListActivity(int position, List<GuessEntity> guessEntity, int leveid) {
        GuessEntity entity = guessEntity.get(position);
        SDLog.d(TAG, "-guess entity-->" + entity.toString());

        int lasthash = entity.downloadurl.lastIndexOf("/");
        int lastindex = entity.downloadurl.lastIndexOf(".");
        String filename = entity.downloadurl.substring(lasthash + 1);
        String filepath = entity.downloadurl.substring(lasthash + 1, lastindex);
        SDLog.d(TAG, "isOther-->" + lasthash + "," + lastindex + "," + filename + "," + filepath);

        //StartActivity
        Intent intent = new Intent(baseContext, GuessMissionListActivity.class);
        intent.putExtra("filepath", filepath);
        intent.putExtra("filename", filename);
        intent.putExtra("leveid", leveid);
        intent.putExtra("missionid", entity.getMissionid());
        ActivityUtil.startActivity(baseContext, intent);
    }


    @Override
    protected void updateUI() {
    }


    @Override
    protected void processLogic() {
    }


    /**
     * 下载zip包
     */
    public void downLoadZip() {
        for (GuessPicMisson guessMissionInfo : guessMissionList) {
            String missionurl = guessMissionInfo.getMissionurl();
            String filename = missionurl.substring(missionurl.lastIndexOf("/") + 1);

            SDLog.d(TAG, "--initLevelData filename->" + filename + ",guess mission-->" + guessMissionInfo.toString());
            String path = GuessUtils.GUESS_DOWNLOAD_PATH + filename;
            File f = new File(path);

            if (!f.exists()) {
                startDownloadService(missionurl);
            } else {
                //TODO 获取文件大小进行比对，删除zip文件及解压后的文件夹
                SDLog.d(TAG, "-filesize->" + f.length() + "-missioninfo->" + guessMissionInfo.getFilesize());
                if (f.length() != 0 && f.length() != guessMissionInfo.getFilesize()) {
                    deleteFile(path);
                    deleteFolder(GuessUtils.GUESS_RESOURCE_PATH + filename.substring(0, filename.lastIndexOf(".")));
                    startDownloadService(missionurl);
                    Constants.GUESSGAMEIS_ISDOWN = true;
                }
            }
        }
    }


    public void startDownloadService(String url) {
        if (DownloadService.getInstance() != null && DownloadService.getInstance().getFlag() != DownloadService.Flag_Init) {
            SDLog.d(TAG, "已经在下载");
            return;
        }
        Intent it = new Intent(this, DownloadService.class);
        it.putExtra("flag", "start");
        it.putExtra("url", url);
        startService(it);
    }


    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_DOWNLOAD_PROGRESS)) {
                int pro = intent.getExtras().getInt("progress");
            } else if (action.equals(ACTION_DOWNLOAD_SUCCESS)) {
                SDLog.d(TAG, "guess--下载成功");
                Intent it = new Intent(GuessPicActivity.this, DownloadService.class);
                it.putExtra("flag", "stop");
                startService(it);
            } else if (action.equals(ACTION_DOWNLOAD_FAIL)) {
                SDLog.d(TAG, "下载失败");
                Intent it = new Intent(GuessPicActivity.this, DownloadService.class);
                it.putExtra("flag", "stop");
                startService(it);
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }


    /**
     * 根据路径删除文件或目录，无论存在与否
     *
     * @param path
     * @return
     */
    public boolean deleteFolder(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            //不存在就提示没有数据
            SDLog.d(TAG, "没有文件目录");
            return flag;
        } else {

            //存在删除
            if (file.isFile()) {//判断是否为文件
                //删除文件
                return deleteFile(path);
            } else {
                //删除目录
                return deleteDirectory(path);
            }
        }
    }


    /**
     * 删除单个文件
     *
     * @param path
     */
    public boolean deleteFile(String path) {
        boolean flag = false;
        File f = new File(path);
        if (f.isFile() && f.exists()) {
            f.delete();
            SDLog.d(TAG, "删除单个文件成功");
            return flag = true;
        }
        return flag;
    }

    /**
     * 删除目录
     *
     * @param path
     */
    public boolean deleteDirectory(String path) {

        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }

        File dirFile = new File(path);

        //如果dir对应的文件不存在或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }

        boolean flag = true;

        //删除文件夹下的所有文件（包括子目录）
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile()) {//删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {//删除目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                SDLog.d(TAG, "删除目录文件成功");
                if (!flag) break;
            }
        }

        if (!flag) return false;

        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }

    }

}
