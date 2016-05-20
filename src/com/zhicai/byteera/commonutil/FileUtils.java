package com.zhicai.byteera.commonutil;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//文件操作相关类
public class FileUtils {
    public static String SDPATH = Environment.getExternalStorageDirectory() + "/ZHICAI/";
    public static String PHOTO_PAHT = SDPATH + "picture/";

    /** 保存bitmap */
    public static String saveBitmap(Bitmap bm, String picName) {
        try {
            File dirFile = new File(PHOTO_PAHT);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File f = new File(PHOTO_PAHT, picName + ".jpg");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PHOTO_PAHT + picName + ".jpg";
    }

    /** 获得应用的logo所对应的本地的地址 */
    public static String getLogo() {
        //获得通过Drawable获得bitmap
        Bitmap bitmap = FormatTools.drawable2Bitmap(MyApp.getInstance().getResources().getDrawable(R.drawable.start_logo));
        //保存bitMap
        return FileUtils.saveBitmap(bitmap, "logo");
    }

    /**
     * 判断sd卡是否可用
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /** 返回图片保存地址对应的Uri */
    public static Uri getCaptureFilePath() {
        String saveDir;
        if (hasSdcard()) {
            saveDir = PHOTO_PAHT;
            createSaveDir(saveDir);
        } else {
            return null;
        }
        String fileName = "picture" + System.currentTimeMillis() + ".JPEG";
        return Uri.fromFile(new File(saveDir, fileName));
    }

    public static File getCaptureFile() {
        String saveDir;
        if (hasSdcard()) {
            saveDir = PHOTO_PAHT;
            createSaveDir(saveDir);
        } else {
            return null;
        }
        String fileName = "picture" + System.currentTimeMillis() + ".JPEG";
        return new File(saveDir, fileName);
    }

    /** 根据目录名来创建目录 */
    public static void createSaveDir(String savePath) {
        File savedir = new File(savePath);
        if (!savedir.exists()) {
            savedir.mkdirs();
        }
    }
}
