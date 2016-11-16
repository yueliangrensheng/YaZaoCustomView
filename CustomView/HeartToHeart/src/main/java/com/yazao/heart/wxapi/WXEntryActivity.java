package com.yazao.heart.wxapi;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	private IWXAPI iwxapi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		iwxapi = WXAPIFactory.createWXAPI(this,"wx647ad3b38c09adc6");
		iwxapi.handleIntent(getIntent(),this);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

	@Override
	public void onResp(BaseResp baseResp) {
		switch (baseResp.errCode){
			case BaseResp.ErrCode.ERR_OK:
				//分享成功
				Toast.makeText(this,"分享成功",Toast.LENGTH_SHORT).show();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				//分享取消
				Toast.makeText(this,"分享取消",Toast.LENGTH_SHORT).show();

				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				//分享拒绝
				Toast.makeText(this,"分享拒绝",Toast.LENGTH_SHORT).show();

				break;
		}
	}
}