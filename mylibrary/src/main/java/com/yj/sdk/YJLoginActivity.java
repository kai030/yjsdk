package com.yj.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.yj.entity.Constants;
import com.yj.entity.Session;
import com.yj.listener.QQListener;
import com.yj.ui.ForgetPassword;
import com.yj.ui.LoginLayout;
import com.yj.ui.MResource;
import com.yj.ui.RegisterLayout;
import com.yj.ui.ResettingPassword;
import com.yj.ui.YAServiceLayout;
import com.yj.util.ActivityService;
import com.yj.util.CheckAccount;
import com.yj.util.GetMiBaoToUserTask;
import com.yj.util.GoLoginTask;
import com.yj.util.LoginClick;
import com.yj.util.NetworkImpl;
import com.yj.util.ToastUtils;
import com.yj.util.Utils;


/**
 * @author lufengkai
 * @date 2015年5月27日
 * @copyright
 */
public class YJLoginActivity extends Activity implements OnClickListener {

    private LoginClick loginClick;
    //	private LoginLayout loginLayout;// 登录布局
    private LoginLayout loginLayout;// 登录布局
    private ForgetPassword forgetlayout;// 忘记密码提交
    private ResettingPassword resettingPassword;// 忘记密码重置
    // 注册布局
    private RegisterLayout registerLayout;// 注册
    private YAServiceLayout youaiServiceLayout;
//    private LoginService loginService;// 登录业务类
    private ActivityService activityService;
    public static Tencent mTencent;

    public static IWXAPI mIWXAPI;

    private static Context context;


    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(context,YJTisActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("content","返回结果："+msg.obj.toString());
            context.startActivity(intent,bundle);
//           ToastUtils.toastShow(context, "返回结果：" + json);
        }
    } ;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(YouaiAppService.isLogin){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
//        this.loginService = LoginService.getInit(this);
        this.activityService = new ActivityService(this);
//
//		/* 获取本机登录记录 */
//        Session session = loginService.getLoginRecord(this);
//
//		/* 初始化登录界面 */
        loginLayout = new LoginLayout(this);// 添加登录界面布局
        loginLayout.setStartGameListener(this);// 传点击监听
//        if (session.userAccount != null && session.userAccount.length() >= YouaiAppService.min) {
//            loginLayout.autolLogin();// 自动登录
//        }
        setContentView(loginLayout);
//    PayChoiceLayout payChoiceLayout = new PayChoiceLayout(this);
        this.activityService.pushView2Stack(loginLayout);// 入栈
//        initData();

    }
//    public void d(){
//        activityService.popViewFromStack()
//    }

    @Override
    public void onClick(View v) {

		/* 检查网络 */
        Log.i("feng", "LoginActivity.this:" + YJLoginActivity.this);
        if (!NetworkImpl.isNetworkConnected(YJLoginActivity.this)) {
            Utils.toastInfo(YJLoginActivity.this, Constants.NETWORK_FAILURE);
            return;
        }

        CheckAccount checkAccount = new CheckAccount(YJLoginActivity.this);// 获取检查帐号密码实例

        if (forgetlayout == null) {
            forgetlayout = ForgetPassword.getForgetPassword(this);
            loginClick = new LoginClick(YJLoginActivity.this, checkAccount, loginLayout, forgetlayout);
            forgetlayout.init(Session.getInstance().userAccount, loginClick);
            forgetlayout.setButtonClickListener(this);
        }

        int i = v.getId();
        if (i == MResource.getIdByName(this,Constants.ID,"login_tv_register")) {
//            ToastUtils.toastShow(this, "login_tv_register");
            /* 初始化注册界面 */
            registerLayout = new RegisterLayout(this);
            registerLayout.setButtonClickListener(this);
//            loginService.getRegisterRecord(registerLayout);// 检查注册记录
            activityService.pushView2Stack(registerLayout);

        } else if (i == MResource.getIdByName(this,Constants.ID,"login_iv_qq")) {//        ToastUtils.toastShow(this,"login_iv_qq");
           if(mTencent == null) {
               mTencent = Tencent.createInstance(Constants.QQ_APP_ID, this.getApplicationContext());
           }
            mTencent.login(this, "all", qqListener);
//        }


        } else if (i == MResource.getIdByName(this,Constants.ID,"login_iv_wx")) {
            if(mIWXAPI == null) {
                mIWXAPI = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, true);
                mIWXAPI.registerApp(Constants.WX_APP_ID);
            }
             SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            mIWXAPI.sendReq(req);
//            mIWXAPI.registerApp(Constants.WX_APP_ID);
//            mIWXAPI.openWXApp();
//            ToastUtils.toastShow(this, "login_iv_wx");//wxa4b3681595458cc6

        } else if (i == MResource.getIdByName(this,Constants.ID,"login_iv_sina_wb")) {
//            ToastUtils.toastShow(this, "login_iv_sina_wb");

        } else if (i == MResource.getIdByName(this,Constants.ID,"login_tv_login")) {
            loginClick.login();

        } else if (i == MResource.getIdByName(this,Constants.ID,"login_go_game")) {
            new GoLoginTask(this, loginLayout).execute();

        } else if (i == MResource.getIdByName(this,Constants.ID,"register_tv")) {
            Utils.fengLog("register_tv");
            loginClick.register(registerLayout);

        } else if (i == Constants.ID_REGISTER_AGREEMENT) {
            youaiServiceLayout = new YAServiceLayout(this, "用户协议", Constants.YOUAI_HTML);
            youaiServiceLayout.setButtonClickListener(this);
            activityService.pushView2Stack(youaiServiceLayout);

        } else if (i == Constants.ID_BACK || i == Constants.ID_REGISTER_BACK_BTN) {
            onBackPressed();

        } else if (i == Constants.CLOSE_ID) {
            onBackPressed();
            finish();

        } else if (i == Constants.ID_FORGET_PASSWORD) {
            activityService.pushView2Stack(forgetlayout);


	/*	case Constants.FORGER_PASSWORD:// 忘记密码提交帐号
                        loginClick.forgerPasswodToAccount(LoginActivity.this);
			break;*/
        } else if (i == Constants.FORGER_PASSWORD_NEW) {
            loginClick.forgerPasswodToPassword(GetMiBaoToUserTask.resettingPassword);

        } else {
        }
    }

    @Override
    public void onBackPressed() {// 系统返回键
        this.activityService.listenerBack(Constants.BACK_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Utils.fengLog("onActivityResult");
        Tencent.onActivityResultData(requestCode, resultCode, data, qqListener);
    }

    private QQListener qqListener = new QQListener(this);

    //  @Override
//  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    Tencent.onActivityResultData(requestCode,resultCode,data,listener);
//  }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//    mTencent.logout(this.getApplicationContext());
    }
}
