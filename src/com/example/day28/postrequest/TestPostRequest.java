package com.example.day28.postrequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.example.day28.R;
import com.example.util.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class TestPostRequest extends Activity implements OnClickListener {
	EditText ed_name, ed_password;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Toast.makeText(TestPostRequest.this, (String)msg.obj, 0).show();
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b7_getrequest);
		findViewById(R.id.b7_login).setOnClickListener(this);
		ed_name = (EditText) findViewById(R.id.b7_name);
		ed_password = (EditText) findViewById(R.id.b7_password);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b7_login:
			final String name = ed_name.getText().toString();
			final String password = ed_password.getText().toString();
			Thread t = new Thread(){
				@Override
				public void run() {
					super.run();
					//提交的数据需要经过url编码，英文和数字编码后不变
					
					String path = "http://10.0.2.2:8080/AndroidRequest/servlet/LoginRequest";
					try {
						URL url = new URL(path);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.setConnectTimeout(8000);
						conn.setReadTimeout(8000);
						
						//拼接出要提交的数据的字符串
						String data = "name="+URLEncoder.encode(name)+"&password="+password;
						//添加POST请求的两行属性
						conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
						conn.setRequestProperty("Content-Length", data.length()+"");
						//打开输出流
						conn.setDoOutput(true);
						//拿到输出流
						OutputStream os = conn.getOutputStream();
						//使用输出流往服务器提交数据，没有网络交互
						os.write(data.getBytes());
						//建立连接，开始网络交互
						if(conn.getResponseCode()==200){
							InputStream is = conn.getInputStream();
							
							String text = Utils.getTextFromStream(is);
							
							Message msg = handler.obtainMessage();
							msg.obj =text;
							msg.what=1;
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
