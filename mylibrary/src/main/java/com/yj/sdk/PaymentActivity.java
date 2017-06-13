package com.yj.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.yj.entity.ChargeData;
import com.yj.entity.ChargeResult;
import com.yj.entity.Constants;
import com.yj.entity.Flag;
import com.yj.entity.PayChannel;
import com.yj.entity.Result;
import com.yj.ui.AlipayChanger;
import com.yj.ui.CardCharger;
import com.yj.ui.ChargeCardRightAbstractLayout;
import com.yj.ui.ChargePaymentListLayout;
import com.yj.ui.ChargeRightAbstractLayout;
import com.yj.ui.CharhePaymentListLayoutVertical;
import com.yj.ui.DialogHelper;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.GetDataImpl;
import com.yj.util.JsonUtil;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
public class PaymentActivity extends Activity implements View.OnClickListener {

	private boolean isPay = true;
	private static final String TOAST_TEXT = "充值金额不正确，请输入1-9999范围内的金额";
	private Result mResult;

	// 卡类
	public static final int INDEX_CHARGE_CARD = 1;
	// 支付宝 / 财付通
	public static final int INDEX_CHARGE_ZHIFUBAO = 2;
	// 财付通
	public static final int INDEX_CHARGE_CAIFUTONG = 3;

	private long mLastClickTime;

	public static Handler payHandler;
	public static int payWhat;

	private ChargeData mCharge;
	private PaymentCallbackInfo mPaymentCallbackInfo;
	private boolean isCancelDialog;
	private Dialog dialog;
	private ChargePaymentListLayout mPaymentListLayout;// 横屏布局
	private CharhePaymentListLayoutVertical mPaymentListLayoutVertical;// 竖屏
	private ChargeRightAbstractLayout rightLayout;
	private PayChannel mChannelMessage;

	private boolean noPayChannel;// 标志获取支付列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* 初始化数据 */
		initData();
		/* 获取支付列表 */
		new PayListTask(mCharge).execute();
	}

	private void initData() {

		/* 初始化标志位 */
          Flag.isFirstCardList = false;
          Flag.isFirst = false;
          Flag.isFirstCard = false;
          Flag.payTypeFlag = 1;
          Flag.position = 0;
          Flag.positionCard = 0;//
          Flag.positionCardList = 0;//标志卡类金额索引
          Flag.positionFast = 0;
          Flag.flag = false;

		Intent intent = getIntent();
		/* 绑定字段简码 */
		mCharge = new ChargeData();
		/* 获取参数 */
		/*mCharge.roleId = intent.getIntExtra(Constants.EXTRA_PRICE, 1) + "";// roleid
		mCharge.roleName = intent.getStringExtra(Constants.EXTRA_ROLE);// rolName
		mCharge.callBackInfo = intent
				.getStringExtra(Constants.EXTRA_CALLBACKINFO);// 自定义参数
//		mCharge.roleLevel = intent.getIntExtra(Constants.EXTRA_ROLELEVEL, 1);
		/* 初始化回调 */
		mPaymentCallbackInfo = new PaymentCallbackInfo();
		mPaymentCallbackInfo.statusCode = -1;
		mPaymentCallbackInfo.desc = "用户没有充值行为";
		/* 加载框 */
		isCancelDialog = false;
		dialog = DialogHelper.showProgress(PaymentActivity.this, "", true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				isCancelDialog = true;
				finish();
			}
		});
	}

	// 获取支付列表
	class PayListTask extends AsyncTask<Void, Void, PayChannel[]> {
		private ChargeData charge;

		public PayListTask(ChargeData charge) {
			if (charge != null) {
				this.charge = charge;
			} else {
				Utils.toastInfo(PaymentActivity.this, "获取列表初始化失败");
				this.charge = new ChargeData();
			}
		}

		@Override
		protected PayChannel[] doInBackground(Void... params) {

			Utils.youaiLog("获取列表");
			return GetDataImpl.getInstance(PaymentActivity.this)
					.getPaymentList(charge);
		}

		@Override
		protected void onPostExecute(PayChannel[] result) {
			for (int i = 0; i < result.length; i++) {
				Log.i("feng", "result[]:" + result[i].selectMoney);
			}
			

			if (isCancelDialog) {
				hideDialog();
				finish();
				return;
			}
			if (result != null && result.length != 0
					&& YouaiAppService.mPayChannels != null
					&& YouaiAppService.mPayChannels.length > 0) {
				Utils.youaiLog("获取列表成功!");
				noPayChannel = true;
				hideDialog();
				init();
				if (mPaymentListLayout != null) {
					mPaymentListLayout
							.setChannelMessages(YouaiAppService.mPayChannels);
					mPaymentListLayout.showPayList(View.VISIBLE);
				}
			} else {
				noPayChannel = false;
				hideDialog();
				init();
				if (mPaymentListLayout != null) {
					mPaymentListLayout.showPayList(View.GONE);
				}
			}
		}
	}

	private void init() {

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
				|| !noPayChannel) {// 横屏或获取列表失败
			// 横屏
			mPaymentListLayout = new ChargePaymentListLayout(
                            PaymentActivity.this, mCharge, false);
			PaymentActivity.this.setContentView(mPaymentListLayout);
//			mPaymentListLayout.setOnItemClickListener(mOnItemClickListener);
			mPaymentListLayout.setButtonClickListener(this);

			if (YouaiAppService.mPayChannelsFast == null)
				return;
			/*
			 * for (PayChannel payChannel : YouaiAppService.mPayChannelsFast) {
			 * if (payChannel.paymentId == 2) { mChannelMessage = payChannel; }
			 * }
			 */
			if (mChannelMessage != null) {
				rightLayout = new AlipayChanger(PaymentActivity.this,
                                                                mChannelMessage, mCharge);
				rightLayout.setButtonClickListener(PaymentActivity.this);
				mPaymentListLayout.rightLayout.addView(rightLayout, -1, -1);
			}
			// 网格金额选中监听
			if (rightLayout != null) {
//				rightLayout.setOnItemClickListener(onItemClickListener);
				rightLayout.setButtonClickListener(this);
			}
			setContentView(mPaymentListLayout);
		} else {
			if (YouaiAppService.mPayChannelsFast == null)
				return;
			// 竖屏
			mPaymentListLayoutVertical = new CharhePaymentListLayoutVertical(
                            PaymentActivity.this, YouaiAppService.mPayChannels, mCharge);
			mPaymentListLayoutVertical.setButtonClickListener(this);
			mPaymentListLayoutVertical.setYibaoOnClickListener(this);
			mPaymentListLayoutVertical.setZhifubaoOnClickListener(this);
			setContentView(mPaymentListLayoutVertical);
		}
	}

	// 网格
	/*private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adpater, View view,
				int position, long arg3) {
			// 如果禁止输入金额，直接禁止金额选中事件
			// if (isLockInput)
			// return;
			Log.i("feng", "直接禁止金额选中事件");
			for (int i = 0; i < adpater.getCount(); i++) {
				ChagerLayout layout = (ChagerLayout) adpater.getChildAt(i);
				if (position == i) {// 当前选中的Item改变背景颜色
					layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
							PaymentActivity.this, "grid_click_it.9.png"));
					layout.yuan.setTextColor(0xffffffff);
					int money = -1;
					try {
						money = Integer.valueOf(layout.textView.getText()
								.toString());
					} catch (Exception e) {
					}

					if (money <= 0 || money > 100000) {
						money = 10;
					}
					layout.textView.setTextColor(0xffffffff);
					// 网格可填入金额
					if (rightLayout.input != null) {
						rightLayout.input.setText(layout.textView.getText()
								.toString());
//						Utils.clearEditTextFocus(rightLayout.input);
						Log.i("feng", "直接禁止金额选中事件555555555555");
					}

				} else {
					layout.yuan.setTextColor(0xff758ba6);
					layout.textView.setTextColor(0xff758ba6);
					layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
							PaymentActivity.this, "grid_click.9.png"));
				}
				layout.setPadding(
						DimensionUtil.dip2px(PaymentActivity.this, 10),
						DimensionUtil.dip2px(PaymentActivity.this, 7),
						DimensionUtil.dip2px(PaymentActivity.this, 10),
						DimensionUtil.dip2px(PaymentActivity.this, 7));
			}
		}
	};*/

	/** 支付列表点击事件 */
//	public OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//			Log.i("feng", "/** 支付列表点击事件 */ /** 支付列表点击事件 */");
//			// 清除网格记录选中金额
//			if (rightLayout != null) {
//				mPaymentListLayout.rightLayout.removeView(rightLayout);
//			}
//			mChannelMessage = (PayChannel) parent.getAdapter()
//					.getItem(position);
//			cancelPitchOn();
//			ChargePaymentListLayout.isClickId = mChannelMessage.paymentId;
//			ChargePaymentListLayout.ChagerListLayout layout = (ChargePaymentListLayout.ChagerListLayout) view;
//			itemClick(layout);
//			if (mChannelMessage == null)
//				return;
//			switch (mChannelMessage.paymentType) {
//			// 支付宝 // 财付通
//			case INDEX_CHARGE_ZHIFUBAO:
//				rightLayout = new AlipayChanger(PaymentActivity.this,
//						mChannelMessage, mCharge);
//				rightLayout.setButtonClickListener(PaymentActivity.this);
//				mPaymentListLayout.rightLayout.addView(rightLayout, -1, -1);
//				// pushView2Stack(mPaymentListLayout);
//				break;
//
//			case INDEX_CHARGE_CAIFUTONG:
//
//				break;
//			// 卡类
//			case INDEX_CHARGE_CARD:
//
//				rightLayout = new CardCharger(PaymentActivity.this,
//						mChannelMessage, mCharge);
//				rightLayout.setButtonClickListener(PaymentActivity.this);
//				mPaymentListLayout.rightLayout.addView(rightLayout, -1, -1);
//				break;
//
//			}
//			// 网格金额选中监听
//			rightLayout.setOnItemClickListener(onItemClickListener);
//		}
//	};

	// 左边栏目取消选中
	public void cancelPitchOn() {
		ChargePaymentListLayout.ChagerListLayout layout = (ChargePaymentListLayout.ChagerListLayout) findViewById(
                    ChargePaymentListLayout.isClickId + 2);
		layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
                    PaymentActivity.this, "charge_btn.9.png"));
		layout.textView.setPadding(
				DimensionUtil.dip2px(PaymentActivity.this, 15),
				DimensionUtil.dip2px(PaymentActivity.this, 10),
				DimensionUtil.dip2px(PaymentActivity.this, 15),
				DimensionUtil.dip2px(PaymentActivity.this, 10));
		layout.textView.setTextColor(0xff4c4c4c);
	}

	// 左边栏目选中
	private void itemClick(ChargePaymentListLayout.ChagerListLayout layout) {
		layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
                    PaymentActivity.this, "charge_lefe_btn.9.png"));
		layout.textView.setTextColor(0xff007ef9);
		layout.textView.setPadding(
				DimensionUtil.dip2px(PaymentActivity.this, 15),
				DimensionUtil.dip2px(PaymentActivity.this, 10),
				DimensionUtil.dip2px(PaymentActivity.this, 15),
				DimensionUtil.dip2px(PaymentActivity.this, 10));
	}

	public void setPaymentCallbackInfo(int statusCode, String desc) {
		if (mPaymentCallbackInfo != null) {
			mPaymentCallbackInfo.statusCode = statusCode;
			mPaymentCallbackInfo.desc = desc;
		}
	}

	protected void hideDialog() {
		if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		// 防止连续点击，时间间隔2秒
		long millis = System.currentTimeMillis();
		if (millis - mLastClickTime < 2000) {
			return;
		}

		ChargeData charge = null;
		int type = -1;
		switch (v.getId()) {

		// 支付宝
		case AlipayChanger.ID_ALIPAY:

			
			if (rightLayout != null && !rightLayout.checkNum()) {
				Utils.toastInfo(PaymentActivity.this, TOAST_TEXT);
				return;
			}

			if (Flag.payTypeFlag == Constants.ALIPAY) {
				type = 2;
			} else if (Flag.payTypeFlag == Constants.YEEPAY) {
				type = 3;
			}

			break;

		// 卡类
		case CardCharger.ID_YIBO:
			type = 4;
		/*	if (mPaymentListLayoutVertical != null
					&& !mPaymentListLayoutVertical.rightLayout.checkMoney()) {
				return;
			}
			if (rightLayout != null && !rightLayout.checkNum()) {
				Utils.toastInfo(PaymentActivity.this, "请选择金额！");
				return;
			}
			if (rightLayout != null && !rightLayout.checkMoney()) {
				return;
			}
			type = INDEX_CHARGE_CARD;*/
			break;

		case Constants.ID_REGISTER_BACK_BTN:
			onBackPressed();
			break;

		case ChargeRightAbstractLayout.CHARGE_EXPLAIN:
			
			break;
		}

		mLastClickTime = System.currentTimeMillis();

		if (type != -1) {
			isPay = true;
//			if (rightLayout == null) {
				// 竖版
				charge = mPaymentListLayoutVertical.rightLayout
						.getChargeEntity();
				mChannelMessage = mPaymentListLayoutVertical.mPayChannel;

		/*	} else {
				charge = rightLayout.getChargeEntity();
			}*/
			if (charge != null) {
				dialog = DialogHelper.showProgress(PaymentActivity.this, "",
                                                                   true);
				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						isPay = false;
						mPaymentCallbackInfo.money = 0;
					}
				});
				if(type == 4){
					String[] str = YouaiAppService.mPayChannelsCard.get(
                                            Flag.positionCard).selectMoney.split(",");
//					charge.money = Integer.valueOf(str[Flag.positionCardList]);
					charge.cardAccount = ChargeCardRightAbstractLayout.cardNumber.getText().toString();
					charge.cardPassword = ChargeCardRightAbstractLayout.inputPsw.getText().toString();
					if(charge.cardAccount == null ||"".equals(charge.cardAccount)){
						Utils.toastInfo(PaymentActivity.this, "卡号不能为空");
						dialog.cancel();
						return;
					}else if(charge.cardPassword == null || "".equals(charge.cardPassword)){
						Utils.toastInfo(PaymentActivity.this, "卡密码不能为空");
						dialog.cancel();
						return;
					}
				}
				new PaymentThread(type, charge, mHandler).start();
//				mPaymentCallbackInfo.money = charge.money;
			}
		}
	}

	class PaymentThread extends Thread {

		private int type;
		private ChargeData charge;
		private Handler handler;

		public PaymentThread(int type, ChargeData charge, Handler handler) {
			this.type = type;
			this.charge = charge;
			this.handler = handler;

		}

		@Override
		public void run() {
			if (YouaiAppService.mSession == null || !YouaiAppService.isLogin) {
				Result result1 = new Result();
				result1.resultCode = -1;
				result1.resultDescr = "帐号信息已经过时，请重新登录游戏";
				return;
			}
			Message message = new Message();
			message.what = type;
			/*message.obj = GetDataImpl.getInstance(PaymentActivity.this).charge(
					charge);*/
			handler.sendMessage(message);
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (!isPay) {
				hideDialog();
				return;
			}
			isPay = false;

			String json = (String) msg.obj;
			mResult = (Result) JsonUtil.parseJSonObject(Result.class, json);
			Utils.youaiLog("订单信息------>订单信息------>" + mResult.resultCode + "  " + "   " + msg.what);
			if(msg.what == 4 && mResult.resultCode == 0){
				DialogHelper.showPayResultDialog(PaymentActivity.this);
			}
			ChargeResult chargeResult = (ChargeResult) JsonUtil
					.parseJSonObject(ChargeResult.class, json);
//			Utils.youaiLog("订单信息------>订单信息------>" + chargeResult.resultStr + "  " + "   "+msg.what);
			if (mResult == null) {
				Utils.toastInfo(PaymentActivity.this,
                                                "对不起，网络连接失败，请确认您的网络是否正常后再尝试，如需帮助请联系客服!");
				hideDialog();
				return;
			}

			if (chargeResult == null) {
				Utils.toastInfo(PaymentActivity.this, mResult.resultDescr);
				hideDialog();
				return;
			}
			Utils.youaiLog("订单号------>" + chargeResult.orderId);
			Utils.youaiLog("订单信息------>" + chargeResult.resultStr);
			

			if (mResult.resultCode != 0) {
				Utils.toastInfo(PaymentActivity.this, "支付失败！");
				hideDialog();
				return;
			}

			mLastClickTime = System.currentTimeMillis();

			switch (msg.what) {
			// 支付宝
			case 2:
				Log.i("feng", "chargeResult:" + chargeResult.resultStr + "   mChannelMessage.paymentId:" + mChannelMessage.paymentId + "    mResult:" + mResult.resultDescr );
			/*	new AlipayImpl(PaymentActivity.this, chargeResult,
						mChannelMessage.paymentId, mResult).pay();*/
				break;
			case 3:
				Utils.openWebpage(chargeResult.resultStr, PaymentActivity.this);
				// 易宝
				break;
			// 卡类
			case 4:
//				DialogHelper.showPayResultDialog(PaymentActivity.this);
				break;
			}

			if (msg.what != INDEX_CHARGE_CARD) {
				hideDialog();
			}
		};
	};

	protected void onDestroy() {
		super.onDestroy();
		if (payHandler != null) {
			Message msg = Message.obtain();
			msg.what = payWhat;
			msg.obj = mPaymentCallbackInfo;
			payHandler.sendMessage(msg);
		}

	};

}
