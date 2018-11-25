package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;

import java.util.List;

/**
 * Created by L on 2018/6/8.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.VH> {

    private Context context;
    private List<AccountInfoBean.ReturnBean> data;

    public AccountAdapter(Context context, List<AccountInfoBean.ReturnBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_account, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.time.setText(data.get(position).getCreate_time());
        if (data.get(position).getType() == 8 || data.get(position).getType() == 13) {
            holder.goldNum.setTextColor(context.getResources().getColor(R.color.green_tab4));
        } else {
            holder.goldNum.setTextColor(context.getResources().getColor(R.color.pink_color));
        }
        holder.goldNum.setText(data.get(position).getGold());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private TextView goldNum, title, time;

        public VH(View itemView) {
            super(itemView);
            goldNum = (TextView) itemView.findViewById(R.id.gold_num);
            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

}
