package com.yj.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;

import com.yj.entity.Constants;
import com.yj.entity.Result;
import com.yj.ui.DialogHelper;

//设置密保提交；
class ModiSubmitTask extends AsyncTask<Void, Void, Result> {
	private Activity mContext;
	private String password;
	private String answer;
	private String flagId;
	private Dialog myDialog;
	private ActivityService activityService;

	public ModiSubmitTask(Activity context, String password, String answer,
                              String secretId, ActivityService activityService) {
		this.password = password;
		this.answer = answer;
		this.flagId = secretId;
		this.mContext = context;
		this.activityService = activityService;

//		myDialog = DialogHelper.showProgress(context, "正在设置密保...", false);
		myDialog = DialogHelper.showProgress(context, "", false);
	}

	protected Result doInBackground(Void... params) {
	/*	 Result mResult  = null;
		if (Constants.A_PHONE_VERIFICATION_CODE.equals(flagId)) {
			
		} else if (Constants.A_MODI.equals(flagId)) {
           mResult = GetDataImpl.getInstance(mContext).mibaoSubmit(
				password, answer, flagId);
		}*/
//		Result mResult = GetDataImpl.getInstance(mContext).mibaoSubmit(
//				password, answer, flagId);
		myDialog.dismiss();
		return null;
	}

	protected void onPostExecute(Result result) {
		if (null != myDialog && myDialog.isShowing()) {
			myDialog.cancel();
		}
		if (result != null && result.resultCode == 0) {
			Utils.toastInfo(mContext, result.resultDescr);
			if(Constants.A_MODI.equals(flagId))
				this.activityService.listenerBack(Constants.BACK_USER);
		} else {
			try {
				Utils.toastInfo(mContext, result.resultDescr);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
