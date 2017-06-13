package com.yj.ui;

import android.content.Context;
import android.widget.GridView;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
	class NumGridView extends GridView {

		public NumGridView(Context context) {
			super(context);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			int expandSpec = MeasureSpec.makeMeasureSpec(
                            Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);

		}
	}
