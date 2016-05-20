package com.zhicai.byteera.activity.traincamp.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述：json解析工具类
 **/
public class GsonUtils {

	public static <T> T json2bean(String result, Class<T> clazz) {
		Gson gson = new Gson();
		try {
			T t = gson.fromJson(result, clazz);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String bean2json(Object o){
		Gson gson = new Gson();
		return gson.toJson(o);
	}

	public static <T> List<T> getLists(String json, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getListStr(String jsonString) {
		List<String> list = new ArrayList<String>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<String>>() {}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

}
