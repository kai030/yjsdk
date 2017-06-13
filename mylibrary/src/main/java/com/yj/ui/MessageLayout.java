package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.yj.entity.NewsBean;
import com.yj.ui.adapter.MessageAdapter;
import com.yj.util.Utils;

import java.util.List;

/**
 * Created by lufengkai on 2017/6/11.
 */

public class MessageLayout extends FloatListLayout{

   private Context mContext;

    public MessageLayout(Activity context) {
        super(context);
        mContext = context;
    }

    public void initUi(final List<NewsBean> newsBeanList){
        MessageAdapter messageAdapter = new MessageAdapter(mContext,newsBeanList);
        mListView.setAdapter(messageAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.opneWebvieAvtivity(context,newsBeanList.get(position).getTitle(),newsBeanList.get(position).getUrl());
            }
        });
    }


    @Override
    protected String getTitle() {
        return "平台消息";
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v);
    }
}
