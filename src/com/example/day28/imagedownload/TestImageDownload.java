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
			// ������Ϣ
			switch (msg.what) {
			case 0:
				
				image.setImageBitmap((Bitmap) msg.obj);
				break;
			case 1:
				Toast.makeText(myActivity, "����ʧ��",
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
					// ����ͼƬ
					// 1.ȷ����ַ
					String path = "http://10.0.2.2:8080/tomcat.png";

					try {
						// 2.����ַ��װ��URL����
						URL url = new URL(path);
						// 3.��ȡ�ͻ��˺ͷ��������Ӷ��󣬴�ʱ��û�н�������
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						// 4.�����ʼ������������ʽ��ע��Ҫ��д
						conn.setRequestMethod("GET");
						// �������ӳ�ʱ
						conn.setConnectTimeout(5000);
						// ���ö�ȡ��ʱ
						conn.setReadTimeout(5000);
						// 5.�����������������������
						conn.connect();
						// �����Ӧ��Ϊ200˵������ɹ�
						if (conn.getResponseCode() == 200) {
							// ��ȡ��������Ӧͷ�е�������������ݾ��ǿͻ������������
							InputStream is = conn.getInputStream();
							// ��ȡ����������ݣ��������λͼ����
							Bitmap bm = BitmapFactory.decodeStream(is);

							// ImageView image = (ImageView)
							// findViewById(R.id.b1_image);
							// // ��ʾλͼ
							// image.setImageBitmap(bm);

							Message msg = new Message();
							msg.what = 0;
							// ��Ϣ�������Я������
							msg.obj = bm;
							// ����Ϣ���������̵߳���Ϣ����
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
