package com.deshang.ttjx.ui.tab4.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.BannerImageLoader;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.mywidget.NoScrollGridLayoutManager;
import com.deshang.ttjx.ui.tab3.activity.HowApprenticeActivity;
import com.deshang.ttjx.ui.tab4.adapter.QuestionAdapter;
import com.deshang.ttjx.ui.tab4.adapter.QuestionTitleAdapter;
import com.deshang.ttjx.ui.tab4.bean.QuestionListBean;
import com.deshang.ttjx.ui.tab4.persenter.QuestionPresenter;
import com.deshang.ttjx.ui.tab4.view.QuestionView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by L on 2018/6/14.
 */

public class NormalQuestionActivity extends MvpSimpleActivity<QuestionView, QuestionPresenter> implements QuestionView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycler_view_title)
    RecyclerView recyclerView;
    @BindView(R.id.expandable_list_view)
    ExpandableListView listView;
    @BindView(R.id.banner)
    Banner banner;

    private QuestionTitleAdapter titleAdapter;
    private List<QuestionListBean.ReturnBean.QuestionBean> titleData;
    private QuestionAdapter adapter;
    private List<QuestionListBean.ReturnBean.QuestionBean.ListBean> data;

//    private QuestionListBean questionListBean;
    private List<String> images;

    {
        images = new ArrayList<>();
        titleData = new ArrayList<>();
        data = new ArrayList<>();
    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_question);
    }

    @Override
    public void initView() {
        titleBar.setBack(true);
        titleBar.setTitle("常见问题");

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new BannerImageLoader());
        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(3000);
        //设置点击事件，下标是从1开始
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //设置点击事件
                if (SharedPrefHelper.getInstance().getOnLine())
                    UIManager.turnToAct(NormalQuestionActivity.this, ApprenticeActivity.class);
            }
        });

        titleAdapter = new QuestionTitleAdapter(this, titleData);
        NoScrollGridLayoutManager gridLayoutManager = new NoScrollGridLayoutManager(this, 3);
        gridLayoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(gridLayoutManager);
        titleAdapter.setOnSelectListener(new QuestionTitleAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position, int id) {

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getId() == id && data.get(i).isFirst()) {
                        listView.setSelectedGroup(i);
                        break;
                    }
                }
            }
        });
        recyclerView.setAdapter(titleAdapter);

        adapter = new QuestionAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setGroupIndicator(null);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (data.get(groupPosition).getType() == 1) {
                    UIManager.turnToAct(NormalQuestionActivity.this, HowApprenticeActivity.class);
                    return true;
                }
                return false;
            }
        });
        getPresenter().getQuestionData();
    }

    @Override
    public QuestionPresenter createPresenter() {
        return new QuestionPresenter();
    }

    @Override
    public void getQuestionSucc(QuestionListBean bean) {
        if (bean.getErrcode() == 0) {
//            questionListBean = bean;
            if (bean.getReturn() == null)
                return;

            if (bean.getReturn().getBanner() == null)
                return;
            images.clear();
            for (int i = 0; i < bean.getReturn().getBanner().size(); i++) {
                images.add(bean.getReturn().getBanner().get(i).getImg());
            }
            //自定义图片加载框架
            banner.setImages(images);
            banner.start();

            if (bean.getReturn().getQuestion() == null)
                return;

            titleData.clear();
            titleData.addAll(bean.getReturn().getQuestion());
            titleAdapter.notifyDataSetChanged();

            data.clear();
            for (int i = 0; i < bean.getReturn().getQuestion().size(); i++) {
                QuestionListBean.ReturnBean.QuestionBean questionBean = bean.getReturn().getQuestion().get(i);
                for (int j = 0; j < questionBean.getList().size(); j++) {
                    questionBean.getList().get(j).setId(questionBean.getId());
                    questionBean.getList().get(j).setFirst(j == 0);
                    questionBean.getList().get(j).setTypeName(questionBean.getName());
                    data.add(questionBean.getList().get(j));
                }
            }
            adapter.notifyDataSetChanged();

        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoPlay();
    }

}
