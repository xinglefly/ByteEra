package com.zhicai.byteera.commonutil;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;
import com.zhicai.byteera.MyApp;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ActivityController {

    private static List<Activity> activitys = new ArrayList();


    public static List<Activity> getActivitys() {
        return activitys;
    }

    public static void add(Activity activity) {
        activitys.add(activity);
    }

    public static void remove(Activity activity) {
        if (activity != null) {
            activitys.remove(activity);
            activity = null;
        }
    }

    public static void exitApp() {
        for (Activity activity : activitys) {
            if (null != activity) {
                activity.finish();
            }
        }
        MobclickAgent.onKillProcess(MyApp.getInstance());
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void finishAllActivitys() {
        for (Activity activity : activitys) {
            if (null != activity) {
                activity.finish();
            }
        }
    }
}
