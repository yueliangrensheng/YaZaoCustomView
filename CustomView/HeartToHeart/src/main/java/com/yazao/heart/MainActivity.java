package com.yazao.heart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MainActivity extends Activity implements View.OnClickListener {

	private ImageView imageView;
	private Button share;
	private EditText content;


	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_main);

		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, "wx647ad3b38c09adc6");
		// 将该app注册到微信
		api.registerApp("wx647ad3b38c09adc6");

		imageView = (ImageView) findViewById(R.id.image);
		share = (Button) findViewById(R.id.share);
		content = (EditText) findViewById(R.id.content);

		imageView.setOnClickListener(this);
		share.setOnClickListener(this);

		//设置输入的字体的样式
		content.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.image: //选择图片
				choseLocalImage();
				break;
			case R.id.share: //分享到朋友圈
				sendShare();
				share.setVisibility(View.VISIBLE);
				break;

		}
	}

	/**
	 * 分享到朋友圈
	 */
	private void sendShare() {
		if (content != null && TextUtils.isEmpty(content.getText().toString().trim())) {
			Toast.makeText(this, "既然来了就留下一句心愿吧", Toast.LENGTH_SHORT).show();
			return;
		}
		Bitmap bitmap =generateCard();

		WXImageObject imageObject = new WXImageObject(bitmap);
		WXMediaMessage msg =new WXMediaMessage();
		msg.mediaObject = imageObject;

//		WXWebpageObject webpageObject = new WXWebpageObject();
//		WXMediaMessage msg = new WXMediaMessage(webpageObject);
//		msg.mediaObject = new WXImageObject(bitmap);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);

	}

	private Bitmap generateCard() {
		share.setVisibility(View.INVISIBLE);
		View view = getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		return view.getDrawingCache();
	}

	/**
	 * 选择本地的图片
	 */
	private void choseLocalImage() {
		//两种方式实现 图片的浏览
		//1.调用手机自身图像浏览工具
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, 101);

		//2.调用手机安装的图像浏览工具
//		Intent intent2 =new Intent();
//		intent2.setType("image/*");
//		intent2.setAction(Intent.ACTION_GET_CONTENT);
//		startActivityForResult(intent2,101);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 101 && resultCode == RESULT_OK) {
			if (data != null) {
				Uri uri = data.getData();
				imageView.setImageURI(uri);
			}
		}
	}
}
