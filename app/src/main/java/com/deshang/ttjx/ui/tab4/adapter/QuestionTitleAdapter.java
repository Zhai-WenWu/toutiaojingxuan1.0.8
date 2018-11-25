package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab4.bean.QuestionListBean;

import java.util.List;

/**
 * Created by L on 2018/6/8.
 */

public class QuestionTitleAdapter extends RecyclerView.Adapter<QuestionTitleAdapter.VH> {

    private Context context;
    private List<QuestionListBean.ReturnBean.QuestionBean> data;

    public QuestionTitleAdapter(Context context, List<QuestionListBean.ReturnBean.QuestionBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_title, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {

        holder.title.setText(data.get(position).getName());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onSelect(position, data.get(position).getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView title;

        public VH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    private OnSelectListener listener;

    public interface OnSelectListener {
        void onSelect(int position, int id);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

}
