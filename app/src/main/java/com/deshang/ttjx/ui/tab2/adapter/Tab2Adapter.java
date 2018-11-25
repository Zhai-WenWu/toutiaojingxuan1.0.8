package com.deshang.ttjx.ui.tab2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyBean;
import com.deshang.ttjx.ui.tab4.activity.ApprenticeActivity;
import com.deshang.ttjx.ui.tab4.activity.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 2018/6/7.
 */

public class Tab2Adapter extends RecyclerView.Adapter<Tab2Adapter.VH> {

    private Context context;
    private List<MakeMoneyBean.DataBeanX.TaskBean> list;
    private List<MakeMoneyBean.DataBeanX.TaskBean.DataBean> data;

    public Tab2Adapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<MakeMoneyBean.DataBeanX.TaskBean> list) {
        this.list = list;
        data.clear();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getData() != null) {
                for (int j = 0; j < list.get(i).getData().size(); j++) {
                    list.get(i).getData().get(j).setFirst(j == 0);
                    list.get(i).getData().get(j).setIsOpen(0);
                    list.get(i).getData().get(j).setGroupTitle(list.get(i).getTitle());
                }
            }
            data.addAll(list.get(i).getData());
        }
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab2, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {

        holder.tv_title.setText(data.get(position).getTitle());
        holder.content.setText(data.get(position).getContent());
        holder.tv_money.setText("+" + data.get(position).getGold());

        if (data.get(position).isFirst()) {
            holder.ll_type.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.VISIBLE);
            holder.type.setText(data.get(position).getGroupTitle());
            if ("悬赏任务".equals(data.get(position).getGroupTitle())) {
                holder.image_type.setImageResource(R.mipmap.task_reward);
            } else if ("新手任务".equals(data.get(position).getGroupTitle())) {
                holder.image_type.setImageResource(R.mipmap.task_novice);
            } else {
                holder.image_type.setImageResource(R.mipmap.task_day);
            }
        } else {
            holder.ll_type.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
        }

        if (data.get(position).getState() == 0) {
            if ("邀请收徒即可获得".equals(data.get(position).getTitle())) {
                holder.receive.setBackgroundResource(R.mipmap.make_money_selector);
                holder.receive.setText("去完成");
            } else {
                holder.receive.setBackgroundResource(R.mipmap.make_money_no_selector);
                holder.receive.setText("待完成");
            }
        } else if (data.get(position).getState() == 1) {
            holder.receive.setBackgroundResource(R.mipmap.make_money_selector);
            holder.receive.setText("领取");
        } else {
            holder.receive.setBackgroundResource(R.mipmap.make_money_no_selector);
            holder.receive.setText("已完成");
        }

        holder.receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(position).getState() == 0) {
                    if ("去完成".equals(holder.receive.getText())) {
                        UIManager.turnToAct(context, ApprenticeActivity.class);
//                        Intent intent = new Intent(context, WebViewActivity.class);
//                        intent.putExtra("loadUrl", Constants.APPRENTICE);
//                        context.startActivity(intent);
                    } /*else {
                        ReceiveGoldToast.makeToast(context, "完成任务后才能领取哦~").show();
                    }*/
                } else if (data.get(position).getState() == 1) {
                    if (listener != null) {
                        listener.onClick(data.get(position).getTaskurl());
                    }
                } else {
                    ReceiveGoldToast.makeToast(context, "该任务已完成~").show();
                }
            }
        });

        if (data.get(position).getIsOpen() == 0) {
            holder.rl_bottom.setVisibility(View.GONE);
            holder.up_down.setImageResource(R.mipmap.down);
            holder.view1.setVisibility(View.VISIBLE);
        } else {
            holder.rl_bottom.setVisibility(View.VISIBLE);
            holder.up_down.setImageResource(R.mipmap.up);
            holder.view1.setVisibility(View.GONE);
        }

        holder.rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.rl_bottom.getVisibility() == View.VISIBLE) {
                    data.get(position).setIsOpen(0);
                } else {
                    data.get(position).setIsOpen(1);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VH extends RecyclerView.ViewHolder {

        RelativeLayout rl_parent, rl_bottom;
        ImageView up_down; // 向上向下的箭头
        TextView tv_title, tv_money, receive, content;
        LinearLayout ll_type;
        ImageView image_type;
        TextView type;
        View view, view1;

        public VH(View itemView) {
            super(itemView);
            rl_parent = (RelativeLayout) itemView.findViewById(R.id.rl_parent);
            rl_bottom = (RelativeLayout) itemView.findViewById(R.id.rl_bottom);
            up_down = (ImageView) itemView.findViewById(R.id.up_down);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            receive = (TextView) itemView.findViewById(R.id.receive);
            content = (TextView) itemView.findViewById(R.id.content);
            ll_type = (LinearLayout) itemView.findViewById(R.id.ll_type);
            image_type = (ImageView) itemView.findViewById(R.id.image_type);
            type = (TextView) itemView.findViewById(R.id.type);
            view = itemView.findViewById(R.id.view);
            view1 = itemView.findViewById(R.id.view1);
        }
    }

    private OnReceiveClickListener listener;

    public void setOnReceiveClickListener(OnReceiveClickListener listener) {
        this.listener = listener;
    }

    public interface OnReceiveClickListener {
        void onClick(String url);
    }

}
