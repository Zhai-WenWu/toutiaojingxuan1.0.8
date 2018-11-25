package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalBean;

import java.util.List;

/**
 * Created by L on 2018/6/8.
 */

public class WithdrawalListAdapter extends RecyclerView.Adapter<WithdrawalListAdapter.VH> {

    private Context context;
    private List<WithdrawalBean> data;

    public WithdrawalListAdapter(Context context, List<WithdrawalBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_withdrawal, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {

        holder.dollarNum.setText(data.get(position).getDollarNum() + "元");
//        holder.tv_need.setText("需" + data.get(position).getContent() + "金币");

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSelect(position);
                }
            }
        });

        if (data.get(position).isSelector()) {
            holder.image.setImageResource(R.drawable.withdrawal_selector);
        } else {
            holder.image.setImageResource(R.drawable.withdrawal_no_selector);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView dollarNum;
        ImageView image;
        RelativeLayout item;
        TextView tv_need;

        public VH(View itemView) {
            super(itemView);
            dollarNum = (TextView) itemView.findViewById(R.id.dollar_num);
            image = (ImageView) itemView.findViewById(R.id.image);
            item = (RelativeLayout) itemView.findViewById(R.id.item);
            tv_need = (TextView) itemView.findViewById(R.id.tv_need);
        }
    }

    private OnSelectListener listener;

    public interface OnSelectListener {
        void onSelect(int position);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

}
