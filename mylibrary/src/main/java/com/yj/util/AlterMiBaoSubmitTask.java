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
public class AlterMiBaoSubmitTask extends AsyncTask<Void, Void, Result> {
	private Activity mContext;
	private String oldPhone;
	private String verificationCode;
	private String flagId;
	private Dialog myDialog;

	public AlterMiBaoSubmitTask(Activity context, String oldPhone, String verificationCode,
                                    String flagId) {
//		Log.i("", msg)
		this.mContext = context;
		this.oldPhone = oldPhone;
		this.flagId = flagId;
		this.verificationCode = verificationCode;
		myDialog = DialogHelper.showProgress(context, "", false);
	}

	protected Result doInBackground(Void... params) {
//		Result result = GetDataImpl
//				.getInstance(mContext).mibaoAlter(
//						oldPhone, verificationCode,this.flagId);
		/*Result result = GetDataImpl.getInstance(mContext).MiBaoSubmit(
				userName, newPw, answer);*/
		myDialog.dismiss();
		return null;

	}

	protected void onPostExecute(Result result) {
		if (null != myDialog && myDialog.isShowing()) {
			myDialog.cancel();
		}
		if (result != null) {
			if (result.resultCode == 0) {
				if(flagId == Constants.A_ALTER_MODI){
					mContext.finish();
				Utils.toastInfo(mContext, "修改密保成功");
				}else{
					Utils.toastInfo(mContext, "获取验证成功，请留意信息");
				}
				
			} else {
				Utils.toastInfo(mContext,
                                                result.resultDescr);
			}

		} else {
			if(flagId == Constants.A_ALTER_MODI){
				mContext.finish();
			Utils.toastInfo(mContext, "修改密保失败");
			}else{
				Utils.toastInfo(mContext, "获取验证失败");
			}
		}
	}
}