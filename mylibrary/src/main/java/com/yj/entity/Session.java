package com.yj.entity;

import android.content.Context;

import com.yj.sdk.YJManage;
import com.yj.util.Utils;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright
 */
public class Session extends JsonParseInterface implements Serializable {

	
	public static int secret = 0;

	public String playerId;//角色id
	public String commodiyName;//商品详情
	public String tradeName;
	public String tradeId;
	public int chargeType;
	public String money;
	public String cpOrderId;
	public String openId;
	public String gameName;
	public String channel = Utils.getChannel(YJManage.getApplication());
	public String imei= "";// 手机序列号+mac //
	public String userAccount;
	public String password;
	public String imsi = "";
	public String phoneModel = "";
	public String networkType = "1";
	public String sdkVersoin = "1.0.0";
;   public String serverName;
	public String rand = "";
	public String uid = "";
	public String payId;
	public String access_token;
	public String code;
    public String vipLevel;
	public String playerName;


	private static final String u_playerName = "playerName";
	private static final String u_vipLevel = "vipLevel";
	private static final String u_cpOrderId = "cpOrderId";
	private static final String u_code = "code";
	private static final String u_openid = "openid";
	private static final String u_access_token = "access_token";
	private static final String u_serverName = "serverName";
	private static final String u_gameName = "gameName";//游戏名称
	private static final String u_payType = "chargeType";//支付方式
	private static final String u_channel = "Channel";
	private static final String u_imei = "imei";//手机序列号
	private static final String u_userAccount = "username";//账号
	private static final String u_password = "password";//密码
	private static final String u_rand = "rand";//验证码
	private static final String u_imsi = "imsi";//手机卡id
	private static final String u_phoneModel = "phoneModel";//手机型号
	private static final String u_networkType = "networkType";//网络类型
	private static final String u_gatewayType = "j";
	private static final String u_sdkVersoin = "sdkVersion";//sdk版本号
	private static final String u_uid = "uid";//uid
	private static final String u_chargeType = "chargeType";
	private static final String u_money = "money";
	private static final String u_tradeName = "tradeName";//商品名称
	public static final String u_tradeId = "tradeId";
	private static final String u_commodiyName = "commodiyName";
	private static final String u_orderNo = "orderNo";
	private static final String u_playerId = "playerId";



	
	
	public Session(){
//		init();
	}
	
	public void init(){
		
		
		
		/*签名*/
		Context context = YJManage.getApplication();
		/*设备号*/
		imei = Utils.getImei(context);
//	imeiMac = Utils.getImei(context) + Utils.getMacAddress(context);
		/* 本应用名称 */
//		gameName = Utils.getApplicationName(context);
		/*imsi*/
		imsi = Utils.getImsi(context);
		/*手机型号*/
		phoneModel = android.os.Build.MODEL;
		
		/*网络类型  wifi：1*/
		networkType = Utils.getNetType(context) + "";
		
		/*运营商*/
//		gatewayType = Utils.checkOperator(context);
		
		/*电话号码*/
//		telephoneNumber = Utils.getPhoneNum(context);
	}
	private static Session session; //单例

	public static Session getInstance() {
		if (session == null) {
			session = new Session();
		}
		return session;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			setString(json, u_userAccount, userAccount);
			setString(json, u_password, password);
			setString(json, u_gameName, gameName);
			setString(json, u_channel, channel);
			setString(json, u_rand, rand);
			setString(json, u_imei, imei);
			setString(json, u_imsi, imsi);
			setString(json, u_phoneModel, phoneModel);
			setString(json, u_networkType, networkType);
			setString(json, u_sdkVersoin, sdkVersoin);
			setInt(json, u_payType, chargeType);
			setString(json,u_openid,openId);
			setInt(json,u_chargeType,chargeType);
			setString(json,u_serverName,serverName);
			setString(json,u_money,money);
			setString(json,u_tradeName,tradeName);
			setString(json,u_commodiyName,commodiyName);
			setString(json,u_orderNo,payId);
			setString(json,u_uid,uid);
			setString(json,u_access_token,access_token);
			setString(json,u_code,code);
			setString(json,u_tradeId,tradeId);
			setString(json,u_cpOrderId,cpOrderId);
			setString(json,u_vipLevel,vipLevel);
			setString(json,u_playerName,playerName);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void parseJson(JSONObject json) {
		if (json == null)
			return;
		try {
			
//			setString(json, u_userAccount, userAccount);
//			setString(json, u_password, password);
			userAccount = getString(json, u_userAccount);
			password = getString(json, u_password);
			uid = getString(json, u_uid);

				/*password = getString(json, u_password);
			loginTime = getString(json, u_loginTime);
			sign = getString(json, u_sign);
//			accountType = getInt(json, u_accountType);
//			nickName = getString(json, u_nickName);
//			birthday = getString(json, u_birthday);
			bindMobile = getString(json, u_bindMobile);
//			bindEmail = getString(json, u_bindEmail);
			try {
//				noLoginPay = getInt(json, u_noLoginPay);
			} catch (Exception e) {
			}
			
//			secretId = getInt(json, u_secretId);
//			secretAnswer = getString(json, u_secretAnswer);
			secret = getInt(json, u_secret);
			newPwd = getString(json, u_newPwd);
//			newAnswer = getString(json, u_newAnswer);
//			newSecretId = getInt(json, u_newSecretId);
*/		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public String getShortName() {
		// TODO Auto-generated method stub
		return "data";
	}

}
