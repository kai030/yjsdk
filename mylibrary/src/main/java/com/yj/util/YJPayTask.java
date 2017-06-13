package com.yj.util;

import android.content.Context;
import android.os.AsyncTask;

import com.yj.entity.Constants;
import com.yj.entity.Result;


/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright
 */
public class YJPayTask extends AsyncTask<Void, Void, Result> {

	private Context ctx;

	public YJPayTask(Context context) {
		this.ctx = context;
	}

	@Override
	protected Result doInBackground(Void... params) {
		GetDataImpl instance = GetDataImpl.getInstance(ctx);
		Result result = instance.pay();
		return result;
	}

	@Override
	protected void onPostExecute(Result result) {
		if (result != null) {
			switch (result.resultCode) {
			case Constants.REQUEST_SUCCESS:
				// 成功
				break;
			default:
				// 失败
				break;
			}
		} else {
			// 失败;
		}
	}
}