package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalAuditBean;

import java.util.List;

/**
 * Created by L on 2018/6/15.
 */

public class WithdrawalAuditAdapter extends RecyclerView.Adapter<WithdrawalAuditAdapter.VH> {

    private Context context;
    private List<WithdrawalAuditBean.ReturnBean> data;

    public WithdrawalAuditAdapter(Context context, List<WithdrawalAuditBean.ReturnBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_withdrawal_audit, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.content.setText("微信" + data.get(position).getCash() + "元提现");
        holder.time.setText(data.get(position).getTime());
        if (data.get(position).getStatu() == 1) {
            holder.state.setText("已完成");
            holder.state.setBackgroundResource(R.mipmap.withdrawal_finish);
        } else {
            holder.state.setText("审核中");
            holder.state.setBackgroundResource(R.mipmap.withdrawal_audit);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView content, time, state;

        public VH(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            time = (TextView) itemView.findViewById(R.id.time);
            state = (TextView) itemView.findViewById(R.id.tv_state);
        }
    }

}
