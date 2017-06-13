package com.yj.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.yj.entity.Constants;
import com.yj.entity.GetGiftBean;
import com.yj.sdk.YJGetGiftActivity;
import com.yj.ui.CustomProgressDialog;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public class CustomServiceTask extends AsyncTask<Void, Void, String> {

    private Activity ctx;
    private CustomProgressDialog customProgressDialog;

    public CustomServiceTask(Context context) {
        this.ctx = (Activity) context;
        customProgressDialog = new CustomProgressDialog(context, "请稍后...");
        customProgressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        GetDataImpl instance = GetDataImpl.getInstance(ctx);
        String result = instance.giftGet();
        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        customProgressDialog.dismiss();
        if (result != null) {
            GetGiftBean giftListBean = parseJson(result);
            if (giftListBean == null) return;
            switch (giftListBean.getCode()) {
                case Constants.REQUEST_SUCCESS:
                    Intent intent = new Intent(ctx, YJGetGiftActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(YJGetGiftActivity.TIS_CONTENT, giftListBean.getCard());
                    intent.putExtras(bundle);
                    ctx.startActivity(intent);
                    break;
                default:

                    Utils.toastInfo(ctx, giftListBean.getMsg());

                    break;
            }
        } else {
            Utils.toastInfo(ctx, "请求失败");
        }
    }

    private GetGiftBean parseJson(String json) {
        Gson gson = new Gson();
        GetGiftBean giftListBean = gson.fromJson(json, GetGiftBean.class);
        return giftListBean;
    }
}