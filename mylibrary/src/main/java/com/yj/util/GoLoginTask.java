package com.yj.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;

import com.yj.entity.Constants;
import com.yj.entity.Result;
import com.yj.entity.Session;
import com.yj.sdk.YouaiAppService;
import com.yj.ui.CustomDialog;
import com.yj.ui.CustomProgressDialog;
import com.yj.ui.LoginLayout;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class GoLoginTask extends AsyncTask<Void, Void, Result> {

    public static boolean autoLoginFlag = false;//登录标志位
    private Activity ctx;
    private CustomProgressDialog customProgressDialog;

    public GoLoginTask(Activity context, LoginLayout loginLayout) {
        this.ctx = context;
        customProgressDialog = new CustomProgressDialog(context, "请稍后...");
        customProgressDialog.show();
    }

    @Override
    protected Result doInBackground(Void... params) {

		/* 自动登录延时2秒 */
        autoLoginFlag = Constants.YES.equals(SharedPreferencesUtil.getXmlAutoLogin(ctx));


        // 登陆
        GetDataImpl instance = GetDataImpl.getInstance(ctx);
        Result loginResult = instance.goLogin();
        return loginResult;
    }

    @Override
    protected void onPostExecute(Result result) {
        customProgressDialog.dismiss();
        // mDialog.cancel();
        if (result != null) {
            switch (result.resultCode) {
                case Constants.REQUEST_SUCCESS:
                    // 成功
                    YouaiAppService.isLogin = true;
//				Utils.toastInfo(ctx, result.resultDescr);
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ctx.setResult(ctx.RESULT_OK, intent);
                    YouaiAppService.resultListen.loginSucceedResult(Session.getInstance().uid);
                    // 反馈成功信息
                    ctx.finish();
//				new ActivityService(ctx).onPostLogin(result,ctx);
                    break;
                default:
                    // 登陆失败
                    YouaiAppService.resultListen.loginFailureResult();
                    Utils.toastInfo(ctx,result.resultDescr);
                    Utils.youaiLog("登录失败： " + result.resultDescr);
                    YouaiAppService.isLogin = false;
                    break;
            }
        } else {
            YouaiAppService.resultListen.loginFailureResult();
            Utils.toastInfo(ctx, "请求失败");
            Utils.youaiLog("登录失败");
            YouaiAppService.isLogin = false;
        }
    }


    //***********************
}