package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab4.activity.WebViewActivity;
import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;
import com.deshang.ttjx.ui.tab4.bean.SettingBean;

import java.util.List;

/**
 * Created by L on 2018/6/8.
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.VH> {

    private Context context;
    private List<SettingBean.ReturnBean> data;

    public SettingAdapter(Context context, List<SettingBean.ReturnBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_setting, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.title.setText(data.get(position).getTitle());

        holder.llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getType() == 0) {
                    // 跳网页
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("Title", data.get(position).getTitle());
                    intent.putExtra("loadUrl", data.get(position).getUrl());
                    context.startActivity(intent);
                } else {
                    // 原生
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private TextView title;
        private LinearLayout llSetting;

        public VH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            llSetting = (LinearLayout) itemView.findViewById(R.id.ll_setting);
        }
    }

}
