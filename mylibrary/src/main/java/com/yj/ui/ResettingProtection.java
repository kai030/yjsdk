package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.entity.SecretData;
import com.yj.sdk.YouaiAppService;
import com.yj.util.ActivityService;
import com.yj.util.BitmapCache;
import com.yj.util.CheckAccount;
import com.yj.util.DimensionUtil;
import com.yj.util.LoginService;
import com.yj.util.ToastUtils;
import com.yj.util.UserClickService;
import com.yj.util.Utils;

public class ResettingProtection extends AbstractLayoutTow implements OnClickListener {
	private ActivityService activityService;
	private int dynamicTime = 60;
	private int time = dynamicTime;
	private Button button3;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (time == dynamicTime) {
				// 获取验证码
				CheckAccount checkAccount = new CheckAccount(mContext);
				UserClickService clickService = new UserClickService(mContext,checkAccount,activityService);
				clickService.setSecretCommit(resettingProtection,Constants.A_PHONE_VERIFICATION_CODE,mContext);
			}
			time--;
			button3.setText(time + "秒");
			if (time > 0) {
				handler.removeCallbacks(runnable);
				handler.postDelayed(runnable, 1000);
			} else {
				handler.removeCallbacks(runnable);
				button3.setFocusable(true);
				button3.setText("获取验证");
				time = dynamicTime;
			}

		}
	};

	private Activity mContext;
	private SecretData[] secretData;
	// private static final int ID_BACK = 0x006;
	// 返回事件
	private OnClickListener mConfirmListener;
	// 下拉事件
	private OnClickListener optionListener;
	// 下拉事件
	private OnClickListener mSubmitListener;

	public LinearLayout wrap2;
	/** 密保问题 */
	public TextView questionEdit;
	/** 答案 */
	public EditText answerEdit;
	/** 登录密码 */
	public EditText inputPas;

	public RelativeLayout questionLayout;

	private LinearLayout inputPasLayout;
	private LinearLayout answerEditLayout;

	private InputMethodManager imm;

	private static ResettingProtection resettingProtection;
	
	public static ResettingProtection getResettingProtection(Activity context, SecretData[] secretData, ActivityService activityService){
		if(resettingProtection == null){
			resettingProtection = new ResettingProtection(context, secretData, activityService);
		}
		return resettingProtection;
	}
	
	public void setActivity(Activity mContext){
		this.mContext = mContext;
	}
	
	public ResettingProtection(Activity context, SecretData[] secretData, ActivityService activityService) {
		super(context);
		this.activityService = activityService;
		this.secretData = secretData;
		this.mContext = context;
		initUI();
	}

	/**
	 */
	@SuppressWarnings("ResourceType")
	private void initUI() {
		// -----------------title-----------------------------------
		imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		RelativeLayout title = new RelativeLayout(mContext);
		title.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity,
				"title.9.png"));
		LayoutParams rlp = new LayoutParams(-1,
                                                                                  -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		title.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
				DimensionUtil.dip2px(mContext, 7));
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-1, -2);
		llp.leftMargin = 2;
		llp.rightMargin = 2;
		llp.topMargin = 2;
		content.addView(title, llp);

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
		textView.setText("设置密保");
		textView.setTextColor(0xff000000);
		textView.setTextSize(22);
		title.addView(textView, rlp);

		// -----------------------------------------------------

		// 5399账号
		LinearLayout wrap1 = new LinearLayout(mContext);
		/*
		 * wrap1.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
		 * mActivity, "titil_user.9.png"));
		 */
		wrap1.setOrientation(LinearLayout.HORIZONTAL);
		wrap1.setGravity(Gravity.CENTER_VERTICAL);

		LinearLayout.LayoutParams lpid = new LinearLayout.LayoutParams(-1,
                                                                               DimensionUtil.dip2px(mContext, 35));
		lpid.gravity = Gravity.CENTER_HORIZONTAL;
		lpid.topMargin = DimensionUtil.dip2px(mContext, 8);
		content.addView(wrap1, lpid);

		ImageView userLogo = new ImageView(mContext);
		userLogo.setBackgroundDrawable(BitmapCache.getDrawable(mActivity,
				Constants.ASSETS_RES_PATH + "user_logo.png"));
		LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-2, -2);
		lp3.setMargins(
				Constants.BORDER_MARGIN + DimensionUtil.dip2px(mContext, 25),
				0, DimensionUtil.dip2px(mContext, 10), 0);
		wrap1.addView(userLogo, lp3);

		TextView mRegistUserId = new TextView(mContext);
		mRegistUserId.setSingleLine(true);
		mRegistUserId.setBackgroundDrawable(null);
		mRegistUserId.setText(Html.fromHtml(Constants.COMPANY_NAME
                                                    + "帐号: <font color = '#000000'> "
                                                    + YouaiAppService.mSession.userAccount + "</font>"));
		mRegistUserId.setTextColor(0xff435153);
		mRegistUserId.setTextSize(16);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		lp3.weight = 1;
		wrap1.addView(mRegistUserId, lp3);

		/* 横线 */
		View topLine = new View(mContext);
		topLine.setBackgroundColor(0xff999999);
		LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(-1, 1);
		lp4.leftMargin = Utils.getBorderMargin(mContext,
				Constants.BORDER_MARGIN);
		lp4.rightMargin = Utils.getBorderMargin(mContext,
				Constants.BORDER_MARGIN);
		lp4.topMargin = DimensionUtil.dip2px(mContext, 8);
		lp4.bottomMargin = DimensionUtil.dip2px(mContext, 8);
		// wrap1.addView(topLine,lp4);
		content.addView(topLine, lp4);

		// -----------密保问题------------------------------------------------

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
				DimensionUtil.dip2px(mContext, 25), 0);
		lp4 = new LinearLayout.LayoutParams(-1, -1);
		lp4.gravity = Gravity.CENTER_HORIZONTAL;
		scroll.addView(mAllLayout, lp4);

		// 用来放置密保帐号信息的布局
		wrap2 = new LinearLayout(mContext);
		wrap2.setGravity(Gravity.CENTER_VERTICAL);
		wrap2.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(mContext,
                                                                            36));
		lp.topMargin = DimensionUtil.dip2px(mContext, 5);
		// mAllLayout.addView(wrap2, lp);

		TextView question = new TextView(mContext);
		question.setSingleLine(true);
		question.setHint("密保问题 : ");
		question.setTextSize(15);
		question.setTextColor(0xff58687b);
		// wrap2.addView(question);

		questionLayout = new RelativeLayout(mContext);
		questionLayout.setGravity(Gravity.CENTER_VERTICAL);
		questionLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				mActivity, "input_no.9.png"));
		// questionLayout.setOrientation(LinearLayout.HORIZONTAL);
		questionLayout.setId(Constants.ID_SECRET_QUESTION);
		questionLayout.setOnClickListener(this);
		// wrap2.addView(questionLayout,-1,DimensionUtil.dip2px(mContext, 36));
		questionEdit = new TextView(mActivity);
		questionEdit.setPadding(DimensionUtil.dip2px(mActivity, 5), 0, 0, 0);
		questionEdit.setHint(secretData[0].secretName);
		questionEdit.setId(1);
		questionEdit.setTextColor(0xff333333);
		questionEdit.setTextSize(15);
		questionEdit.setSingleLine();
		questionEdit.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(-2, -1);
		// questionLayout.addView(questionEdit,lp2);

		LinearLayout rightLayout = new LinearLayout(mActivity);
		rightLayout.setOrientation(LinearLayout.VERTICAL);
		rightLayout.setGravity(Gravity.RIGHT);
		rightLayout.setLayoutParams(new LayoutParams(-1, -2));
		ImageView rightImage = new ImageView(mActivity);
		rightImage.setBackgroundDrawable(BitmapCache.getDrawable(mActivity,
				Constants.ASSETS_RES_PATH + "down_bk.png"));
		rightImage.setId(120);
		rightImage.setOnClickListener(this);
		lp = new LinearLayout.LayoutParams(-2, -1);
		lp.rightMargin = DimensionUtil.dip2px(mActivity, 8);
		lp.topMargin = DimensionUtil.dip2px(mActivity, 13);
		rightLayout.addView(rightImage, lp);
		LayoutParams paramsR = new LayoutParams(
				-2, -2);
		paramsR.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// paramsR.topMargin = DimensionUtil.dip2px(mContext, 8);
		questionLayout.addView(rightLayout, paramsR);

		// --------------------密保答案------------------------------------------
		LinearLayout wrap4 = new LinearLayout(mContext);
		wrap4.setGravity(Gravity.CENTER_VERTICAL);
		wrap4.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mActivity, 14);
		mAllLayout.addView(wrap4, lp);

		TextView answer = new TextView(mContext);
		answer.setSingleLine(true);
		answer.setHint("手机号码 : ");
		answer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		answer.setTextColor(0xff58687b);
		wrap4.addView(answer);

		answerEditLayout = new LinearLayout(mContext);
		answerEditLayout.setGravity(Gravity.CENTER_VERTICAL);
		answerEditLayout.setOrientation(LinearLayout.HORIZONTAL);
		answerEditLayout.setBackgroundDrawable(BitmapCache
				.getNinePatchDrawable(mActivity, "input.9.png"));

		answerEdit = new EditText(mActivity);
		answerEdit.setPadding(DimensionUtil.dip2px(mActivity, 2), 0, 0, 0);
		answerEdit.setId(0x110);
		answerEdit.setBackgroundDrawable(null);
		answerEdit.setHint("请输入11位手机号码");
		answerEdit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(
				20) });
		answerEdit.setTextColor(0xff333333);
		answerEdit.setInputType(InputType.TYPE_CLASS_DATETIME);
		answerEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		answerEdit.setSingleLine();

		lp2 = new LinearLayout.LayoutParams(-1, -1);
		lp2.leftMargin = DimensionUtil.dip2px(mContext, 5);
		answerEditLayout.addView(answerEdit, lp2);
		Utils.getEditTextFocus(answerEdit);
		answerEdit.setGravity(Gravity.CENTER_VERTICAL);

		Utils.setKeyboard(mContext);// 键盘

		lp = new LinearLayout.LayoutParams(-2, DimensionUtil.dip2px(mActivity,
                                                                            36));
		lp.weight = 1;
		wrap4.addView(answerEditLayout, lp);

		// -----------------------登录密码--------------------------------------------

		LinearLayout newPassLayout = new LinearLayout(mContext);
		newPassLayout.setGravity(Gravity.CENTER_VERTICAL);
		newPassLayout.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mActivity, 14);
		mAllLayout.addView(newPassLayout, lp);

		TextView newPass = new TextView(mContext);
		newPass.setSingleLine(true);
		newPass.setHint("验证信息 : ");
		newPass.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		newPass.setTextColor(0xff58687b);
		newPassLayout.addView(newPass);

		inputPasLayout = new LinearLayout(mContext);
		inputPasLayout.setGravity(Gravity.CENTER_VERTICAL);
		inputPasLayout.setOrientation(LinearLayout.HORIZONTAL);
		inputPasLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				mActivity, "input_no.9.png"));

		inputPas = new EditText(mActivity);
		inputPas.setInputType(InputType.TYPE_CLASS_NUMBER);
		inputPas.setBackgroundDrawable(null);
		/*inputPas.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);*/
		// inputPas.setHint("验证码");
		inputPas.setPadding(DimensionUtil.dip2px(mActivity, 2), 0, 0, 0);
		inputPas.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		inputPas.setGravity(Gravity.CENTER_VERTICAL);
		// lp2 = new LinearLayout.LayoutParams(DimensionUtil.dip2px(mContext,
		// 80), -1);
		lp2 = new LinearLayout.LayoutParams(DimensionUtil.dip2px(mActivity, 80),
                                                    DimensionUtil.dip2px(mActivity, 36));
		lp2.leftMargin = DimensionUtil.dip2px(mContext, 5);
		inputPasLayout.addView(inputPas, lp2);

		lp = new LinearLayout.LayoutParams(DimensionUtil.dip2px(mActivity, 80),
                                                   DimensionUtil.dip2px(mActivity, 36));
		// lp.weight = 1;
		newPassLayout.addView(inputPasLayout, lp);

		button3 = new Button(mContext);
		button3.setText("获取验证");
		button3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		lp = new LinearLayout.LayoutParams(DimensionUtil.dip2px(mActivity, 80), DimensionUtil.dip2px(mActivity,
                                                                                                             40));
		lp.leftMargin = DimensionUtil.dip2px(mActivity, 10);
		newPassLayout.addView(button3, lp);

		/* 输入框背景切换 */
		LoginService.getInit(mContext).selectInputMb(inputPasLayout,
				answerEditLayout, inputPas, answerEdit, questionLayout);

		LinearLayout layout1 = new LinearLayout(mActivity);
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
		lp3.topMargin = DimensionUtil.dip2px(mContext, 5);
		mAllLayout.addView(wrap3, lp3);

		// 确认按钮
		LinearLayout layout = new LinearLayout(mContext);
		layout.setGravity(Gravity.CENTER);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		lp3.topMargin = DimensionUtil.dip2px(mContext, 5);
		wrap3.addView(layout, lp3);

		final Button mBtConfirm = new Button(mContext);
		mBtConfirm.setBackgroundDrawable(Utils.getStateListtNinePatchDrawable(
				mContext, "btn_blue.9.png", "btn_blue_down.9.png"));
		mBtConfirm.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
				DimensionUtil.dip2px(mContext, 7));
		mBtConfirm.setGravity(Gravity.CENTER);
		mBtConfirm.setId(Constants.ID_SECRET_COMMIT);
		mBtConfirm.setText("确   认");
		mBtConfirm.setTextColor(Color.WHITE);
		mBtConfirm.setTextSize(22);
		mBtConfirm.setSingleLine();
		mBtConfirm.setOnClickListener(this);
		lp4 = new LinearLayout.LayoutParams(-1, -2);
		lp4.topMargin = DimensionUtil.dip2px(mActivity, 5);
		lp4.bottomMargin = DimensionUtil.dip2px(mActivity, 10);
		layout.addView(mBtConfirm, lp4);
        
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!Utils.isInteger(answerEdit.getText().toString())){
					ToastUtils.toastShow(mContext, "请正确输入手机号码");
					return;
				}
				if (time == dynamicTime) {
					inputPas.setText("");
					button3.setText(dynamicTime+"秒");
					handler.postDelayed(runnable, 1000);
				}

			}
		});

	}

	public void setConfirmListener(OnClickListener mConfirmListener) {
		this.mConfirmListener = mConfirmListener;
	}

	public void setOptionListener(OnClickListener optionListener) {
		this.optionListener = optionListener;
	}

	public void setmSubmitListener(OnClickListener mSubmitListener) {
		this.mSubmitListener = mSubmitListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case Constants.CLOSE_ID:
		case Constants.ID_BACK:
			// 返回
			if (mConfirmListener != null) {
				mConfirmListener.onClick(v);
			}
			break;
		case 120:
		case Constants.ID_SECRET_QUESTION:

			/*
			 * if(mContext.getWindow().getAttributes().softInputMode==WindowManager
			 * .LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED){ //隐藏软键盘
			 * mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.
			 * SOFT_INPUT_STATE_HIDDEN); }
			 */

			if (!YouaiAppService.keyboard) {
				// Utils.setKeyboard(mContext);
				YouaiAppService.keyboard = true;
			}
			Utils.clearEditTextFocus(answerEdit);
			Utils.clearEditTextFocus(inputPas);
			inputPasLayout.setBackgroundDrawable(BitmapCache
					.getNinePatchDrawable(mActivity, "input_no.9.png"));
			questionLayout.setBackgroundDrawable(BitmapCache
					.getNinePatchDrawable(mActivity, "input.9.png"));
			answerEditLayout.setBackgroundDrawable(BitmapCache
					.getNinePatchDrawable(mActivity, "input_no.9.png"));
			// 密保下拉事件
			if (optionListener != null) {
				optionListener.onClick(v);

			}
			break;
		case Constants.ID_SECRET_COMMIT:
			if (mSubmitListener != null) {
				mSubmitListener.onClick(v);
			}
			break;
		}
	}

	@Override
	public void setButtonClickListener(OnClickListener listener) {
		this.setOptionListener(listener);
		this.setConfirmListener(listener);
		this.setmSubmitListener(listener);
	}

}