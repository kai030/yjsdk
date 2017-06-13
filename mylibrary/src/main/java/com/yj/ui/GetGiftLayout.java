package com.yj.ui;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.util.ToastUtils;


/**
 * @author lufengkai
 */
public class GetGiftLayout extends ParentLayout implements OnClickListener {

    private TextView tisContnet;
    private TextView tisConfirm;
    private String content;

    public GetGiftLayout(Activity context, String content) {
        super(context, MResource.getIdByName(context, Constants.LAYOUT, "get_gift_layout"));
        this.content = content;
        initUi();
    }

    private void initUi() {
        tisContnet = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID, "gift_tv"));
        if (!TextUtils.isEmpty(content)) {
            tisContnet.setText(content);
        }
        tisConfirm = (TextView) view.findViewById(MResource.getIdByName(context, Constants.ID, "gift_copy"));
        tisConfirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int i = v.getId();
        if (i == MResource.getIdByName(context, Constants.ID, "gift_copy")) {
            // 从API11开始android推荐使用android.content.ClipboardManager
            // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(content);
            ((Activity) context).finish();
            ToastUtils.toastShow(context, "复制成功");

        } else {
        }
    }


}
