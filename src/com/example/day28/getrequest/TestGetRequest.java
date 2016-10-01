package com.example.day28.getrequest;

import java.io.IOException;
import java.io.InputStream;
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

public class TestGetRequest extends Activity implements OnClickListener {
	EditText ed_name, ed_password;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Toast.makeText(TestGetRequest.this, (String)msg.obj, 0).show();
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
					
					String path = "http://10.0.2.2:8080/AndroidRequest/servlet/LoginRequest?name="+URLEncoder.encode(name)+"&password="+password;
					try {
						URL url = new URL(path);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(8000);
						conn.setReadTimeout(8000);
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
