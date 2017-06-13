package com.yj.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.entity.GiftBean;
import com.yj.entity.GiftListBean;
import com.yj.sdk.YJGetGiftActivity;
import com.yj.ui.MResource;
import com.yj.util.GetGiftTask;
import com.yj.util.NewsListTask;
import com.yj.util.ToastUtils;

import java.util.List;


public class GiftAdapter extends BaseAdapter {

    private List<GiftBean> mGiftListBeanList;
    private Context mContext;

    public GiftAdapter(Context context,List<GiftBean> giftListBeanList){
        mContext = context;
        mGiftListBeanList = giftListBeanList;
    }

    @Override
    public int getCount() {
        return mGiftListBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGiftListBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(MResource.getIdByName(mContext, Constants.LAYOUT,"gift_list_item"),null);
            viewHolder = new ViewHolder();
            viewHolder.nameTv = (TextView) convertView.findViewById
                    (MResource.getIdByName(mContext, Constants.ID,"gift_name"));
            viewHolder.desTv = (TextView) convertView.findViewById
                    (MResource.getIdByName(mContext, Constants.ID,"gift_des"));
            viewHolder.getTv = (TextView) convertView.findViewById
                    (MResource.getIdByName(mContext, Constants.ID,"gift_get"));
            viewHolder.bottpmView = convertView.findViewById(MResource.getIdByName(mContext, Constants.ID,"gift_list_bottom_view"));
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(!TextUtils.isEmpty(mGiftListBeanList.get(position).getTitle())){
            viewHolder.nameTv.setText(mGiftListBeanList.get(position).getTitle());
        }
        if(!TextUtils.isEmpty(mGiftListBeanList.get(position).getContent())){
            viewHolder.desTv.setText(mGiftListBeanList.get(position).getContent());
        }

        viewHolder.getTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              new GetGiftTask(mContext).execute();
            }
        });

        return convertView;
    }

    /*存放控件 的ViewHolder*/
    public final class ViewHolder {
        public TextView nameTv;
        public TextView desTv;
        public TextView getTv;
        public View bottpmView;
    }
}
