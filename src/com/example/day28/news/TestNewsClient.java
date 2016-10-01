package com.example.day28.news;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.day28.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.Window;
import android.widget.ListView;

public class TestNewsClient extends Activity {
	List<News> newsList;
	ListView listView;
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				listView.setAdapter(new NewsAdapter(TestNewsClient.this, newsList));
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
		setContentView(R.layout.b6_newslistview);

		getNewsInfo();
		listView = (ListView) findViewById(R.id.b6_newslist);
		//因为解析XML在子线程执行，主线程会不等子线程执行完毕继续执行，所以这里会报空指针
//		listView.setAdapter(new NewsAdapter());
	}

	private void getNewsInfo() {
		Thread t = new Thread() {

			@Override
			public void run() {
				super.run();
				try {
					String path = "http://10.0.2.2:8080/test/news.xml";
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(8000);
					conn.setReadTimeout(15000);
					if (conn.getResponseCode() == 200) {
						InputStream is = conn.getInputStream();
						// 使用pull解析器，解析这个流
						parseNewsXml(is);
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

	private void parseNewsXml(InputStream is) {
		XmlPullParser xp = Xml.newPullParser();

		try {
			xp.setInput(is, "utf-8");
			// 对节点的事件类型进行判断，就可以知道当前节点是什么节点
			int type = xp.getEventType();
			News news = null;
			while (type != XmlPullParser.END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_TAG:
					if("newslist".equals(xp.getName())){
						newsList = new ArrayList<News>();
					}else if("news".equals(xp.getName())){
						news = new News();
					}else if("title".equals(xp.getName())){
						String title = xp.nextText();
						news.setTitle(title);
					}else if("detail".equals(xp.getName())){
						String detail = xp.nextText();
						news.setDetail(detail);
					}else if("comment".equals(xp.getName())){
						String comment = xp.nextText();
						news.setComment(comment);
					}else if("image".equals(xp.getName())){
						String image = xp.nextText();
						news.setImageUrl(image);
					}
					break;
				case XmlPullParser.END_TAG:
					if("news".equals(xp.getName())){
						newsList.add(news);
					}
					break;
				default:
					break;
				}
				//解析完当前节点，把指针移动至下一个节点，并返回它的事件类型
				type = xp.next();
			}
			//要保证在设置适配器时，新闻XML已经解析完毕了，给主线程发消息
			//因为要发送的数据是全局变量，所以不需要把数据携带过去，就用这个发送空消息
			handler.sendEmptyMessage(1);
			
			for (News n : newsList) {
				Log.d("TestNewsClient", n.toString());
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
