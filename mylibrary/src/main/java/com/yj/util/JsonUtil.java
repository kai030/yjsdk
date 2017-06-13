package com.yj.util;

import com.yj.entity.JsonParseInterface;
import com.yj.entity.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;


/**
 * @author lufengkai 
 * @date 2015年5月25日
 * @copyright 游鹏科技
 */
public class JsonUtil {
	
	
	public static JsonParseInterface parseJSonObject(Class<?> clz,
			String jsonString) {
		if (jsonString == null)
			return null;
		try {
			JSONObject jo = new JSONObject(jsonString);
			JsonParseInterface jInterface = (JsonParseInterface) clz
					.newInstance();
			if (!jo.isNull(jInterface.getShortName())) {
				jInterface
						.parseJson(jo.getJSONObject(jInterface.getShortName()));
				return jInterface;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JsonParseInterface[] parseJSonArray(Class<?> clz,
			String jsonString) {
		if (jsonString == null)
			return null;
		try {
			JSONObject jo = new JSONObject(jsonString);
			JsonParseInterface ji = (JsonParseInterface) clz.newInstance();
			if (!jo.isNull(ji.getShortName())) {
				JSONArray ja = jo.getJSONArray(ji.getShortName());
				if (ja != null) {
					JsonParseInterface[] interfaces = (JsonParseInterface[]) Array
							.newInstance(clz, ja.length());
					for (int i = 0; i < ja.length(); i++) {
						ji = (JsonParseInterface) clz.newInstance();
						ji.parseJson(ja.getJSONObject(i));
						interfaces[i] = ji;
					}
					return interfaces;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Result getResult(String json){
		try {
			Result result = new Result();
			JSONObject jsonObject = new JSONObject(json);


			try {
				result.resultCode = jsonObject.getInt("code");
			}catch (Exception e){
				Utils.fengLog("JSONException1:"+e.getMessage());
			}
			try {
				result.resultDescr = jsonObject.getString("msg");
			}catch (Exception e){
				Utils.fengLog("JSONException1:"+e.getMessage());
			}
			try {
				result.username = jsonObject.getString("username");
			}catch (Exception e){
				Utils.fengLog("JSONException1:"+e.getMessage());
			}
			try {
				result.password = jsonObject.getString("password");
			}catch (Exception e){
				Utils.fengLog("JSONException1:"+e.getMessage());
			}
			try {
				result.uid = jsonObject.getInt("uid");
			}catch (Exception e){
				Utils.fengLog("JSONException1:"+e.getMessage());
			}

			return result;

		} catch (JSONException e) {
			Utils.fengLog("JSONException:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
