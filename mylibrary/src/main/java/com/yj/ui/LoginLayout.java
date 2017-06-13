package com.yj.ui;

import android.app.Activity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
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
import com.yj.util.BitmapCache;
import com.yj.util.CheckAccount;
import com.yj.util.DimensionUtil;
import com.yj.util.LoginService;
import com.yj.util.LoginTask;
import com.yj.util.SharedPreferencesUtil;
import com.yj.util.Utils;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class LoginLayout extends ParentLayout implements OnClickListener {

    private Button btnRegister;
    private Button btnStartGame;
    private Activity context;
    public ImageView downImageView;
    private ImageView autoImage;//自动登录
    //	public LinearLayout layout2;
    private OnClickListener btClickListener;// 忘记密码

    private LoginEditLayout loginEditLayout;

    //*********************************************************************

    private LoginService loginService;

    private EditText loginEtAccount;
    private EditText loginEtPwd;
    private ImageView autoLoginIv;
    private TextView loginTv;
    private TextView registerTv;
    private ImageView qqLogin;
    private ImageView wxLogin;
    private ImageView wbLogin;
    private ImageView goGameIv;

    public LoginLayout(Activity context) {
        super(context, MResource.getIdByName(context, Constants.LAYOUT, "login_layout"));
        this.context = context;
        loginService = LoginService.getInit(context);
        initUi();
//		init();
    }

    private void initUi() {

        //一键进入游戏
        goGameIv = (ImageView) view.findViewById(MResource.getIdByName(context, Constants.ID, "login_go_game"));
        goGameIv.setOnClickListener(this);

        //输入框
        loginEtAccount = (EditText) view.findViewById(MResource.getIdByName(context, Constants.ID, "login_et_account"));
        loginEtAccount.setCompoundDrawables(BitmapCache.getDrawable(context, MResource.getIdByName(context, Constants.DRAWABLE, "pic_zhanghao"), 21, 21), null, null, null);

        loginEtPwd = (EditText) view.findViewById(MResource.getIdByName(context, Constants.ID, "login_et_pwd"));
        loginEtPwd.setCompoundDrawables(BitmapCache.getDrawable(context, MResource.getIdByName(context, Constants.DRAWABLE, "pic_mima"), 21, 21), null, null, null);
        setRtContentAndAutoLogin();
        //自动登录
        autoLoginIv = (ImageView) view.findViewById(MResource.getIdByName(context, Constants.ID, "login_iv_auto"));
        autoLoginIv.setOnClickListener(this);
        loginService.setAutoLoginIvState(autoLoginIv);

        //注册登录按钮
        loginTv = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID, "login_tv_login"));
        loginTv.setOnClickListener(this);
        registerTv = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID, "login_tv_register"));
        registerTv.setOnClickListener(this);

        //第三方登录
        qqLogin = (ImageView) view.findViewById(MResource.getIdByName(context, Constants.ID, "login_iv_qq"));
        qqLogin.setOnClickListener(this);
        wxLogin = (ImageView) view.findViewById(MResource.getIdByName(context, Constants.ID, "login_iv_wx"));
        wxLogin.setOnClickListener(this);
        wbLogin = (ImageView) view.findViewById(MResource.getIdByName(context, Constants.ID, "login_iv_sina_wb"));
        wbLogin.setOnClickListener(this);

    }

    private void setRtContentAndAutoLogin() {
        String account = SharedPreferencesUtil.getXmlAccount(context);
        String pwd = SharedPreferencesUtil.getXmlPwd(context);

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            return;
        }

        loginEtAccount.setText(account);
        loginEtPwd.setText(pwd);

        if (Constants.YES.equals(SharedPreferencesUtil.getXmlAutoLogin(context))) {
            CheckAccount checkAccount = new CheckAccount(context);
            boolean isLegal = checkAccount.checkAccount2PasswordLegal(account, pwd);
            if (!isLegal)
                return;
        /* 登录 */
            new LoginTask(this.context, account, pwd).execute();
//            autolLogin();
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int i = v.getId();
        if (i == MResource.getIdByName(context, Constants.ID, "login_iv_qq")
                || i == MResource.getIdByName(context, Constants.ID, "login_iv_wx")
                || i == MResource.getIdByName(context, Constants.ID, "login_iv_sina_wb")
                || i == MResource.getIdByName(context, Constants.ID, "login_tv_login")
                || i == MResource.getIdByName(context, Constants.ID, "login_tv_register")
                || i == MResource.getIdByName(context, Constants.ID, "login_go_game")) {
            if (this.btClickListener != null)
                this.btClickListener.onClick(v);

        } else if (i == MResource.getIdByName(context, Constants.ID, "login_iv_auto")) {//                loginService.setActions(Constants.ACTIONS);
            loginService.setAutol(autoLoginIv);

        } else {
        }
    }

    /**
     * 获取帐号
     *
     * @return
     */
    public String getAccount() {
        if (loginEtAccount != null && loginEtAccount.getText() != null && loginEtAccount.getText().toString() != null) {
            return loginEtAccount.getText().toString().trim();
        }
        return "";
    }

    /**
     * 获取密码
     */
    public String getPassWord() {
        if (loginEtPwd != null && loginEtPwd.getText() != null && loginEtPwd.getText().toString() != null) {
            return loginEtPwd.getText().toString();
        }
        return "";

    }


    @SuppressWarnings("ResourceType")
    private void init() {

		/* 添加logo */
        LinearLayout logoLayout = new LinearLayout(context);
        logoLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(-1, -2);
        lp4.topMargin = DimensionUtil.dip2px(context,
                DimensionUtil.dip2px(context, 8));
        layout.addView(logoLayout, lp4);

        ImageView icon = new ImageView(context);
        icon.setBackgroundDrawable(BitmapCache.getDrawable(context,
                Constants.ASSETS_RES_PATH + "youai_logo.png"));
        logoLayout.addView(icon);

		
		
		/* 设置滚动 */
        ScrollView scroll = new ScrollView(context);
        layout.addView(scroll, -1, -1);

        LinearLayout wrap1 = new LinearLayout(context);
        scroll.addView(wrap1, -1, -2);
        wrap1.setOrientation(LinearLayout.VERTICAL);

		/*横线*/
        View topLine = new View(context);
        topLine.setBackgroundColor(0xff999999);
        lp4 = new LinearLayout.LayoutParams(-1, 1);
        lp4.leftMargin = Utils.getBorderMargin(context, Constants.BORDER_MARGIN);
        lp4.rightMargin = Utils.getBorderMargin(context, Constants.BORDER_MARGIN);
        lp4.topMargin = DimensionUtil.dip2px(context, 16);
        lp4.bottomMargin = DimensionUtil.dip2px(context, 18);
        wrap1.addView(topLine, lp4);
		
		
		/*输入框*/
        loginEditLayout = new LoginEditLayout(context);
        wrap1.addView(loginEditLayout);


        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(-1, -2);

        RelativeLayout layout2 = new RelativeLayout(context);
        RelativeLayout.LayoutParams r1 = new RelativeLayout.LayoutParams(-2, -2);

        LinearLayout autoLayout = new LinearLayout(context);
        autoLayout.setId(Constants.AUTO_LOGIN_ID);
        autoLayout.setOrientation(LinearLayout.HORIZONTAL);
        autoLayout.setOnClickListener(this);
        autoImage = new ImageView(context);

//	    SharedPreferences preferences = Utils.getXmlShared(context,
//				Constants.LOGIN_XML_NAME);
        String auto = SharedPreferencesUtil.getXmlAutoLogin(context);
        if (Constants.NO.equals(auto)) {
            autoImage.setBackgroundDrawable(BitmapCache.getDrawable(context,
                    Constants.ASSETS_RES_PATH + "check_off.png"));
        } else {
            autoImage.setBackgroundDrawable(BitmapCache.getDrawable(context,
                    Constants.ASSETS_RES_PATH + "check_on.png"));
        }

        lp1 = new LinearLayout.LayoutParams(-2, -2);
        lp1.topMargin = DimensionUtil.dip2px(context, 5);
        autoLayout.addView(autoImage, lp1);

        TextView autoText = new TextView(context);
        autoText.setText("自动登录");
        autoText.setTextColor(0xff8b8b8b);
        autoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        lp1 = new LinearLayout.LayoutParams(-2, -2);
        lp1.leftMargin = DimensionUtil.dip2px(context, 2);
        autoLayout.addView(autoText, lp1);

        lp1 = new LinearLayout.LayoutParams(-2, -2);
        lp1.leftMargin = Utils.getBorderMargin(context, Constants.BORDER_MARGIN);
        lp1.topMargin = DimensionUtil.dip2px(context, 9);
        layout2.addView(autoLayout, lp1);
		
	
		
		 /*忘记密码*/
        TextView forgetPassword = new TextView(context);
        forgetPassword.setId(Constants.ID_FORGET_PASSWORD);
        forgetPassword.setOnClickListener(this);
        forgetPassword.setText("忘记密码？");
        forgetPassword.setTextColor(0xffA4CAF1);
        forgetPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        r1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        r1.rightMargin = Utils.getBorderMargin(context, Constants.BORDER_MARGIN);
        r1.addRule(RelativeLayout.ALIGN_TOP, Constants.AUTO_LOGIN_ID);
        layout2.addView(forgetPassword, r1);

        r1 = new RelativeLayout.LayoutParams(-1, -2);
        wrap1.addView(layout2, lp1);

		/* 提交按钮 */
        LinearLayout submitLayout = new LinearLayout(context);
        submitLayout.setOrientation(LinearLayout.HORIZONTAL);
        submitLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        lp1 = new LinearLayout.LayoutParams(-1, -2);
        lp1.leftMargin = Utils.getBorderMargin(context, 9);
        lp1.rightMargin = Utils.getBorderMargin(context, 9);
        lp1.topMargin = DimensionUtil.dip2px(context, 20);
        lp1.bottomMargin = DimensionUtil.dip2px(context, 25);
        wrap1.addView(submitLayout, lp1);

		/* 注册按钮 */
        btnRegister = new Button(context);
        btnRegister.setId(Constants.ID_JUMP_REGISTER_BTN);
        btnRegister.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        btnRegister.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
                "sel_pic_31.9.png"));
        btnRegister.setPadding(0, DimensionUtil.dip2px(context, 8), 0,
                DimensionUtil.dip2px(context, 8));
        btnRegister.setTextColor(0xff696d73);
        btnRegister.setGravity(Gravity.CENTER);
        btnRegister.setTextColor(0xfff46d10);
        btnRegister.setText("一秒注册");
        lp1 = new LinearLayout.LayoutParams(-2, -2);
        lp1.weight = 1;
        lp1.rightMargin = DimensionUtil.dip2px(context, 15);
        submitLayout.addView(btnRegister, lp1);

		/* 游戏按钮 */
        btnStartGame = new Button(context);
        btnStartGame.setId(Constants.ID_LOGIS_BTN);
        btnStartGame.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        btnStartGame.setTextColor(0xffffffff);
        btnStartGame.setBackgroundDrawable(Utils.getStateListtNinePatchDrawable(
                context, "btn_blue.9.png", "btn_blue_down.9.png"));
        btnStartGame.setPadding(0, DimensionUtil.dip2px(context, 7), 0,
                DimensionUtil.dip2px(context, 7));
        btnStartGame.setText("立即登录");
        btnStartGame.setGravity(Gravity.CENTER);
        lp1 = new LinearLayout.LayoutParams(-2, -2);
        lp1.leftMargin = DimensionUtil.dip2px(context, 15);
        lp1.weight = 1;
        submitLayout.addView(btnStartGame, lp1);

        btnRegister.setOnClickListener(this);
        btnStartGame.setOnClickListener(this);
    }


    /**
     * 获取输入框布局
     */
    public LoginEditLayout getLoginEditLayout() {
        return loginEditLayout;
    }

    /**
     * 传点击监听对象
     *
     * @param OnClickListener
     */
    public void setStartGameListener(OnClickListener OnClickListener) {
        this.btClickListener = OnClickListener;//忘记密码
    }

    /**
     * 自动登录
     */
    public void autolLogin() {
        LoginService.getInit(context).autolLogin(btnStartGame);
    }

}
