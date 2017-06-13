package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yj.entity.ChargeData;
import com.yj.entity.PayChannel;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;

public class ChargePaymentListLayout extends ChargeAbstractLayout {

	
	public static int isClickId = 0;
	protected ListView mPaymentType;
	private Activity mActivity;
	private TextView mErr;
	private LinearLayout mLayout;
	public LinearLayout rightLayout;

	// 初始模拟点击事件
	private Button btn;

	public ChargePaymentListLayout(Activity activity, ChargeData mCharge,
                                       boolean isLockInput) {
		super(activity);
		mActivity = activity;
		initUI(activity);
	}

	@SuppressWarnings("ResourceType")
	@Override
	protected void initUI(Activity activity) {
		super.initUI(activity);

		mSubject = new LinearLayout(activity);
		mSubject.setOrientation(LinearLayout.VERTICAL);
		LayoutParams lp = new LayoutParams(-1, -1);
		mContent.addView(mSubject, lp);

		mLayout = new LinearLayout(activity);
		mLayout.setOrientation(LinearLayout.HORIZONTAL);
		mSubject.addView(mLayout, -1, -1);

		LinearLayout lefeLayout = new LinearLayout(activity);
		lefeLayout.setOrientation(LinearLayout.VERTICAL);
		lefeLayout.setBackgroundColor(0xffdadddd);
		mLayout.addView(lefeLayout, DimensionUtil.dip2px(activity, 100), -1);

		mPaymentType = new ListView(activity);
		mPaymentType.setDivider(null);
		mPaymentType.setSelector(android.R.color.transparent);
		lp = new LayoutParams(-1, -1);
		lp.topMargin = DimensionUtil.dip2px(activity, 10);
		lefeLayout.addView(mPaymentType, lp);

		ScrollView mScrollView = new ScrollView(activity);
		lp = new LayoutParams(-1, -1);
		mLayout.addView(mScrollView, lp);

		rightLayout = new LinearLayout(activity);
		lefeLayout.setOrientation(LinearLayout.VERTICAL);
		mScrollView.addView(rightLayout);
		//

		// LinearLayout rightLayout = new CardCharger(activity);
		// mScrollView.addView(rightLayout,-1,-1);

		mErr = new TextView(mActivity);
		lp = new LayoutParams(-1, -1);
		lp.gravity = Gravity.CENTER;
		mErr.setText("不好意思，获取充值通道失败啦~(≧▽≦)/~。");
		mErr.setTextColor(0xfffdc581);
		mErr.setTextSize(16);
		mErr.setVisibility(View.GONE);
		mErr.setGravity(Gravity.CENTER);
		mContent.addView(mErr, lp);

		btn = new Button(mActivity);
		btn.setId(0x110011);
	}

	public void showPayList(int visibility) {
		switch (visibility) {
		case View.GONE:
			mSubject.setVisibility(View.GONE);
			mErr.setVisibility(View.VISIBLE);
			break;
		case View.VISIBLE:
			mSubject.setVisibility(View.VISIBLE);
			mErr.setVisibility(View.GONE);
			break;
		}

	}

	@Override
	public ChargeData getChargeEntity() {
		return null;
	}

	class PaymentListAdapter extends BaseAdapter {

		private PayChannel[] payChannels;

		public PaymentListAdapter(PayChannel[] channelMessages) {
			this.payChannels = channelMessages;
		}

		@Override
		public int getCount() {
			return CharhePaymentListLayoutVertical.payChannelCount;
		}

		@Override
		public Object getItem(int position) {
			return payChannels[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ChagerListLayout chagerLayout = (ChagerListLayout) convertView;
			if (chagerLayout == null) {
				chagerLayout = new ChagerListLayout(mActivity);
			}
			if (payChannels[position].paymentId == 2) {
				// 充值默认选中
				// isClickId = payChannels[position].paymentId;
				// // 模拟点击一次

				isClickId = payChannels[position].paymentId;
				chagerLayout.setBackgroundDrawable(BitmapCache
						.getNinePatchDrawable(mActivity,
								"charge_lefe_btn.9.png"));
				chagerLayout.textView.setTextColor(0xff007ef9);
				chagerLayout.textView.setPadding(
						DimensionUtil.dip2px(mActivity, 15),
						DimensionUtil.dip2px(mActivity, 10),
						DimensionUtil.dip2px(mActivity, 15),
						DimensionUtil.dip2px(mActivity, 10));
				// isClickId = payChannels[position].paymentId;
			}
			chagerLayout.textView.setText(payChannels[position].paymentName);
			chagerLayout.setId(payChannels[position].paymentId + 2);
			return chagerLayout;
		}
	}

	public class ChagerListLayout extends LinearLayout {
		ImageView imageView;
		public TextView textView;

		public ChagerListLayout(Context context) {
			super(context);
			initUI(context);
		}

		public void initUI(Context context) {
			this.setClickable(false);
			this.setOrientation(LinearLayout.HORIZONTAL);
			this.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
					mActivity, "charge_btn.9.png"));
			textView = new TextView(mActivity);
			textView.setPadding(DimensionUtil.dip2px(mActivity, 15),
					DimensionUtil.dip2px(mActivity, 10),
					DimensionUtil.dip2px(mActivity, 15),
					DimensionUtil.dip2px(mActivity, 10));
			textView.setTextSize(18);
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
			textView.setTextColor(0xff4c4c4c);
			addView(textView);

		}

	}

	public void setChannelMessages(PayChannel[] channelMessages) {
		mPaymentType.setAdapter(new PaymentListAdapter(channelMessages));

	}

	public void setOnItemClickListener(
			AdapterView.OnItemClickListener onItemClickListener) {
		mPaymentType.setOnItemClickListener(onItemClickListener);
	}

	// 将所有的数字、字母及标点全部转为全角字符
	public String ToDBC(String input) {
		if (null == input)
			return null;
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public void clickListItem(View v) {
		switch (v.getId()) {
		case 0x110011:
			// 模拟点击

			break;
		case ChargeAbstractLayout.ID_EXIT:
			// 返回按钮

			break;
		default:
			break;
		}
	}

}
