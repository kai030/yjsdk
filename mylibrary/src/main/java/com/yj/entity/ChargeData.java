package com.yj.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class ChargeData extends JsonParseInterface implements Serializable, Cloneable {
	
	private static ChargeData chargeData;

	
	private static final String u_gameName = "a";//游戏名字
	private static final String u_oneLevelChannel = "b";//一级渠道
	private static final String u_twoLevelChannel = "c";//二级渠道
	private static final String u_serverName = "d";//服务I起名称
	private static final String u_userAccount = "e";//账号
	private static final String u_chargeType = "f";//充值类型    1：支付宝，2：易宝，3：充值卡
	private static final String u_operatorCardType = "g";//充值卡运营商   1：移动，2：联通，3：电信
	private static final String u_cardAccount= "h";//卡号
	private static final String u_cardPassword = "i";//卡密
	private static final String u_cardDenomination = "j";//卡面额
	private static final String u_money = "k";//充值金额
	private static final String u_tradeId = "l";//商品id
	private static final String u_telephoneNumber = "m";//角色等级
	private static final String u_tradeName = "n";//商品名称
	
	
	/** youai内部金额单位尽量统一用分 **/
	/*public  String money = "1";
	public  int paymentId = 1;
	public int roleLevel;
	public String roleId;
	public String roleName;
	public String callBackInfo;
	public String cardAccount;
	public String cardPassword;
	*/
	public String gameName = "";//游戏名字
	public String oneLevelChannel = "";//一级渠道
	public String twoLevelChannel = "";//二级渠道
	public String serverName = "";//服务I起名称
	public String userAccount = "";//账号
	public String chargeType = "1";//充值类型    1：支付宝，2：易宝，3：充值卡
	public String operatorCardType = "1";//充值卡运营商   1：移动，2：联通，3：电信
	public String cardAccount = "";//卡号
	public String cardPassword = "" ;//卡密
	public String cardDenomination = "";//卡面额
	public String money = "";//充值金额
	public String tradeId = "";//商品id
	public String telephoneNumber;//电话号码
	public String tradeName = "";//商品名称
	
//	public static String price;
	
	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			setString(json,u_gameName,gameName);
			setString(json,u_oneLevelChannel,oneLevelChannel);
			setString(json,u_twoLevelChannel,twoLevelChannel);
			setString(json,u_serverName,serverName);
			setString(json,u_userAccount,userAccount);
			setString(json,u_cardAccount,cardAccount);
			setString(json,u_cardPassword,cardPassword);
			setString(json,u_chargeType,chargeType);
			setString(json,u_operatorCardType,operatorCardType);
			setString(json,u_cardAccount,cardAccount);
			setString(json,u_cardPassword,cardPassword);
			setString(json,u_cardDenomination,""+cardDenomination);
			setString(json,u_money,""+money);
			setString(json,u_tradeId,tradeId);
			setString(json,u_telephoneNumber,telephoneNumber);
			setString(json,u_tradeName,tradeName);
			
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
			/*roleId = getString(json, u_roleId);
			roleName = getString(json, u_roleName);
			roleLevel = getInt(json, u_roleLevel);
			money = getString(json, u_money);
			paymentId = getInt(json, u_paymentId);
			callBackInfo = getString(json, u_callBackInfo);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ChargeData getChargeData(){

		if(chargeData == null)  {
			
			chargeData = new ChargeData();
		}
		return chargeData;
	}

	public String getShortName() {
		return ShortName.CHARGEDATA;
	}

	@Override
	public String toString() {
		return "";
	}


		public ChargeData clone() {
			ChargeData o = null;
			try {
				o = (ChargeData) super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return o;
		}

}
