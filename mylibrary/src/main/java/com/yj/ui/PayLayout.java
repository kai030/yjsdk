/**
 * 
 */
package com.yj.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.entity.ChargeData;
import com.yj.entity.Constants;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
@SuppressWarnings("ResourceType")
public class PayLayout extends RelativeLayout implements OnClickListener {

	protected LinearLayout payMoney;
	protected LinearLayout activeLayout;
	protected LinearLayout.LayoutParams activeParams;
	protected LinearLayout layout;
	protected LinearLayout operatorsLayout;
	protected ImageView yeepayImage;

	private OnClickListener clickListener;

	public void setOnClickListener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}

	/**
	 * @param context
	 */
	public PayLayout(Context context, int flag) {
		super(context);

		this.setBackgroundColor(0x0aefefef);
		// ScrollView scrolllView = new ScrollView(context);
		int height = 0;
		if (flag == 1) {
			height = (int)(Utils.compatibleToWidth(context) * 0.9);
		} else {
			height = -2;
		}
		LayoutParams params = new LayoutParams(
                    Utils.compatibleToWidth(context), height);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		layout = new LinearLayout(context);
		layout.setId(Constants.ID_LOGOUT);
		this.addView(layout, params);

		layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
                                                                              "pay_home_bg.9.png"));
		/* layout.setBackgroundColor(0xcccccccc); */
		layout.setOrientation(LinearLayout.VERTICAL);

		RelativeLayout titleLayoutOuter = new RelativeLayout(context);
		RelativeLayout titleLayout = new RelativeLayout(context);
		// titleLayout.setBackgroundColor(color);
		layout.addView(titleLayoutOuter, -1, DimensionUtil.dip2px(context, 40));
		titleLayout.setGravity(Gravity.CENTER);
		ImageView titleImage = new ImageView(context);
		titleImage.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "zhimeng_pay.png"));

		titleLayout.addView(titleImage, -2, -2);
		titleLayoutOuter.addView(titleLayout, -1,
                                         DimensionUtil.dip2px(context, 40));
		LayoutParams layoutParams = new LayoutParams(
				-2, -2);
		layoutParams.rightMargin = DimensionUtil.dip2px(context, 6);
		layoutParams.topMargin = DimensionUtil.dip2px(context, 7);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		ImageView close = new ImageView(context);
		close.setId(Constants.ID_PAY_CLOSE);
		close.setOnClickListener(this);
		close.setImageDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "pay_home_close.png"));
		titleLayoutOuter.addView(close, layoutParams);

		View divideTitle = new View(context);
		divideTitle.setBackgroundColor(0x66bbbbbb);
		LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                    -1, DimensionUtil.dip2px(context, 1));
		layout.addView(divideTitle, layoutParams2);// listview_divide

		payMoney = new LinearLayout(context);
		payMoney.setGravity(Gravity.CENTER_VERTICAL);
		payMoney.setOrientation(LinearLayout.VERTICAL);
		// payMoney.setBackgroundColor(0xaaeeeeee);
		layoutParams2 = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(
				context, -2));
		layoutParams2.leftMargin = DimensionUtil.dip2px(context, 6);
		layoutParams2.rightMargin = DimensionUtil.dip2px(context, 6);
		layoutParams2.topMargin = DimensionUtil.dip2px(context, 8);
		layout.addView(payMoney, layoutParams2);

		TextView payMoneyText = new TextView(context);
		payMoneyText.setTextColor(0xff222222);
		payMoneyText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		payMoneyText.setText("订单金额：" + ChargeData.getChargeData().money + "元");
		layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
		layoutParams2.leftMargin = DimensionUtil.dip2px(context, 10);
		layoutParams2.topMargin = DimensionUtil.dip2px(context, 6);
		payMoney.addView(payMoneyText, layoutParams2);

		activeLayout = new LinearLayout(context);
		activeLayout.setOrientation(LinearLayout.VERTICAL);
		activeParams = new LinearLayout.LayoutParams(-1, -2);
		layout.addView(activeLayout, activeParams);

		operatorsLayout = new LinearLayout(context);
		operatorsLayout.setOrientation(LinearLayout.VERTICAL);
		/*
		 * operatorsLayout.setBackgroundDrawable(BitmapCache.getDrawable(context,
		 * Constants.ASSETS_RES_PATH + "operators_bg.png"));// operators_bg.png
		 */
		
		View divide= new View(context);
		divide.setBackgroundColor(0x66bbbbbb);
	    layoutParams2 = new LinearLayout.LayoutParams(
                -1, DimensionUtil.dip2px(context, 1));
	    layoutParams2.topMargin = DimensionUtil.dip2px(context, 10);
		layout.addView(divide, layoutParams2);// listview_divide
		
		layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
		layoutParams2.topMargin = DimensionUtil.dip2px(context, 12);
		
		
		
		layout.addView(operatorsLayout, layoutParams2);

		LinearLayout layout2 = new LinearLayout(context);
		// layout2.setGravity(Gravity.CENTER_HORIZONTAL);
		layout2.setOrientation(LinearLayout.VERTICAL);

		layout.addView(layout2, -1, -2);

		/*
		 * LinearLayout submitLayout = new LinearLayout(context);
		 * submitLayout.setGravity(Gravity.CENTER); layoutParams2 = new
		 * LinearLayout.LayoutParams( -1, DimensionUtil.dip2px(context, 60));
		 * layoutParams2.topMargin = DimensionUtil.dip2px(context, 8);
		 * layoutParams2.bottomMargin = DimensionUtil.dip2px(context, 8);
		 * layout2.addView(submitLayout,layoutParams2);
		 */

		/*
		 * Button submitBtn = new Button(context);//yellow_btn.9.png
		 * getStateListDrawable
		 * submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
		 * submitBtn.setText("立即支付"); //
		 * submitBtn.setBackgroundDrawable(BitmapCache
		 * .getNinePatchDrawable(context,"yellow_btn.9.png"));
		 * submitBtn.setBackgroundDrawable(BitmapCache.getDrawable(context,
		 * Constants.ASSETS_RES_PATH + "jk.png"));
		 * submitBtn.setBackgroundDrawable(Utils.getStateListDrawable(context,
		 * "pay_btn_on.png", "jk.png")); layoutParams2 = new
		 * LinearLayout.LayoutParams( Utils.compatibleToWidth(context)*5/6, -1);
		 * submitLayout.addView(submitBtn,layoutParams2);
		 */

		LinearLayout layoutPaymentOut = new LinearLayout(context);
		layoutPaymentOut.setId(123);
		// layoutPaymentOut.setBackgroundColor(0xaaeeeeee);
		layoutPaymentOut.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout layoutPayment = new LinearLayout(context);
		layoutPayment.setOrientation(LinearLayout.HORIZONTAL);
		layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
		layoutParams2.bottomMargin = DimensionUtil.dip2px(context, 5);
//		 layoutParams2.topMargin = DimensionUtil.dip2px(context, 10);
		layoutParams2.leftMargin = DimensionUtil.dip2px(context, 6);
		layoutParams2.rightMargin = DimensionUtil.dip2px(context, 6);
		layout2.addView(layoutPaymentOut, layoutParams2);
		layoutPaymentOut.addView(layoutPayment, -2, -2);
		ImageView alipayImage = new ImageView(context);
		alipayImage.setId(Constants.ID_PAY_ALIPAY);
		alipayImage.setOnClickListener(this);
		alipayImage.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "alipay_icon.png"));
		layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
		// layoutParams2.topMargin = DimensionUtil.dip2px(context, 10);
		// layoutParams2.bottomMargin = DimensionUtil.dip2px(context, 5);
		// layoutParams2.leftMargin = DimensionUtil.dip2px(context, 12);
		layoutPayment.addView(alipayImage, layoutParams2);

		yeepayImage = new ImageView(context);
		if (flag == 2) {
			yeepayImage.setVisibility(View.GONE);
		} else {
			yeepayImage.setVisibility(View.VISIBLE);
		}
		yeepayImage.setOnClickListener(this);
		yeepayImage.setId(Constants.ID_PAY_YEEPAY);
		yeepayImage.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "yeepay_icon.png"));
		layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
		// layoutParams2.topMargin = DimensionUtil.dip2px(context, 10);
		// layoutParams2.bottomMargin = DimensionUtil.dip2px(context, 5);
		layoutParams2.leftMargin = DimensionUtil.dip2px(context, 30);
		layoutPayment.addView(yeepayImage, layoutParams2);

		ImageView unionpayImage = new ImageView(context);
		unionpayImage.setOnClickListener(this);
		unionpayImage.setId(Constants.ID_PAY_UNIONPAY);
		unionpayImage.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "unionpay_icon.png"));
		/*
		 * layoutParams2 = new LinearLayout.LayoutParams( -2, -2);
		 */
		// layoutParams2.leftMargin = DimensionUtil.dip2px(context, 30);
		// layoutParams2.rightMargin = DimensionUtil.dip2px(context, 12);
		layoutPayment.addView(unionpayImage, layoutParams2);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case Constants.ID_PAY_ALIPAY:
		case Constants.ID_PAY_YEEPAY:
		case Constants.ID_PAY_UNIONPAY:
		case Constants.ID_PAY_MOBILE:
		case Constants.ID_PAY_UNICOM:
		case Constants.ID_PAY_TELECOM:
		case Constants.ID_PAY_CLOSE:
		case Constants.ID_PAY_DENOMINATION:
		case Constants.ID_PAY_CARD_PAYBTN:	
			if (this.clickListener != null) {
				this.clickListener.onClick(v);
			}
			break;
//
		default:
			break;
		}

	}

}
