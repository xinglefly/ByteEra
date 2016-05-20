package com.zhicai.byteera.commonutil;

import android.graphics.Bitmap;
import android.os.Environment;

/**
 * 常量定义
 *
 * @author xinglefly
 */
@SuppressWarnings("unused")
public class Constants {
    /********************* LoadingPage中使用的常量 ******************/
    public static final int STATE_LOADING = 1;//加载状态
    public static final int STATE_ERROR = 3;//加载完毕，但是出错状态
    public static final int STATE_EMPTY = 4;//加载完毕，但是没有数据状态

    /*********** 文件图片储存位置 *******************************/
    public static final String ETAXER_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhicai";
    public static final String AVATAR_PATH_IN_SDCARD = ETAXER_PATH + "/picture";


    /***************************************************/
    //发布动态时最多选择图片的张数
    public static int PHOTO_NUM = 3;

    /******************** 定义SharedPreference相关常量 开始 **********************/
    public static final String BYTEERA_ISSTARTGUIDE = "byteera_isStartGuide";
    public static final String BYTEERA_ISLOGINED = "byteera_logined";
    public static final String BYTEERA_LOGIN_USERNAME = "byteera_login_username";
    public static final String BYTEERA_LOGIN_USERID = "byteera_login_userid";
    public static final String BYTEERA_LOGIN_PWD = "byteera_login_pwd";
    public static final String BYTEERA_USER_ID = "byteera_user_id";
    public static final String BYTEERA_MOBILE_PHONE = "byteera_mobile_phone";
    public static final String BYTEERA_COMPANIESLIST = "byteera_companiesList";
    /******************** 定义SharedPreference相关常量 结束 **********************/


    /******************** 环信相关常量 begin **********************/
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "account_conflict";
    public static final String SYSTEM_MESSAGE_ACCOUNT = "caixun";
    public static final String SMALL_SECRETARY_ACCOUNT = "secretary";
    /******************** 环信相关常量 end **********************/


    /******************** 定义猜图常量 start **********************/
    public static final String BYTEERA_DB = "byteera.db";
    public static final String GUESSPIC_DB = "guesspic.db";
    public static final int MISSION_LOCKED = 0;
    public static final int MISSION_PASSED = 1;
    public static final int MISSION_FAILED = 2;
    /******************** 定义猜图常量 end **********************/


    /******************** 定义任务常量 start **********************/
    public static final String BYTEERA_TODAY = "byteera_today";
    public static final String BYTEERA_COINCOUNT = "byteera_coincount";
    public static final String BYTEERA_COINRANK = "byteera_coinrank";
    public static final String BYTEERA_COINLOGINDAYS = "byteera_coinlogindays";
    public static final String BYTEERA_DAYLOGIN = "byteera_daylogin";


    //客户端判断是否和服务器交互一次
    public static final String BYTEERA_DYNAMIC_PUBLISH_TIME = "byteera_dynamic_publish_time"; //是否发布动态1次
    public static final String BYTEERA_DYNAMIC_COMMENT_TIME = "byteera_dynamic_comment_time"; //是否评论动态1次
    public static final String BYTEERA_PRAISE_SEND_TIME = "byteera_praise_send_time";         //是否发送点赞1次
    public static final String BYTEERA_PRAISE_RECEIVE_TIME = "byteera_praise_receive_time";     //是否收到点赞1次
    public static final String BYTEERA_COMMENT_ESSAY_TIME = "byteera_comment_essay_time";     //是否评论文章1次
    public static final String BYTEERA_TOPIC_DISCUSS_TIME = "byteera_topic_discuss_time";      //是否参与话题讨论1次

    //是否领取
    public static final String BYTEERA_DYNAMIC_PUBLISH = "byteera_dynamic_publish"; //是否发布动态领取财币
    public static final String BYTEERA_DYNAMIC_COMMENT = "byteera_dynamic_comment"; //是否评论动态领取财币
    public static final String BYTEERA_PRAISE_SEND = "byteera_praise_send";            //是否发送点赞领取财币
    public static final String BYTEERA_PRAISE_RECEIVE = "byteera_praise_receive";    //是否收到点赞领取财币
    public static final String BYTEERA_COMMENT_ESSAY = "byteera_comment_essay";     //是否评论文章领取财币
    public static final String BYTEERA_TOPIC_DISCUSS = "byteera_topic_discuss";    //是否参与话题讨论领取财币
    /******************** 定义任务常量 end **********************/


    /******************** 定义广播常量 end **********************/
//    public static final String DAYTASKBROADCASTACTION = "daytask_broadcast";
    public static final String GUESSPICGAMEACTION = "guesspicgameaction";
    public static final String GROUPACTION = "group_broadcast";
    public static final String ORGANIZATIONACTION = "organization_broadcast";
    public static final String SHOUYIIONACTION = "shouyi_broadcast";
    /******************** 定义广播常量 end **********************/

    /********************** ShareSDK ***********************************/
    public static final String APPKEY = "779123d656a0";
    public static final String APPSECRET = "5a9cd8e304749ee8c222e03bf1ed60e0";
    /**********************END***********************************/

    /******************** 猜图 常量 **********************/
    public static final String GUESS_FLAG = "guess_flag";
    public static boolean GUESS_GAME_FLAG = true;
    public static boolean GUESSGAMEIS_ISDOWN = false;    //下载重新下载 true覆盖数据库
    public static String Guess_filter_1 = "";
    public static String Guess_filter_2 = "";
    public static String Guess_filter_3 = "";
    public static String Guess_filter_4 = "";
    /********************猜图 常量**********************/

    /******* 用户从登录界面返回 *********/
    public static final int RETURN_FROM_LOGIN = 123;
    public static final int RESULT_OK = 124;

    /********* 定义跳转到登录界面后的返回值  确定是哪个界面跳转过来的 ***********/
    public static final int organizationhomeactivity_userhomeproductitem = 0x11;

    public static final int SELECT_FINISH = 1;
    public static Bitmap bimap;

    /**************** KnowWealthFragment中的常量 ***************/
    public static final int ZHICAI_ALL = 1;         // 全部
    public static final int ZHICAI_P2P = 2;         // P2P
    public static final int ZHICAI_ZHONGCHOU = 3;   // 众筹
    public static final int ZHICAI_PIAOJU = 4;      // 票据
    public static final int ZHICAI_ZXYH = 5;        // 直销银行
    public static final int ZHICAI_HLWBX = 6;       // 互联网保险
    public static final int ZHICAI_HLWLC = 7;       // 互联网理财
    public static final int ZHICAI_JIJIN = 8;       // 基金
    public static final int ZHICAI_XIAOFEI = 9;     // 消费金融


    /************* 界面数据携带一些常量 *****************/
    public static final int REQUEST_DYNAMIC_FRAGMENT = 11;
    public static final int REQUEST_ORGANIZATION_HOME = 12;
    public static final int KNOW_WEALTH_DETAIL = 13;
    public static final int USER_HOME_DYNAMIC = 14;
    public static final int REQUEST_TOPIC_DETAIL = 15;
    public static final int RESULT_SUCCESS = 22;
    public static final int REQUEST_MY_GROUP = 16;
    public static final int REQUEST_PRODUCT_HOME = 17;
    public static int REQUEST_FRESH_DYNAMIC_FRAGMENT = 18;
    public static final int RATING = 67;
    public static final int REQUEST_SHOUYI = 20;


    public static boolean isUpdateShopping = false; //是否更新财币商城，通知收到后刷新
}
