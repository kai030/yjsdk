package com.yj.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.yj.entity.Constants;
import com.yj.entity.Result;
import com.yj.sdk.YJLoginActivity;
import com.yj.sdk.YouaiAppService;
import com.yj.ui.CustomProgressDialog;


/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright
 */
public class LoginTask extends AsyncTask<Void, Void, Result> {
	
    public static boolean autoLoginFlag = false;//登录标志位
//	private CustomDialog mDialog;
	private String user;
	private String password;
	private Activity ctx;
//	private boolean isCancel = false;
	private CustomProgressDialog customProgressDialog;

	public LoginTask(Context context, String user, String psw) {
		this.ctx = (Activity) context;
		this.user = user;
		this.password = psw;
		customProgressDialog = new CustomProgressDialog(context,"登录中...");
		customProgressDialog.show();
//		this.isCancel = false;
//		this.mDialog = new CustomDialog(ctx, loginLayout);
//		this.mDialog.setOnCancelListener(new OnCancelListener() {
//			@Override
//			public void onCancel(DialogInterface dialog) {
////				YouaiAppService.isLogin = false;
//				isCancel = true;
//			}
//		});
//		if (mDialog.isShowing()) {
//			mDialog.cancel();
//		}
//		mDialog.show();
	}

	@Override
	protected Result doInBackground(Void... params) {

		/* 自动登录延时2秒 */
//		autoLoginFlag = Constants.YES.equals(SharedPreferencesUtil.getXmlAutoLogin(ctx));
//		if (autoLoginFlag) {
//			autoLoginFlag = false;
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

		// 登陆
		GetDataImpl instance = GetDataImpl.getInstance(ctx);
		Result loginResult = instance.login(user, password);
		return loginResult;
	}

	@Override
	protected void onPostExecute(Result result) {
//		Utils.youaiLog("AsyncTask完成");
//		if (isCancel) {
//			Utils.youaiLog("已经取消登录");
//			return;
//		}
//		if (null != mDialog && mDialog.isShowing()) {
//			mDialog.cancel();
//
//		}
		// mDialog.cancel();
		customProgressDialog.dismiss();
		if (result != null) {
			switch (result.resultCode) {
			case Constants.REQUEST_SUCCESS:
				// 成功
                YouaiAppService.isLogin = true;
//				Utils.toastInfo(ctx, result.resultDescr);
				YouaiAppService.resultListen.loginSucceedResult(result.uid+"");
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				ctx.setResult(ctx.RESULT_OK, intent);
				// 反馈成功信息
				if(ctx instanceof YJLoginActivity) {
					ctx.finish();
				}
//				new ActivityService(ctx).onPostLogin(result,ctx);
				break;
			default:
				// 登陆失败
				YouaiAppService.resultListen.loginFailureResult();
				Utils.toastInfo(ctx, "登录失败： " + result.resultDescr);
				Utils.youaiLog("登录失败： " + result.resultDescr);
                          YouaiAppService.isLogin = false;
				break;
			}
		} else {
			YouaiAppService.resultListen.loginFailureResult();
			Utils.toastInfo(ctx, "登录失败");
			Utils.youaiLog("登录失败： ");
                  YouaiAppService.isLogin = false;
		}
	}

	
	//***********************
}