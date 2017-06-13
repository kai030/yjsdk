package com.yj.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.entity.SecretData;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class OptionsAdapter extends BaseAdapter {

	private SecretData[] secretData;
	private Context context = null;

	/**
	 * 
	 * @param activity
	 * @param handler
	 * @param list
	 */
	public OptionsAdapter(Context context, SecretData[] secretData) {
		this.context = context;
		this.secretData = secretData;
	}

	@Override
	public int getCount() {
		return secretData.length;
	}

	@Override
	public Object getItem(int position) {
		return secretData[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		OptionView view = (OptionView) convertView;
		if (view == null) {
			view = new OptionView(context);
		}
		// convertView = view;
		String sss = secretData[position].secretName;
		view.account.setText(sss);
		return view;
	}

	public class OptionView extends LinearLayout {
		public TextView account;

		/**
		 * @param context
		 */
		@SuppressWarnings("deprecation")
		public OptionView(Context context) {
			super(context);
			this.setGravity(Gravity.CENTER_VERTICAL);
			this.setOrientation(LinearLayout.VERTICAL);
			this.setPadding(0, DimensionUtil.dip2px(context, 5), 0,
                                        DimensionUtil.dip2px(context, 5));
			this.setBackgroundDrawable(Utils.getNormalColorList(context,
                                                                            0xffadadad, 0xfff6f6f6));
			LinearLayout layout = new LinearLayout(context);
			addView(layout, -2, -2);
			LayoutParams lp = new LayoutParams(-2, -2);
			account = new TextView(context);
			account.setSingleLine(true);
			account.setHorizontallyScrolling(true);
			lp.weight = 1;
			lp.leftMargin = DimensionUtil.dip2px(context, 10);
			lp.rightMargin = DimensionUtil.dip2px(context, 5);
			account.setTextSize(15);
			account.setTextColor(0xff4f4f4f);
			layout.addView(account, lp);

		}

	}
}
