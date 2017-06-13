package com.yj.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yj.sdk.PaymentActivity;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * 
 * @author lufengkai
 *
 */
public class DialogHelper {

	/**
	 * 
	 * @param context
	 * @param message
	 *            显示的信息
	 *            进度是否确定 false：不确定， true:进度可以确定
	 * @param cancelable
	 *            设置为false，按返回键不能退出。默认为true。
	 * @return
	 */
	public static Dialog showProgress(Context context, CharSequence message,
                                          boolean cancelable) {
		MyProgressDialog dialog = new MyProgressDialog(context);
		dialog.setMessage(message);
		// dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(cancelable);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

		return dialog;
	}

	public static void showPayResultDialog(Activity activity) {
		Dialog dialog = new MyDialog(activity);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	
	public static void showPaySMDialog(Activity activity, String str) {
		Dialog dialog = new ChargeSMDialog(activity, str);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
}

class MyDialog extends Dialog {
	private Activity mActivity;
	private LinearLayout ll;
	private Drawable drawable;
	private StateListDrawable stateListDrawable;

	public MyDialog(Activity activity) {
		super(activity);
		mActivity = activity;
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		if (ll == null) {
			ll = new LinearLayout(activity);
			ll.setOrientation(LinearLayout.VERTICAL);
			ll.setGravity(Gravity.CENTER);
			if (drawable == null) {
				drawable = BitmapCache.getNinePatchDrawable(activity, "login_bg.9.png");
			}
			ll.setBackgroundDrawable(drawable);
			ll.setPadding(DimensionUtil.dip2px(mActivity, 10),
					DimensionUtil.dip2px(mActivity, 10),
					DimensionUtil.dip2px(mActivity, 10),
					DimensionUtil.dip2px(mActivity, 10));
			TextView textView = new TextView(activity);
			textView.setTextSize(18);
			textView.setTextColor(0xff2a2a2a);
			textView.setText("充值正在进行中，请稍后在游戏中查看，一般1-10分钟到账。如未到账，请联系客服，祝您游戏愉快！");
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
			lp.rightMargin = DimensionUtil.dip2px(activity, 15);
			lp.leftMargin = DimensionUtil.dip2px(activity, 15);
			lp.topMargin = DimensionUtil.dip2px(activity, 5);
			ll.addView(textView, lp);

			Button imageButton = new Button(activity);
			if (stateListDrawable == null) {
				stateListDrawable = Utils
                                    .getStateListtNinePatchDrawable(activity, "btn_blue.9.png", "btn_blue_down.9.png");
			}
			imageButton.setBackgroundDrawable(stateListDrawable);
			;
			lp = new LinearLayout.LayoutParams(-2, -2);
			imageButton.setText("确  定");
			imageButton.setTextColor(Color.WHITE);
			imageButton.setTextSize(18);
			lp.topMargin = DimensionUtil.dip2px(activity, 25);
			lp.bottomMargin = DimensionUtil.dip2px(mActivity, 10);
			// lp.topMargin = DimensionUtil.dip2px(activity, 25);
			imageButton.setOnClickListener(new CloseListener());
			imageButton.setPadding(DimensionUtil.dip2px(mActivity, 20),
					DimensionUtil.dip2px(mActivity, 8),
					DimensionUtil.dip2px(mActivity, 20),
					DimensionUtil.dip2px(mActivity, 8));
			ll.addView(imageButton, lp);
		}

		setContentView(ll);
	}

	class CloseListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			cancel();
			if(mActivity instanceof PaymentActivity){
				((PaymentActivity)mActivity).setPaymentCallbackInfo(0, "用户有充值行为");
			}
			mActivity.finish();
		}
	};

}

class MyProgressDialog extends Dialog {

	private TextView mMessage;
	private LinearLayout mLayout;

	public MyProgressDialog(Context context) {
		super(context);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mLayout = new LinearLayout(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
		mLayout.setLayoutParams(lp);
		mLayout.setGravity(Gravity.CENTER);

		mLayout.setOrientation(LinearLayout.HORIZONTAL);
		ProgressBar bar = new ProgressBar(context);
		//noinspection ResourceType
		bar.setInterpolator(context, android.R.anim.linear_interpolator);
		mLayout.addView(bar, lp);

		mMessage = new TextView(context);
		mMessage.setTextColor(Color.BLACK);
		lp.rightMargin = 10;
		mLayout.addView(mMessage, lp);
		setContentView(mLayout);
	}

	void setMessage(CharSequence message) {
		this.mMessage.setText(message);
	}

}


class ChargeSMDialog extends Dialog {
	private Activity mActivity;
	private LinearLayout ll;
	private Drawable drawable;
	private StateListDrawable stateListDrawable;

	public ChargeSMDialog(Activity activity, String str) {
		super(activity);
		mActivity = activity;
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		if (ll == null) {
			ll = new LinearLayout(activity);
			ll.setOrientation(LinearLayout.VERTICAL);
			ll.setGravity(Gravity.CENTER);
			if (drawable == null) {
				drawable = BitmapCache.getNinePatchDrawable(activity, "login_bg.9.png");
			}
			ll.setBackgroundDrawable(drawable);
			ll.setPadding(DimensionUtil.dip2px(mActivity, 10),
					DimensionUtil.dip2px(mActivity, 10),
					DimensionUtil.dip2px(mActivity, 10),
					DimensionUtil.dip2px(mActivity, 10));
			
			ScrollView scroll = new ScrollView(mActivity);
			ll.addView(scroll, Utils.compatibleToWidth(mActivity, 390), Utils.compatibleToWidth(mActivity, 250));
			
			TextView textView = new TextView(activity);
			textView.setTextSize(18);
			textView.setTextColor(0xff2a2a2a);
			textView.setText(Html.fromHtml(str));
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
			lp.rightMargin = DimensionUtil.dip2px(activity, 15);
			lp.leftMargin = DimensionUtil.dip2px(activity, 15);
			lp.topMargin = DimensionUtil.dip2px(activity, 5);
			scroll.addView(textView, lp);

			Button imageButton = new Button(activity);
			if (stateListDrawable == null) {
				stateListDrawable = Utils
                                    .getStateListtNinePatchDrawable(activity, "btn_blue.9.png", "btn_blue_down.9.png");
			}
			imageButton.setBackgroundDrawable(stateListDrawable);
			;
			lp = new LinearLayout.LayoutParams(-2, -2);
			imageButton.setText("确  定");
			imageButton.setTextColor(Color.WHITE);
			imageButton.setTextSize(18);
			lp.topMargin = DimensionUtil.dip2px(activity, 10);
			lp.bottomMargin = DimensionUtil.dip2px(mActivity, 5);
			// lp.topMargin = DimensionUtil.dip2px(activity, 25);
			imageButton.setOnClickListener(new CloseListener());
			imageButton.setPadding(DimensionUtil.dip2px(mActivity, 20),
					DimensionUtil.dip2px(mActivity, 3),
					DimensionUtil.dip2px(mActivity, 20),
					DimensionUtil.dip2px(mActivity, 3));
			ll.addView(imageButton, lp);
		}
		setContentView(ll);
	}
	
	class CloseListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			cancel();
		}
	};
}