/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.yj.sdk;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import com.yj.entity.ChargeResult;
import com.yj.entity.PayResult;
import com.yj.entity.Result;
import com.yj.pay.MobileSecurePayHelper;
import com.yj.ui.DialogHelper;
import com.yj.util.StringUtils;

//import com.zhimeng.pay.MobileSecurePayer;

/**
 * 模拟商户应用的商品列表，交易步骤。
 *
 * 1. 将商户ID，收款帐号，外部订单号，商品名称，商品介绍，价格，通知地址封装成订单信息 2. 对订单信息进行签名 3.
 * 将订单信息，签名，签名方式封装成请求参数 4. 调用pay方法
 *
 */

/**
 * @author lufengkai
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
public class AlipayImpl {

	private Activity mActivity;
//	private Result mResult;
	private PayResult mPayResult;
	private ChargeResult chargeResult;
	public static final int ID_RESULT = 1;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			// 充值后返回給服務器
			mPayResult.orderDescr = (String) msg.obj;

			try {
				String tradeStatus = "resultStatus={";
				int imemoStart = mPayResult.orderDescr.indexOf("resultStatus=");
				imemoStart += tradeStatus.length();
				int imemoEnd = mPayResult.orderDescr.indexOf("};memo=");
				tradeStatus = mPayResult.orderDescr.substring(imemoStart, imemoEnd);
				if(!"".equals(tradeStatus) && "9000".equals(tradeStatus)){
					mPayResult.orderStatus = 1;
					DialogHelper.showPayResultDialog(mActivity);
				}else
				{
					mPayResult.orderStatus = 0;
				}
				String[] json = (msg.obj.toString()).split(";");
				mPayResult.orderDescr = json[0];
				if (!StringUtils.isEmpty(mPayResult.orderId)) {
//					new PayResultReturnThread(mActivity, mPayResult).start();
				}
			} catch (Exception e) {
			}
		}
	};

	//
	public AlipayImpl(Activity act, ChargeResult chargeResult, int paymentID, Result mResult) {
		mActivity = act;
//		this.mResult = mResult;
		this.chargeResult = chargeResult;
		mPayResult = new PayResult();
		mPayResult.paymentId = paymentID;
		mPayResult.orderId = chargeResult.orderId;
	}

	public static void pay(String resultStr, Activity mActivity, Handler mHandler) {
		// 检测安全支付服务是否安装
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(mActivity,
                                                                            null);
		boolean isMobile_spExist = mspHelper.detectMobile_sp();
		if (!isMobile_spExist)
			return;
		try {
//			MobileSecurePayer msp = new MobileSecurePayer();
////			msp.pay("partner=\"2088801536938559\"&seller=\"2088801536938559\"&out_trade_no=\"0424172609-6726\"&subject=\"123456\"&body=\"2010新款NIKE 耐克902第三代板鞋 耐克男女鞋 386201 白红\"&total_fee=\"0.01\"&notify_url=\"http://notify.java.jpxx.org/index.jsp\"&sign=\"PUeJy3uLaFReOfKJM9gL%2FVloL7ufwa5CqdRO36zkYcjtDDdkOvw0ONCmi695CbnbLj3sjKYL6yWTReWjhfNhtws81VOGnfzQvN6MnTpjJ8Fbzt3IJlWhdR3jhHfawfDmsOCR5ryxmMXzOU2nu0aS2eDXveWji6ySXmSVZ%2Fu%2FOzY%3D\"&sign_type=\"RSA\"", mActivity, mHandler);
//			msp.pay(resultStr, mActivity, mHandler);
		} catch (Exception ex) {
			Toast.makeText(mActivity, "fail to call remote server",
                                       Toast.LENGTH_SHORT).show();
		}
	}

	//
	//
	/**
	 * the OnCancelListener for lephone platform. lephone系统使用到的取消dialog监听
	 */
	static class AlixOnCancelListener implements
			DialogInterface.OnCancelListener {
		Activity mcontext;

		AlixOnCancelListener(Activity context) {
			mcontext = context;
		}

		public void onCancel(DialogInterface dialog) {
			mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
		}
	}

}