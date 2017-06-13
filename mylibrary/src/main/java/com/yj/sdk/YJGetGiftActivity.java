package com.yj.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.yj.ui.GetGiftLayout;
import com.yj.ui.TisLayout;
import com.yj.util.ActivityService;

/**
 * Created by Frank on 2017/5/10 0010.
 */

public class YJGetGiftActivity extends Activity {
    private ActivityService activityService;
    public static final String TIS_CONTENT = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.pay_choice_layout);

        this.activityService = new ActivityService(this);

        if(getIntent().getExtras() != null){
            GetGiftLayout tisLayout = new GetGiftLayout(this,getIntent().getExtras().getString(TIS_CONTENT));

            this.activityService.pushView2Stack(tisLayout);// 入栈
        }



    }
}
