package com.deshang.ttjx.ui.tab1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.AdView;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.ui.mywidget.NoScrollGridView;
import com.deshang.ttjx.ui.tab1.bean.NewsListBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendNewsBean;
import com.deshang.ttjx.ui.tab4.activity.NewsDetailActivity;
import com.deshang.ttjx.ui.tab4.activity.WebViewActivity;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 新闻详情页推荐列表适配器
 * Created by L on 2018/6/5.
 */

public class NewsDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int NORMAL_VIEW_TYPE = 0; // 单图新闻
    private final int THREE_VIEW_TYPE = 1; // 三图新闻
    private final int GDT_AD = 2; // 广点通广告信息流（左图和大图）
    private final int TOUTIAO_SMALL_IMAGE = 3; // 穿山甲小图
    private final int TOUTIAO_THREE_IMAGE = 4; // 穿山甲三图

    private Context context;
    private List<Object> data;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;

    public NewsDetailAdapter(Context context, List<Object> data, HashMap<NativeExpressADView, Integer> mAdViewPositionMap) {
        this.context = context;
        this.data = data;
        this.mAdViewPositionMap = mAdViewPositionMap;
    }

    // 添加穿山甲广告
    public void addTTADToPosition(int position, TTFeedAd adView) {
        if (position >= 0 && position < data.size() && adView != null) {
            data.add(position, adView);
        }
    }

    // 把返回的NativeExpressADView添加到数据集里面去
    public void addADViewToPosition(int position, NativeExpressADView adView) {
        if (position >= 0 && position < data.size() && adView != null) {
            data.add(position, adView);
        }
    }

    // 移除NativeExpressADView的时候是一条一条移除的
    public void removeADView(int position, NativeExpressADView adView) {
        data.remove(position);
        notifyItemRemoved(position); // position为adView在当前列表中的位置
        notifyItemRangeChanged(0, data.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        /*
        区分item类型,返回不同的int类型的值
        在onCreateViewHolder方法中用viewType来创建不同的ViewHolder
         */
        if (data.get(position) instanceof NativeExpressADView) {
            // 广点通信息流广告
            return GDT_AD;
        } else if (data.get(position) instanceof TTFeedAd) {
            // 穿山甲广告
            TTFeedAd ad = (TTFeedAd) data.get(position);
            if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
                // 小图广告
                return TOUTIAO_SMALL_IMAGE;
            } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
                // 多图广告
                return TOUTIAO_THREE_IMAGE;
            } else {
                return NORMAL_VIEW_TYPE;
            }
        } else {
            // 自己的新闻
            RecommendNewsBean.DataBean bean = (RecommendNewsBean.DataBean) data.get(position);
            if (bean.getImg() != null && bean.getImg().size() > 2) {
                return THREE_VIEW_TYPE;
            } else {
                return NORMAL_VIEW_TYPE;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == NORMAL_VIEW_TYPE) {
            // 单图
            view = LayoutInflater.from(context).inflate(R.layout.item_recommend_news_normal, parent, false);
            return new NormalHolder(view);
        } else if (viewType == THREE_VIEW_TYPE) {
            // 三图
            view = LayoutInflater.from(context).inflate(R.layout.item_recommend_news_three, parent, false);
            return new ThreeHolder(view);
        } else if (viewType == TOUTIAO_SMALL_IMAGE) {
            // 穿山甲小图广告
            view = LayoutInflater.from(context).inflate(R.layout.item_toutiao_recommend_small_image, parent, false);
            return new SmallImageAdHolder(view);
        } else if (viewType == TOUTIAO_THREE_IMAGE) {
            // 穿山甲三图广告
            view = LayoutInflater.from(context).inflate(R.layout.item_toutiao_recommend_three_image, parent, false);
            return new ThreeImageAdHolder(view);
        } else {
            // 广点通广告
            view = LayoutInflater.from(context).inflate(R.layout.item_express_ad, parent, false);
            return new GDTADHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof NormalHolder) {
            NormalHolder holder = (NormalHolder) viewHolder;
            if (data.get(position) instanceof RecommendNewsBean.DataBean) {
                final RecommendNewsBean.DataBean bean = (RecommendNewsBean.DataBean) data.get(position);
                GlideLoading.getInstance().loadImgUrlNyImgLoader(context, bean.getCover_img(), holder.image);
                holder.read_number.setText(String.valueOf(bean.getClicks()));
                holder.title.setText(bean.getTitle());
                holder.rl_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onClick(bean.getId());
                        }
                    }
                });
            }
        } else if (viewHolder instanceof GDTADHolder) {
            // 广点通广告SDK
            GDTADHolder adHolder = (GDTADHolder) viewHolder;
            final NativeExpressADView adView = (NativeExpressADView) data.get(position);

            mAdViewPositionMap.put(adView, position); // 广告在列表中的位置是可以被更新的
            if (adHolder.container.getChildCount() > 0 && adHolder.container.getChildAt(0) == adView) {
                return;
            }
            if (adHolder.container.getChildCount() > 0) {
                adHolder.container.removeAllViews();
            }
            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }
            adHolder.container.addView(adView);
            adView.render(); // 调用render方法后sdk才会开始展示广告
        } else if (viewHolder instanceof SmallImageAdHolder) {
            // 穿山甲小图
            TTFeedAd ttFeedAd = (TTFeedAd) data.get(position);
            SmallImageAdHolder smallImageHolder = (SmallImageAdHolder) viewHolder;
            bindData(smallImageHolder, ttFeedAd);
            smallImageHolder.title.setText(ttFeedAd.getDescription());
            if (ttFeedAd.getImageList() != null && !ttFeedAd.getImageList().isEmpty()) {
                TTImage image = ttFeedAd.getImageList().get(0);
                if (image != null && image.isValid()) {
                    GlideLoading.getInstance().loadImageHeader(context, image.getImageUrl(), smallImageHolder.image);
                }
            }
        } else if (viewHolder instanceof ThreeImageAdHolder) {
            // 穿山甲三图
            TTFeedAd ttFeedAd = (TTFeedAd) data.get(position);
            ThreeImageAdHolder ttThreeImageHolder = (ThreeImageAdHolder) viewHolder;
            bindData(ttThreeImageHolder, ttFeedAd);
            ttThreeImageHolder.ad_title.setText(ttFeedAd.getDescription());
            if (ttFeedAd.getImageList() != null && ttFeedAd.getImageList().size() >= 3) {
                List<String> imageList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    imageList.add(ttFeedAd.getImageList().get(i).getImageUrl());
                }
                GridImageAdapter adapter = new GridImageAdapter(context, imageList);
                adapter.setType(1);
                ttThreeImageHolder.gridView.setAdapter(adapter);
                ttThreeImageHolder.gridView.setClickable(false);
                ttThreeImageHolder.gridView.setPressed(false);
                ttThreeImageHolder.gridView.setEnabled(false);
            }
        } else {
            // 三图
            final RecommendNewsBean.DataBean bean = (RecommendNewsBean.DataBean) data.get(position);
            ThreeHolder threeHolder = (ThreeHolder) viewHolder;
            threeHolder.ad_title.setText(bean.getTitle());
            GridImageAdapter adapter = new GridImageAdapter(context, bean.getImg());
            threeHolder.gridView.setAdapter(adapter);
            threeHolder.gridView.setClickable(false);
            threeHolder.gridView.setPressed(false);
            threeHolder.gridView.setEnabled(false);
            threeHolder.ll_ad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(bean.getId());
                    }
                }
            });
        }
    }

    private void bindData(final RecyclerView.ViewHolder holder, TTFeedAd ad) {
        List<View> clickViewList = new ArrayList<>();
        clickViewList.add(holder.itemView);
        List<View> creativeViewList = new ArrayList<>();
        creativeViewList.add(holder.itemView);
        ad.registerViewForInteraction((ViewGroup) holder.itemView, clickViewList, creativeViewList, new TTFeedAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, TTFeedAd ad) {
            }

            @Override
            public void onAdCreativeClick(View view, TTFeedAd ad) {
            }

            @Override
            public void onAdShow(TTFeedAd ad) {
            }
        });
    }

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(int id);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class NormalHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        TextView read_number;
        RelativeLayout rl_item;

        private NormalHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            read_number = (TextView) itemView.findViewById(R.id.read_number);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    private class ThreeHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_ad, ll_image;
        TextView ad_title;
        NoScrollGridView gridView;

        private ThreeHolder(View itemView) {
            super(itemView);

            ll_ad = (LinearLayout) itemView.findViewById(R.id.ll_ad);
            ll_image = (LinearLayout) itemView.findViewById(R.id.ll_image);
            ad_title = (TextView) itemView.findViewById(R.id.ad_title);

            gridView = (NoScrollGridView) itemView.findViewById(R.id.grid_view);
        }
    }

    private class GDTADHolder extends RecyclerView.ViewHolder {

        ViewGroup container;

        private GDTADHolder(View itemView) {
            super(itemView);
            container = (ViewGroup) itemView.findViewById(R.id.express_ad_container);
        }
    }

    private class SmallImageAdHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        RelativeLayout rl_item;

        private SmallImageAdHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    private class ThreeImageAdHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_ad, ll_image;
        TextView ad_title;
        NoScrollGridView gridView;

        private ThreeImageAdHolder(View itemView) {
            super(itemView);

            ll_ad = (LinearLayout) itemView.findViewById(R.id.ll_ad);
            ll_image = (LinearLayout) itemView.findViewById(R.id.ll_image);
            ad_title = (TextView) itemView.findViewById(R.id.ad_title);

            gridView = (NoScrollGridView) itemView.findViewById(R.id.grid_view);
        }
    }
}
