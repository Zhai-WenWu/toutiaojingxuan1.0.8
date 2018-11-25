package com.deshang.ttjx.ui.tab1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab1.bean.MessageBean;

import java.util.List;

/**
 * 消息列表Adapter
 * Created by L on 2018/6/12.
 */

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<MessageBean.ReturnBean> data;

    public MessageAdapter(Context context, List<MessageBean.ReturnBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_message, null);
            holder = new ViewHolder();
            holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.newMessage = (TextView) convertView.findViewById(R.id.image_new_message);
            holder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(data.get(position).getTitle());
        holder.content.setText(data.get(position).getContent());
        holder.time.setText(data.get(position).getCreate_time());

        if (data.get(position).getIs_new() == 0) {
            holder.newMessage.setVisibility(View.VISIBLE);
        } else {
            holder.newMessage.setVisibility(View.GONE);
        }

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteClick(position, data.get(position).getId());
                }
            }
        });

        return convertView;
    }

    private OnDeleteClickListener listener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.listener = listener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position, int id);
    }

    class ViewHolder {
        private LinearLayout ll_item;
        private TextView title, content;
        private TextView time;
        private TextView newMessage;
        private TextView tv_delete;
    }
}
