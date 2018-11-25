package com.deshang.ttjx.ui.tab1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.imageload.GlideLoading;

import java.util.List;

/**
 * Created by L on 2018/7/3.
 */

public class GridImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    public GridImageAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_image, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.ad = (TextView) convertView.findViewById(R.id.ad);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (type == 1 && position == 2) {
            holder.ad.setVisibility(View.VISIBLE);
        } else {
            holder.ad.setVisibility(View.GONE);
        }
        GlideLoading.getInstance().loadImgUrlNyImgLoader(context, data.get(position), holder.imageView);

        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
        private TextView ad;
    }
}
