package com.zhicai.byteera.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.easemob.util.HanziToPinyin;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.commonutil.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ContactsDb {

	//TODO 后期用Xutils db代替
	private ZhiCaiOpenHelper dbHelper;
	
	public static final String CONTACTS_TABLE_NAME = "contacts";
	public static final String CONTACTS_COLUMN_NAME_ID = "username";
	public static final String COLUMN_NAME_NICK = "nick";
	public static final String COLUMN_NAME_AVATAR = "avatar";

	public ContactsDb(Context context) {
		dbHelper = ZhiCaiOpenHelper.getInstance(context);
	}

	/**
	 * 保存好友list
	 * 
	 * @param contactList
	 */
	public void saveContactList(List<ZCUser> contactList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(CONTACTS_TABLE_NAME, null, null);
			for (ZCUser user : contactList) {
				ContentValues values = new ContentValues();
				values.put(CONTACTS_COLUMN_NAME_ID, user.getUsername());
				if(user.getNick() != null)
					values.put(COLUMN_NAME_NICK, user.getNick());
				if(user.getHeader() != null)
				    values.put(COLUMN_NAME_AVATAR, user.getHeader());
				db.replace(CONTACTS_TABLE_NAME, null, values);
			}
		}
	}

	/**
	 * 获取好友list
	 * @return
	 */
	public Map<String, ZCUser> getContactList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, ZCUser> users = new HashMap<String, ZCUser>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + CONTACTS_TABLE_NAME /* + " desc" */, null);
			while (cursor.moveToNext()) {
				String username = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME_ID));
				String nick = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NICK));
				String avatar = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_AVATAR));
				ZCUser user = new ZCUser();
				user.setUsername(username);
				user.setNick(nick);
				user.setHeader(avatar);
				String headerName = null;
				if (!TextUtils.isEmpty(user.getNick())) {
					headerName = user.getNick();
				} else {
					headerName = user.getUsername();
				}
				
				if (Constants.NEW_FRIENDS_USERNAME.equals(username) || Constants.GROUP_USERNAME.equals(username)) {
					user.setHeader("");
				} else if (Character.isDigit(headerName.charAt(0))) {
					user.setHeader("#");
				} else {
					user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target.substring(0, 1).toUpperCase());
					char header = user.getHeader().toLowerCase().charAt(0);
					if (header < 'a' || header > 'z') {
						user.setHeader("#");
					}
				}
				users.put(username, user);
			}
			cursor.close();
		}
		return users;
	}
	
	/**
	 * 删除一个联系人
	 * @param username
	 */
	public void deleteContact(String username){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.delete(CONTACTS_TABLE_NAME, CONTACTS_COLUMN_NAME_ID + " = ?", new String[]{username});
		}
	}
	
	/**
	 * 保存一个联系人
	 * @param user
	 */
	public void saveContact(ZCUser user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CONTACTS_COLUMN_NAME_ID, user.getUsername());
		if(user.getNick() != null)
			values.put(COLUMN_NAME_NICK, user.getNick());
		if(user.getHeader() != null)
		    values.put(COLUMN_NAME_AVATAR, user.getHeader());
		if(db.isOpen()){
			db.replace(CONTACTS_TABLE_NAME, null, values);
		}
	}
}
