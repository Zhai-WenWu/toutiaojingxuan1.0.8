package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab4.bean.MyVedioBean;

import java.util.List;

/**
 * Created by 13364 on 2018/10/27.
 */

public class VideoVoteAdapter extends RecyclerView.Adapter<VideoVoteAdapter.VH> {
    private Context context;
    private List<MyVedioBean.ReturnBean> mData;

    public VideoVoteAdapter(Context context, List<MyVedioBean.ReturnBean> mData) {
        this.context = context;
        this.mData = mData;
    }


    @Override
    public VideoVoteAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vedio, parent, false);
        return new VideoVoteAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(VideoVoteAdapter.VH holder, final int position) {
        Glide.with(context).load(mData.get(position).getCover_img()).into(holder.iv);
        holder.tvMoney.setText("作品已赚 " + mData.get(position).getGain_gold());
        holder.tvZan.setText(mData.get(position).getArticle_vote_count() + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class VH extends RecyclerView.ViewHolder {
        public TextView tvMoney;
        public TextView tvZan;
        public ImageView iv;

        public VH(View itemView) {
            super(itemView);
            tvMoney = (TextView) itemView.findViewById(R.id.maeked_money);
            tvZan = (TextView) itemView.findViewById(R.id.fabulous);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
