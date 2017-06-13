package com.yj.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;

import com.yj.entity.Result;
import com.yj.ui.DialogHelper;
import com.yj.ui.PayFailureLayout;
import com.yj.ui.PaySucceedLayout;

//设置密保提交；
public class YeepayTask extends AsyncTask<Void, Void, Result> {
	private Activity mContext;
	private Dialog myDialog;
	private PayFailureLayout failureLayout;
	private PaySucceedLayout succeedLayout;
	private ActivityService activityService;

	public YeepayTask(Activity context, PayFailureLayout failureLayout, PaySucceedLayout succeedLayout, ActivityService activityService) {
		this.mContext = context;
        this.failureLayout = failureLayout;
        this.succeedLayout = succeedLayout;
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
		Result mResult = GetDataImpl.getInstance(mContext).yeepaySubmit();
		myDialog.dismiss();
		return mResult;
	}

	protected void onPostExecute(Result result) {
		if (null != myDialog && myDialog.isShowing()) {
			myDialog.cancel();
		}
		if (result != null && result.resultCode == 0) {
//			YouaiAppService.resultListen.paymentSucceedResult(YJResultCode.PAYMENT_SUCCEED);
			if(this.activityService != null){
				activityService.pushView2Stack(succeedLayout);
			}
//			Utils.toastInfo(mContext, result.resultDescr);
			/*if(Constants.A_MODI.equals(cardPwd))
			new ActivityService(mContext).listenerBack(Constants.BACK_USER);*/
		} else {
//			YouaiAppService.resultListen.paymentFailureResult(YJResultCode.PAYMENT_Failure);
			try {
				if(this.activityService != null){
					activityService.pushView2Stack(failureLayout);
				}
				  
//				Utils.toastInfo(mContext, result.resultDescr);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
