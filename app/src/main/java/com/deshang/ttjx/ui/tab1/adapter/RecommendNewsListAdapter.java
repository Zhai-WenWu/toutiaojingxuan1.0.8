package com.deshang.ttjx.ui.tab1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.ui.mywidget.NoScrollGridView;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.deshang.ttjx.ui.tab4.activity.NewsDetailActivity;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 推荐新闻列表适配器
 * Created by L on 2018/6/5.
 */

public class RecommendNewsListAdapter extends BaseAdapter {

    private Context context;
    private List<Object> data;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;

    public RecommendNewsListAdapter(Context context, List<Object> data, HashMap<NativeExpressADView, Integer> mAdViewPositionMap) {
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
//        notifyItemRemoved(position); // position为adView在当前列表中的位置
//        notifyItemRangeChanged(0, data.size() - 1);
        notifyDataSetChanged();
    }

    private OnClickListener listener;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (data.get(position) instanceof RecommendBean.ReturnBean) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recommend_news, null);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            final RecommendBean.ReturnBean bean = (RecommendBean.ReturnBean) data.get(position);
            holder.title.setText(bean.getTitle());
            GlideLoading.getInstance().loadImgUrlNyImgLoader(context, bean.getL_img(), holder.image);

            holder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(position);
                    }
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    intent.putExtra("Type", 1);
                    intent.putExtra("TypeID", bean.getCat_id() + "");
                    intent.putExtra("Url", bean.getId() + "");
                    context.startActivity(intent);
                }
            });
        } else if (data.get(position) instanceof NativeExpressADView) {
            // 广告
            convertView = LayoutInflater.from(context).inflate(R.layout.item_express_ad, null);
            ADViewHolder holder = new ADViewHolder(convertView);
            convertView.setTag(holder);
            final NativeExpressADView adView = (NativeExpressADView) data.get(position);

            mAdViewPositionMap.put(adView, position); // 广告在列表中的位置是可以被更新的
            if (!(holder.container.getChildCount() > 0 && holder.container.getChildAt(0) == adView)) {
                if (holder.container.getChildCount() > 0) {
                    holder.container.removeAllViews();
                }
                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }
                holder.container.addView(adView);
                adView.render(); // 调用render方法后sdk才会开始展示广告
            }
        } else if (data.get(position) instanceof TTFeedAd) {
            TTFeedAd ttFeedAd = (TTFeedAd) data.get(position);
            if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
                // 穿山甲小图
                convertView = LayoutInflater.from(context).inflate(R.layout.item_toutiao_recommend_small, null);
                SmallAdHolder holder = new SmallAdHolder(convertView);
                holder.title.setText(ttFeedAd.getDescription());
                bindData(holder.rl_item, ttFeedAd);
                if (ttFeedAd.getImageList() != null && !ttFeedAd.getImageList().isEmpty()) {
                    TTImage image = ttFeedAd.getImageList().get(0);
                    if (image != null && image.isValid()) {
                        GlideLoading.getInstance().loadImageHeader(context, image.getImageUrl(), holder.image);
                    }
                }
            } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
                // 穿山甲三图
                convertView = LayoutInflater.from(context).inflate(R.layout.item_toutiao_recommend_three, null);
                ThreeAdHolder holder = new ThreeAdHolder(convertView);
                bindData(holder.ll_ad, ttFeedAd);
                holder.ad_title.setText(ttFeedAd.getDescription());
                if (ttFeedAd.getImageList() != null && ttFeedAd.getImageList().size() >= 3) {
                    List<String> imageList = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        imageList.add(ttFeedAd.getImageList().get(i).getImageUrl());
                    }
                    GridSmallImageAdapter adapter = new GridSmallImageAdapter(context, imageList);
                    holder.gridView.setAdapter(adapter);
                    holder.gridView.setClickable(false);
                    holder.gridView.setPressed(false);
                    holder.gridView.setEnabled(false);
                }
            }
        }
        return convertView;
    }

    private void bindData(final ViewGroup view, TTFeedAd ad) {
        List<View> clickViewList = new ArrayList<>();
        clickViewList.add(view);
        List<View> creativeViewList = new ArrayList<>();
        creativeViewList.add(view);
        ad.registerViewForInteraction(view, clickViewList, creativeViewList, new TTFeedAd.AdInteractionListener() {
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

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    private class ViewHolder {
        TextView title;
        ImageView image;
        RelativeLayout rl_item;

        private ViewHolder(View itemView) {
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    private class ADViewHolder {
        ViewGroup container;

        private ADViewHolder(View itemView) {
            container = (ViewGroup) itemView.findViewById(R.id.express_ad_container);
        }
    }

    private class SmallAdHolder {
        TextView title;
        ImageView image;
        RelativeLayout rl_item;

        private SmallAdHolder(View itemView) {
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    private class ThreeAdHolder {
        LinearLayout ll_ad, ll_image;
        TextView ad_title;
        NoScrollGridView gridView;

        TextView from;

        private ThreeAdHolder(View itemView) {
            ll_ad = (LinearLayout) itemView.findViewById(R.id.ll_ad);
            ll_image = (LinearLayout) itemView.findViewById(R.id.ll_image);
            ad_title = (TextView) itemView.findViewById(R.id.ad_title);

            gridView = (NoScrollGridView) itemView.findViewById(R.id.grid_view);
            from = (TextView) itemView.findViewById(R.id.from);
        }
    }

}
