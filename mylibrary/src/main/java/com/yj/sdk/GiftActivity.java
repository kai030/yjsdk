package com.yj.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.yj.ui.FloatLayout;
import com.yj.ui.GiftLayout;
import com.yj.util.ActivityService;

/**
 * Created by Frank on 2017/5/10 0010.
 */

public class GiftActivity extends Activity {

    private ActivityService activityService;



   public static void setPaymentParam(){
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.pay_choice_layout);

        this.activityService = new ActivityService(this);

        GiftLayout giftLayout = new GiftLayout(this);
        this.activityService.pushView2Stack(giftLayout);// 入栈

    }

}
