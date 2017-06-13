package com.yj.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;

import com.yj.entity.Result;
import com.yj.ui.DialogHelper;
import com.yj.ui.ForgetPassword;
import com.yj.ui.ResettingPassword;

//根据用户名获取密保信息；
 public	class GetMiBaoToUserTask extends AsyncTask<Void, Void, Result> {
		private Activity mContext;
		private String userName;
		private Dialog myDialog;
		public static ResettingPassword resettingPassword;
		private ForgetPassword forgetlayout;

		public GetMiBaoToUserTask(Activity context, String userName, ForgetPassword forgetlayout) {
			this.mContext = context;
			this.userName = userName;
			this.forgetlayout = forgetlayout;
			myDialog = DialogHelper.showProgress(context, "", false);
		}
		

		protected Result doInBackground(Void... params) {
			Result result = GetDataImpl.getInstance(mContext).getMibaoToUser(
					userName);
			myDialog.dismiss();
			return result;

		}

		protected void onPostExecute(Result result) {
			if (null != myDialog && myDialog.isShowing()) {
				myDialog.cancel();
			}
			
			if (result != null) {
				ToastUtils.toastShow(mContext, result.resultDescr);
				if (result.resultCode == 0) {
//					Utils.toastInfo(mContext, result.resultDescr);
					/*resettingPassword = new ResettingPassword(
							this.mContext, forgetlayout.getAccount());
					resettingPassword.setConfirmListener(this.clickListener);
					resettingPassword
							.setResettingPasswordListener(this.clickListener);
					new ActivityService(mContext).pushView2Stack(resettingPassword);*/
					// onBackPressed();
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
