package com.zhicai.byteera.commonutil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.zhicai.byteera.R;

@SuppressWarnings("unused")
public class Utils {
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean checkNetwork(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        return net != null && net.isConnected();
    }


    public static void setingNetWork(final Context context){
        if(!Utils.checkNetwork(context)){
            BaseAlertDialog dialog = new BaseAlertDialog(BaseAlertDialog.BaseAlertDialogCallback.FLAG_SCAN_OTHER,
                    new BaseAlertDialog.BaseAlertDialogCallback() {
                        @Override
                        public void positive(int flag) {
                            Intent intent = null;
                            if (Build.VERSION.SDK_INT > 10) {
                                intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            } else {
                                intent = new Intent();
                                ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                                intent.setComponent(component);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            context.startActivity(intent);
                        }

                        @Override
                        public void negative(int flag) {
                            ActivityController.exitApp();
                        }
                    }, context, "网络设置提示", "网络连接不可用,赶快去设置咯?取消将退出应用！", "设置", "取消");

        }
    }


    /**
     * 根据消息内容和消息类型获取消息内容提示
     */
    public static String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
            case LOCATION: // 位置消息
                if (message.direct == EMMessage.Direct.RECEIVE) {
                    digest = getString(context, R.string.location_recv);
                    digest = String.format(digest, message.getFrom());
                    return digest;
                } else {
                    digest = getString(context, R.string.location_prefix);
                }
                break;
            case IMAGE: // 图片消息
                digest = getString(context, R.string.picture);
                break;
            case VOICE:// 语音消息
                digest = getString(context, R.string.voice);
                break;
            case VIDEO: // 视频消息
                digest = getString(context, R.string.video);
                break;
            case TXT: // 文本消息
                TextMessageBody txtBody = (TextMessageBody) message.getBody();
                digest = txtBody.getMessage();
                break;
            case FILE: // 普通文件消息
                digest = getString(context, R.string.file);
                break;
            default:
                System.err.println("error, unknow type");
                return "";
        }

        return digest;
    }

    static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

}
