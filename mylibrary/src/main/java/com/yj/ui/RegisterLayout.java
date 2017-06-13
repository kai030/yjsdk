package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.util.BitmapCache;



/**
 * @author lufengkai
 * @date 2015年5月25日
 * @copyright 游鹏科技
 */
@SuppressWarnings("ResourceType")
public class RegisterLayout extends ParentLayout implements OnClickListener {

    /**
     * 注册账号
     */
    public EditText mRegistUserId;
    /**
     * 注册密码
     */
    public EditText mRegistUserPwd;
    public boolean isCheck, isCheck2;
    public SharedPreferences sharedPreferences;
    private ImageView check2;
    private TextView text2;
    private LinearLayout wrap1, wrap2;
    public ImageView user, password;


    private OnClickListener servieListener;//协议
    private OnClickListener confirmListener;//进入游戏
    private OnClickListener backListener;//返回
    //**********************

    private EditText loginEtAccount;
    private EditText loginEtPwd;
    private ImageView qqLogin;
    private ImageView wxLogin;
    private ImageView wbLogin;
    private CheckBox registerIvProtocol;
    private TextView registerTv;

    public RegisterLayout(Activity context) {
        super(context, MResource.getIdByName(context,Constants.LAYOUT,"register_layout"));
        initUi(context);
    }

    private void initUi(Context context) {
       //输入框
        loginEtAccount = (EditText) view.findViewById(MResource.getIdByName(context,Constants.ID,"login_et_account"));
        loginEtAccount.setCompoundDrawables(BitmapCache.getDrawable(context, MResource.getIdByName(context,Constants.DRAWABLE,"pic_zhanghao"), 21, 21), null, null, null);

        loginEtPwd = (EditText) view.findViewById(MResource.getIdByName(context,Constants.ID,"login_et_pwd"));
        loginEtPwd.setCompoundDrawables(BitmapCache.getDrawable(context, MResource.getIdByName(context,Constants.DRAWABLE,"pic_mima"), 21, 21), null, null, null);

        //用户协议
        registerIvProtocol = (CheckBox) view.findViewById(MResource.getIdByName(context,Constants.ID,"register_iv_protocol"));

        //第三方登录
        qqLogin = (ImageView) view.findViewById(MResource.getIdByName(context,Constants.ID,"login_iv_qq"));
        qqLogin.setOnClickListener(this);
        wxLogin = (ImageView) view.findViewById(MResource.getIdByName(context,Constants.ID,"login_iv_wx"));
        wxLogin.setOnClickListener(this);
        wbLogin = (ImageView) view.findViewById(MResource.getIdByName(context,Constants.ID,"login_iv_sina_wb"));
        wbLogin.setOnClickListener(this);

        //注册按钮
        registerTv = (TextView) view.findViewById(MResource.getIdByName(context,Constants.ID,"register_tv"));
        registerTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == MResource.getIdByName(context,Constants.ID,"register_tv")
                || i ==  MResource.getIdByName(context,Constants.ID,"login_iv_qq")
                || i ==  MResource.getIdByName(context,Constants.ID,"login_iv_wx")
                || i == MResource.getIdByName(context,Constants.ID,"login_iv_sina_wb"))
//                || MResource.getIdByName(context,Constants.ID,"register_tv")i == Constants.ID_REGISTER_BACK_BTN
//                || MResource.getIdByName(context,Constants.ID,"register_tv")i == Constants.ID_REGISTER_AGREEMENT
//                || MResource.getIdByName(context,Constants.ID,"register_tv")i == Constants.ID_REGISTER_STARTGAME_BTN)
        {
            if (this.confirmListener != null) {
                this.confirmListener.onClick(v);

        } else {
        }

    }
    }


//	private void initUI(Activity mContext) {
//
//		/*顶部布局*/
//		RelativeLayout title = new RelativeLayout(mContext);
//		title.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity,
//                                                                             "title.9.png"));
//		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1,
//                                                                                  -2);
//		rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//		title.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
//                                 DimensionUtil.dip2px(mContext, 7));
//		content.addView(title, rlp);
//
//		/*返回*/
//		ImageView fanhui = new ImageView(mContext);
//		fanhui.setImageDrawable(BitmapCache.getDrawable(mContext,
//                                                                Constants.ASSETS_RES_PATH + "fanhui.png"));
//		fanhui.setId(Constants.ID_REGISTER_BACK_BTN);
//		fanhui.setOnClickListener(this);
//		rlp = new RelativeLayout.LayoutParams(-2, -2);
//		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT
//                            | RelativeLayout.CENTER_VERTICAL);
//		rlp.leftMargin = DimensionUtil.dip2px(mContext, 5);
//		title.addView(fanhui, rlp);
//
//		/*关闭*/
//		/*ImageView close = LayoutUtil.getCloseImage(mContext, this);
//		rlp = LayoutUtil.getRelativeParams(mContext);
//		title.addView(close,rlp);*/
//
//		/*注册标题*/
//		TextView textView = new TextView(mContext);
//		textView.setGravity(Gravity.CENTER_HORIZONTAL);
//		rlp = new RelativeLayout.LayoutParams(-1, -2);
//		textView.setText("注册帐号");
//		textView.setTextColor(0xff000000);
//		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//		title.addView(textView, rlp);
//
//		/*注册布局*/
//		LinearLayout mSubject = new LinearLayout(mContext);
//		mSubject.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
//				mActivity, "no_title_bg.9.png"));
//		mSubject.setOrientation(LinearLayout.VERTICAL);
//		mSubject.setGravity(Gravity.CENTER_HORIZONTAL);
//		content.addView(mSubject, -1, -1);
//
//		/*滚动*/
//		ScrollView scroll = new ScrollView(mContext);
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -1);
//		lp.gravity = Gravity.CENTER;
//		mSubject.addView(scroll, lp);
//
//		LinearLayout mAllLayout = new LinearLayout(mContext);
//		mAllLayout.setOrientation(LinearLayout.VERTICAL);
//		LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(-1, -1);
//		scroll.addView(mAllLayout, lp4);
//
//		// 用来放置注册帐号信息的布局
//		wrap1 = new LinearLayout(mContext);
//		wrap1.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity,
//                                                                             "input.9.png"));
//		wrap1.setOrientation(LinearLayout.HORIZONTAL);
//		wrap1.setGravity(Gravity.CENTER_VERTICAL);
//
//		LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-2, -2);
//		lp3.leftMargin = DimensionUtil.dip2px(mContext, 5);
//
//	    user = new ImageView(mContext);
//		user.setBackgroundDrawable(BitmapCache.getDrawable(mContext,
//                                                                   Constants.ASSETS_RES_PATH + "user_on.png"));
//
//		wrap1.addView(user, lp3);
//
//		mRegistUserId = new EditText(mContext);
//		mRegistUserId.setSingleLine(true);
//		mRegistUserId.setBackgroundDrawable(null);
//		mRegistUserId.setId(Constants.ID_REGISTER_ACCOUNTS_EDIT);
//		mRegistUserId.setHint("请输入" + YouaiAppService.min + "-" + YouaiAppService.max + "位帐号");
//		mRegistUserId.requestFocus();
//		mRegistUserId.requestFocusFromTouch();
//		mRegistUserId.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
//		mRegistUserId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//	    lp3 = new LinearLayout.LayoutParams(-1, -2);
//		//lp3.weight = 1;
//		lp3.topMargin = DimensionUtil.dip2px(mContext, 2);
//		wrap1.addView(mRegistUserId, lp3);
//		wrap1.setClickable(true);
//
//		LinearLayout.LayoutParams lpid = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(mContext, 45));
//		lpid.bottomMargin = DimensionUtil.dip2px(mContext, 5);
//		lpid.topMargin = DimensionUtil.dip2px(mContext, 10);
////		lpid.gravity = Gravity.CENTER_HORIZONTAL;
//		lpid.leftMargin = Utils.getBorderMargin(mActivity, Constants.BORDER_MARGIN);
//		lpid.rightMargin = Utils.getBorderMargin(mActivity, Constants.BORDER_MARGIN);
//		lpid.topMargin = DimensionUtil.dip2px(mContext, 25);
//		mAllLayout.addView(wrap1, lpid);
//
//		// 用来放置密码信息的布局
//		wrap2 = new LinearLayout(mContext);
//		wrap2.setGravity(Gravity.CENTER_VERTICAL);
//		wrap2.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity,
//                                                                             "input_no.9.png"));
//		wrap2.setOrientation(LinearLayout.HORIZONTAL);
//
//		LinearLayout.LayoutParams lppwd = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(mContext, 45));
//		lppwd.topMargin = DimensionUtil.dip2px(mContext, 15);
//		lppwd.leftMargin = Utils.getBorderMargin(mActivity, Constants.BORDER_MARGIN);
//		lppwd.rightMargin = Utils.getBorderMargin(mActivity, Constants.BORDER_MARGIN);
//		mAllLayout.addView(wrap2, lppwd);
//
//		 password = new ImageView(mContext);
//		 password.setBackgroundDrawable(BitmapCache.getDrawable(mContext,
//                                                                        Constants.ASSETS_RES_PATH + "password_off.png"));
//		 lp3 = new LinearLayout.LayoutParams(-2, -2);
//		 lp3.leftMargin = DimensionUtil.dip2px(mContext, 4);
//	     wrap2.addView(password, lp3);
//
//		mRegistUserPwd = new EditText(mContext);
//		mRegistUserPwd.setId(Constants.ID_REGISTER_PASSWORD_EDIT);
//		mRegistUserPwd.setSingleLine(true);
//		mRegistUserPwd.setBackgroundDrawable(null);
//		mRegistUserPwd.setHint("请输入" + YouaiAppService.min + "-" + YouaiAppService.max + "位密码");
//		mRegistUserPwd.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//		mRegistUserPwd.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
//		lp3 = new LinearLayout.LayoutParams(-1, -2);
//		lp3.topMargin = DimensionUtil.dip2px(mContext, 2);
//		wrap2.addView(mRegistUserPwd, lp3);
//		wrap2.setClickable(true);
//
//		LoginService.getInit(mContext).select(wrap2, wrap1, mRegistUserPwd, mRegistUserId, user, password);
//
//		LinearLayout layout1 = new LinearLayout(mActivity);
//		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(-2, -2);
//		lp1.gravity = Gravity.LEFT;
//		lp1.topMargin = DimensionUtil.dip2px(mContext, 10);
//		layout1.setGravity(Gravity.CENTER);
//		lp1.leftMargin = Utils.getBorderMargin(mActivity, Constants.BORDER_MARGIN) + 1;
//		lp1.bottomMargin = DimensionUtil.dip2px(mContext, 15);
//		mAllLayout.addView(layout1, lp1);
//		layout1.setGravity(Gravity.CENTER);
//
//		check2 = new ImageView(mActivity);
//		check2.setId(4);
//		lp1 = new LinearLayout.LayoutParams(-2, -2);
//		lp1.gravity = Gravity.CENTER;
//		layout1.addView(check2, lp1);
//
//		text2 = new TextView(mActivity);
//		text2.setText("同意指盟用户服务协议");
//		text2.setId(Constants.ID_REGISTER_AGREEMENT);
//		text2.setOnClickListener(this);
//		text2.setTextColor(0xff58687a);
//		text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//		lp1 = new LinearLayout.LayoutParams(-2, -2);
//		lp1.leftMargin  = DimensionUtil.dip2px(mContext, 4);
//		lp1.gravity = Gravity.CENTER;
//		layout1.addView(text2, lp1);
//		sharedPreferences = mActivity.getSharedPreferences("SaveSetting",
//                                                                   Context.MODE_PRIVATE);
//		isCheck2 = sharedPreferences.getBoolean("isReadWord", true);
//
//		if (isCheck2) {
//			check2.setBackgroundDrawable(BitmapCache.getDrawable(mActivity,
//                                                                             Constants.ASSETS_RES_PATH + "check_on.png"));
//		} else {
//			check2.setBackgroundDrawable(BitmapCache.getDrawable(mActivity,
//                                                                             Constants.ASSETS_RES_PATH + "check_off.png"));
//		}
//
//		// 用来放确认和返回按钮的子布局
//		LinearLayout wrap3 = new LinearLayout(mContext);
//		wrap3.setOrientation(LinearLayout.HORIZONTAL);
//		wrap3.setGravity(Gravity.CENTER);
//		lp3 = new LinearLayout.LayoutParams(-1, -2);
//		lp3.topMargin = DimensionUtil.dip2px(mContext, 10);
//		mAllLayout.addView(wrap3, lp3);
//
//		// 确认按钮
//		LinearLayout layout = new LinearLayout(mContext);
//		layout.setGravity(Gravity.CENTER);
//		lp3 = new LinearLayout.LayoutParams(-1, -2);
//		wrap3.addView(layout, lp3);//
//
//		Button mBtConfirm = new Button(mContext);
//		mBtConfirm.setBackgroundDrawable(Utils.getStateListtNinePatchDrawable(
//				mContext, "btn_blue.9.png", "btn_blue_down.9.png"));
//		mBtConfirm.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
//                                      DimensionUtil.dip2px(mContext, 7));
//		mBtConfirm.setGravity(Gravity.CENTER);
//		mBtConfirm.setText("注册");
//		mBtConfirm.setTextColor(Color.WHITE);
//		mBtConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//		mBtConfirm.setSingleLine();
//		mBtConfirm.setId(Constants.ID_REGISTER_STARTGAME_BTN);
//		mBtConfirm.setOnClickListener(this);
//		lp4 = new LinearLayout.LayoutParams(-1, -2);
//		lp4.leftMargin = Utils.getBorderMargin(mActivity, Constants.BORDER_MARGIN);
//		lp4.rightMargin = Utils.getBorderMargin(mActivity, Constants.BORDER_MARGIN);
//		layout.addView(mBtConfirm, lp4);
//
//		LinearLayout layout2 = new LinearLayout(mActivity);
//		lp1 = new LinearLayout.LayoutParams(-2, -2);
//		lp1.topMargin = DimensionUtil.dip2px(mContext, 6);
//		lp1.bottomMargin = DimensionUtil.dip2px(mContext, 20);
//		lp1.gravity = Gravity.LEFT;
//		mAllLayout.addView(layout2, lp1);
//
//		TextView tishi = new TextView(mActivity);
//		tishi.setText("温馨提示：可修改为您喜欢的帐号和密码");
//		tishi.setTextColor(0xffff377e);
//		tishi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//		tishi.setVisibility(View.GONE);
//		layout2.addView(tishi);
//
//	}

    /**
     * 传点击监听对象
     */

    public void setButtonClickListener(OnClickListener listener) {
        this.confirmListener = listener;
    }



    /**
     * 帐号
     *
     * @return
     */
    public String getInputUserName() {
        if(loginEtAccount != null && loginEtAccount.getText() != null && loginEtAccount.getText().toString() != null){
            return loginEtAccount.getText().toString().trim();
        }
       return "";
    }

    /**
     * 密码
     *
     * @return
     */
    public String getInputUserPwd() {
        if (loginEtPwd != null && loginEtPwd.getText() != null && loginEtPwd.getText().toString() != null) {
            return loginEtPwd.getText().toString().trim();
        }
        return "";
    }

        /**
         * 是否同意协议
         */
        public boolean isCheck(){
            return registerIvProtocol.isChecked();
        }
    }
