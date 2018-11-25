package com.deshang.ttjx.ui.tab1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.deshang.ttjx.ui.tab4.activity.NewsDetailActivity;

import java.util.List;

/**
 * 推荐新闻列表适配器
 * Created by L on 2018/6/5.
 */

public class RecommendNewsAdapter extends RecyclerView.Adapter<RecommendNewsAdapter.VH> {

    private Context context;
    private List<RecommendBean.ReturnBean> data;

    public RecommendNewsAdapter(Context context, List<RecommendBean.ReturnBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecommendNewsAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_news, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(RecommendNewsAdapter.VH holder, final int position) {

        holder.title.setText(data.get(position).getTitle());
        GlideLoading.getInstance().loadImgUrlNyImgLoader(context, data.get(position).getL_img(), holder.image);

        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("Type", 1);
                intent.putExtra("TypeID", data.get(position).getCat_id() + "");
                intent.putExtra("Url", data.get(position).getId() + "");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    class VH extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
//        TextView from, readNumber, time;
        RelativeLayout rl_item;


        public VH(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
//            from = (TextView) itemView.findViewById(R.id.from);
//            readNumber = (TextView) itemView.findViewById(R.id.read_number);
//            time = (TextView) itemView.findViewById(R.id.time);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

}
