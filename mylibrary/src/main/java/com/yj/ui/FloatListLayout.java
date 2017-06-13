package com.yj.ui;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.util.ToastUtils;
import com.yj.util.Utils;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public abstract class FloatListLayout extends ParentLayout implements OnClickListener{

    protected ListView mListView;

    protected OnClickListener clickListener;

    public void setClickListener(OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    public FloatListLayout(Activity context) {
        super(context, MResource.getIdByName(context, Constants.LAYOUT, "float_list_layout"));
        initUi();
    }

    private void initUi() {

        View viewBack = view.findViewById(MResource.getIdByName(context, Constants.ID, "view_back"));
        viewBack.setOnClickListener(this);

        TextView titleTv = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID, "title"));
        Utils.setBold(titleTv);
        titleTv.setText(getTitle());

        mListView = (ListView) view.findViewById(MResource.getIdByName(context, Constants.ID, "float_list"));
//        ListView.LayoutParams params = (ListView.LayoutParams) mListView.getLayoutParams();
//        params.height =
    }

    protected abstract String getTitle();



}
