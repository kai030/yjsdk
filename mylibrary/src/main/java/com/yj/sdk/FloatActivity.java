package com.yj.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yj.entity.Constants;
import com.yj.entity.GiftBean;
import com.yj.entity.GiftListBean;
import com.yj.entity.NewsBean;
import com.yj.ui.CustomerServicesLayout;
import com.yj.ui.FloatLayout;
import com.yj.ui.GiftLayout;
import com.yj.ui.MResource;
import com.yj.ui.MessageLayout;
import com.yj.util.ActivityService;
import com.yj.util.CustomerServiceTask;
import com.yj.util.GiftListTask;
import com.yj.util.NewsListTask;
import com.yj.util.ToastUtils;

import java.util.List;

/**
 * Created by Frank on 2017/5/10 0010.
 */

public class FloatActivity extends Activity implements View.OnClickListener {

    private ActivityService activityService;
    private GiftLayout giftLayout;
    private MessageLayout messageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.pay_choice_layout);

        this.activityService = new ActivityService(this);

        FloatLayout floatLayout = new FloatLayout(this);
        floatLayout.setClickListener(this);
        this.activityService.pushView2Stack(floatLayout);// 入栈

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == MResource.getIdByName(this, Constants.ID, "view_back")) {
            close();

        } else if (i == MResource.getIdByName(this, Constants.ID, "float_message_tv")) {
            new NewsListTask(this).execute();

        } else if (i == MResource.getIdByName(this, Constants.ID, "float_gift_tv")) {
            new GiftListTask(this).execute();

        } else if (i == MResource.getIdByName(this, Constants.ID, "float_customer_service_tv")) {
            new CustomerServiceTask(this).execute();

        } else if (i == MResource.getIdByName(this, Constants.ID, "float_user_center_tv")) {
            ToastUtils.toastShow(this, "下版本开放");
        }
    }

    public void setQqData(String qq){
        CustomerServicesLayout customerServicesLayout = new CustomerServicesLayout(this, "QQ: " + qq);
        customerServicesLayout.setClickListener(this);
        this.activityService.pushView2Stack(customerServicesLayout);// 入栈
    }

    public void setNewsData(List<NewsBean> newsBeanList){
        messageLayout = new MessageLayout(this);
        messageLayout.initUi(newsBeanList);
        messageLayout.setClickListener(this);
        this.activityService.pushView2Stack(messageLayout);
    }

    public void setGiftData(List<GiftBean> giftList) {
        giftLayout = new GiftLayout(this);
        giftLayout.initUi(giftList);
        giftLayout.setClickListener(this);
        this.activityService.pushView2Stack(giftLayout);// 入栈

    }

    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        View view = this.activityService.popViewFromStack();
        if (view == null) finish();
    }
}
