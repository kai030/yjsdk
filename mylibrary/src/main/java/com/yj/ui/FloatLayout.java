package com.yj.ui;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yj.entity.Constants;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public class FloatLayout extends ParentLayout implements OnClickListener {

    private OnClickListener clickListener;

    public void setClickListener(OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    public FloatLayout(Activity context) {
        super(context, MResource.getIdByName(context, Constants.LAYOUT,"float_layout"));
        initUi();
    }

    private void initUi(){

        TextView tvUserCenter = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID,"float_user_center_tv"));
        tvUserCenter.setOnClickListener(this);

        TextView tvCustomerService = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID,"float_customer_service_tv"));
        tvCustomerService.setOnClickListener(this);

        TextView tvGift = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID,"float_gift_tv"));
        tvGift.setOnClickListener(this);

        TextView tvMessage = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID,"float_message_tv"));
        tvMessage.setOnClickListener(this);


        ImageView ivClose= (ImageView) view.findViewById(MResource.getIdByName(context, Constants.ID,"view_back"));
        ivClose.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        this.clickListener.onClick(v);
    }



}
