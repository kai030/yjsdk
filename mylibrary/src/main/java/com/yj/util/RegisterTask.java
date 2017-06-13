package com.yj.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import com.yj.entity.Constants;
import com.yj.entity.Result;
import com.yj.entity.Session;
import com.yj.sdk.YouaiAppService;
import com.yj.sdk.YJManage;
import com.yj.ui.CustomDialog;
import com.yj.ui.CustomProgressDialog;
import com.yj.ui.LoginLayout;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class RegisterTask extends AsyncTask<Void, Void, Result> {
	private String user;
	private String pw;
	private Activity ctx;
	private CustomProgressDialog customProgressDialog;
//	private CustomDialog mDialog;
//	private boolean isCancel = false;// 标志登录 true为取消

	public RegisterTask(Activity context, String userName, String password, LoginLayout loginLayout) {
		this.ctx = context;
		this.user = userName;
		this.pw = password;
		customProgressDialog = new CustomProgressDialog(ctx);
		customProgressDialog.show();
//		this.mDialog = new CustomDialog(ctx, loginLayout);
//		this.mDialog.setTvText("正在注册...");
//		this.isCancel = false;
//		this.mDialog.setOnCancelListener(new OnCancelListener() {
//
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				// TODO Auto-generated method stub
//				isCancel = true;// 取消登录
//			}
//		});
//		this.mDialog.show();
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected Result doInBackground(Void... params) {
		GetDataImpl mInstance = GetDataImpl.getInstance(ctx);
		Result result = mInstance.register(user, pw);
		return result;
	}

	protected void onPostExecute(Result result) {
		customProgressDialog.dismiss();
//		if (isCancel) {
//			Utils.youaiLog("已经取消注册");
//			return;
//		}
//		if (null != mDialog && mDialog.isShowing()) {
//			mDialog.cancel();
//		}
		if (result != null) {
			if (result.resultCode == Constants.REQUEST_SUCCESS) {

//				ToastUtils.toastShow(ctx,"注册成功");
				Utils.youaiLog("注册成功");
				/* 登录 */
//				ctx.finish();
				new LoginTask(ctx, user,pw).execute();
//				YJManage.getInstance().yjLogin(ctx,YouaiAppService.resultListen);
			} else {
				// 注册失败
				Utils.toastInfo(ctx, result.resultDescr);
			}
		} else {
			Utils.toastInfo(ctx, "注册失败");
		}
	}

}
