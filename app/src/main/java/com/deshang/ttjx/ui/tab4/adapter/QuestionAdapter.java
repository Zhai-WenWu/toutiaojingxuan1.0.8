package com.deshang.ttjx.ui.tab4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.bean.QuestionListBean;

import java.util.List;

/**
 * Created by L on 2018/6/14.
 */

public class QuestionAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<QuestionListBean.ReturnBean.QuestionBean.ListBean> data;
    private LayoutInflater inflater = null;

    public QuestionAdapter(Context context, List<QuestionListBean.ReturnBean.QuestionBean.ListBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition).getTitle();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getDesc();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.item_group_question, null);
            groupViewHolder.title = (TextView) convertView.findViewById(R.id.title);
            groupViewHolder.content = (TextView) convertView.findViewById(R.id.content);
            groupViewHolder.image = (ImageView) convertView.findViewById(R.id.image_state);
            groupViewHolder.titleView = convertView.findViewById(R.id.title_view);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        groupViewHolder.content.setText(data.get(groupPosition).getTitle());

        if (data.get(groupPosition).isFirst()) {
            groupViewHolder.title.setText(data.get(groupPosition).getTypeName());
            groupViewHolder.title.setVisibility(View.VISIBLE);
            groupViewHolder.titleView.setVisibility(View.VISIBLE);
        } else {
            groupViewHolder.title.setVisibility(View.GONE);
            groupViewHolder.titleView.setVisibility(View.GONE);
        }

        if (b) {
            groupViewHolder.image.setImageResource(R.mipmap.up);
        } else {
            groupViewHolder.image.setImageResource(R.mipmap.down);
        }

        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        ChildViewHolder child;
        if (convertView == null) {
            child = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.item_child_question, null);
            child.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(child);
        } else {
            child = (ChildViewHolder) convertView.getTag();
        }
        child.content.setText(data.get(groupPosition).getDesc());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupViewHolder {
        TextView title;
        TextView content;
        ImageView image;
        View titleView;
    }

    private class ChildViewHolder {
        TextView content;
    }

}
