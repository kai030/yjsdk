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
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class PayFailureLayout extends PayExitLayout {

	/**
	 * @param context
	 */
	public PayFailureLayout(Activity context) {
		super(context);
		titleIcon.setVisibility(View.VISIBLE);
		titleIcon.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "pay_failure.png"));
		titleText.setText("支付失败");
		confirmBtn.setVisibility(View.GONE);
//		confirmBtn.setText("确认");
		cancelBtn.setVisibility(View.VISIBLE);
		cancelBtn.setId(Constants.ID_PAY_FAILURE);
		cancelBtn.setText("重新支付");
		txtPrompt.setText("支付失败");
		
	}

}
