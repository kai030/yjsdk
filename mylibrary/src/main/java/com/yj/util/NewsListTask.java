package com.yj.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import com.google.gson.Gson;
import com.yj.entity.Constants;
import com.yj.entity.NewsListBean;
import com.yj.sdk.FloatActivity;
import com.yj.ui.CustomProgressDialog;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public class NewsListTask extends AsyncTask<Void, Void, String> {

    private Activity ctx;
    private CustomProgressDialog customProgressDialog;

    public NewsListTask(Context context) {
        this.ctx = (Activity) context;
        customProgressDialog = new CustomProgressDialog(context, "请稍后...");
        customProgressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        GetDataImpl instance = GetDataImpl.getInstance(ctx);
        String result = instance.getNews();
        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        customProgressDialog.dismiss();

        if (result != null) {
            NewsListBean newsListBean = new Gson().fromJson(result, NewsListBean.class);
            if (newsListBean == null) return;
			switch (newsListBean.getCode()) {
			case Constants.REQUEST_SUCCESS:
                FloatActivity floatActivity = (FloatActivity) ctx;
                floatActivity.setNewsData(newsListBean.getData());
				break;
				default:
				Utils.toastInfo(ctx, newsListBean.getMsg());
				break;
			}
		} else {
			Utils.toastInfo(ctx, "请求失败");
        }
    }
}