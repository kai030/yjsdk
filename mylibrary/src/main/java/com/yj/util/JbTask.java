package com.yj.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.yj.entity.Constants;
import com.yj.entity.JbBean;
import com.yj.entity.Result;
import com.yj.sdk.YJLoginActivity;
import com.yj.sdk.YouaiAppService;
import com.yj.ui.CustomProgressDialog;


/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright
 */
public class JbTask extends AsyncTask<Void, Void, String> {

	private Activity ctx;
	private CustomProgressDialog customProgressDialog;

	public JbTask(Context context) {
		this.ctx = (Activity) context;
		customProgressDialog = new CustomProgressDialog(context,"请稍后...");
		customProgressDialog.show();
	}

	@Override
	protected String doInBackground(Void... params) {
		// 登陆
		GetDataImpl instance = GetDataImpl.getInstance(ctx);
		String result = instance.payJb();
		return result;
	}

	@Override
	protected void onPostExecute(String result) {

		customProgressDialog.dismiss();
		JbBean jbBean = new Gson().fromJson(result,JbBean.class);
		if(jbBean ==null)return;
		if (result != null) {
			switch (jbBean.getCode()) {
			case Constants.REQUEST_SUCCESS:
             Utils.opneWebvieAvtivity(ctx,"平台币兑换",jbBean.getUrl());
				break;
			default:
				Utils.toastInfo(ctx, "登录失败： " + jbBean.getMsg());
				break;
			}
		} else {
			Utils.toastInfo(ctx, "请求失败");
		}
	}

}