package com.deshang.ttjx.ui.tab2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyNewBean;

import java.util.List;

/**
 * Created by L on 2018/9/27.
 */

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private List<MakeMoneyNewBean.DataBeanX.DataBean> data;

    public TaskAdapter(Context context, List<MakeMoneyNewBean.DataBeanX.DataBean> data) {
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(data.get(position).getTitle());
        holder.content.setText(data.get(position).getContent());
        switch (data.get(position).getState()) {
            case 0:
                holder.state.setImageDrawable(context.getResources().getDrawable(R.mipmap.task_to_finish));
                break;

            case 1:
                holder.state.setImageDrawable(context.getResources().getDrawable(R.mipmap.task_receive));
                break;

            case 2:
                holder.state.setImageDrawable(context.getResources().getDrawable(R.mipmap.task_finish));
                break;
        }
        holder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(position).getState() == 0) {
                    if (finishListener != null){
                        finishListener.onToFinishClick(data.get(position).getTitle());
                    }
                } else if (data.get(position).getState() == 1) {
                    if (listener != null) {
                        listener.onClick(data.get(position).getTaskurl());
                    }
                } else {
                    ReceiveGoldToast.makeToast(context, "该任务已完成~").show();
                }
            }
        });
        return convertView;
    }

    private OnClickToFinishListener finishListener;

    public void setOnClickToFinishListener(OnClickToFinishListener listener) {
        this.finishListener = listener;
    }

    public interface OnClickToFinishListener {
        void onToFinishClick(String title);
    }

    private OnReceiveClickListener listener;

    public void setOnReceiveClickListener(OnReceiveClickListener listener) {
        this.listener = listener;
    }

    public interface OnReceiveClickListener {
        void onClick(String url);
    }

    private class ViewHolder {
        private TextView title, content;
        private ImageView state;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            state = (ImageView) view.findViewById(R.id.state);
        }
    }

}
