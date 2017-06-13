package com.yj.ui;

import android.app.Activity;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yj.entity.Constants;
import com.yj.entity.Session;
import com.yj.sdk.YouaiAppService;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.LoginService;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
@SuppressWarnings("ResourceType")
public class LoginEditLayout extends LinearLayout {
	
	public LinearLayout editLayout;
	public LinearLayout key_layout;
	public EditText mInputPsW;
	public EditText mInputUser;
	public ImageView user,password;

	public LoginEditLayout(Activity context) {
		super(context);
		
		this.setOrientation(LinearLayout.VERTICAL);
		

		/* 帐号输入框 */
		editLayout = new LinearLayout(context);
		editLayout.setOrientation(LinearLayout.HORIZONTAL);
		editLayout.setGravity(Gravity.CENTER_VERTICAL);
		LayoutParams lp1 = new LayoutParams(-1, DimensionUtil.dip2px(context, 45));
		lp1.leftMargin = Utils.getBorderMargin(context, Constants.BORDER_MARGIN);
		lp1.rightMargin = Utils.getBorderMargin(context, Constants.BORDER_MARGIN);
		lp1.topMargin = DimensionUtil.dip2px(context, 5);
		this.addView(editLayout, lp1);
		editLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
                                                                                  "input.9.png"));


		lp1 = new LayoutParams(-2, -2);
		lp1.leftMargin = DimensionUtil.dip2px(context, 5);

	    user = new ImageView(context);
		user.setBackgroundDrawable(BitmapCache.getDrawable(context,
                                                                   Constants.ASSETS_RES_PATH + "user_on.png"));

		editLayout.addView(user, lp1);

		mInputUser = new EditText(context);
		mInputUser.setGravity(Gravity.CENTER_VERTICAL);
		mInputUser.setBackgroundDrawable(null);
		mInputUser.setHint("帐号/手机/邮箱");
		mInputUser.setSingleLine(true);
		mInputUser.setId(100);
		mInputUser.setTextSize(16);
		mInputUser.setHintTextColor(0xff58687a);
		mInputUser
		.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
		if(Session.getInstance() != null && Session.getInstance().userAccount != null && Session.getInstance().userAccount.length() >= YouaiAppService.min){
			mInputUser.setText(Session.getInstance().userAccount);
			CharSequence text = mInputUser.getText();
			if (text instanceof Spannable) {
			    Spannable spanText = (Spannable)text;
			    Selection.setSelection(spanText, text.length());
			}
		}
//		lp1.leftMargin = DimensionUtil.dip2px(context, 5);
		lp1 = new LayoutParams(-1, -1);
		lp1.topMargin = DimensionUtil.dip2px(context, 2);
		editLayout.addView(mInputUser, lp1);

		/* 密码输入框 */
		key_layout = new LinearLayout(context);
		key_layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
                                                                                  "input_no.9.png"));
		key_layout.setGravity(Gravity.CENTER_VERTICAL);

		lp1 = new LayoutParams(-2, -2);
		lp1.leftMargin = DimensionUtil.dip2px(context, 5);
	    password = new ImageView(context);
		password.setBackgroundDrawable(BitmapCache.getDrawable(context,
                                                                       Constants.ASSETS_RES_PATH + "password_off.png"));

		key_layout.addView(password, lp1);

		mInputPsW = new EditText(context);
		mInputPsW.setSingleLine(true);
		mInputPsW.setBackgroundDrawable(null);
		mInputPsW.setHintTextColor(0xff58687a);
		mInputPsW.setTextSize(16);
		mInputPsW.setInputType(InputType.TYPE_CLASS_TEXT
                                       | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		mInputPsW.setId(102);
		mInputPsW.setHint("请输入" + YouaiAppService.min + "-" + YouaiAppService.max + "位密码");
		mInputPsW.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
		if(Session.getInstance() != null && Session.getInstance().password != null && Session.getInstance().password.length() >= YouaiAppService.min){
			mInputPsW.setText(Session.getInstance().password);
		}

		lp1 = new LayoutParams(-1, -1);
		lp1.topMargin = DimensionUtil.dip2px(context, 2);
		key_layout.addView(mInputPsW, lp1);

		lp1 =  new LayoutParams(-1, DimensionUtil.dip2px(context, 45));
		lp1.topMargin = DimensionUtil.dip2px(context, 20);
		lp1.leftMargin = Utils.getBorderMargin(context, Constants.BORDER_MARGIN);
		lp1.rightMargin = Utils.getBorderMargin(context, Constants.BORDER_MARGIN);
		this.addView(key_layout, lp1);


		/* 切换输入框背景 */
		LoginService.getInit(context).select(key_layout, editLayout, mInputPsW, mInputUser, user, password);
		
		mInputUser.clearFocus();
		mInputPsW.clearFocus();
	}
	
	/**
	 * 获取帐号
	 * 
	 * @return
	 */
	public String getAccount() {
		if(mInputUser != null && mInputUser.getText() != null && mInputUser.getText().toString() != null) {
			return mInputUser.getText().toString().trim();
		}
		return "";
	}

	/**
	 * 获取密码
	 */
	public String getPassWord() {
		if(mInputPsW != null && mInputPsW.getText() != null && mInputPsW.getText().toString() != null) {
			return mInputPsW.getText().toString();
		}
		return "";

	}

}
