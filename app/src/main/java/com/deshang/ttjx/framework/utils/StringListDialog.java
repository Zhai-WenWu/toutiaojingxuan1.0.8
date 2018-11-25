package com.deshang.ttjx.framework.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.framework.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.deshang.ttjx.R;

public class StringListDialog extends Dialog {

    @BindView(R.id.rolePop_listView)
    ListView listView;

    private Context ct;
    private StringAdapter mAdapter;
    private View view;
    private List<String> itemList = new ArrayList<String>();


    public StringListDialog(Context context, int theme) {
        super(context, theme);
        this.ct = context;
        initView();
    }

    private void initView() {
        View contentView = View.inflate(ct, R.layout.img_pop, null);
        ButterKnife.bind(this, contentView);

        int width = ScreenUtils.getScreenWidth(ct);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, -2);
        contentView.setLayoutParams(params);
        setContentView(contentView, params);

        setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(p);
        getWindow().setWindowAnimations(R.style.AnimationDialog);

        mAdapter = new StringAdapter(ct);
        mAdapter.setItemList(itemList);
        listView.setAdapter(mAdapter);
    }


    public void setData(List<String> itemList) {
        mAdapter.setItemList(itemList);
//        this.itemList = itemList;
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }


    private boolean isPopShowing = false;

    class StringAdapter extends MyBaseAdapter<String> {

        public StringAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = View.inflate(ct, R.layout.img_string_item, null);
            }
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_name.setText(getItemList().get(position));
            return convertView;
        }
    }


}
