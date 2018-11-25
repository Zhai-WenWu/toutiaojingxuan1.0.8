package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab4.activity.NewsDetailActivity;
import com.deshang.ttjx.ui.tab4.bean.BallotBean;

import java.util.List;

import mvp.cn.util.ToastUtil;

/**
 * Created by 13364 on 2018/9/21.
 */

public class MyBallotItemAdapter extends RecyclerView.Adapter<MyBallotItemAdapter.VH> {

    private Context mContext;
    private List<BallotBean.ReturnBean.DataBean> datas;

    public MyBallotItemAdapter(Context context, List<BallotBean.ReturnBean.DataBean> mDatas) {
        this.mContext = context;
        this.datas = mDatas;
    }

    @Override
    public MyBallotItemAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ballot_item, parent, false);
        return new MyBallotItemAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {

        holder.tvTitle.setText(datas.get(position).getTitle());
        holder.tvHhhh.setText(datas.get(position).getGain_gold() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("Cat_id", datas.get(position).getCat_id());
                intent.putExtra("Url", String.valueOf(datas.get(position).getAid()));
                intent.putExtra("TypeID", String.valueOf(datas.get(position).getCat_id()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvHhhh;

        public VH(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_content_title);
            tvHhhh = (TextView) itemView.findViewById(R.id.tv_hhh);

        }
    }

}