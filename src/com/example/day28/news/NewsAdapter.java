package com.example.day28.news;

import java.util.List;

import com.example.day28.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	List<News> newsList;

	public NewsAdapter(Context context, List<News> newsList) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.newsList = newsList;
	}

	@Override
	public int getCount() {
		return newsList.size();
	}

	@Override
	public Object getItem(int position) {
		return newsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		News news = newsList.get(position);
		View v = null;
		ViewHolder mHolder;
		if (convertView == null) {
			v = View.inflate(context, R.layout.news_item, null);
			mHolder = new ViewHolder();
			mHolder.tv_title = (TextView) v.findViewById(R.id.newsitem_title);
			mHolder.tv_detail = (TextView) v.findViewById(R.id.newsitem_detail);
			mHolder.tv_comment = (TextView) v
					.findViewById(R.id.newsitem_comment);
			mHolder.siv = (SmartImageView) v.findViewById(R.id.newsitem_image);
			v.setTag(mHolder);
		} else {
			v = convertView;
			mHolder = (ViewHolder) v.getTag();
		}
		mHolder.tv_title.setText(news.getTitle());
		mHolder.tv_detail.setText(news.getDetail());
		mHolder.tv_comment.setText(news.getComment() + "条评论");
		// 智能加载图片
		mHolder.siv.setImageUrl(news.getImageUrl());
		return v;
	}

	class ViewHolder {
		// item条目的布局文件中有什么组件，就定义什么属性
		TextView tv_title;
		TextView tv_detail;
		TextView tv_comment;
		SmartImageView siv;
	}
}
