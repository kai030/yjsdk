package com.yj.sdk;

import android.os.Handler;

import com.yj.entity.BaseData;
import com.yj.entity.PayChannel;
import com.yj.entity.Session;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lufengkai 
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
public class YouaiAppService {
	
	public static YJResultListen resultListen;
	/**
	 * 基础数据
	 */
	public static BaseData basicDate;
	
	/**
	 * 当前正在登录的用户帐号(用户信息保存在数据库中
	 */
	public static Session mSession = null;
	
	public static boolean keyboard = false;
	
	/**
	 * 支付渠道信息
	 */
	public static List<PayChannel> mPayChannelsFast = new ArrayList<PayChannel>();
	public static List<PayChannel> mPayChannelsCard = new ArrayList<PayChannel>();
	public static PayChannel[] mPayChannels;
//	public static PayChannel[] mPayChannelsCard;
	
	
	/**
	 *  标志是否登录
	 */
	public static boolean isLogin = false;
	
	/**
	 * 第三方登录回调
	 */
		public static Handler callbackHandler;
		public static int what;
		
		/**
		 * 帐号密码位数限制
		 */
		public static int min = 3;
		public static int max = 18;
		
		/**
		 * 密码字数
		 */
		public static int minM = 3;
		public static int maxM = 18;

	/**
	 *
	 */
}
