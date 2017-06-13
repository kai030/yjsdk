/**
 * 
 */
package com.yj.sdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.yj.entity.ChargeData;
import com.yj.entity.Constants;
import com.yj.ui.DenominationLayout;
import com.yj.ui.ExitLayout;
import com.yj.ui.PayFailureLayout;
import com.yj.ui.PayHomeLayout;
import com.yj.ui.PaySucceedLayout;
import com.yj.ui.YeePayLayout;
import com.yj.util.ActivityService;
import com.yj.util.AlipayTask;
import com.yj.util.SignUtils;
import com.yj.util.ToastUtils;
import com.yj.util.UnionPayTask;
import com.yj.util.YeepayTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class ZhimengPayActivity extends Activity implements OnClickListener {
	private ActivityService activityService;
	private PayHomeLayout payHomeLayout;
	private ExitLayout exit;
	private PayFailureLayout failureLayout;
	private PaySucceedLayout succeedLayout;

	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		exit = new ExitLayout(this);
		exit.setOnClickListener(this);
		failureLayout = new PayFailureLayout(this);
		failureLayout.setOnClickListener(this);
		succeedLayout = new PaySucceedLayout(this);
		succeedLayout.setOnClickListener(this);
		this.yeePayLayout = new YeePayLayout(this, 2);
		this.activityService = new ActivityService(this);
		payHomeLayout = new PayHomeLayout(this);
		payHomeLayout.setOnClickListener(this);
		setContentView(payHomeLayout);
		this.activityService.pushView2Stack(payHomeLayout);// 入栈

//		new AlipayTask(this,mHandler).execute();

	}

	private YeePayLayout yeePayLayout;
	private String moneys[] = new String[] {"10", "20", "30", "50", "100",
                                                "300", "500" };
	// 商户PID
	// public static final String PARTNER = "2088911356733297";
//	public static final String PARTNER = "2088811770736345";// 指盟
	// 商户收款账号
	// public static final String SELLER =
	// "pulamsi@qq.com";//haodong5399@163.com

	/* 指盟 */
//	public static final String SELLER = "zmzfb@zytcgame.com";
	// 商户私钥，pkcs8格式
	// public static final String RSA_PRIVATE =
	//	                                         "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJZ04mRaYwt1ScwzF+vEPzZURkjGMJrFwiIzb0m0Lf8zH9dolx2xAJ9H8RwPL05Mc8B/uSE5D+n76pyNb7mh6Ewo1HFsjHNnD8o0827oN05M+xybh2fgv2Kci0nMhBI2Lu1i2AXNODNV4/9yEDX+16Frgotm0miAubV26hRTwCtAgMBAAECgYAuP0CtMXmvEctt6Cd2wEylZCy1WfZ3o5FLcz32SHRi4Tid5kfNtjZJduyf4YK+hnA1/4x68ULGhuOMzSTGpbyRTtD/lXutbs9ITnA8iiaQIEJsvyv1d3QEvTewIiDjT2NzF8fOrPH8DFtKV31RbRsQQIG2pxd9Pm3r3KvG/wLwOQJBANvW/wbjDOmGNmsWUmYB1InJrhMvUgdKyoWTgnPTIMinSqR6U4rJAq7oRQuvgWUKSTu9BC41bentn2r+QKK/9h8CQQDPr9Da5lv1R06kmuE48975rEUJVsSNrrDgWQ7Ca2rIEVkJMIa1FafaS2ZConsFcrwdl9s7xdeIT+8xYrKRovezAkEAjf8pzO39Gh1pqvPJF0BZYwKU4KlxB2rEs1DbMysNAu4jpWep7Hv3srguWOTs5DGnqeFmAN7b9vxYO0iswTLSqwJAFSjPeY3grpOuQnz5F0lZXUyc1/+8FMdIhALuywYQogOKc7I69zYWnNnzuDQ/nmV7HvS6xFM6y0uBjiGFGetlmwJBAIUwh1rjvPdnL4novgIsvzfRy2lcGkmY6qfyOhEQ2OyiRn7G1G0jsx1nR1SZQNTVKkAQNm7luGBri/JR2NE2D2o=";

	/* zhimeng */
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALkwC1oE6/mUzD8ulLruC65TYU72logMfOmzCgWz1BZmUKVBziJ/4SUYh05yJhFT898gAkIai67KHp6JntJRbBHIJyUnTprk4j0yHu8GMfsz03/5jTfYqs4BabEcYnQ4CTKBBF8j5WW4ZW3xDtFFoqXo/UteNta3NTOjAvcGpbibAgMBAAECgYBJLg0uU4eUpxJXXWb0v6DqEJUBiTxPIvAxwciSHFARUflIdMqqo3IrIJhHjaAxdMWIRLPfv3G3brPO3n9eiUJ00FNv2+zfcAvRBNXLe7xDEWTBfy3+zcKul1pZbWxKdqGXKdDITCifbA0Yhjt7qcqVIiTKFVZZ8ADsAi1eBIkI0QJBANzQAV81lKgdR15k6SkiAwXXD5Q+DKtN7W0eB5i1NDIlXAkRcZ+cOgS7ncVCyC7SRNg79UVMwRSTvfShl+0FUwcCQQDWsrtS7y2H4emfY0UDW+/mQ0QDVbZwROSXm4tjKHoO5454+j3VGI+NgJtpdYsaWbEHAmqeADbTS62ZaSOdEOTNAkEAhJrRyBiITrFOk8xsDUyknhQ6Ad+FgjaJN/dJvpZLYzX2YGe+YYjTetYk+DqTtzKFL4pKUDS42x+ies+0Sh3hbwJAB27mt2dafzLeIMuSDxy2wJUhnK/yAg8QjwMunz8+gpeXwi5/x4nzJmYGe287Yq0qSODXiiLdwxECsKYIOp7kHQJAN6NiYqHvjzWjzMlNZk1PRN1oWyl9PIOzQsaeiiTTiEWL+Z0wn1eP6p9BqlxZywPiUkRm81ruWP7x/HKexAe3jA==";

	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                                                               Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case Constants.ID_PAY_ALIPAY:// 调支付宝界面
                  ChargeData.getChargeData().chargeType = "1";
			new AlipayTask(this,mHandler).execute();
			// String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

		/*	String orderInfo = Result.sessionId;
			Log.i("feng",
					"orderInfo::: " + orderInfo + "     kkk:"
							+ orderInfo.length());
			// 对订单做RSA 签名
			String sign = sign(orderInfo);
			Log.i("feng","999999999999999:"+ sign);
			try {
				// 仅需对sign 做URL编码
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 完整的符合支付宝参数规范的订单信息
			final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
					+ getSignType();
			ChargeData.getChargeData().chargeType = "1";
			
			 AlipayImpl .pay(
			 * "_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fpay.m.5399.com%2Fapi%2Fsdk%2Falipay.php\"&out_trade_no=\"m201505211824285004421\"&partner=\"2088901660518978\"&payment_type=\"1\"&seller_id=\"haodong5399@163.com\"&service=\"mobile.securitypay.pay\"&subject=\"5399手游充值\"&total_fee=\"1\"&sign=\"rBObqZNw7%2BtJLd%2FVEEd0WXXmXJn5%2BqxfswonD6HruONbZJ%2B1TruiZsnSSEs%2Bj831S2jfERPRZpRMqIRcvGYKHiwYweQltPeIHdnF2m73p8UBaXJE57s4358rourh93woCCYD54ExxoRO7Xzv%2FzQHwyTf5%2Brwkb1ayVt79PVIXOE%3D\"&sign_type=\"RSA\""
			 , this, mHandler);
			 
			Log.i("feng", "Result.sessionId:" + Result.sessionId + "     kkkk:"
					+ Result.sessionId.length());
			AlipayImpl.pay(payInfo, this, mHandler);*/
			break;
		case Constants.ID_PAY_CARD_PAYBTN:// 充值卡提交
                  ChargeData.getChargeData().chargeType = "3";
			ChargeData chargeData = ChargeData.getChargeData();
			chargeData.cardDenomination = YeePayLayout.cardDenominationEdit
					.getText().toString();
			chargeData.cardAccount = yeePayLayout.getCardNumEdit().getText()
					.toString();
			chargeData.cardPassword = yeePayLayout.getCardPwdEdit().getText()
					.toString();

			if (chargeData.cardAccount != null
					&& chargeData.cardPassword != null
					&& chargeData.cardAccount.length() > 12
					&& chargeData.cardPassword.length() > 12) {
				try {
					Long.valueOf(chargeData.cardPassword);
					Long.valueOf(chargeData.cardAccount);
					new YeepayTask(this, failureLayout, succeedLayout,
							activityService).execute();

					Log.i("feng",
							"price:"
									+ yeePayLayout.cardDenominationEdit
											.getText() + "     nub:"
									+ yeePayLayout.getCardNumEdit().getText()
									+ "     pwd:"
									+ yeePayLayout.getCardPwdEdit().getText());
				} catch (Exception e) {
					ToastUtils.toastShow(this, "请正确输入充值卡信息");
				}

			} else {
				ToastUtils.toastShow(this, "请正确输入充值卡信息");
			}

			break;
		case Constants.ID_PAY_YEEPAY:// 调易宝充值卡界面
			// ChargeData.getChargeData().chargeType = "2";
			yeePayLayout.setOnClickListener(this);
			activityService.pushView2Stack(yeePayLayout);
			break;
		case Constants.ID_PAY_UNIONPAY:// 银行卡支付
			Log.i("feng", "ID_PAY_UNIONPAY");
                  ChargeData.getChargeData().chargeType = "2";
			new UnionPayTask(this,activityService,this).execute();
			break;

		case Constants.ID_PAY_MOBILE:// 移动充值卡
                  ChargeData.getChargeData().operatorCardType = "1";
			moneys = new String[] {"10", "20", "30", "50", "100", "300", "500" };
			setOperatorBg(View.VISIBLE, View.GONE, View.GONE);
			break;
		case Constants.ID_PAY_UNICOM:// 联通充值卡
                  ChargeData.getChargeData().operatorCardType = "2";
			moneys = new String[] {"20", "30", "50", "100", "300", "500" };
			setOperatorBg(View.GONE, View.VISIBLE, View.GONE);
			break;
		case Constants.ID_PAY_TELECOM:// 电信充值卡
                  ChargeData.getChargeData().operatorCardType = "3";
			moneys = new String[] {"50", "100" };
			setOperatorBg(View.GONE, View.GONE, View.VISIBLE);
			break;
		case Constants.ID_PAY_CLOSE:// 退出支付按钮
			View view = activityService.popViewFromStack();
			if (view == null) {// exit
				// activityService.pushView2Stack(failureLayout);
				activityService.pushView2Stack(exit);
				// activityService.pushView2Stack(succeedLayout);
			}
			break;
		case Constants.ID_PAY_DENOMINATION://充值卡面额
			DenominationLayout denominationLayout = new DenominationLayout(
					this, moneys);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(null);
			builder.setView(denominationLayout);
			builder.create();
			dialog = builder.show();
			break;

		case Constants.ID_EXIT_CLOSE://返回
		case Constants.ID_REGISTER_BACK_BTN://银行卡充值返回
			Log.i("feng", "ID_EXIT_CLOSE");
			activityService.popViewFromStack();
			break;
		case Constants.ID_EXIT_CONFIRM:// 确认退出支付
			Log.i("feng", "ID_EXIT_CONFIRM");
//			YouaiAppService.resultListen
//					.paymentCancelResult(YJResultCode.PAYMENT_CANCEL);
			finish();
			break;
		case Constants.ID_EXIT_CANCEL:// 取消退出支付
			Log.i("feng", "ID_EXIT_CANCEL");
			activityService.popViewFromStack();
			break;
		case Constants.ID_PAY_FAILURE:// 重新支付按钮
			activityService.popViewFromStack();
			Log.i("feng", "ID_PAY_FAILURE");
			break;

		case Constants.ID_PAY_SUCCEED:// 支付成功
			finish();
			Log.i("feng", " Constants.ID_PAY_SUCCEED");
			break;
			
		default:
			break;
		}

	}

	public static Dialog dialog;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.i("feng", "充值后返回給服務器:" + msg.obj);
			// 充值后返回給服務器
			String orderDescr = (String) msg.obj;
			Log.i("feng", "orderDescr:" + orderDescr);
			try {

				String tradeStatus = "resultStatus={";
				int imemoStart = orderDescr.indexOf("resultStatus=");
				imemoStart += tradeStatus.length();
				int imemoEnd = orderDescr.indexOf("};memo=");
				tradeStatus = orderDescr.substring(imemoStart, imemoEnd);
				Log.i("feng", "充值后返回給服務器tradeStatus:" + tradeStatus);
				if ("9000".equals(tradeStatus)) {
					activityService.pushView2Stack(succeedLayout);
//					YouaiAppService.resultListen
//							.paymentSucceedResult(YJResultCode.PAYMENT_SUCCEED);
					/*
					 * mPayResult.orderStatus = 1;
					 * DialogHelper.showPayResultDialog(mActivity);
					 */
				} else {
//					YouaiAppService.resultListen
//							.paymentFailureResult(YJResultCode.PAYMENT_Failure);
					activityService.pushView2Stack(failureLayout);
					// mPayResult.orderStatus = 0;
				}
				/*
				 * String[] json = (msg.obj.toString()).split(";");
				 * mPayResult.orderDescr = json[0]; if
				 * (!StringUtils.isEmpty(mPayResult.orderId)) { // new
				 * PayResultReturnThread(mActivity, mPayResult).start(); }
				 */
			} catch (Exception e) {
			}
		}
	};

	@Override
	public void onBackPressed() {// 系统返回键
		View view = this.activityService.listenerBack(Constants.BACK_PAY);
		if (view == null) {
			exit.setOnClickListener(this);
			activityService.pushView2Stack(exit);
		}
	}

	private void setOperatorBg(int visibility1, int visibility2, int visibility3) {
		this.yeePayLayout.getOperatorBg()[0].setVisibility(visibility1);
		this.yeePayLayout.getOperatorBg()[1].setVisibility(visibility2);
		this.yeePayLayout.getOperatorBg()[2].setVisibility(visibility3);
	}

}
