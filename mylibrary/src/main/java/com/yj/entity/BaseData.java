package com.yj.entity;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class BaseData extends JsonParseInterface implements Serializable {
	
	private static final String u_serviceQQ = "a";
	private static final String u_serviceTel = "b";
	private static final String u_describevt = "c"; //
/*	private static final String u_isReplace = "d";
	private static final String u_seiId = "e";
	private static final String u_gameName= "f";
	private static final String u_gameCoinName = "g";
	private static final String u_gameMoneyDouble = "h";*/
	
	public static String serviceTel;//客服电话
	public static String serviceQQ;//客服QQ
	public String describevt;//记录用户操作间隔时间，毫秒
//	public int isReplace;//是否更新缓存
//	public String seiId;//缓存id
	
/*	private static BaseData baseData;
	private BaseData(){};
	public BaseData getInstance(){
		if(baseData == null){
			baseData = new BaseData();
		}
		return baseData;
	}*/
	@Override
	public JSONObject buildJson() {
		JSONObject json = new JSONObject();
		try {
		setString(json, u_serviceQQ, serviceQQ);
		setString(json, u_serviceTel, serviceTel);
		setString(json, u_describevt, describevt);
	/*	setInt(json, u_isReplace, isReplace);
		setString(json, u_seiId, DeviceProperties.seid);*/
		return json;
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return null;
	}
	@Override
	public void parseJson(JSONObject json) {
		if (json == null) 
			return ;
		try {
			serviceTel = getString(json, u_serviceTel);
			serviceQQ = getString(json, u_serviceQQ);
			describevt = getString(json, u_describevt);
			Log.i("feng", "serviceTel:" + serviceTel + "    serviceQQ:" + serviceQQ);
			/*isReplace = getInt(json, u_isReplace);
			DeviceProperties.seid = getString(json, u_seiId);
			Flag.moneyType = getString(json, u_gameCoinName);
			Flag.gameMoneyDouble = getInt(json, u_gameMoneyDouble);*/
//			if(Flag.gameMoneyDouble == 0) Flag.gameMoneyDouble = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	@Override
	public String getShortName() {
		// TODO Auto-generated method stub
		return "b";
	}

}
