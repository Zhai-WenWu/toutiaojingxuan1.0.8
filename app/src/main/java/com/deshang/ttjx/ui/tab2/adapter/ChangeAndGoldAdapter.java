package com.deshang.ttjx.ui.tab2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab2.bean.ChangeOrGoldBean;

import java.util.List;

/**
 * 我的钱包/零钱或金币列表 适配器
 * Created by L on 2018/6/5.
 */

public class ChangeAndGoldAdapter extends RecyclerView.Adapter<ChangeAndGoldAdapter.Holder> {

    private Context context;
    private List<ChangeOrGoldBean> data;
    private int type = 0;

    public ChangeAndGoldAdapter(Context context, List<ChangeOrGoldBean> data) {
        this.context = context;
        this.data = data;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public ChangeAndGoldAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_account, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(ChangeAndGoldAdapter.Holder holder, final int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.time.setText(data.get(position).getCreate_time());
        if (data.get(position).getGold().contains("+")) {
            holder.goldNum.setTextColor(context.getResources().getColor(R.color.pink_color));
        } else {
            holder.goldNum.setTextColor(context.getResources().getColor(R.color.green_tab4));
        }
        if (type == 0) {
            holder.goldNum.setText(data.get(position).getGold() + "元");
        } else {
            holder.goldNum.setText(data.get(position).getGold() + "金币");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        private TextView goldNum, title, time;

        public Holder(View itemView) {
            super(itemView);
            goldNum = (TextView) itemView.findViewById(R.id.gold_num);
            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

}
