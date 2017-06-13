package com.yj.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.yj.entity.Constants;
import com.yj.sdk.PayActivity;
import com.yj.sdk.YouaiAppService;

/**
 * @author lufengkai
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
public class PayService {

	private static PayService mPayService;

	public Context context;

	private PayService(Context context) {
		this.context = context;
	}

	public static PayService getExample(Context context) {
		if (mPayService == null) {
			mPayService = new PayService(context);
		}
		return mPayService;
	}

	public void showPaymentView(Context context) {
		if (!NetworkImpl.isNetworkConnected(this.context)) {
			Utils.toastInfo(this.context, Constants.NETWORK_FAILURE);
			return;
		}
		if (!YouaiAppService.isLogin) {
			Utils.toastInfo(this.context, "请登录游戏！！！");
			return;
		}



		/* 打开支付选择金额界面 */
		Intent intent = new Intent();
//		intent.putExtra(Constants.EXTRA_CALLBACKINFO, callBackInfo);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, PayActivity.class);
//		intent.setClass(this.context, PaymentActivity.class);
		this.context.startActivity(intent);
	}

	/**
	 * 隐藏加载框
	 */
	public void hideDialog(Dialog dialog) {
		if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
