package com.yj.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.dao.UserCenterBean;
import com.yj.entity.Constants;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;

import java.util.List;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class UserListAdapter extends BaseAdapter {
	
	private List<UserCenterBean> list;
	private Context context;
	private Column column;
//	public static Map<Integer, ImageView> iconMap = new HashMap<>();
	
	public UserListAdapter(Context context, List<UserCenterBean> list){
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			column = new Column(context,position);
			convertView = column;
			convertView.setTag(column);
		}else{
			column = (Column) convertView.getTag();
		}
		
		column.name.setText(list.get(position).getName());
		column.icon.setBackgroundDrawable(BitmapCache.getDrawable(context, Constants.ASSETS_RES_PATH + list.get(position).getIconPath()));
	
		return convertView;
	}
	
	class Column extends LinearLayout {
		
		public ImageView icon;
		public TextView name;
		
		public Column(Context context, int position) {
			super(context);
			this.setOrientation(LinearLayout.VERTICAL);
			this.setGravity(Gravity.CENTER_HORIZONTAL);
			LinearLayout layoutIcon = new LinearLayout(context);
			layoutIcon.setGravity(Gravity.CENTER);
			layoutIcon.setBackgroundDrawable(null);
			icon = new ImageView(context);
			layoutIcon.addView(icon,-2,-2);
			
			this.addView(layoutIcon, -2, -2);
			name = new TextView(context);
			name.setTextColor(0xff58687b);
			LayoutParams params = new LayoutParams(-2, -2);
			params.topMargin = DimensionUtil.dip2px(context,5);
			params.bottomMargin = DimensionUtil.dip2px(context,10);
			this.addView(name,params);
			
			if(position == 2){
			}
			
//			
		}
		
	}

}
