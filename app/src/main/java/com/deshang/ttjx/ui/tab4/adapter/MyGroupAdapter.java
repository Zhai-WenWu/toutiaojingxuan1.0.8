package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab2.adapter.TaskAdapter;
import com.deshang.ttjx.ui.tab4.activity.MyGroupActivity;
import com.deshang.ttjx.ui.tab4.bean.GroupBean;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 13364 on 2018/9/25.
 */

public class MyGroupAdapter extends RecyclerView.Adapter<MyGroupAdapter.VH> {

    private List<GroupBean.ReturnBean> mDatas;
    private Context context;

    public MyGroupAdapter(List<GroupBean.ReturnBean> data, Context context) {
        this.mDatas = data;
        this.context = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
        return new VH(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        final String key = mDatas.get(position).getKeyaz();
        holder.tvGroup.setText(mDatas.get(position).getTitle());
        holder.tvNum.setText(mDatas.get(position).getPeople());
        //已满
        if (mDatas.get(position).getType() == 1) {
            holder.icon.setImageResource(R.mipmap.groupfull);
            holder.ivAdd.setImageResource(R.mipmap.no_add);
            holder.tvNum.setVisibility(View.GONE);
        }
        holder.tvQQ.setText(": " + mDatas.get(position).getQq());
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDatas.get(position).getType() == 0) {
                    addClickListener.OnAddClick(key);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private OnAddClickListener addClickListener;

    public void setOnAddClick(MyGroupAdapter.OnAddClickListener listener) {
        this.addClickListener = listener;
    }

    public interface OnAddClickListener {
        void OnAddClick(String qq);
    }

    class VH extends RecyclerView.ViewHolder {
        private ImageView ivAdd;
        private TextView tvGroup;
        private ImageView icon;
        private TextView tvNum;
        private TextView tvQQ;

        public VH(View itemView) {
            super(itemView);
            tvGroup = (TextView) itemView.findViewById(R.id.tv_group);
            ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            icon = (ImageView) itemView.findViewById(R.id.iv);
            tvQQ = (TextView) itemView.findViewById(R.id.tv_qq);

        }
    }


}
