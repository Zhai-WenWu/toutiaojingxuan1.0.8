package com.deshang.ttjx.ui.tab3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.widget.CircleImageView;
import com.deshang.ttjx.ui.tab2.bean.ChangeOrGoldBean;
import com.deshang.ttjx.ui.tab3.bean.ApprenticeOrDiscipleBean;

import java.util.List;

/**
 * 我的徒弟/徒弟或徒孙列表 适配器
 * Created by L on 2018/6/5.
 */

public class ApprenticeOrDiscipleAdapter extends RecyclerView.Adapter<ApprenticeOrDiscipleAdapter.Holder> {

    private Context context;
    private List<ApprenticeOrDiscipleBean> data;
    private int type = 0;

    public ApprenticeOrDiscipleAdapter(Context context, List<ApprenticeOrDiscipleBean> data) {
        this.context = context;
        this.data = data;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public ApprenticeOrDiscipleAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_apprentice, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(ApprenticeOrDiscipleAdapter.Holder holder, final int position) {
        GlideLoading.getInstance().loadImgUrlNyImgLoader(context, data.get(position).getImg(), holder.header);
        holder.name.setText(data.get(position).getName());
        holder.time.setText(data.get(position).getFinish_time());
        holder.phone.setText(data.get(position).getMobile());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        private CircleImageView header;
        private TextView name, phone, time;

        public Holder(View itemView) {
            super(itemView);
            header = (CircleImageView) itemView.findViewById(R.id.header);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

}
