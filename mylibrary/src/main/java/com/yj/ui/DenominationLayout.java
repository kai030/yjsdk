package com.yj.ui;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.sdk.ZhimengPayActivity;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */

public class DenominationLayout extends LinearLayout {
	private Context context;

	/**
	 * @param context
	 */
	public DenominationLayout(Context context, final String moneys[]) {
		super(context);
		if (moneys == null) {
			Log.i("feng", "充值卡面额有误");
			return;
		}
		this.setBackgroundColor(0xffffffff);
		this.context = context;
		this.setOrientation(LinearLayout.VERTICAL);
		LinearLayout txt = new LinearLayout(context);
		txt.setGravity(Gravity.CENTER);
		txt.setBackgroundColor(0xff333333);
		LayoutParams params = new LayoutParams(
                    (int) (Utils.compatibleToWidth(context) * 0.8),
                    DimensionUtil.dip2px(context, 35));
		this.addView(txt, params);

		params = new LayoutParams(-2, -2);
		TextView denomination = new TextView(context);
		denomination.setText("金额");
		denomination.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		denomination.setTextColor(0xff888888);
		txt.addView(denomination, params);
		Log.i("feng", "listView:");
		ListView listView = new ListView(context);
		listView.setDivider(BitmapCache.getDrawable(context,
                                                            Constants.ASSETS_RES_PATH + "listview_divide.png"));
		this.addView(listView, -1, -1);
		listView.setAdapter(new DenominationAdapter(moneys));

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
				YeePayLayout.cardDenominationEdit.setText(moneys[position]
                                                                          + "元");
				ZhimengPayActivity.dialog.dismiss();
			}
		});
	}

	public class DenominationAdapter extends BaseAdapter {
		private String moneys[];

		public DenominationAdapter(String moneys[]) {
			this.moneys = moneys;
		}

		@Override
		public int getCount() {
			return moneys.length;
		}

		@Override
		public Object getItem(int position) {
			return moneys[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.i("feng", "position:" + position);
			Column column = (Column) convertView;
			if (column == null) {
				column = new Column(context);
			}
			column.money.setText(moneys[position] + "元");
			return column;
		}

		class Column extends LinearLayout {

			public TextView money;

			public Column(Context context) {
				super(context);
				LayoutParams params = new LayoutParams(
						-2, -2);
				params.topMargin = DimensionUtil.dip2px(context, 6);
				params.leftMargin = DimensionUtil.dip2px(context, 10);
				params.bottomMargin = DimensionUtil.dip2px(context, 6);
				money = new TextView(context);
				money.setTextColor(0xff777777);
				money.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
				this.addView(money, params);
			}

		}

	}

}
