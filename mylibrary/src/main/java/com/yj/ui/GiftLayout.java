package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.yj.entity.GiftBean;
import com.yj.entity.GiftListBean;
import com.yj.ui.adapter.GiftAdapter;

import java.util.List;

/**
 * Created by lufengkai on 2017/6/11.
 */

public class GiftLayout extends FloatListLayout{

    private Context mContext;

    public GiftLayout(Activity context) {
        super(context);
        mContext = context;
    }

    public void initUi(List<GiftBean> giftList){
        GiftAdapter giftAdapter = new GiftAdapter(mContext,giftList);
        mListView.setAdapter(giftAdapter);
    }


    @Override
    protected String getTitle() {
        return "礼包中心";
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v);
    }
}
