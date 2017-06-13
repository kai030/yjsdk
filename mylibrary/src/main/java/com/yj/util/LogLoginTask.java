package com.yj.util;

import android.content.Context;
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
public class LogLoginTask extends AsyncTask<Void, Void, Result> {

	private Context ctx;
//	private CustomProgressDialog customProgressDialog;

	public LogLoginTask(Context context) {
		this.ctx = context;
//		customProgressDialog = new CustomProgressDialog(context);
//		customProgressDialog.show();
	}

	@Override
	protected Result doInBackground(Void... params) {
		GetDataImpl instance = GetDataImpl.getInstance(ctx);
		Result loginResult = instance.serverId(Constants.URL_LOG);
		return loginResult;
	}

	@Override
	protected void onPostExecute(Result result) {
//		customProgressDialog.dismiss();
		Utils.youaiLog("AsyncTask完成");

		// mDialog.cancel();
		if (result != null) {
			switch (result.resultCode) {
			case Constants.REQUEST_SUCCESS:
				// 成功
//				YouaiAppService.isLogin = true;
//				if(ctx instanceof YJLoginActivity){
//					((YJLoginActivity) ctx).finish();
//				}
//				YouaiAppService.resultListen.loginSucceedResult(result.uid+"");
//				Utils.toastInfo(ctx, "登录成功");
//				Utils.fengLog("登录成功： " + result.resultDescr);
				break;
			default:
				// 登陆失败
//				YouaiAppService.isLogin = false;
//				YouaiAppService.resultListen.loginFailureResult();
//				Utils.toastInfo(ctx, "登录失败： " + result.resultDescr);
//				Utils.youaiLog("登录失败： " + result.resultDescr);
//                          YouaiAppService.isLogin = false;
				break;
			}
		} else {
//			YouaiAppService.resultListen.loginFailureResult();
//			Utils.toastInfo(ctx, "登录失败");
//			Utils.youaiLog("登录失败：参数错误 ");
//                  YouaiAppService.isLogin = false;
		}
	}

	
	//***********************
}