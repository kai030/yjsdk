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
 * @copyright 游鹏科技
 */
public class TWXLoginTask extends AsyncTask<Void, Void, Result> {

	private Context ctx;
	private CustomProgressDialog customProgressDialog;

	public TWXLoginTask(Context context) {
		this.ctx = context;
		 customProgressDialog = new CustomProgressDialog(context);
		customProgressDialog.show();
	}

	@Override
	protected Result doInBackground(Void... params) {
		GetDataImpl instance = GetDataImpl.getInstance(ctx);
		Result loginResult = instance.tQQLogin(Constants.WX_LOGING_URL);
		return loginResult;
	}

	@Override
	protected void onPostExecute(Result result) {
		customProgressDialog.dismiss();
		Utils.youaiLog("AsyncTask完成");

		// mDialog.cancel();
		if (result != null) {
			switch (result.resultCode) {
			case Constants.REQUEST_SUCCESS:
				// 成功
				YouaiAppService.isLogin = true;
				YouaiAppService.resultListen.loginSucceedResult(result.uid+"");
//				((Activity) ctx).finish();
//				new LoginTask()
//				Utils.toastInfo(ctx, "登录成功");
//				Utils.fengLog("登录成功： " + result.resultDescr);
				break;
			default:
				// 登陆失败
				YouaiAppService.isLogin = false;
				YouaiAppService.resultListen.loginFailureResult();
				Utils.toastInfo(ctx, "登录失败： " + result.resultDescr);
//				Utils.youaiLog("登录失败： " + result.resultDescr);
//                          YouaiAppService.isLogin = false;
				break;
			}
		} else {
			YouaiAppService.resultListen.loginFailureResult();
//			Utils.toastInfo(ctx, "登录失败");
			Utils.youaiLog("登录失败：参数错误 ");
                  YouaiAppService.isLogin = false;
		}

		ctx.startActivity(new Intent(ctx,YJLoginActivity.class));
	}

	
	//***********************
}