/**
 * 
 */
package com.yj.ui;

import android.app.Activity;
import android.view.View;

import com.yj.entity.Constants;
import com.yj.util.BitmapCache;

/**
 * @author lufengkai 
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
public class PaySucceedLayout extends PayExitLayout {

	/**
	 * @param context
	 */
	public PaySucceedLayout(Activity context) {
		super(context);
		titleIcon.setVisibility(View.VISIBLE);
		titleIcon.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "pay_succeed.png"));
		titleText.setText("交易提交成功");
		confirmBtn.setVisibility(View.GONE);
//		confirmBtn.setText("确认");
		cancelBtn.setVisibility(View.VISIBLE);
		cancelBtn.setId(Constants.ID_PAY_SUCCEED);
//		cancelBtn.setOnClickListener(this);
		cancelBtn.setText("确定");
		txtPrompt.setText("订单已提交，若支付成功道具将在1-10分钟到账！");
		
	}

}
