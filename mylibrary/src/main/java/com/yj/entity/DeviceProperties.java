package com.yj.entity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.yj.util.NetworkImpl;
import com.yj.util.Utils;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;


/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class DeviceProperties extends JsonParseInterface implements Serializable {

	private static DeviceProperties deviceProperties;
//	private Context context;
	
	
	public static final int SDK_TYPE_ANDROID = 0;
	public static final int SDK_TYPE_IOS = 1;
	
	private static final String u_sdkVersion = "a";
	private static final String u_phoneModel = "b";
	private static final String u_imsi = "c";
//	private static final String u_uuid = "c";
	private static final String u_imei = "d";//imei + mac
	private static final String u_densityDpi = "e";
	private static final String u_displayScreenWidth = "f";
	private static final String u_displayScreenHeight = "g";
//	private static final String u_latitude = "h";
//	private static final String u_longitude = "i";
	private static final String u_networkInfo = "h";
	private static final String u_youpengId = "i";
	private static final String u_packageName = "j";
	private static final String u_gameVersion = "k";
/*	private static final String u_currentUUID = "n";
//	private static final String u_currentYouaiId = "o";
	private static final String u_sdkType = "p";
	private static final String u_sysVersion = "q";
	private static final String u_serverId = "s";
	private static final String u_seid = "t";*/
	

	public String sdkVersion = "1.0.0";			//SDK版本号
	public String phoneModel;			//手机型号
	public String uuid;					//唯一标识（imsi、mac、idfa），android的imsi 默认读取缓存数据
	public String imsi;					//当前手机卡的imsi
	public String imeiMac;					//手机序列号+mac
	public int densityDpi;				//手机屏幕Dpi
	public int displayScreenWidth;		//手机屏幕宽度
	public int displayScreenHeight;		//手机屏幕高度
//	public double latitude;				//经度
//	public double longitude;			//纬度
//	public String area;					//地域（省、市、县/区），逗号分割
	public String networkInfo;			//网络类型/WiFi/移动网
	public static String youpengId;	    //游戏包标识（youaiID=渠道+产品），客户端需要缓存处理
	public String packageName;			//游戏包名
	public String gameVersion;			//游戏版本
	public String deviceParams;			//
	public String currentUUID;			//当前的唯一标识，参考uuid
//	public static String currentYouaiId;		//当前的游戏包标识，参考youaiID
	public String sdkType = "1";				//客户端类型 1-android；2-ios；默认1
	public String sysVersion;			//操作系统版本，android可以传版本号，对应数据库version
	/** ios use **/
	public static int serverId = 8885399;
	public static String seid;
	/** ios use **/
	

	/**
	 * 单例
	 * 
	 * @param context
	 * @return
	 */
	public static DeviceProperties getInstance(Context context) {

		if (deviceProperties == null) {
			deviceProperties = new DeviceProperties(context);
		}
		return deviceProperties;
	}

	/**
	 * 构造    获取手机信息
	 * 
	 * @param context
	 */
	private DeviceProperties(Context context) {
//		this.context = context;

		/* 手机型号 */
		phoneModel = android.os.Build.PRODUCT;

		/*imsi、硬件参数*/
		imsi = Utils.getImsi(context);
		Utils.youaiLog("imsi: " + imsi);
		/*imei+mac*/
		imeiMac = Utils.getImei(context) + Utils.getMacAddress(context);
		
		/*u_uuid*/
		try {
			final String androidId = Secure.getString(context.getContentResolver(),
                                                                  Secure.ANDROID_ID);
			UUID uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
			this.uuid = uuid.toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		/*系统版本*/
		sysVersion = android.os.Build.VERSION.RELEASE;

		// --获取手机屏幕参数
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		densityDpi = metrics.densityDpi;//像素密度
		displayScreenWidth = metrics.widthPixels;//宽
		displayScreenHeight = metrics.heightPixels;//高
		
		/*本应用包名 */
		PackageManager pm = context.getPackageManager();
		packageName = context.getPackageName();

		/*游戏版本 */
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			gameVersion = info.versionName;
			Utils.youaiLog("youaiId:" + youpengId);
		} catch (Exception e) {
		}
		
		/*网络类型*/
		networkInfo = ""+NetworkImpl.checkNetworkAvailableType(context);
	}
	
	/**
	 * 获取硬件号  序列号+mac
	 * @param ctx
	 * @return
	 */
	private String getDeviceParams(Context ctx, String imei) {
		StringBuilder deviceParams = new StringBuilder();
		deviceParams.append(imei);
		WifiManager wifiMgr = (WifiManager) ctx
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			String macAddress = info.getMacAddress();
			if (macAddress != null)
				deviceParams.append(macAddress);
		}
		return Utils.calc(deviceParams.toString());
	}
	
	/**
	 * 设置设备字段
	 * @return
	 */
	@Override
	public String getShortName() {
		return ShortName.DEVICEPROPERTIES;
	}

	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			setString(json,u_sdkVersion,sdkVersion);
			setString(json,u_phoneModel,phoneModel);
//			setString(json,u_uuid,uuid);
			setString(json,u_imei,imeiMac);
			setInt(json,u_densityDpi,densityDpi);
			setInt(json,u_displayScreenWidth,displayScreenWidth);
			setInt(json,u_displayScreenHeight,displayScreenHeight);
//			setDouble(json,u_latitude,latitude);
//			setDouble(json,u_longitude,longitude);
			setString(json,u_networkInfo,networkInfo);
			setString(json,u_youpengId,youpengId);
			setString(json,u_packageName,packageName);
			setString(json,u_gameVersion,gameVersion);
//			setString(json,u_currentUUID,currentUUID);
//			setString(json,u_currentYouaiId,currentYouaiId);
//			setString(json,u_sdkType,sdkType);
//			setString(json,u_sysVersion,sysVersion);
			setString(json, u_imsi, imsi);
//			setInt(json, u_serverId, serverId);
//			setString(json, u_seid, seid);
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
			sdkVersion = getString(json, u_sdkVersion);
			phoneModel = getString(json, u_phoneModel);
//			uuid = getString(json, u_uuid);
			deviceParams = getString(json, u_imei);
			densityDpi = getInt(json, u_densityDpi);
			displayScreenWidth = getInt(json, u_displayScreenWidth);
			displayScreenHeight = getInt(json, u_displayScreenHeight);
//			latitude = getDouble(json, u_latitude);
//			longitude = getDouble(json, u_longitude);
			networkInfo = getString(json, u_networkInfo);
			youpengId = getString(json, u_youpengId);
			packageName = getString(json, u_packageName);
			gameVersion = getString(json, u_gameVersion);
//			currentUUID = getString(json, u_currentUUID);
//			currentYouaiId = getString(json, u_currentYouaiId);
//			sdkType = getString(json, u_sdkType);
//			sysVersion = getString(json, u_sysVersion);
			imsi = getString(json, u_imsi);
//			serverId = getInt(json, u_serverId);
//			seid = getString(json, u_seid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
