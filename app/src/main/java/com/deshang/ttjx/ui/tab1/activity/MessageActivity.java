package com.deshang.ttjx.ui.tab1.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.mywidget.dialog.DeleteMessageDialog;
import com.deshang.ttjx.ui.mywidget.ListScrollerView;
import com.deshang.ttjx.ui.mywidget.PullToRefreshLayout;
import com.deshang.ttjx.ui.tab1.adapter.MessageAdapter;
import com.deshang.ttjx.ui.tab1.bean.MessageBean;
import com.deshang.ttjx.ui.tab1.presenter.SystemMessagePresenter;
import com.deshang.ttjx.ui.tab1.view.SystemMessageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 系统消息页面
 * Created by L on 2018/6/6.
 */

public class MessageActivity extends MvpSimpleActivity<SystemMessageView, SystemMessagePresenter> implements SystemMessageView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.clear_message)
    TextView clearMessage;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @BindView(R.id.cord_List)
    ListScrollerView listScrollerView;
    @BindView(R.id.ll_no_data)
    LinearLayout noData;

    private MessageAdapter adapter;
    private List<MessageBean.ReturnBean> data;
    private int page = 1;
    private int deleteId = 0;
    private int readPosition;
    {
        data = new ArrayList<>();
    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_message);
    }

    @Override
    public void initView() {
        adapter = new MessageAdapter(this, data);
        adapter.setOnDeleteClickListener(new MessageAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position, int id) {
                getPresenter().deleteMessage(id);
                deleteId = id;
            }
        });
        listScrollerView.setAdapter(adapter);

        listScrollerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data.get(position).getIs_new() == 0) {
                    readPosition = position;
                    getPresenter().readMessage(data.get(position).getId());
                }
            }
        });

        refreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page = 1;
                getPresenter().getSystemMessageData(page);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                getPresenter().getSystemMessageData(page);
            }
        });
        getPresenter().getSystemMessageData(page);
    }

    @OnClick({R.id.back, R.id.clear_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.clear_message:
                if (data.size() == 0) {
                    ReceiveGoldToast.makeToast(this, "您还没有消息").show();
                    break;
                }
                DeleteMessageDialog dialog = new DeleteMessageDialog(this);
                dialog.setOnClearClickListener(new DeleteMessageDialog.OnClearClickListener() {
                    @Override
                    public void onClearClick() {
                        // TODO 去清空消息列表
                        getPresenter().clearMessage();
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public void getDataSucc(MessageBean bean) {
        if (bean.getErrcode() == 0) {
            if (page == 1) {
                data.clear();
                refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            } else {
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
            if (bean.getReturn() == null || bean.getReturn().size() == 0) {
                if (data.size() == 0) {
                    noData.setVisibility(View.VISIBLE);
                }
                return;
            }
            page++;
            data.addAll(bean.getReturn());
            adapter.notifyDataSetChanged();
            if (data.size() > 0) {
                noData.setVisibility(View.GONE);
            } else {
                noData.setVisibility(View.VISIBLE);
            }
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public void clearMessageSucc(BaseBean bean) {
        if (bean.errcode == 0) {
            showToast("清除成功");
            data.clear();
            adapter.notifyDataSetChanged();
            noData.setVisibility(View.VISIBLE);
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public void deleteMessageSucc(BaseBean bean) {
        if (bean.errcode == 0) {
            showToast("删除成功");
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId() == deleteId) {
                    data.remove(i);
                    break;
                }
            }
            if (data.size() == 0) {
                noData.setVisibility(View.VISIBLE);
            }
            listScrollerView.setAdapter(adapter);
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public void readMessageSucc(BaseBean bean) {
        if (bean.errcode == 0) {
            data.get(readPosition).setIs_new(1);
            adapter.notifyDataSetChanged();
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public SystemMessagePresenter createPresenter() {
        return new SystemMessagePresenter();
    }
}
