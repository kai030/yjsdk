package com.yj.entity;

import org.json.JSONObject;


/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public abstract class JsonParseInterface {
	
	/** 组织JSON数据方法 **/
	public abstract JSONObject buildJson();
	
	/** 解析JSON数据 **/
	public abstract void parseJson(JSONObject json);

	/** 对象标识 **/
	public abstract String getShortName();
	
	/** set int数据  **/
	protected void setInt(JSONObject json, String key, int value) throws Exception {
		if(!Helper.isNullOrEmpty(key)){
			json.put(key, value);
		}
	}
	
	/** set double数据  **/
	protected void setDouble(JSONObject json, String key, double value) throws Exception {
		if(!Helper.isNullOrEmpty(key) && 0 != value){
			json.put(key, value);
		}
	}
	
	/** set String数据  **/
	protected void setString(JSONObject json, String key, String value) throws Exception {
		if(!Helper.isNullOrEmpty(key) && !Helper.isNullOrEmpty(value)){
			json.put(key, value);
		}
	}
	

	/** get int数据  **/
	protected int getInt(JSONObject json, String key) throws Exception {
		int value = 0;
		if(!Helper.isNullOrEmpty(key) && json.has(key)){
			value = json.getInt(key);
		}
		return value;
	}
	
	/** get double数据  **/
	protected double getDouble(JSONObject json, String key) throws Exception {
		double value = 0;
		if(!Helper.isNullOrEmpty(key) && json.has(key)){
			value = json.getDouble(key);
		}
		return value;
	}
	
	/** get String数据  **/
	protected String getString(JSONObject json, String key) throws Exception {
		String value = null;
		if(!Helper.isNullOrEmpty(key) && json.has(key)){
			value = json.getString(key);
		}
		return value;
	}

}
