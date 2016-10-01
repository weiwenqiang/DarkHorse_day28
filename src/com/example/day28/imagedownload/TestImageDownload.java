package com.example.day28.imagedownload;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.day28.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class TestImageDownload extends Activity implements OnClickListener {
	static ImageView image;
	static TestImageDownload myActivity;
	
	static Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			// 区分消息
			switch (msg.what) {
			case 0:
				
				image.setImageBitmap((Bitmap) msg.obj);
				break;
			case 1:
				Toast.makeText(myActivity, "请求失败",
						Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.b1_imagedownload);
		findViewById(R.id.b1_download).setOnClickListener(this);
		image = (ImageView) findViewById(R.id.b1_image);
		myActivity = TestImageDownload.this;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b1_download: {
			Thread t = new Thread() {

				@Override
				public void run() {
					// 下载图片
					// 1.确定地址
					String path = "http://10.0.2.2:8080/tomcat.png";

					try {
						// 2.把网址封装成URL对象
						URL url = new URL(path);
						// 3.获取客户端和服务器连接对象，此时还没有建立连接
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						// 4.对象初始化，设置请求方式，注意要大写
						conn.setRequestMethod("GET");
						// 设置连接超时
						conn.setConnectTimeout(5000);
						// 设置读取超时
						conn.setReadTimeout(5000);
						// 5.发送请求，与服务器建立连接
						conn.connect();
						// 如果响应码为200说明请求成功
						if (conn.getResponseCode() == 200) {
							// 获取服务器响应头中的流，流里的数据就是客户端请求的数据
							InputStream is = conn.getInputStream();
							// 读取出流里的数据，并构造成位图对象
							Bitmap bm = BitmapFactory.decodeStream(is);

							// ImageView image = (ImageView)
							// findViewById(R.id.b1_image);
							// // 显示位图
							// image.setImageBitmap(bm);

							Message msg = new Message();
							msg.what = 0;
							// 消息对象可以携带数据
							msg.obj = bm;
							// 把消息发送至主线程的消息队列
							handler.sendMessage(msg);
						} else {
							Message msg = handler.obtainMessage();
							msg.what = 1;
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
		}
		default:
			break;
		}
	}

}
