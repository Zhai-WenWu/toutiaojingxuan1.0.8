package com.deshang.ttjx.framework.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected Context ct;
	private List<T> itemList;
	public int count = 0;

	public Context getContext() {
		return ct;
	}

	public MyBaseAdapter(Context context) {
		this.ct = context;
	}

	public List<T> getItemList() {
		return itemList;
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}

	public void addItemList(List<T> itemList) {
		if (itemList != null) {
			this.itemList.addAll(itemList);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View myView = getMyView(position, convertView, parent);
		/*if (convertView == null) {
			SoftApplication.softApplication.changeFontsThin((ViewGroup) myView, ct);
		}*/
		return myView;
	}

	public View getMyView(int position, View convertView, ViewGroup parent) {
		return null;
	}

	@Override
	public int getCount() {
		if (itemList != null && itemList.size() > 0)
			return itemList.size();
		else
			return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public T getItem(int position) {
		if(itemList==null){
			return null ;
		}
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
