package com.yj.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;

import com.yj.entity.Constants;
import com.yj.entity.Result;
import com.yj.ui.DialogHelper;

/**
 * 根据密保信息重置密码；
 */
public class MiBaoSubmitTask extends AsyncTask<Void, Void, Result> {
	private Activity mContext;
	private String userName;
	private String newPw;
	private String answer;
	private Dialog myDialog;

	public MiBaoSubmitTask(Activity context, String userName, String newPw,
                               String answer) {
		this.mContext = context;
		this.userName = userName;
		this.answer = answer;
		this.newPw = newPw;
		myDialog = DialogHelper.showProgress(context, "", false);
	}

	protected Result doInBackground(Void... params) {
//		Result result = GetDataImpl.getInstance(mContext).MiBaoSubmit(
//				userName, newPw, answer);
//		myDialog.dismiss();
		return null;

	}

	protected void onPostExecute(Result result) {
		if (null != myDialog && myDialog.isShowing()) {
			myDialog.cancel();
		}
		if (result != null) {
			if (result.resultCode == 0) {
				Utils.toastInfo(mContext, result.resultDescr);
				new ActivityService(mContext).listenerBack(Constants.BACK_USER);
			} else {
				// 修改密码失败
				Utils.toastInfo(mContext, result.resultDescr);
			}
		} else {
			// 修改密码失败
			Utils.toastInfo(mContext, "获取密保信息失败");
		}
	}
}