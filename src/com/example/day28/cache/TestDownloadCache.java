package com.example.day28.cache;

import java.io.File;
import java.io.FileOutputStream;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TestDownloadCache extends Activity implements OnClickListener {
	static ImageView image;
	static TestDownloadCache myActivity;
	static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
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
		myActivity = TestDownloadCache.this;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b1_download: {
			final String path = "http://10.0.2.2:8080/tomcat.png";
			final File file = new File(getCacheDir(),getFileName(path));
			//�жϣ��������Ƿ��и��ļ�
			if(file.exists()){
				//���������ڣ��ӻ����ȡ
				Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
				image.setImageBitmap(bm);
				Log.d("TestDownloadCache", "�ӻ����ȡ��");
			}else{
				//������治���ڣ�����������
				Log.d("TestDownloadCache", "���������ص�");
				Thread t = new Thread() {
					@Override
					public void run() {
						
						try {
							URL url = new URL(path);
							HttpURLConnection conn = (HttpURLConnection) url
									.openConnection();
							conn.setRequestMethod("GET");
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							conn.connect();
							if (conn.getResponseCode() == 200) {
								InputStream is = conn.getInputStream();
								//��ȡ������������������ݣ�������д�������ļ�����������
								
								FileOutputStream fos = new FileOutputStream(file);
								byte[] b = new byte[1024];
								int len = 0;
								while((len = is.read(b))!= -1){
									fos.write(b, 0, len);
								}
								fos.close();
								
								//�����Ѿ�û��������
//								Bitmap bm = BitmapFactory.decodeStream(is);
								Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
								
								Message msg = new Message();
								msg.what = 0;
								msg.obj = bm;
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
				
			}
			
			
			break;
		}
		default:
			break;
		}
	}
	
	public String getFileName(String path){
		int index =path.lastIndexOf("/");
		return path.substring(index+1);
	}
}
