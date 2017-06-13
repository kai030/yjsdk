package com.yj.util;
/*package com.youai.util;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.youai.entity.ChargeData;
import com.youai.entity.PayChannel;
import com.youai.sdkc.PaymentActivity;
import com.youai.sdkc.YouaiAppService;
import com.youai.ui.ChargePaymentListLayout;

public class PayListTask extends AsyncTask<Void, Void, PayChannel[]> {
	private ChargeData charge;
	private Context context;
	private ChargePaymentListLayout mPaymentListLayout;
	private Dialog dialog;

	public PayListTask(ChargeData charge,Dialog dialog,Context context) {
		if (charge != null) {
			this.dialog = dialog;
			this.context = context;
			this.charge = charge;
		} else {
			Utils.toastInfo(this.context, "��ȡ�б��ʼ��ʧ��");
			this.charge = new ChargeData();
		}
	}

	@Override
	protected PayChannel[] doInBackground(Void... params) {

		Utils.youaiLog("��ȡ�б�");
		return GetDataImpl.getInstance(this.context).getPaymentList(
				charge);
	}

	@Override
	protected void onPostExecute(PayChannel[] result) {

		if (isCancelDialog) {
			hideDialog();
			finish();
			return;
		}
		if (result != null && result.length != 0
				&& YouaiAppService.mPayChannels != null
				&& YouaiAppService.mPayChannels.length > 0) {
			Utils.youaiLog("��ȡ�б�ɹ�!");
			noPayChannel = true;
			hideDialog();
			init();
			if (mPaymentListLayout != null) {
				mPaymentListLayout
						.setChannelMessages(YouaiAppService.mPayChannels);
				mPaymentListLayout.showPayList(View.VISIBLE);
			}
		} else {
			noPayChannel = false;
			hideDialog();
			init();
			if (mPaymentListLayout != null) {
				mPaymentListLayout.showPayList(View.GONE);
			}
		}
	}
}
*/