package com.yj.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yj.util.DimensionUtil;

/**
 * Created by Frank on 2017/5/6 0006.
 */

public class ParentLayout extends RelativeLayout {
    protected Context context;
    protected LinearLayout layout;
    protected View view;

    public ParentLayout(Context context, int layoutId) {
        super(context);
        this.context = context;
        init(layoutId);
    }

    private void init(int layoutId){
		this.setBackgroundColor(0x99000000);
//        LayoutParams params = new LayoutParams(Utils.compatibleToWidth(context), -2);
        LayoutParams params = new LayoutParams(DimensionUtil.dip2px(context,304), -2);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//		layout = new LinearLayout(context);
        view = LayoutInflater.from(context).inflate(layoutId, null);
        addView(view,params);

//		layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
//				context, "login_bg.9.png"));
//		layout.setOrientation(LinearLayout.VERTICAL);
    }
}
