package com.yj.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.entity.NewsBean;
import com.yj.ui.MResource;

import java.util.List;


public class MessageAdapter extends BaseAdapter {

    private List<NewsBean> mMessageBeanList;
    private Context mContext;

    public MessageAdapter(Context context, List<NewsBean> messageBeanList){
        mContext = context;
        mMessageBeanList = messageBeanList;
    }

    @Override
    public int getCount() {
        return mMessageBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(MResource.getIdByName(mContext, Constants.LAYOUT,"message_list_item"),null);
            viewHolder = new ViewHolder();
            viewHolder.readFlagIv = (ImageView) convertView.findViewById
                    (MResource.getIdByName(mContext, Constants.ID,"message_flag_image"));
            viewHolder.titleTv = (TextView) convertView.findViewById
                    (MResource.getIdByName(mContext, Constants.ID,"message_title"));

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            if(mMessageBeanList.get(position).getBrowse() == 0){
                viewHolder.readFlagIv.setImageResource(MResource.getIdByName(mContext,Constants.DRAWABLE,"pic_guangdian_sel"));
            }else {
                viewHolder.readFlagIv.setImageResource(MResource.getIdByName(mContext,Constants.DRAWABLE,"pic_guangdian"));
            }

        if(!TextUtils.isEmpty(mMessageBeanList.get(position).getTitle())){
            viewHolder.titleTv.setText(mMessageBeanList.get(position).getTitle());
        }

        return convertView;
    }

    /*存放控件 的ViewHolder*/
    public final class ViewHolder {
        public ImageView readFlagIv;
        public TextView titleTv;
    }
}
