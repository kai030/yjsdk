package com.yj.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;

import com.yj.entity.Constants;
import com.yj.entity.Result;
import com.yj.ui.DialogHelper;

//修改密码；
  public class ModificTask extends AsyncTask<Void, Void, Result> {
			private Activity mContext;
			private String pw;
			private Dialog myDialog;
			private String newPw;

			public ModificTask(Activity context, String pw, String newPw) {
				this.mContext = context;
				this.pw = pw;
				this.newPw = newPw;
				myDialog = DialogHelper.showProgress(context, "", false);
			}

			protected Result doInBackground(Void... params) {
//				Result result = GetDataImpl.getInstance(mContext).changePassword(
//						pw, newPw);
//				myDialog.dismiss();
				return null;

			}

			protected void onPostExecute(Result result) {
				
				if (null != myDialog && myDialog.isShowing()) {
					myDialog.cancel();
				}
				if (result != null) {
					if (result.resultCode == 0) {
						Utils.toastInfo(mContext, "修改成功");
						new ActivityService(this.mContext).listenerBack(Constants.BACK_USER);
					} else {
						// 修改密码失败
						Utils.toastInfo(mContext, result.resultDescr);
					}
				} else {
					// 修改密码失败
					Utils.toastInfo(mContext, "修改密码失败");
				}
			}
		}
