package com.zhicai.byteera;

import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.easemob.EMCallBack;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.DbUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.database.ContactsDb;
import com.zhicai.byteera.service.register.Register.LoginRequest;
import com.zhicai.byteera.service.register.Register.LoginResponse;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.smssdk.SMSSDK;

/** App global context @author xinglefly */

@ReportsCrashes(
        httpMethod = HttpSender.Method.PUT,
        reportType = HttpSender.Type.JSON,
        formUri = "http://log.zijieshidai.com:5984/acra-zhicai/_design/acra-storage/_update/report",
        formUriBasicAuthLogin = "reporter",
        formUriBasicAuthPassword = "p4ssw0rd",
        mode = ReportingInteractionMode.TOAST,
        forceCloseDialogAfterToast = false,
        resToastText = R.string.crash_toast_test
)


public class MyApp extends LitePalApplication {

    private static MyApp instance;
    private static long mMainThreadId = -1;
    private Map<String, ZCUser> contactList;
    private static Handler mainThreadHandler;
    public ExecutorService executorService =null;
    int cpuNums = Runtime.getRuntime().availableProcessors();
    public DbUtils db;

    private static final String APP_ID = "wx3d7d17201d5e03a7";
    public IWXAPI api;
    public Tencent mTencent;

    private AppComponent component;

    public static MyApp getInstance() {
        return instance;
    }

    public static Handler getMainThreadHandler() {
        return mainThreadHandler;
    }

    @Override public void onCreate() {
        super.onCreate();
        initCommon();
        initSDK();
        initServer();
        initImageLoader();
        getUserInfo();
        initWx();
        initDagger();
    }

    private void initDagger() {
//        component = DaggerAppComponent.create();
    }

    public AppComponent getComponent(){
        return component;
    }

    private void initWx() {
        api = WXAPIFactory.createWXAPI(instance, APP_ID, true);
        api.registerApp(APP_ID);
        mTencent = Tencent.createInstance("1104879487", instance);
    }

    private void initCommon() {
        NetworkOnMainThreadException();
        Connector.getDatabase();
        instance = this;
        mMainThreadId = android.os.Process.myTid();
        mainThreadHandler = new Handler();
        executorService = Executors.newFixedThreadPool(cpuNums);
        db = DbUtils.create(instance, Constants.BYTEERA_DB);
    }


    public void initSDK() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
        ACRA.init(this);
        SMSSDK.initSDK(this, Constants.APPKEY, Constants.APPSECRET);
        MobclickAgent.updateOnlineConfig(instance);
        AnalyticsConfig.enableEncrypt(true);
    }


    private void initServer() {
        TiangongClient.instance().init("gate-prd.zijieshidai.com", 12002);
        PreferenceUtils.getInstance(this).saveAddress("gate-prd.zijieshidai.com");
    }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.consult_default)
                .showImageOnFail(R.drawable.loading_err).cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(instance)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void getUserInfo() {
        String mobilePhone = PreferenceUtils.getInstance(this).getMobilePhone();
        String pwd = PreferenceUtils.getInstance(this).getPwd();
        if (!TextUtils.isEmpty(mobilePhone) && !TextUtils.isEmpty(pwd)) {
            LoginRequest login = LoginRequest.newBuilder().setMobilePhone(mobilePhone).setPassword(pwd).build();
            TiangongClient.instance().send("chronos", "login", login.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        LoginResponse response = LoginResponse.parseFrom(buffer);
                        if (response.getErrorno() == 0) {
                            getSex(response.getSex().getNumber());
                            ZCUser user = new ZCUser(response);
                            PreferenceUtils.getInstance(instance).setUserInfo(user);
                        }
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private String getSex(int number) {
        if (number == 1) {
            return "男";
        } else {
            return "女";
        }
    }

    public void NetworkOnMainThreadException() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public String getUserId(){
        return PreferenceUtils.getInstance(this).readUserInfo().getUser_id();
    }



    public void setCoinCnt(int coinCnt){
        PreferenceUtils.getInstance(instance).setCoinCount(coinCnt);
    }

    public int getCoinCnt(){
        return PreferenceUtils.getInstance(instance).getCoinCount();
    }


    public void setContactList(Map<String, ZCUser> contactList) {
        this.contactList = contactList;
    }

    public Map<String, ZCUser> getContactList() {
        if (contactList == null) {
            ContactsDb cdbContactsDb = new ContactsDb(instance);
            contactList = cdbContactsDb.getContactList();
        }
        return contactList;
    }


    public void logout(final EMCallBack callback) {
        HXSDKHelper.getInstance().logout(callback);
    }


    public static long getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
