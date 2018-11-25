package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab4.bean.BallotBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by 13364 on 2018/9/25.
 */

public class MyBallotAdapter extends RecyclerView.Adapter<MyBallotAdapter.VH> {

    private List<BallotBean.ReturnBean> mDatas;
    private Context context;

    public MyBallotAdapter(List<BallotBean.ReturnBean> data, Context context) {
        this.mDatas = data;
        this.context = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ballot, parent, false);
        return new VH(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //获取当前时间
        String str = format.format(new Date());
        String substring = str.substring(5, 11);

        if (substring.equals(mDatas.get(position).getTime())){
            holder.tvTime.setText("今天");
        }else {
            holder.tvTime.setText(mDatas.get(position).getTime());
        }
        holder.rvItem.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));
        holder.rvItem.setAdapter(new MyBallotItemAdapter(context, mDatas.get(position).getData()));//设置子类自己的适配器
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        public TextView tvTime;
        public RecyclerView rvItem;

        public VH(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            rvItem = (RecyclerView) itemView.findViewById(R.id.rv_item);
        }
    }
}
