package com.yj.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View.OnClickListener;

import com.yj.entity.Result;
import com.yj.ui.DialogHelper;
import com.yj.ui.YAServiceLayout;

//设置密保提交；
public class UnionPayTask extends AsyncTask<Void, Void, Result> {
	private Activity mContext;
	private Dialog myDialog;
	private ActivityService activityService;
	private OnClickListener onClickListener;

	public UnionPayTask(Activity context, ActivityService activityService, OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
		this.mContext = context;
        this.activityService = activityService;
		myDialog = DialogHelper.showProgress(context, "", false);
	}

	
	protected Result doInBackground(Void... params) {
		Result mResult = GetDataImpl.getInstance(mContext).unionPaySubmit();
		myDialog.dismiss();
		return mResult;
	}

	protected void onPostExecute(Result result) {
		if (null != myDialog && myDialog.isShowing()) {
			myDialog.cancel();
		}
		if (result != null && result.resultCode == 0) {
			Log.i("feng", "Result.sessionId3:" + Result.sessionId);
//			Utils.toastInfo(mContext, result.resultDescr);
			Utils.youaiLog("银联:" + result.resultDescr);
			YAServiceLayout youaiServiceLayout = new YAServiceLayout(mContext, "易宝支付", Result.sessionId);
			youaiServiceLayout.setButtonClickListener(onClickListener);
			activityService.pushView2Stack(youaiServiceLayout);
//			Utils.openWebpage(Result.sessionId, mContext);
//			new ActivityService(mContext).listenerBack(Constants.BACK_USER);
		} else {
			try {
				Utils.youaiLog("银联:" + result.resultDescr);
				Log.i("feng", "Result.sessionId3:" + Result.sessionId);
//				Utils.toastInfo(mContext, result.resultDescr);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
