/**
 * 
 */
package com.yj.ui;

import android.app.Activity;
import android.view.View;

import com.yj.entity.Constants;

/**
 * @author lufengkai 
 * @date 2015年5月28日
 * @copyright 游鹏科技
 */
public class ExitLayout extends PayExitLayout {

	/**
	 * @param context
	 */
	public ExitLayout(Activity context) {
		super(context);
		titleIcon.setVisibility(View.GONE);
		titleText.setText("退出支付");
		confirmBtn.setVisibility(View.VISIBLE);
		confirmBtn.setId(Constants.ID_EXIT_CONFIRM);
		confirmBtn.setText("确认");
		cancelBtn.setVisibility(View.VISIBLE);
		cancelBtn.setId(Constants.ID_EXIT_CANCEL);
		cancelBtn.setText("取消");
		txtPrompt.setText("购买未完成，是否退出！");
		
	}

}
