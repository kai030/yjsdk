package com.yj.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.ui.Loading;
import com.yj.ui.LoginLayout;

class CustomDialog extends Dialog {
	private Context ctx;
	private TextView tv;
	private Button cancel;
	private LinearLayout content;
	private Drawable drawable;
	private Loading loading;
	private LoginLayout loginLayout;

	@SuppressWarnings({"WrongConstant", "ResourceType"})
	public CustomDialog(Context context, LoginLayout loginLayout) {
		super(context);
		this.ctx = context;
		this.loginLayout = loginLayout;
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		if (content == null) {
			content = new LinearLayout(ctx);
			// 垂直
			content.setOrientation(1);
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
			tv.setText(Html.fromHtml("正在登录  <font color = #e10143>"
                                                 + this.loginLayout.getLoginEditLayout().getAccount()
                                                 + "...</font>"));
			tv.setTextSize(14);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					-2, -2);
			lp.topMargin = DimensionUtil.dip2px(ctx, 5);
			content.addView(tv, lp);
			//

			loading = new Loading(ctx);

			// loading.setTextColor(Color.TRANSPARENT);
			LinearLayout.LayoutParams lploading = new LinearLayout.LayoutParams(
					-2, -2);
			lploading.topMargin = DimensionUtil.dip2px(ctx,
                                                                   13);
			content.addView(loading, lploading);

			cancel = new Button(ctx);
			cancel.setId(110);
			cancel.setText("取  消");
			cancel.setTextColor(0xff696d73);
			cancel.setTextSize(18);
			// if (state == null) {
			// state = Utils.getStateCornerListDrawable(context,
			// 0xff858585, 0xffadadad, 7);
			// }
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

		// content.addView(tv);

		// 对话框的内容布局
		setContentView(content);
		setCanceledOnTouchOutside(false);
	}

	protected void setTvText(String str) {
		tv.setText(str);
	}
	// @Override
	// public void onBackPressed() {
	// onClick(cancel);
	// }

}
