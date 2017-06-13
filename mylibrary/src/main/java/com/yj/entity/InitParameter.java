package com.yj.entity;

import android.content.Context;

import com.yj.util.Utils;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class InitParameter extends JsonParseInterface implements Serializable {

	private static InitParameter deviceProperties;

	public String gameName;
	public String oneLevelChannel = "12563";
	public String twoLevelChannel= "5521";;
	public String imeiMac;// 手机序列号+mac
	public String userAcconut;
	public String password;
	public String imsi;
	public String phoneModel;
	public String networkType;
	public String gatewayType;
	public String sdkVersoin;
	public String newPassword;
	public String bindMObile;

	private static final String u_gameName = "a";// imei + mac
	private static final String u_oneLevelChannel = "b";
	private static final String u_twoLevelChannel = "c";
	private static final String u_imeiMac = "d";
	private static final String u_userAcconut = "e";
	private static final String u_password = "f";
	private static final String u_imsi = "g";
	private static final String u_phoneModel = "h";
	private static final String u_networkType = "i";
//	private static final String u_imeiMac = "j";
	private static final String u_gatewayType = "j";
	private static final String u_sdkVersoin = "k";
	private static final String u_newPassword = "l";
	private static final String u_bindMObile = "m";

	/**
	 * 单例
	 * 
	 * @param context
	 * @return
	 */
	public static InitParameter getInstance(Context context) {

		if (deviceProperties == null) {
			deviceProperties = new InitParameter(context);
		}
		return deviceProperties;
	}

	/**
	 * 构造 获取手机信息
	 * 
	 * @param context
	 */
	private InitParameter(Context context) {
		imeiMac = Utils.getImei(context) + Utils.getMacAddress(context);

		/* 本应用名称 */
		gameName = Utils.getApplicationName(context);

	}



	/**
	 * 设置设备字段
	 * 
	 * @return
	 */
	@Override
	public String getShortName() {
		return ShortName.INIT;
	}

	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			setString(json, u_gameName, gameName);
			setString(json, u_oneLevelChannel, oneLevelChannel);
			setString(json, u_twoLevelChannel, twoLevelChannel);
			setString(json, u_imeiMac, imeiMac);

			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void parseJson(JSONObject json) {
		if (json == null)
			return;
		try {
			gameName = getString(json, u_gameName);
			oneLevelChannel = getString(json, u_oneLevelChannel);
			twoLevelChannel = getString(json, u_twoLevelChannel);
			imeiMac = getString(json, u_imeiMac);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
