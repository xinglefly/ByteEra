package com.zhicai.byteera.activity.traincamp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.GuessDb;
import com.zhicai.byteera.activity.bean.GuessGameEntity;
import com.zhicai.byteera.activity.bean.GuessSmallMissionInfo;
import com.zhicai.byteera.activity.traincamp.adapter.GuessAnswerAdapter;
import com.zhicai.byteera.activity.traincamp.adapter.GuessOptionsAdapter;
import com.zhicai.byteera.activity.traincamp.util.GsonUtils;
import com.zhicai.byteera.activity.traincamp.util.GuessUtils;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.SDLog;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuessGameActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = GuessGameActivity.class.getSimpleName();

    @Bind(R.id.img_back) ImageView back;
    @Bind(R.id.img_question) ImageView question;

    //coin
    @Bind(R.id.tv_game_coin) TextView tv_game_coin;

    //tips
    @Bind(R.id.lay_tips) LinearLayout lay_tips;
    @Bind(R.id.img_tips) ImageView img_tips;
    @Bind(R.id.tv_showtips) TextView tv_showtips;

    //helper
    @Bind(R.id.lay_helper) LinearLayout lay_helper;

    //answer
    @Bind(R.id.lay_right_answer) LinearLayout lay_right_answer;
    @Bind(R.id.tv_answer_coin) TextView tv_answer_coin;
    @Bind(R.id.img_right_answer) ImageView img_right_answer;

    //pass
    @Bind(R.id.lay_pass) LinearLayout lay_pass;
    @Bind(R.id.tv_pass_coin) TextView tv_pass_coin;
    @Bind(R.id.img_pass) ImageView img_pass;

    //image
    @Bind(R.id.img_gamepic) ImageView img_gamepic;

    //gv_answer
    @Bind(R.id.gv_answer) GridView gv_answer;
    //gv_options
    @Bind(R.id.gv_options) GridView gv_options;


    private GuessAnswerAdapter answer_adapter;
    private GuessOptionsAdapter game_adapter;
    private GuessGameEntity entity;

    //getIntent
    private String filepath;
    private int entity_id;
    private int gridview_id;
    private int leveid;
    private int mission_id;
    private int guesssmallsize;

    //builder
    private View customView;
    private AlertDialog alertDialog;

    //StringBuilder to replace StringBuffer
    private StringBuilder sbAnswer;//首次获取答题区
    private StringBuilder sbCheck;//选中答案
    public static StringBuilder sbRandom = new StringBuilder(); //随机

    private boolean flag;
    private int length;
    private List<String> listAnswer;
    private List<String> listOptions;
    private HashMap<Integer, String> map = new HashMap<>(); //gridView点击后找到这个map中的值，将其list.set(position,"")，主要是为了记录options的每个位置
    private int count = 0;        //作为点击的次数来统计options向answer中填充数据
    private int[] ran = new int[4];//产生随机数过滤自个错别字（目前对于过滤正确字没做处理）

    //消耗财币 action
    private boolean tip_flag;
    private boolean answer_flag;
    private int pass_flag = 1;

    private boolean next_flag; //判断下一题

    private static final int MSG_GAMECOIN_SUCC = 0;
    private static final int MSG_GAMECOIN_FAIL = 1;
    private static final int MSG_GAMECOIN_ERROR = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GAMECOIN_SUCC:
                    int position = (Integer) msg.obj;
                    switch (position) {
                        //1-4 pass  5-6 tips  7 helper 8-9 right_answer
                        case 0: //下一题
                            next_flag = true;
                            clearList();
                            notifyLayout();
                            initData();
                            break;
                        case 1:
                            tv_pass_coin.setText(10 + "");
                            Constants.Guess_filter_1 = listOptions.get(ran[0]);
                            notifyGvOptions();
                            break;
                        case 2:
                            tv_pass_coin.setText(15 + "");
                            Constants.Guess_filter_2 = listOptions.get(ran[1]);
                            notifyGvOptions();
                            break;
                        case 3:
                            tv_pass_coin.setText(25 + "");
                            Constants.Guess_filter_3 = listOptions.get(ran[2]);
                            notifyGvOptions();
                            break;
                        case 4:
                            lay_pass.setEnabled(false);
                            tv_pass_coin.setBackgroundColor(getResources().getColor(R.color.gray));
                            img_pass.setBackgroundColor(getResources().getColor(R.color.gray));
                            Constants.Guess_filter_4 = listOptions.get(ran[3]);
                            notifyGvOptions();
                            break;
                        case 5:
                            TextView tv_tips = setTipsDialog();
                            tv_tips.setText(entity.tip1);
                            tip_flag = true;
                            break;
                        case 6:
                            tv_tips = setTipsDialog();
                            tv_tips.setText(entity.tip2);
                            lay_tips.setEnabled(false);
                            tv_showtips.setBackgroundColor(getResources().getColor(R.color.gray));
                            img_tips.setBackgroundColor(getResources().getColor(R.color.gray));
                            break;
                        case 7:
                            break;
                        case 8:
                            tv_answer_coin.setText(60 + "");
                            answer_flag = true;

                            //处理第一个正确字
                            if (sbCheck.length() != 0) {
                                //遍历map中匹配sbCheck中的第一个数字
                                for (Map.Entry<Integer, String> m : map.entrySet()) {
                                    if (m.getValue().equals(sbCheck.substring(0, 1))) {
                                        SDLog.d(TAG, "-map --key>" + m.getKey());
                                        listOptions.set(m.getKey(), sbCheck.substring(0, 1));
                                    }
                                }
                                sbCheck.deleteCharAt(0);
                            }
                            listAnswer.set(0, sbAnswer.substring(0, 1));
                            sbCheck.insert(0, sbAnswer.substring(0, 1));
                            notifyGvAnswer(1);
                            break;
                        case 9:
                            lay_right_answer.setEnabled(false);
                            tv_answer_coin.setBackgroundColor(getResources().getColor(R.color.gray));
                            img_right_answer.setBackgroundColor(getResources().getColor(R.color.gray));


                            //第二个正确字
                            if (sbCheck.length() > 1) {
                                //遍历map中匹配sbCheck中的第一个数字
                                for (Map.Entry<Integer, String> m : map.entrySet()) {
                                    if (m.getValue().equals(sbCheck.substring(1, 2))) {
                                        SDLog.d(TAG, "-map --key>" + m.getKey());
                                        listOptions.set(m.getKey(), sbCheck.substring(1, 2));
                                    }
                                }
                                sbCheck.deleteCharAt(1);
                            }
                            listAnswer.set(1, sbAnswer.substring(1, 2));
                            sbCheck.insert(1, sbAnswer.substring(1, 2));
                            notifyGvAnswer(2);
                            break;
                    }
                    updateUI();
                    break;
                case MSG_GAMECOIN_FAIL:
                    ToastUtil.showToastText("你没有足够的金币");
                    break;
                case MSG_GAMECOIN_ERROR:
                    ToastUtil.showToastText("服务器异常...");
                    break;
            }
        }


    };

    /**
     * 初始化控件
     */
    private void notifyLayout() {
        tip_flag = false;
        answer_flag = false;
        pass_flag = 1;
        lay_pass.setEnabled(true);
        tv_pass_coin.setBackgroundColor(Color.parseColor("#4DCE46"));
        img_pass.setBackgroundColor(Color.parseColor("#279321"));

        lay_tips.setEnabled(true);
        tv_showtips.setBackgroundColor(Color.parseColor("#FB5151"));
        img_tips.setBackgroundColor(Color.parseColor("#CF3636"));

        lay_right_answer.setEnabled(true);
        tv_answer_coin.setBackgroundColor(Color.parseColor("#718FFF"));
        img_right_answer.setBackgroundColor(Color.parseColor("#3659DC"));
    }

    /**
     * 下一题时清空缓存
     */
    public void clearList() {
        flag = false;
        sbAnswer.delete(0, sbAnswer.toString().length());
        listAnswer.clear();
        listOptions.clear();
        map.clear();
        count = 0;
        Constants.Guess_filter_1 = "";
        Constants.Guess_filter_2 = "";
        Constants.Guess_filter_3 = "";
        Constants.Guess_filter_4 = "";
        answer_flag = false;
        tip_flag = false;
        pass_flag = 1;
    }

    /**
     * 刷新显示区
     */
    private void notifyGvOptions() {
        BaseAdapter sa = (BaseAdapter) gv_options.getAdapter();
        sa.notifyDataSetChanged();
    }

    /**
     * 刷新答题区
     */
    private void notifyGvAnswer(int size) {
        if (sbCheck.length() == size) {
            count = size;
        } else {
            count = sbCheck.length();
        }
        SDLog.d(TAG, "-game count->" + count + ",sbcheck-->" + sbCheck.length() + "," + sbCheck.toString());
        answer_adapter.notifyDataSetChanged();
        game_adapter.notifyDataSetChanged();
    }


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_guess_game);
        ButterKnife.bind(this);
    }


    /**
     * 解析数据
     */
    private void parseJson() throws DbException, Exception {
        if (next_flag) {
            entity_id += 1;
            gridview_id += 1;
            if (gridview_id == guesssmallsize + 1) {
                ToastUtil.showToastText("当前最后一关,解锁下一关卡");
                finish();
                return;
            } else {
                SDLog.d(TAG, "-game entity_id true->" + entity_id + ",gridview_id-->" + gridview_id);

                //记录关卡的进度
                GuessDb guessDb = db.findFirst(Selector.from(GuessDb.class).where("level_id", "=", leveid).and("mission_id", "=", mission_id));
                if (guessDb != null) {
                    GuessDb guess = new GuessDb(guessDb.getId(), leveid, mission_id, entity_id);
                    db.update(guess, "level_id", "mission_id", "game_id");
                }

                //记录关卡的进度进行判断
                GuessSmallMissionInfo missionInfo = new GuessSmallMissionInfo(entity_id, leveid, mission_id, 1);
                db.update(missionInfo, "levelId", "missionid", "missionstatus");
                SDLog.d(TAG, "-game update db mission->" + missionInfo.toString());
                baseContext.sendBroadcast(new Intent(Constants.GUESSPICGAMEACTION));
            }
        } else {
            entity_id = getIntent().getExtras().getInt("entity_id");
            gridview_id = getIntent().getExtras().getInt("gridview_id");
            leveid = getIntent().getExtras().getInt("leveid");
            mission_id = getIntent().getExtras().getInt("mission_id");
            filepath = getIntent().getExtras().getString("filepath");
            guesssmallsize = getIntent().getExtras().getInt("guesssmallsize");
            SDLog.d(TAG, "-game entity_id false->" + entity_id + ",filepath-->" + filepath + ",-missionid->" + mission_id + ",-gridview_id->" + gridview_id + ",guesssmallsize->" + guesssmallsize);
        }
        GuessSmallMissionInfo missioninfo = db.findById(GuessSmallMissionInfo.class, entity_id);
        entity = GsonUtils.json2bean(missioninfo.getMissionjson(), GuessGameEntity.class);
    }

    /**
     * 加载图片
     */
    private void setControlValue() {
        //TODO 要遍历整个文件夹
        String imagepath = GuessUtils.GUESS_RESOURCE_PATH + filepath + File.separator + "image" + File.separator + entity.image;
        SDLog.d(TAG, "game pic-->" + imagepath);
        ImageLoader.getInstance().displayImage(imagepath, img_gamepic);
        tv_game_coin.setText(MyApp.getInstance().getCoinCnt() + "");
    }

    @Override
    protected void initData() {
        try {
            parseJson();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setControlValue();

        PreferenceUtils.getInstance(baseContext).setGuessPicFlag(false); //判断答题区答案是否正确

        //setAdapter
        gridViewAnswer();
        gridViewOptions();
        getRandomFilter();
    }

    /**
     * 过滤字处理
     */
    public void getRandomFilter() {
        for (int i = 0; i < 4; i++) {
            ran[i] = new Random().nextInt(listOptions.size());
            SDLog.d(TAG, "--game ran->" + ran[i]);
        }
    }

    private boolean isSbCheck;  //

    /**
     * 答案区
     */
    private void gridViewAnswer() {
        sbAnswer = new StringBuilder();
        listAnswer = new ArrayList<String>();
        String[] answers = entity.answer;
        for (int i = 0; i < answers.length; i++) {
            sbAnswer.append(answers[i]);
            listAnswer.add("");
        }
        SDLog.d(TAG, "-game sb-->" + sbAnswer.toString() + "," + answers.length);
        length = answers.length;
        gv_answer.setNumColumns(answers.length);
        answer_adapter = new GuessAnswerAdapter(this, listAnswer, flag);
        gv_answer.setAdapter(answer_adapter);

        //item
        gv_answer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView tv_answer_value = ViewHolder.get(view, R.id.tv_answer_value);
                String value = tv_answer_value.getText().toString();
                if (!value.equals("")) {
                    //效率高的原理是entryset遍历一次，key和value都是放在entry
                    for (Map.Entry<Integer, String> m : map.entrySet()) {
                        if (m.getValue().equals(value)) {
                            SDLog.d(TAG, "-game map --key>" + m.getKey());
                            listOptions.set(m.getKey(), value);
                            game_adapter.notifyDataSetChanged();
                        }
                    }
                    //TODO 动态判断listAnswer的大小
                    SDLog.d(TAG, "-game gv_answer-position>" + position + ",size-->" + listAnswer.size() + ",sbCheck-->" + sbCheck.toString() + ",count->" + count);
                    listAnswer.set(position, "");
                    answer_adapter.notifyDataSetChanged();
                    sbCheck.deleteCharAt(position);
                    sbCheck.insert(position, position);

                    //截取sbcheck每个字符进行匹配，对count赋值
                    if (sbCheck.substring(0, 1).equals("0")) {
                        count = 0;
                    } else if (sbCheck.substring(1, 2).equals("1")) {
                        count = 1;
                    } else if (sbCheck.substring(2, 3).equals("2")) {
                        count = 2;
                    } else if (sbCheck.substring(3, 4).equals("3")) {
                        count = 3;
                    } else if (sbCheck.substring(4, 5).equals("4")) {
                        count = 4;
                    } else if (sbCheck.substring(5, 6).equals("5")) {
                        count = 5;
                    } else if (sbCheck.substring(6, 7).equals("6")) {
                        count = 6;
                    } else if (sbCheck.substring(7, 8).equals("7")) {
                        count = 7;
                    } else {
                        count = position;
                    }
                    isSbCheck = true;
                }

                SDLog.d(TAG, "-game sbquestion delete-->" + sbCheck.toString() + ",count->" + count);
                PreferenceUtils.getInstance(baseContext).setGuessPicFlag(false);
                BaseAdapter sa = (BaseAdapter) gv_answer.getAdapter();
                sa.notifyDataSetChanged();
            }
        });
    }


    /**
     * 显示区
     */
    private void gridViewOptions() {
        //gridview options
        listOptions = initOptionsDatas();
        game_adapter = new GuessOptionsAdapter(this, listOptions);
        gv_options.setAdapter(game_adapter);

        sbCheck = new StringBuilder();

        //itemClick
        gv_options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_item = ViewHolder.get(view, R.id.tv_item);
                String tv_option_value = tv_item.getText().toString();
                SDLog.d(TAG, "game tv_option_value-->" + tv_option_value + ",postion->" + position + "," + sbCheck.toString());


                if (count < length && !tv_option_value.equals("")) {
                    map.put(position, tv_option_value);
                    listOptions.set(position, "");
                    game_adapter.notifyDataSetChanged();

                    if (isSbCheck) {
                        //这里判断对其answer区进行删除的时候，sbcheck字符串会替换为“0123567”,然后对其字符进行匹配获取字符的位置进行删除
                        if (sbCheck.toString().contains("0")) {
                            sbCheck.deleteCharAt(sbCheck.indexOf("0"));
                            count = 0;
                        } else if (sbCheck.toString().contains("1")) {
                            sbCheck.deleteCharAt(sbCheck.indexOf("1"));
                            count = 1;
                        } else if (sbCheck.toString().contains("2")) {
                            sbCheck.deleteCharAt(sbCheck.indexOf("2"));
                            count = 2;
                        } else if (sbCheck.toString().contains("3")) {
                            sbCheck.deleteCharAt(sbCheck.indexOf("3"));
                            count = 3;
                        } else if (sbCheck.toString().contains("4")) {
                            sbCheck.deleteCharAt(sbCheck.indexOf("4"));
                            count = 4;
                        } else if (sbCheck.toString().contains("5")) {
                            sbCheck.deleteCharAt(sbCheck.indexOf("5"));
                            count = 5;
                        } else if (sbCheck.toString().contains("6")) {
                            sbCheck.deleteCharAt(sbCheck.indexOf("6"));
                            count = 6;
                        } else if (sbCheck.toString().contains("7")) {
                            sbCheck.deleteCharAt(sbCheck.indexOf("7"));
                            count = 7;
                        } else {
                            //TODO
                        }
                        SDLog.d(TAG, "game 处理-->" + sbCheck.toString() + ",size->" + sbCheck.length() + ",count->" + count);
                        isSbCheck = false;
                    }

                    //在当前字符串插入数据
                    sbCheck.insert(count, tv_option_value);
                    SDLog.d(TAG, "game 插入-->" + sbCheck.toString() + ",size->" + sbCheck.length() + ",count->" + count);


                    //修改listAnswer对应position的值
                    listAnswer.set(count, tv_option_value);
                    answer_adapter.notifyDataSetChanged();

                    //判断的关键点还是找到sbcheck中的"1234567",为了定位到下一个要填充的数字，并且不让其大小超过其length值。删除匹配后的数值，对其count定位
                    //上面和下面的方式结合完成了链表结构的算法，我错位删除后对其下一个值进行判断是否为有值
                    if (sbCheck.toString().contains("1")) {
                        count = 1;
                        if (sbCheck.toString().contains("1")) sbCheck.deleteCharAt(sbCheck.indexOf("1"));
                    } else if (sbCheck.toString().contains("2")) {
                        count = 2;
                        if (sbCheck.toString().contains("2")) sbCheck.deleteCharAt(sbCheck.indexOf("2"));
                    } else if (sbCheck.toString().contains("3")) {
                        count = 3;
                        if (sbCheck.toString().contains("3")) sbCheck.deleteCharAt(sbCheck.indexOf("3"));
                    } else if (sbCheck.toString().contains("4")) {
                        count = 4;
                        if (sbCheck.toString().contains("4")) sbCheck.deleteCharAt(sbCheck.indexOf("4"));
                    } else if (sbCheck.toString().contains("5")) {
                        count = 5;
                        if (sbCheck.toString().contains("5")) sbCheck.deleteCharAt(sbCheck.indexOf("5"));
                    } else if (sbCheck.toString().contains("6")) {
                        count = 6;
                        if (sbCheck.toString().contains("6")) sbCheck.deleteCharAt(sbCheck.indexOf("6"));
                    } else if (sbCheck.toString().contains("7")) {
                        count = 7;
                        if (sbCheck.toString().contains("7")) sbCheck.deleteCharAt(sbCheck.indexOf("7"));
                    } else {
                        count += 1;
                    }
                    SDLog.d(TAG, "game count最后赋值->" + count);
                }

                updateAdapter();
            }
        });
    }

    /**
     * 点击显示区某一内容填充到答题区
     *
     * @param
     */
    private void updateAdapter() {
        SDLog.d(TAG, "game sbquestion-->" + sbCheck.toString() + ",size->" + sbCheck.length() + "-count->" + count);

        if (sbCheck.length() == length) {
            if (sbCheck.toString().equals(sbAnswer.toString())) {
                //TODO 完成之后
                setAlertButtonClick(sbAnswer.toString());
            } else {
                PreferenceUtils.getInstance(baseContext).setGuessPicFlag(true);
                BaseAdapter sa = (BaseAdapter) gv_answer.getAdapter();
                sa.notifyDataSetChanged();
            }
        }
    }

    private List<String> initOptionsDatas() {
        //options
        List<String> list = new ArrayList<String>();
        String[] options = entity.option;
        for (int i = 0; i < options.length; i++) {
            list.add(options[i]);
        }
        return list;
    }


    @OnClick({R.id.img_back, R.id.lay_tips, R.id.lay_helper, R.id.lay_right_answer, R.id.lay_pass})
    public void clickListener(View v) {
        Builder builder = myDialogBuilder(R.layout.dialog_game);
        alertDialog = builder.show();

        TextView tv_consumecoin_explain = (TextView) customView.findViewById(R.id.tv_consumecoin_explain);
        Button btn_no = (Button) customView.findViewById(R.id.btn_no);
        Button btn_ok = (Button) customView.findViewById(R.id.btn_ok);

        int position = 0;
        switch (v.getId()) {
            case R.id.img_back:
                alertDialog.dismiss();
                Constants.Guess_filter_1 = "";
                Constants.Guess_filter_2 = "";
                Constants.Guess_filter_3 = "";
                Constants.Guess_filter_4 = "";
                finish();
                break;
            case R.id.lay_tips:
                if (tip_flag) {
                    tv_consumecoin_explain.setText("是否花费30财币提示品牌信息！");
                    position = 6;
                    setConsumeCoin(alertDialog, btn_no, btn_ok, -30, position);
                } else {
                    tv_consumecoin_explain.setText("是否花费15财币提示品牌信息！");
                    position = 5;
                    setConsumeCoin(alertDialog, btn_no, btn_ok, -15, position);
                }
                break;
            case R.id.lay_helper:
                position = 7;
                //TODO 分享好友
                alertDialog.dismiss();
                ToastUtil.showToastText("helper");
                break;
            case R.id.lay_right_answer:
                if (answer_flag) {
                    tv_consumecoin_explain.setText("再次花费60财币补充第二个正确字！");
                    position = 9;
                    setConsumeCoin(alertDialog, btn_no, btn_ok, -60, position);
                } else {
                    tv_consumecoin_explain.setText("花费30财币将补充一个正确字！");
                    position = 8;
                    setConsumeCoin(alertDialog, btn_no, btn_ok, -30, position);
                }
                break;
            case R.id.lay_pass:
                if (pass_flag == 1) {
                    tv_consumecoin_explain.setText("花费5财币排除一个错误字！");
                    position = 1;
                    setConsumeCoin(alertDialog, btn_no, btn_ok, -5, position);
                    pass_flag = 2;
                } else if (pass_flag == 2) {
                    tv_consumecoin_explain.setText("花费10财币排除第二个错误字！");
                    position = 2;
                    setConsumeCoin(alertDialog, btn_no, btn_ok, -10, 2);
                    pass_flag = 3;
                } else if (pass_flag == 3) {
                    tv_consumecoin_explain.setText("花费15财币排除第三个错误字！");
                    position = 3;
                    setConsumeCoin(alertDialog, btn_no, btn_ok, -15, 3);
                    pass_flag = 4;
                } else {
                    tv_consumecoin_explain.setText("花费25财币排除第四个错误字！");
                    position = 4;
                    setConsumeCoin(alertDialog, btn_no, btn_ok, -25, 4);
                }
                break;
        }
    }

    /**
     * 点击tips减去才不后提示相关信息
     *
     * @return
     */
    private TextView setTipsDialog() {
        Builder builder = myDialogBuilder(R.layout.dialog_tip);
        alertDialog = builder.show();
        TextView tv_tips = (TextView) customView.findViewById(R.id.tv_tips);
        final ImageView img_tipcanle = (ImageView) customView.findViewById(R.id.img_tipcanle);
        img_tipcanle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        return tv_tips;
    }


    /**
     * 作为构造函数的方式对其所有财币的动作进行捕获
     */
    private void setConsumeCoin(final AlertDialog alertDialog, Button btn_no, Button btn_ok, final int coin, final int position) {

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consumeCoin(position, coin);
                alertDialog.dismiss();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }


    /**
     * 答案正确后提示
     */
    private void setAlertButtonClick(String answer) {
        Builder builder = myDialogBuilder(R.layout.dialog_confirm_next);
        alertDialog = builder.show();

        TextView tv_title = (TextView) customView.findViewById(R.id.tv_title);
        TextView tv_answer_explain = (TextView) customView.findViewById(R.id.tv_answer_explain);
        tv_title.setText(answer);
        tv_answer_explain.setText(entity.intro);


        ImageView img_canle = (ImageView) customView.findViewById(R.id.img_canle);
        Button btn_share = (Button) customView.findViewById(R.id.btn_share);
        Button btn_next = (Button) customView.findViewById(R.id.btn_next);
        img_canle.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_canle:
                alertDialog.dismiss();
                break;
            case R.id.btn_share:
                //TODO 分享
                ToastUtil.showToastText("分享");
                break;
            case R.id.btn_next:
                //TODO 下一关
                consumeCoin(0, 30);
                alertDialog.dismiss();
                break;
        }
    }


    public Bitmap shot(Activity activity) {
        //你要截图的view
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        Bitmap b1 = view.getDrawingCache();
        //获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        SDLog.d(TAG, "" + statusBarHeight);
        //获取屏幕的长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        //去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
        view.destroyDrawingCache();
        return b;
    }


    @Override
    protected void updateUI() {
        tv_game_coin.setText(MyApp.getInstance().getCoinCnt() + "");
    }

    @Override
    protected void processLogic() {
    }


    protected Builder myDialogBuilder(int dialog_xml) {
        final LayoutInflater inflater = this.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        customView = inflater.inflate(dialog_xml, null);

        return builder.setView(customView);
    }


    /**
     * 财币加减
     *
     * @param position
     * @param coin
     */
    private void consumeCoin(final int position, int coin) {
        String userId = PreferenceUtils.getInstance(this).getUserId();
        SDLog.d(TAG, "-game userid->" + userId + ",coin->" + coin);
        if (!isStartLoginActivity()) {
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
                        } else if (res.getErrorno() == 500) {
                            mHandler.sendEmptyMessage(MSG_GAMECOIN_ERROR);
                        } else {
                            mHandler.sendEmptyMessage(MSG_GAMECOIN_FAIL);
                        }
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
        updateAdapter();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clearList();
            finish();
        }
        return false;
    }


}
