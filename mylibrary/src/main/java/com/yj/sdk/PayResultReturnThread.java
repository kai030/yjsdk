package com.yj.sdk;

import android.content.Context;

import com.yj.entity.PayResult;
import com.yj.entity.Result;
import com.yj.util.GetDataImpl;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
class PayResultReturnThread extends Thread {

	private PayResult mPayResult;
	private Context mContext;

	public PayResultReturnThread(Context context, PayResult payResult) {
		mPayResult = payResult;
		mContext = context;
	}

	@Override
	public void run() {
		boolean isSucces = false;
		int count = 0;
		while (!isSucces) {
			Utils.youaiLog("充值后返回给服务器的信息---->" + mPayResult.toString());
			Result result = GetDataImpl.getInstance(mContext).payResult(
					mPayResult);

			if (result != null && 0 == result.resultCode) {
				isSucces = true;
			} else {
				try {
					Thread.sleep(1 * 60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count++;
				if (count == 5) {
					isSucces = true;
				}
			}
		}
	}

}
