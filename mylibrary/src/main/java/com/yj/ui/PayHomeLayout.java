/**
 * 
 */
package com.yj.ui;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.entity.BaseData;
import com.yj.entity.ChargeData;
import com.yj.entity.Constants;
import com.yj.sdk.YouaiAppService;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class PayHomeLayout extends PayLayout {
	
	/**
	 * @param context
	 */
	public PayHomeLayout(Context context) {
		super(context,1);
		TextView bugArticle = new TextView(context);
		bugArticle.setText("物品名称：" + ChargeData.getChargeData().tradeName);
		bugArticle.setTextColor(0xff222222);
		bugArticle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				-2, -2);
		layoutParams.leftMargin = DimensionUtil.dip2px(context, 10);
		layoutParams.topMargin = DimensionUtil.dip2px(context, 6);
		payMoney.addView(bugArticle, layoutParams);

		TextView accountName = new TextView(context);
		accountName.setText("帐号：" + YouaiAppService.mSession.userAccount);
		accountName.setTextColor(0xff222222);
		accountName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		layoutParams = new LinearLayout.LayoutParams(-2, -2);
		layoutParams.leftMargin = DimensionUtil.dip2px(context, 10);
		layoutParams.topMargin = DimensionUtil.dip2px(context,6);
//		layoutParams.bottomMargin = DimensionUtil.dip2px(context, 6);
		payMoney.addView(accountName, layoutParams);
		
		
		LinearLayout bottomLayout = new LinearLayout(context);
		bottomLayout.setOrientation(LinearLayout.VERTICAL);
		// bottomLayout.setBackgroundColor(0xaaeeeeee);
		LayoutParams relativeParams = new LayoutParams(
				-1, -2);
		relativeParams
				.addRule(RelativeLayout.ALIGN_BOTTOM, Constants.ID_LOGOUT);
		relativeParams
		.addRule(RelativeLayout.BELOW, Constants.ID_PAY_LAYOUT);
		this.addView(bottomLayout, relativeParams);

		int widthMargin = (context.getResources().getDisplayMetrics().widthPixels - (Utils
				.compatibleToWidth(context)));
		TextView tel = new TextView(context);
		tel.setText("客服电话："+BaseData.serviceTel);
		tel.setTextColor(0xff222222);
		tel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
		layoutParams2.topMargin = DimensionUtil.dip2px(context, 8);
		// layoutParams2.bottomMargin = DimensionUtil.dip2px(context, 8);
		layoutParams2.leftMargin = widthMargin
				+ DimensionUtil.dip2px(context, 10);
		bottomLayout.addView(tel, layoutParams2);
		TextView QQ = new TextView(context);
		QQ.setText("客服 Q Q："+BaseData.serviceQQ);
		QQ.setTextColor(0xff222222);
		QQ.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
		layoutParams2.topMargin = DimensionUtil.dip2px(context, 2);
		layoutParams2.bottomMargin = DimensionUtil.dip2px(context, 5);
		layoutParams2.leftMargin = widthMargin
				+ DimensionUtil.dip2px(context, 10);
		bottomLayout.addView(QQ, layoutParams2);
		// activeLayout.
	}

}
