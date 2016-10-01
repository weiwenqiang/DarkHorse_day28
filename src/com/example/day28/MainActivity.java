package com.example.day28;

import com.example.day28.cache.TestDownloadCache;
import com.example.day28.getrequest.TestGetRequest;
import com.example.day28.htmlexamine.TestHtmlExamine;
import com.example.day28.imagedownload.TestImageDownload;
import com.example.day28.mainblock.TestMainBlock;
import com.example.day28.news.TestNewsClient;
import com.example.day28.postrequest.TestPostRequest;
import com.example.day28.smirt.TestSmirtImage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		findViewById(R.id.day28_b1).setOnClickListener(this);
		findViewById(R.id.day28_b2).setOnClickListener(this);
		findViewById(R.id.day28_b3).setOnClickListener(this);
		findViewById(R.id.day28_b4).setOnClickListener(this);
		findViewById(R.id.day28_b5).setOnClickListener(this);
		findViewById(R.id.day28_b6).setOnClickListener(this);
		findViewById(R.id.day28_b7).setOnClickListener(this);
		findViewById(R.id.day28_b8).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.day28_b1:
			startActivity(new Intent(MainActivity.this, TestImageDownload.class));
			break;
		case R.id.day28_b2:
			startActivity(new Intent(MainActivity.this, TestMainBlock.class));
			break;
		case R.id.day28_b3:
			startActivity(new Intent(MainActivity.this, TestDownloadCache.class));
			break;
		case R.id.day28_b4:
			startActivity(new Intent(MainActivity.this, TestSmirtImage.class));
			break;
		case R.id.day28_b5:
			startActivity(new Intent(MainActivity.this, TestHtmlExamine.class));
			break;
		case R.id.day28_b6:
			startActivity(new Intent(MainActivity.this, TestNewsClient.class));
			break;
		case R.id.day28_b7:
			startActivity(new Intent(MainActivity.this, TestGetRequest.class));
			break;
		case R.id.day28_b8:
			startActivity(new Intent(MainActivity.this, TestPostRequest.class));
			break;
		default:
			break;
		}
	}
}
