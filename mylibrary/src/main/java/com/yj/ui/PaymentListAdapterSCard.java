package com.yj.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.entity.Flag;
import com.yj.entity.PayChannel;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;

import java.util.List;

public class PaymentListAdapterSCard extends BaseAdapter {

	private ChagerLayout chagerLayout;
	private static Context mContext;
	private List<PayChannel> list;
	
	public PaymentListAdapterSCard(List<PayChannel> list, Context mContext) {
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position).paymentName;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		chagerLayout = (ChagerLayout) convertView;
		if (chagerLayout == null) {
			chagerLayout = new ChagerLayout(mContext,position);
		}
		chagerLayout.textView.setText(list.get(position).paymentName);
		
		return chagerLayout;

	}
	
	public static class ChagerLayout extends LinearLayout {
		public TextView textView;
        private int position;
		public ChagerLayout(Context context, int position) {
			super(context);
			this.position = position; 
			initUI(context);
		}

		public void initUI(Context context) {
			this.setOrientation(LinearLayout.HORIZONTAL);
			this.setGravity(Gravity.CENTER_HORIZONTAL);
			this.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mContext, "grid_click.9.png"));
			textView = new TextView(context);
			textView.setTextSize(16);
			textView.setTextColor(0xff758ba6);
			LayoutParams lp = new LayoutParams(-2, -1);
			addView(textView, lp);
			if(!Flag.isFirstCardList && this.position == 0 ){
                          Flag.isFirstCardList = true;
				this.setBackgroundDrawable(BitmapCache
						.getNinePatchDrawable(mContext,
								"grid_click_it.9.png"));
				this.textView.setTextColor(0xffffffff);
			}
			this.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0, DimensionUtil.dip2px(mContext, 7));
			
			
		}
	}
}