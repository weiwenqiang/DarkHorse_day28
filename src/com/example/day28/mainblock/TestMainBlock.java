package com.example.day28.mainblock;

import com.example.day28.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

public class TestMainBlock extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b2_mainblock);
		findViewById(R.id.b2_mainblock).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b2_mainblock:
			try {
				Thread.sleep(7500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Toast.makeText(TestMainBlock.this, "按钮被按下了", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
