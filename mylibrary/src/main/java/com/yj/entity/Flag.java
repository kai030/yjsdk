package com.yj.entity;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class Flag {
	
	public static boolean isInitSucceed = false;
	
	public static int payTypeFlag = 1;//支付类型
	public static int position = 0;//  用于记录所选金额
	public static int positionCard = 0;//卡类索引
	public static int positionFast= 0;//快捷索引
	public static int positionCardList = 0;//标志卡类金额索引
	public static boolean cardListFlag = false;//标志选择金额
	public static boolean isFirstCardList = false;//第一次打开卡类支付
	public static boolean isFirst = false;//第一次初始化列表
	public static boolean isFirstCard = false;//第一次初始化列表
//	public static int fastOrCard = 1;
	
	public static int payTypeFlagCard = 3;//卡类支付id
	public static int payTypeFlagAlipay = 1;//快捷支付id
	
	public static String notice = "";//滚动
	public static String descr = "";//说明
	
	public static String moneyType = "";
	public static int gameMoneyDouble = 1;
//	public static int payMoneyDouble = 1;
    public static boolean flag = false;// 充值默认选中
	
}
