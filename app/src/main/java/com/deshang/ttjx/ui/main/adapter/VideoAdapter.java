package com.deshang.ttjx.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.ui.tab3.bean.VideoBean;

import java.util.List;

/**
 * Created by L on 2018/10/26.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NormalVideo = 0;
    private static final int AD = 1;

    private Context context;
    private List<Object> data;

    public VideoAdapter(Context context, List<Object> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
//        if (data.get(position) instanceof VideoBean.ReturnBean) {
            return NormalVideo;
        /*} else {
            return AD;
        }*/
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*if (viewType == NormalVideo) {*/
            View view = LayoutInflater.from(context).inflate(R.layout.item_video_normal, parent, false);
            return new VideoNormalHolder(view);
        /*} else {
            return null;
        }*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VideoNormalHolder) {
            VideoNormalHolder videoHolder = (VideoNormalHolder) holder;
            VideoBean.ReturnBean bean = (VideoBean.ReturnBean) data.get(position);
            videoHolder.content.setText(bean.getTitle());
            videoHolder.likeNumber.setText(String.valueOf(bean.getArticle_vote_count()));

            videoHolder.showNumber.setText(bean.getClicks());

            GlideLoading.getInstance().loadImgUrlNyImgLoader(context, bean.getCover_img(), videoHolder.image);
            videoHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(position);
                    }
                }
            });
        }
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

    private class VideoNormalHolder extends RecyclerView.ViewHolder {

        TextView content;
        ImageView image;
        TextView showNumber;
        TextView likeNumber;

        private VideoNormalHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            content = (TextView) itemView.findViewById(R.id.video_content);
            showNumber = (TextView) itemView.findViewById(R.id.show_number);
            likeNumber = (TextView) itemView.findViewById(R.id.like_number);
        }
    }

}
