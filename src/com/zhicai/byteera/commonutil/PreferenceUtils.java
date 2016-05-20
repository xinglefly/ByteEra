package com.zhicai.byteera.commonutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.zhicai.byteera.activity.bean.ZCUser;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Preference Utils
 * @author xinglefly
 */

public class PreferenceUtils {

    public static final String PREFERENCE_NAME = "byteera_pre_info";

    private static PreferenceUtils mPreferenceUtils = null;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;

    private PreferenceUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static PreferenceUtils getInstance(Context context) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new PreferenceUtils(context);
        }
        editor = mSharedPreferences.edit();
        return mPreferenceUtils;
    }

	//guide
	public void setStartGuide(boolean isstart) {
		editor.putBoolean(Constants.BYTEERA_ISSTARTGUIDE, isstart).commit();
	}

	public boolean isStartGuide(){
		return mSharedPreferences.getBoolean(Constants.BYTEERA_ISSTARTGUIDE, false);
	}

	//logined
	public void setLogined(boolean islogined) {
		editor.putBoolean(Constants.BYTEERA_ISLOGINED, islogined).commit();
	}

	public boolean isLogined(){
		return mSharedPreferences.getBoolean(Constants.BYTEERA_ISLOGINED, false);
	}

	/**设置机构的版本号*/
	public void setInstitutionVersion(int version) {
		editor.putInt("institution_version", version).commit();
	}
	public int getInstitutionVersion() {
		return mSharedPreferences.getInt("institution_version", 0);
	}

	public void setUserInfo(ZCUser user) {
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(user);
			// 将字节流编码成base64的字符窜
			String oAuth_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
			SharedPreferences.Editor editor = mSharedPreferences.edit();
			editor.putString("user_info", oAuth_Base64);
			editor.apply();
		} catch (IOException ignored) {
		}
	}

	public <Data> void setToPre(Data data, String name) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(data);
			String oAuth_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
			SharedPreferences.Editor editor = mSharedPreferences.edit();
			editor.putString(name, oAuth_Base64);
			editor.apply();
		} catch (IOException ignored) {
		}
	}
	
	public ZCUser readUserInfo() {
		ZCUser oAuth_1 = null;
		String productBase64 = mSharedPreferences.getString("user_info", "");
		if(!TextUtils.isEmpty(productBase64)){
			//读取字节
			byte[] base64 = Base64.decodeBase64(productBase64.getBytes());

			//封装到字节流
			ByteArrayInputStream bais = new ByteArrayInputStream(base64);
			try {
				//再次封装
				ObjectInputStream bis = new ObjectInputStream(bais);
				try {
					//读取对象
					oAuth_1 = (ZCUser) bis.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(oAuth_1!=null){
				return oAuth_1;
			}else {
				return new ZCUser();
			}
		}else {
			return new ZCUser();
		}

	}
	public  void clearUserInfo() {
		setUserInfo(null);
		setUserId(null);
		setPwd(null);
		setUserName(null);
		setCoinCount(0);
	}

	public void setUserName(String username){
		editor.putString(Constants.BYTEERA_LOGIN_USERNAME, username).commit();
	}

	public String getUserName(){
		return mSharedPreferences.getString(Constants.BYTEERA_LOGIN_USERNAME, "");
	}

	public void setUserId(String userid){
		editor.putString(Constants.BYTEERA_LOGIN_USERID, userid).commit();
	}

	public String getUserId(){
		return mSharedPreferences.getString(Constants.BYTEERA_LOGIN_USERID, "");
	}

	public void setPwd(String pwd){
		editor.putString(Constants.BYTEERA_LOGIN_PWD, pwd).commit();
	}

	public String getPwd(){
		return mSharedPreferences.getString(Constants.BYTEERA_LOGIN_PWD, "");
	}

	public void setCoinCount(int coincount){
		editor.putInt(Constants.BYTEERA_COINCOUNT, coincount).commit();
	}

	public int getCoinCount(){
		return mSharedPreferences.getInt(Constants.BYTEERA_COINCOUNT, 0);
	}

	public void setCoinRank(int coinRank){
		editor.putInt(Constants.BYTEERA_COINRANK, coinRank).commit();
	}

	public int getCoinRank(){
		return mSharedPreferences.getInt(Constants.BYTEERA_COINRANK, 0);
	}


	public void setLoginDays(int days){
		editor.putInt(Constants.BYTEERA_COINLOGINDAYS, days).commit();
	}

	public int getLoginDays(){
		return mSharedPreferences.getInt(Constants.BYTEERA_COINLOGINDAYS, 0);
	}

    public void setMobilePhone(String mobilePhone){
        editor.putString(Constants.BYTEERA_MOBILE_PHONE, mobilePhone).commit();
    }
    public String getMobilePhone(){
        return  mSharedPreferences.getString(Constants.BYTEERA_MOBILE_PHONE, "");
    }


	//Guess_Pic
	public void setGuessPicFlag(boolean flag){
		editor.putBoolean(Constants.GUESS_FLAG, flag).commit();
	}

	public boolean isGuessPicFlag(){
		return mSharedPreferences.getBoolean(Constants.GUESS_FLAG, false);
	}

	public void saveAddress(String address) {
		editor.putString("address", address).commit();
	}
	public String getAddress(){
		return mSharedPreferences.getString("address","gate-prd.zijieshidai.com");
	}

}
