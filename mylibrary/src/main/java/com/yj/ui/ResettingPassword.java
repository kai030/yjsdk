package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.sdk.YouaiAppService;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * 重置密码
 */
public class ResettingPassword extends AbstractLayoutTow implements OnClickListener {

	public Context mContext;
	private String userName;
	private static final int ID_BACK = 0x006;
	// 返回事件
	private OnClickListener mConfirmListener;
	// 提交按钮
	private OnClickListener ResettingPasswordListener;

	public LinearLayout wrap2;
	public TextView questionEdit;
	/** 密保答案 */
	public EditText answerEdit;
	/** 新密码 */
	public EditText inputPas;

	private LinearLayout inputPasLayout;
	private LinearLayout answerEditLayout;

	/**
	 * 
	 * @param context
	 * @param userName
	 *            用户名
	 *            密保问题
	 */
	public ResettingPassword(Activity context, String userName) {
		super(context);
		this.mContext = context;
		this.userName = userName;
		initUI();
	}

	/**
	 */
	@SuppressWarnings("ResourceType")
	private void initUI() {
		// -----------------title-----------------------------------

		RelativeLayout title = new RelativeLayout(mContext);
		title.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity,
				"title.9.png"));
		LayoutParams rlp = new LayoutParams(-1,
                                                                                  -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		title.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
				DimensionUtil.dip2px(mContext, 7));
		LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(-1, -2);
		lp5.topMargin = 2;
		lp5.leftMargin = 2;
		lp5.rightMargin = 2;
		content.addView(title, lp5);

		ImageView fanhui = new ImageView(mContext);
		fanhui.setImageDrawable(BitmapCache.getDrawable(mContext,
				Constants.ASSETS_RES_PATH + "fanhui.png"));
		fanhui.setId(Constants.ID_BACK);
		fanhui.setOnClickListener(this);
		rlp = new LayoutParams(-2, -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT
                            | RelativeLayout.CENTER_VERTICAL);
		rlp.leftMargin = DimensionUtil.dip2px(mContext, 5);
		title.addView(fanhui, rlp);

		/* 关闭 */
		ImageView close = LayoutUtil.getCloseImage(mContext, this);
		rlp = LayoutUtil.getRelativeParams(mContext);
		title.addView(close, rlp);

		TextView textView = new TextView(mContext);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		rlp = new LayoutParams(-1, -2);
		textView.setText("重置密码");
		textView.setTextColor(0xff000000);
		textView.setTextSize(22);
		title.addView(textView, rlp);

		// -----------------------------------------------------
		// layout.addView(qq, lp); // 5399账号
		LinearLayout wrap1 = new LinearLayout(mContext);
		wrap1.setOrientation(LinearLayout.HORIZONTAL);
		wrap1.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams lpid = new LinearLayout.LayoutParams(-1,
                                                                               DimensionUtil.dip2px(mContext, 37));
		lpid.gravity = Gravity.CENTER_HORIZONTAL;
		lpid.topMargin = DimensionUtil.dip2px(mContext, 5);
		content.addView(wrap1, lpid);

		ImageView userLogo = new ImageView(mContext);
		userLogo.setBackgroundDrawable(BitmapCache.getDrawable(mActivity,
				Constants.ASSETS_RES_PATH + "user_logo.png"));
		LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-2, -2);
		lp3.setMargins(DimensionUtil.dip2px(mContext, 35), 0,
				DimensionUtil.dip2px(mContext, 10), 0);
		wrap1.addView(userLogo, lp3);

		TextView mRegistUserId = new TextView(mContext);
		mRegistUserId.setSingleLine(true);
		mRegistUserId.setBackgroundDrawable(null);
		mRegistUserId.setText(Html.fromHtml(Constants.COMPANY_NAME + "帐号: <font color = '#000000'> "
                                                    + YouaiAppService.mSession.userAccount + "</font>"));
		mRegistUserId.setTextColor(0xff435153);
		mRegistUserId.setTextSize(15);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		lp3.weight = 1;
		wrap1.addView(mRegistUserId, lp3);

		/* 横线 */
		View topLine = new View(mContext);
		topLine.setBackgroundColor(0xff999999);
		lp5 = new LinearLayout.LayoutParams(-1, 1);
		lp5.leftMargin = Utils.getBorderMargin(mContext, 20);
		lp5.rightMargin = Utils.getBorderMargin(mContext, 20);
		// lp4.topMargin = DimensionUtil.dip2px(mContext,8);
//		lp5.bottomMargin = DimensionUtil.dip2px(mContext, 5);
		lp5.topMargin = DimensionUtil.dip2px(mContext, 5);
		// wrap1.addView(topLine,lp4);
		content.addView(topLine, lp5);

		// -----------密保问题，密保代码，新密码------------------------------------------------

		LinearLayout mSubject = new LinearLayout(mContext);
		// mSubject.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity,
		// "no_title_bg.9.png"));
		mSubject.setOrientation(LinearLayout.VERTICAL);
		mSubject.setGravity(Gravity.CENTER_HORIZONTAL);
		content.addView(mSubject, -1, -1);

		ScrollView scroll = new ScrollView(mContext);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -1);
		lp.gravity = Gravity.CENTER;
		mSubject.addView(scroll, lp);

		LinearLayout mAllLayout = new LinearLayout(mContext);
		mAllLayout.setOrientation(LinearLayout.VERTICAL);
		mAllLayout.setPadding(DimensionUtil.dip2px(mContext, 15),
				DimensionUtil.dip2px(mContext, 5),
				DimensionUtil.dip2px(mContext, 15), 0);
		LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(-1, -1);
		lp4.gravity = Gravity.CENTER_HORIZONTAL;
		scroll.addView(mAllLayout, lp4);

		// 用来放置密码帐号信息的布局
		wrap2 = new LinearLayout(mContext);
		wrap2.setGravity(Gravity.CENTER_VERTICAL);
		// wrap2.setBackgroundDrawableDrawable(BitmapCache.getNinePatchDrawable(mActivity,
		// "key_not_down.9.png"));
		wrap2.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(mContext,
                                                                            36));
		lp.topMargin = DimensionUtil.dip2px(mContext, 3);
		mAllLayout.addView(wrap2, lp);

		TextView question = new TextView(mContext);
		question.setSingleLine(true);
		question.setText("密保问题 : ");
		question.setTextSize(16);
		question.setTextColor(0xff58687b);
		wrap2.addView(question);

		LinearLayout questionLayout = new LinearLayout(mContext);
		questionLayout.setGravity(Gravity.CENTER_VERTICAL);
		// questionLayout.setBackgroundDrawableDrawable(BitmapCache.getNinePatchDrawable(mActivity,
		// "input.9.png"));
		questionLayout.setOrientation(LinearLayout.HORIZONTAL);
		wrap2.addView(questionLayout, -1, DimensionUtil.dip2px(mContext, 36));

		TextView questionEdit = new TextView(mActivity);
		questionEdit.setId(0x110);
//		questionEdit.setText(Constants.SECRET[Session.secretId - 1]);
		questionEdit.setTextColor(0xff58687b);
		questionEdit.setTextSize(16);
		questionEdit.setSingleLine();
		questionEdit.setGravity(Gravity.CENTER_VERTICAL);
		lp = new LinearLayout.LayoutParams(-2, -2);
		// lp.weight = 1;
		questionLayout.addView(questionEdit, lp);

		// --------------------密保答案------------------------------------------
		LinearLayout wrap4 = new LinearLayout(mContext);
		wrap4.setGravity(Gravity.CENTER_VERTICAL);
		// wrap2.setBackgroundDrawableDrawable(BitmapCache.getNinePatchDrawable(mActivity,
		// "key_not_down.9.png"));
		wrap4.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mActivity, 10);
		mAllLayout.addView(wrap4, lp);

		TextView answer = new TextView(mContext);
		answer.setSingleLine(true);
		answer.setText("密保答案 : ");
		answer.setTextSize(16);
		answer.setTextColor(0xff58687b);
		wrap4.addView(answer);

		inputPasLayout = new LinearLayout(mContext);
		inputPasLayout.setGravity(Gravity.CENTER_VERTICAL);
		inputPasLayout.setOrientation(LinearLayout.HORIZONTAL);
		inputPasLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				mActivity, "input.9.png"));
		lp = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(mActivity,
                                                                            36));
		lp.weight = 1;
		wrap4.addView(inputPasLayout, lp);

		answerEdit = new EditText(mActivity);
		answerEdit.setId(0x110);
		answerEdit.setBackgroundDrawable(null);
		answerEdit.setHint("请输入密保答案");
		answerEdit.setTextColor(0xff333333);
		answerEdit.setPadding(DimensionUtil.dip2px(mActivity, 5), 0, 0, 0);
		answerEdit.setTextSize(16);
		answerEdit.setSingleLine();
		answerEdit.setGravity(Gravity.CENTER_VERTICAL);
		Utils.getEditTextFocus(answerEdit);
		inputPasLayout.addView(answerEdit, -1, -1);

		// -----------------------新密码--------------------------------------------

		LinearLayout newPassLayout = new LinearLayout(mContext);
		newPassLayout.setGravity(Gravity.CENTER_VERTICAL);
		// wrap2.setBackgroundDrawableDrawable(BitmapCache.getNinePatchDrawable(mActivity,
		// "key_not_down.9.png"));
		newPassLayout.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mActivity, 15);
		mAllLayout.addView(newPassLayout, lp);

		TextView newPass = new TextView(mContext);
		newPass.setSingleLine(true);
		newPass.setText("  新密码   : ");
		newPass.setTextSize(16);
		newPass.setTextColor(0xff58687b);
		newPassLayout.addView(newPass);

		answerEditLayout = new LinearLayout(mContext);
		answerEditLayout.setGravity(Gravity.CENTER_VERTICAL);
		answerEditLayout.setOrientation(LinearLayout.HORIZONTAL);
		answerEditLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				mActivity, "input_no.9.png"));
		lp = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(mActivity,
                                                                            36));
		lp.weight = 1;
		newPassLayout.addView(answerEditLayout, lp);

		inputPas = new EditText(mActivity);
		inputPas.setId(0x111);
		inputPas.setBackgroundDrawable(null);
		inputPas.setHint("请输入" + YouaiAppService.min + "-"
				+ YouaiAppService.max + "位密码");
		inputPas.setBackgroundDrawable(null);
		inputPas.setTextColor(0xff333333);
		inputPas.setTextSize(16);
		inputPas.setPadding(DimensionUtil.dip2px(mActivity, 5), 0, 0, 0);
		inputPas.setSingleLine();
		inputPas.setGravity(Gravity.CENTER_VERTICAL);
		answerEditLayout.addView(inputPas, -1, -1);

		select();

		LinearLayout layout1 = new LinearLayout(mActivity);
		// layout1.setPadding(DimensionUtil.dip2px(mContext, 10), 0, 0, 0);
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(-2, -2);
		lp1.gravity = Gravity.LEFT;
		layout1.setGravity(Gravity.CENTER);
		mAllLayout.addView(layout1, lp1);
		layout1.setGravity(Gravity.CENTER);

		// 用来放确认和返回按钮的子布局
		LinearLayout wrap3 = new LinearLayout(mContext);
		wrap3.setOrientation(LinearLayout.HORIZONTAL);
		wrap3.setGravity(Gravity.CENTER);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		lp3.topMargin = DimensionUtil.dip2px(mContext, 10);
		mAllLayout.addView(wrap3, lp3);

		// 确认按钮
		LinearLayout layout = new LinearLayout(mContext);
		layout.setGravity(Gravity.CENTER);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		wrap3.addView(layout, lp3);

		Button mBtConfirm = new Button(mContext);
		mBtConfirm.setBackgroundDrawable(Utils.getStateListtNinePatchDrawable(mContext,
                                                                                      "btn_blue.9.png", "btn_blue_down.9.png"));
		mBtConfirm.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
				DimensionUtil.dip2px(mContext, 7));
		mBtConfirm.setGravity(Gravity.CENTER);
		mBtConfirm.setText("确   认");
		mBtConfirm.setId(Constants.FORGER_PASSWORD_NEW);
		mBtConfirm.setTextColor(Color.WHITE);
		mBtConfirm.setTextSize(22);
		mBtConfirm.setSingleLine();
		mBtConfirm.setOnClickListener(this);
		lp4 = new LinearLayout.LayoutParams(-1, -2);
		lp4.topMargin = DimensionUtil.dip2px(mActivity, 10);
		lp4.bottomMargin = DimensionUtil.dip2px(mActivity, 15);
		layout.addView(mBtConfirm, lp4);

	}

	public OnClickListener getmConfirmListener() {
		return mConfirmListener;
	}

	public void setConfirmListener(OnClickListener mConfirmListener) {
		this.mConfirmListener = mConfirmListener;
	}

	public OnClickListener getResettingPasswordListener() {
		return ResettingPasswordListener;
	}

	public void setResettingPasswordListener(
			OnClickListener resettingPasswordListener) {
		ResettingPasswordListener = resettingPasswordListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case Constants.CLOSE_ID:
		case Constants.ID_BACK:
			if (mConfirmListener != null) {
				mConfirmListener.onClick(v);
			}
			break;
		case Constants.FORGER_PASSWORD_NEW:
			if ("".equals(answerEdit.getText().toString())) {
				Utils.toastInfo(mContext, "密保答案不能为空！");
				return;
			}
			;
			if (ResettingPasswordListener != null) {
				ResettingPasswordListener.onClick(v);
			}
			break;
		}
	}

	@Override
	public void setButtonClickListener(OnClickListener listener) {
		this.setConfirmListener(listener);
		this.setResettingPasswordListener(listener);
	}

	private void select() {

		answerEdit.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				inputPasLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
						mActivity, "input.9.png"));
				answerEditLayout.setBackgroundDrawable(BitmapCache
						.getNinePatchDrawable(mActivity, "input_no.9.png"));
				return false;
			}
		});

		inputPas.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				answerEditLayout.setBackgroundDrawable(BitmapCache
						.getNinePatchDrawable(mActivity, "input.9.png"));
				inputPasLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
						mActivity, "input_no.9.png"));
				return false;
			}
		});
	}

}
