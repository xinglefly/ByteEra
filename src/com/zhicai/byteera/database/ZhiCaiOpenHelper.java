package com.zhicai.byteera.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.commonutil.PreferenceUtils;

public class ZhiCaiOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static ZhiCaiOpenHelper instance;

	private static final String USERNAME_TABLE_CREATE = "CREATE TABLE " + ContactsDb.CONTACTS_TABLE_NAME + " ("
			+ ContactsDb.COLUMN_NAME_NICK + " TEXT, " + ContactsDb.COLUMN_NAME_AVATAR + " TEXT, "
			+ ContactsDb.CONTACTS_COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

	private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE " + InviteMessageDb.INVITE_TABLE_NAME
			+ " (" + InviteMessageDb.INVITE_COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ InviteMessageDb.COLUMN_NAME_FROM + " TEXT, " + InviteMessageDb.COLUMN_NAME_GROUP_ID + " TEXT, "
			+ InviteMessageDb.COLUMN_NAME_GROUP_Name + " TEXT, " + InviteMessageDb.COLUMN_NAME_REASON + " TEXT, "
			+ InviteMessageDb.COLUMN_NAME_STATUS + " INTEGER, " + InviteMessageDb.COLUMN_NAME_ISINVITEFROMME
			+ " INTEGER, " + InviteMessageDb.COLUMN_NAME_TIME + " TEXT); ";

	private ZhiCaiOpenHelper(Context context) {
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
	}

	public static ZhiCaiOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new ZhiCaiOpenHelper(context.getApplicationContext());
		}
		return instance;
	}

	private static String getUserDatabaseName() {
		return PreferenceUtils.getInstance(MyApp.getContext()).getUserName() + "_zhicai.db";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USERNAME_TABLE_CREATE);
		db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}


	public void closeDB() {
		if (instance != null) {
			try {
				SQLiteDatabase db = instance.getWritableDatabase();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			instance = null;
		}
	}

}
