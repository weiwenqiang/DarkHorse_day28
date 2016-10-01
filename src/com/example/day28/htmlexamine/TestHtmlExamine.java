package com.example.day28.htmlexamine;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.day28.R;
import com.example.util.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class TestHtmlExamine extends Activity implements OnClickListener {
	static TextView html;
	static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			html.setText((String) msg.obj);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b5_htmlexamine);
		html = (TextView) findViewById(R.id.b5_html);
		findViewById(R.id.b5_examine).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b5_examine:
			Thread t = new Thread() {
				@Override
				public void run() {
					super.run();
					String path = "http://10.0.2.2:8080/test/baidu_gbk.htm";
					try {
						URL url = new URL(path);
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(8000);
						conn.setReadTimeout(15000);
						if (conn.getResponseCode() == 200) {
							// 拿到服务器返回的输入流，流里的数据就是html的源文件
							InputStream is = conn.getInputStream();
							// 从流里把文本数据读出来
							String text = Utils.getTextFromStream(is);

							// 发送消息，让主线程刷新UI，显示源文件
							Message msg = handler.obtainMessage();
							msg.obj = text;
							handler.sendMessage(msg);
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
			break;
		default:
			break;
		}
	}
}
