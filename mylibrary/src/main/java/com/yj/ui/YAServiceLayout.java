package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.yj.entity.ChargeData;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class YAServiceLayout extends ChargeAbstractLayout {
	private Activity mActivity;

	public YAServiceLayout(Activity activity, String title, String url) {
		super(activity);
		this.mActivity = activity;
		initUI(title,url);
	}
	
	public void initUI(String title, String urls) {
		super.initUI(mActivity);
		logo.setText(title);
		mSubject = new LinearLayout(mActivity);
		mSubject.setOrientation(LinearLayout.VERTICAL);
		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(DimensionUtil.dip2px(mActivity, 5), 0,DimensionUtil.dip2px(mActivity, 5),DimensionUtil.dip2px(mActivity, 3));
		mContent.addView(mSubject, lp);
		
		FrameLayout fLayout = new FrameLayout(mActivity);
		lp = new LinearLayout.LayoutParams(-1, -1);
		mSubject.addView(fLayout, lp);

		WebView webView = new WebView(mActivity);
		WebSettings webSet = webView.getSettings();
		webSet.setJavaScriptEnabled(true);
		
		final ProgressBar bar = new ProgressBar(mActivity);
		//noinspection ResourceType
		bar.setInterpolator(mActivity, android.R.anim.linear_interpolator);
		FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(-2, -2);
		flp.gravity = Gravity.CENTER;
		bar.setVisibility(View.GONE);
		
		fLayout.addView(webView, -1,-1);
		fLayout.addView(bar,flp);
//		Log.i("feng", "url4:"+urls);
		
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
//				Log.i("feng", "url5:"+url);
				bar.setVisibility(View.GONE);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
//				Log.i("feng", "url6:"+url);
				bar.setVisibility(View.VISIBLE);
			}
		});
		
//		webView.set
		webView.loadUrl(urls);

	}

	public static String readFromFile(Context context, String path) {
		InputStream in = null;
		ByteArrayOutputStream outputStream = null;
		String fileText = null;
		try {
			in = context.getAssets().open(path);
			// 在内存中开辟一段缓冲区
			byte Buffer[] = new byte[1024];
			int len = 0;
//			int size = in.available();
			// 创建一个字节数组输出流
			outputStream = new ByteArrayOutputStream();
			// 读出来的数据首先放入缓冲区，满了之后再写到字符输出流中
			while ((len = in.read(Buffer)) != -1) {
				outputStream.write(Buffer, 0, len);

			}
			// 把字节输出流转String
			fileText = new String(outputStream.toByteArray());
			Utils.youaiLog("游鹏用户服务协议--------->" + fileText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileText;

	}

	@Override
	public ChargeData getChargeEntity() {
		return null;
	}

	
}
