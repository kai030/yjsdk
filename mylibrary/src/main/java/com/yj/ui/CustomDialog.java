package com.yj.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;

/**
 * @author lufengkai 
 * @date 2015年5月28日
 * @copyright 游鹏科技
 */
public class CustomDialog extends Dialog {
	private Context ctx;
	private TextView tv;
	private Button cancel;
	private LinearLayout content;
	private Drawable drawable;// 背景图片
	private Loading loading;
//	private LoginLayout loginLayout;

	public CustomDialog(Context context, LoginLayout loginLayout) {
		super(context);
//		this.loginLayout = loginLayout;
		this.ctx = context;
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		if (content == null) {
			content = new LinearLayout(ctx);
			// 垂直
			content.setOrientation(LinearLayout.VERTICAL);
			content.setGravity(Gravity.CENTER);
			content.setPadding(DimensionUtil.dip2px(ctx, 30),
                                           DimensionUtil.dip2px(ctx, 10),

                                           DimensionUtil.dip2px(ctx, 30),
                                           DimensionUtil.dip2px(ctx, 10));
			if (drawable == null) {
				drawable = BitmapCache.SampleViewUtil.getCorner(0xfff8f8f8,
                                                                                7, 0);
			}

			content.setBackgroundDrawable(drawable);

			// 文字
			tv = new TextView(ctx);
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			tv.setTextColor(0xff696e6f);
//			tv.setText(Html.fromHtml("正在登录  <font color = #e10143>"
//                                                 + loginLayout.getLoginEditLayout().getAccount()
//                                                 + "...</font>"));
			tv.setText("请稍后...");
			tv.setTextSize(14);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					-2, -2);
			lp.topMargin = DimensionUtil.dip2px(ctx, 5);
			content.addView(tv, lp);
			loading = new Loading(ctx);
			LinearLayout.LayoutParams lploading = new LinearLayout.LayoutParams(
					-2, -2);
			lploading.topMargin = DimensionUtil.dip2px(ctx,
                                                                   13);
			content.addView(loading, lploading);
			cancel = new Button(ctx);
			//noinspection ResourceType
			cancel.setId(110);
			cancel.setText("取  消");
			cancel.setTextColor(0xff696d73);
			cancel.setTextSize(18);
			cancel.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
                                                                                      "btn_gray.9.png"));
			cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					cancel();
				}
			});
			LinearLayout.LayoutParams lpcancel = new LinearLayout.LayoutParams(
                            DimensionUtil.dip2px(ctx, 80), -2);
			lpcancel.topMargin = DimensionUtil.dip2px(ctx,
                                                                  10);
			content.addView(cancel, lpcancel);
		}

		// 对话框的内容布局
		setContentView(content);
		setCanceledOnTouchOutside(false);
	}

	public void setTvText(String str) {
		if (tv != null && str != null)
			tv.setText(str);
	}

}
