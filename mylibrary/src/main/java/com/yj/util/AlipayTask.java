package com.yj.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.yj.entity.Result;
import com.yj.sdk.AlipayImpl;
import com.yj.sdk.ZhimengPayActivity;
import com.yj.ui.DialogHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//设置密保提交；
public class AlipayTask extends AsyncTask<Void, Void, Result> {
	private Activity mContext;
	private Handler mHandler;
	private Dialog myDialog;

	// public static final String RSA_PRIVATE =
	// "MIICXAIBAAKBgQCvYl+knxJryUFb9QO2LhYPvHlX3YfpmeejMfzQmqv8VW3/lAcy3KmRO43Xj7fsAibY46HmRNLEuT/pUXanVSiK8BEYO8t3tjp1dP3vOxHsxQVf9G0eZRAM7jRX6kGdZSh5LIXWW85biGemyMMXbjSc+8KCNyjKQwNje2zVYv+fvQIDAQABAoGAf0U97rJYpcsoGNkWVm/fJpA45iAmbsh2paCyu5ZiU/ySlDMRfkFO88tpxRHP//4XvrCBXh53bLaZwHCTUB6mCehh+URsmxyHSqxxx7zEAXh+fabUgdwasF3kgHMlmN6IWMZkEZ5hNlW9ZtecdP2x96QhelmguGTdUtU0ZEek+SkCQQDlnjUg4MDa0gFC8+OvlftWXCb+xcZ0QuA6UdJXmFx5bftmnVGpZ5WCLLafHOJK5DJXRuYB5GrKKHlPoc8YmUOLAkEAw4j6/ghrbP3l9YhyBYbTd+X2XbGLuIlTdNiuE2M40wDviAWbhJlpefQIC+r57NsE4jU7RxFlkVlOjy/nJRxy1wJBAMMkZoCPojcVvO66uWkTLOxjGtzxFkzePRQzT3EUUpr8Zmj6EFnJZ52fBIFLGeKN916HKZi4GC3AogCUQp3kOysCQACH9LaP60rGfcNm9XhSX2yp8Ttb6hc8OD/O/toKoPLhw8TIzx1TFVhBc2wk3Tzpc1/x6RFOWMpGRdJZvPT62M0CQHkJwnYq+XVVZQ5Kb0f1f301FIMx/mQJcNgvRFCPtxVFoY9BoMinTcdxatpP/hdwUQjgIBZGf3MWHM1myUn4R8M=";
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	public AlipayTask(Activity context, Handler mHandler) {
		this.mContext = context;
		this.mHandler = mHandler;

		// myDialog = DialogHelper.showProgress(context, "正在设置密保...", false);
		myDialog = DialogHelper.showProgress(context, "", false);
	}

	protected Result doInBackground(Void... params) {
		Result mResult = GetDataImpl.getInstance(mContext).alipaySubmit();
		myDialog.dismiss();
		return mResult;
	}

	protected void onPostExecute(Result result) {
		if (null != myDialog && myDialog.isShowing()) {
			myDialog.cancel();
		}
		if (result != null && result.resultCode == 0) {

			String orderInfo = "传传参";
			Log.i("feng", "alipay orderInfo:" + orderInfo);
			/*Log.i("feng",
					"orderInfo::: " + orderInfo + "     kkk:"
							+ orderInfo.length());*/
			// 对订单做RSA 签名
			String sign = sign(orderInfo);
			// Log.i("feng","999999999999999:"+ sign);
			try {
				// 仅需对sign 做URL编码
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 完整的符合支付宝参数规范的订单信息
			final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                                               + getSignType();
			/*
			 * AlipayImpl .pay(
			 * "_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fpay.m.5399.com%2Fapi%2Fsdk%2Falipay.php\"&out_trade_no=\"m201505211824285004421\"&partner=\"2088901660518978\"&payment_type=\"1\"&seller_id=\"haodong5399@163.com\"&service=\"mobile.securitypay.pay\"&subject=\"5399手游充值\"&total_fee=\"1\"&sign=\"rBObqZNw7%2BtJLd%2FVEEd0WXXmXJn5%2BqxfswonD6HruONbZJ%2B1TruiZsnSSEs%2Bj831S2jfERPRZpRMqIRcvGYKHiwYweQltPeIHdnF2m73p8UBaXJE57s4358rourh93woCCYD54ExxoRO7Xzv%2FzQHwyTf5%2Brwkb1ayVt79PVIXOE%3D\"&sign_type=\"RSA\""
			 * , this, mHandler);
			 */
//			Log.i("feng", "Result.sessionId:" + Result.sessionId + "     kkkk:"
//                                      + Result.sessionId.length());
			AlipayImpl.pay(payInfo, mContext, mHandler);
			// Utils.toastInfo(mContext, result.resultDescr);
			Utils.youaiLog("支付宝:" + result.resultDescr);
			// new ActivityService(mContext).listenerBack(Constants.BACK_USER);
		} else {
			try {
				Utils.youaiLog("支付宝:" + result.resultDescr);
				// Utils.toastInfo(mContext, result.resultDescr);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public String sign(String content) {
		return SignUtils.sign(content, ZhimengPayActivity.RSA_PRIVATE);
	}

}
