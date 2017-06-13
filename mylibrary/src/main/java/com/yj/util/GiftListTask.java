package com.yj.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.yj.entity.Constants;
import com.yj.entity.GiftListBean;
import com.yj.entity.Result;
import com.yj.sdk.FloatActivity;
import com.yj.ui.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public class GiftListTask extends AsyncTask<Void, Void, String> {

    private Activity ctx;
    private CustomProgressDialog customProgressDialog;

    public GiftListTask(Context context) {
        this.ctx = (Activity) context;
        customProgressDialog = new CustomProgressDialog(context, "请稍后...");
        customProgressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        GetDataImpl instance = GetDataImpl.getInstance(ctx);
        String result = instance.giftList();
        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        customProgressDialog.dismiss();
        if (result != null) {
            GiftListBean giftListBean = parseJson(result);
            if (giftListBean == null) return;
            switch (giftListBean.getCode()) {
                case Constants.REQUEST_SUCCESS:
                    FloatActivity floatActivity = (FloatActivity) ctx;
                    floatActivity.setGiftData(giftListBean.getData());
                    break;
                default:

				Utils.toastInfo(ctx, giftListBean.getMsg());

                    break;
            }
        } else {
			Utils.toastInfo(ctx, "请求失败");
        }
    }

    private GiftListBean parseJson(String json) {
        Gson gson = new Gson();
        GiftListBean giftListBean =  gson.fromJson(json,GiftListBean.class);
        return giftListBean;
    }
}