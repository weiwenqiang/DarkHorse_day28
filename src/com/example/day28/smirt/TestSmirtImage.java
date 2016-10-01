package com.example.day28.smirt;

import com.example.day28.R;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class TestSmirtImage extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b4_smartimage);
		findViewById(R.id.b4_download).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b4_download:
			//1.确定网址
			final String path = "http://10.0.2.2:8080/tomcat.png";
			//2.找到智能图片查看器对象
			SmartImageView siv = (SmartImageView) findViewById(R.id.b4_image);
			//3.下载并显示图片
			siv.setImageUrl(path);
			break;
		default:
			break;
		}
	}

}
