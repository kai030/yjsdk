package com.yj.ui;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.yj.entity.Constants;


/**
 * @author lufengkai
 */
public class TisLayout extends ParentLayout implements OnClickListener {

   private TextView tisContnet;
    private TextView tisConfirm;

    public TisLayout(Activity context,String content) {
        super(context,MResource.getIdByName(context, Constants.LAYOUT,"tis_layout"));
        initUi(content);
    }

    private void initUi(String content){
        tisContnet = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID,"tis_content"));
        if(!TextUtils.isEmpty(content)) {
            tisContnet.setText(content);
        }
        tisConfirm = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID,"tis_confirm"));
        tisConfirm.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int i = v.getId();
        if (i == MResource.getIdByName(context, Constants.ID,"tis_confirm")) {
            ((Activity) context).finish();

        } else {
        }
    }




}
