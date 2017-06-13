package com.yj.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.util.ToastUtils;
import com.yj.util.Utils;


/**
 * @author lufengkai
 */
public class CustomerServicesLayout extends ParentLayout implements OnClickListener {

    private TextView qq;
    private String content;

    private OnClickListener clickListener;

    public void setClickListener(OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    public CustomerServicesLayout(Activity context, String content) {
        super(context, MResource.getIdByName(context, Constants.LAYOUT, "customer_services_layout"));
        this.content = content;
        initUi();
    }

    private void initUi() {
        TextView title = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID, "title"));
        Utils.setBold(title);
        qq = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID, "qq"));
        if (!TextUtils.isEmpty(content)) {
            qq.setText(content);
        }
        qq.setOnClickListener(this);

        View viewBack = view.findViewById(MResource.getIdByName(context, Constants.ID, "view_back"));
        viewBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        clickListener.onClick(v);
        int i = v.getId();
        if (i == MResource.getIdByName(context, Constants.ID, "qq")) {
          try {
              Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
              context.startActivity(intent);
          }catch (Exception e){
              ToastUtils.toastShow(context,"请检查是否安装QQ");
          }
        } else {
        }
    }


}
