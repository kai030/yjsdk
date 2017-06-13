package com.yj.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.yj.entity.Constants;
import com.yj.entity.GiftListBean;
import com.yj.entity.QqBean;
import com.yj.sdk.FloatActivity;
import com.yj.ui.CustomProgressDialog;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public class CustomerServiceTask extends AsyncTask<Void, Void, String> {

    private Activity ctx;
    private CustomProgressDialog customProgressDialog;

    public CustomerServiceTask(Context context) {
        this.ctx = (Activity) context;
        customProgressDialog = new CustomProgressDialog(context, "请稍后...");
        customProgressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        GetDataImpl instance = GetDataImpl.getInstance(ctx);
        String result = instance.customerService();
        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        customProgressDialog.dismiss();
        if (result != null) {
            QqBean qqBean = parseJson(result);
            if (qqBean == null) return;
            switch (qqBean.getCode()) {
                case Constants.REQUEST_SUCCESS:
                    FloatActivity floatActivity = (FloatActivity) ctx;
                    floatActivity.setQqData(qqBean.getQq());
                    break;
                default:

				Utils.toastInfo(ctx, qqBean.getMsg());

                    break;
            }
        } else {
			Utils.toastInfo(ctx, "请求失败");
        }
    }

    private QqBean parseJson(String json) {
        Gson gson = new Gson();
        QqBean qqBean =  gson.fromJson(json,QqBean.class);
        return qqBean;
    }
}