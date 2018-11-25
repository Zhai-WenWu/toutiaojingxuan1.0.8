package com.deshang.ttjx.ui.tab1.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
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
import com.baidu.mobads.AdViewListener;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.mywidget.NoScrollGridView;
import com.deshang.ttjx.ui.tab1.bean.NewsListBean;
import com.deshang.ttjx.ui.tab4.activity.WebViewActivity;
import com.qq.e.ads.nativ.NativeExpressADView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 首页新闻列表适配器
 * Created by L on 2018/6/5.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int NORMAL_VIEW_TYPE = 0; // 单图新闻
    private final int THREE_VIEW_TYPE = 1; // 三图新闻
    private final int AD_VIEW_TYPE = 2; // 广告主
    private final int GDT_AD = 3; // 广点通广告信息流（左图和大图）
    private final int BAIDU_BANNER_AD = 4; // 百度大图Banner
    private final int TOUTIAO_SMALL_IMAGE = 5; // 穿山甲小图
    private final int TOUTIAO_BIG_IMAGE = 6; // 穿山甲大图
    private final int TOUTIAO_THREE_IMAGE = 7; // 穿山甲三图
    private final int NULL = 8; // 空


    private Map<RecyclerView.ViewHolder, TTAppDownloadListener> mTTAppDownloadListenerMap = new WeakHashMap<>();

    private Context context;
    private List<Object> data;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;

    public NewsAdapter(Context context, List<Object> data, HashMap<NativeExpressADView, Integer> mAdViewPositionMap) {
        this.context = context;
        this.data = data;
        this.mAdViewPositionMap = mAdViewPositionMap;
    }

    // 把返回的NativeExpressADView添加到数据集里面去
    public void addADViewToPosition(int position, NativeExpressADView adView) {
        if (position >= 0 && position < data.size() && adView != null) {
            data.add(position, adView);
        }
    }

    // 添加百度大图大图
    public void addBDADToPosition(int position, AdView adView) {
        if (position >= 0 && position < data.size() && adView != null) {
            data.add(position, adView);
        }
    }

    // 添加穿山甲广告
    public void addTTADToPosition(int position, TTFeedAd adView) {
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
        if (data.get(position) instanceof AdView) {
            // 百度大图Banner广告
            return BAIDU_BANNER_AD;
        } else if (data.get(position) instanceof NativeExpressADView) {
            // 广点通信息流广告
            return GDT_AD;
        } else if (data.get(position) instanceof TTFeedAd) {
            // 穿山甲广告
            TTFeedAd ad = (TTFeedAd) data.get(position);
            if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
                // 小图广告
                return TOUTIAO_SMALL_IMAGE;
            } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
                // 大图广告
                return TOUTIAO_BIG_IMAGE;
            } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
                // 多图广告
                return TOUTIAO_THREE_IMAGE;
            } else {
                //
                return NULL;
            }
        } else {
            // 自己的新闻
            NewsListBean.DataBean bean = (NewsListBean.DataBean) data.get(position);
            if (bean.getL_img() != null) {
                if (bean.getL_img().size() > 2) {
                    return THREE_VIEW_TYPE;
                } else {
                    return NORMAL_VIEW_TYPE;
                }
            } else {
                return AD_VIEW_TYPE;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == BAIDU_BANNER_AD) {
            // 百度Banner大图
            view = LayoutInflater.from(context).inflate(R.layout.item_baidu_banner, parent, false);
            return new BaiDuADHolder(view);
        } else if (viewType == GDT_AD) {
            // 广点通广告
            view = LayoutInflater.from(context).inflate(R.layout.item_express_ad, parent, false);
            return new GDTADHolder(view);
        } else if (viewType == TOUTIAO_SMALL_IMAGE) {
            // 穿山甲小图广告
            view = LayoutInflater.from(context).inflate(R.layout.item_toutiao_small_image, parent, false);
            return new TTSmallImageHolder(view);
        } else if (viewType == TOUTIAO_BIG_IMAGE) {
            // 穿山甲大图广告
            view = LayoutInflater.from(context).inflate(R.layout.item_toutiao_big_image, parent, false);
            return new TTBigImageHolder(view);
        } else if (viewType == TOUTIAO_THREE_IMAGE) {
            // 穿山甲三图广告
            view = LayoutInflater.from(context).inflate(R.layout.item_toutiao_three_image, parent, false);
            return new TTThreeImageHolder(view);
        } else if (viewType == AD_VIEW_TYPE) {
            // 广告主
            view = LayoutInflater.from(context).inflate(R.layout.item_news_ad, parent, false);
            return new ADHolder(view);
        } else if (viewType == THREE_VIEW_TYPE) {
            // 三图
            view = LayoutInflater.from(context).inflate(R.layout.item_news_three, parent, false);
            return new ThreeHolder(view);
        } else {
            // 单图
            view = LayoutInflater.from(context).inflate(R.layout.item_news_normal, parent, false);
            return new NormalHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BaiDuADHolder) {
            BaiDuADHolder baiDuADHolder = (BaiDuADHolder) holder;
            AdView adView = (AdView) data.get(position);
            adView.setListener(new AdViewListener() {
                @Override
                public void onAdReady(AdView adView) {
                    LogUtils.d("百度广告：onAdReady " + adView.getResources());
                }

                @Override
                public void onAdShow(JSONObject jsonObject) {
                    LogUtils.d("百度广告：onAdShow");
                }

                @Override
                public void onAdClick(JSONObject jsonObject) {

                }

                @Override
                public void onAdFailed(String s) {

                }

                @Override
                public void onAdSwitch() {

                }

                @Override
                public void onAdClose(JSONObject jsonObject) {

                }
            });
            if (Constants.width == 0) {
                DisplayMetrics dm = new DisplayMetrics();
                ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
                int winW = dm.widthPixels;
                int winH = dm.heightPixels;
                Constants.width = Math.min(winW, winH);
            }
            // 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
            RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(Constants.width, Constants.width / 2);
            rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
            if (baiDuADHolder.rl_banner.getChildCount() > 0) {
                baiDuADHolder.rl_banner.removeAllViews();
            }
            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }
            baiDuADHolder.rl_banner.addView(adView, rllp);
        } else if (holder instanceof GDTADHolder) {
            // 广点通广告SDK
            GDTADHolder adHolder = (GDTADHolder) holder;
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

        } else if (holder instanceof TTSmallImageHolder) {
            // 穿山甲小图
            TTFeedAd ttFeedAd = (TTFeedAd) data.get(position);
            TTSmallImageHolder smallImageHolder = (TTSmallImageHolder) holder;
            bindData(smallImageHolder, ttFeedAd);
            smallImageHolder.title.setText(ttFeedAd.getDescription());
            smallImageHolder.from.setText(ttFeedAd.getSource() == null ? "广告来源" : ttFeedAd.getSource());
            if (ttFeedAd.getImageList() != null && !ttFeedAd.getImageList().isEmpty()) {
                TTImage image = ttFeedAd.getImageList().get(0);
                if (image != null && image.isValid()) {
                    GlideLoading.getInstance().loadImageHeader(context, image.getImageUrl(), smallImageHolder.image);
                }
            }
        } else if (holder instanceof TTBigImageHolder) {
            // 穿山甲大图
            TTFeedAd ttFeedAd = (TTFeedAd) data.get(position);
            TTBigImageHolder bigImageHolder = (TTBigImageHolder) holder;
            bindData(bigImageHolder, ttFeedAd);
            if (ttFeedAd.getImageList() != null && !ttFeedAd.getImageList().isEmpty()) {
                TTImage image = ttFeedAd.getImageList().get(0);
                if (image != null && image.isValid()) {
                    GlideLoading.getInstance().loadImageHeader(context, image.getImageUrl(), bigImageHolder.image);
                }
            }
        } else if (holder instanceof TTThreeImageHolder) {
            // 穿山甲三图
            TTFeedAd ttFeedAd = (TTFeedAd) data.get(position);
            TTThreeImageHolder ttThreeImageHolder = (TTThreeImageHolder) holder;
            bindData(ttThreeImageHolder, ttFeedAd);
            ttThreeImageHolder.ad_title.setText(ttFeedAd.getDescription());
            ttThreeImageHolder.from.setText(ttFeedAd.getSource() == null ? "广告来源" : ttFeedAd.getSource());
            if (ttFeedAd.getImageList() != null && ttFeedAd.getImageList().size() >= 3) {
                List<String> imageList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    imageList.add(ttFeedAd.getImageList().get(i).getImageUrl());
                }
                GridImageAdapter adapter = new GridImageAdapter(context, imageList);
                ttThreeImageHolder.gridView.setAdapter(adapter);
                ttThreeImageHolder.gridView.setClickable(false);
                ttThreeImageHolder.gridView.setPressed(false);
                ttThreeImageHolder.gridView.setEnabled(false);
            }
        } else {
            final NewsListBean.DataBean bean = (NewsListBean.DataBean) data.get(position);
            if (holder instanceof ADHolder) {
                // 广告
                ADHolder adHolder = (ADHolder) holder;
                if (bean.isRead()) {
                    adHolder.ad_title.setTextColor(context.getResources().getColor(R.color.gray_deep));
                } else {
                    adHolder.ad_title.setTextColor(context.getResources().getColor(R.color.tv_black));
                }
                adHolder.ad_title.setText(bean.getTitle());
                if (bean.getImg() != null && bean.getImg().size() != 0) {
                    adHolder.ll_image.setVisibility(View.VISIBLE);
                    switch (bean.getImg().size()) {
                        case 3:
                            GlideLoading.getInstance().loadImgUrlNyImgLoader(context, bean.getImg().get(2).getImg(), adHolder.image_3);
                        case 2:
                            GlideLoading.getInstance().loadImgUrlNyImgLoader(context, bean.getImg().get(1).getImg(), adHolder.image_2);
                        case 1:
                            GlideLoading.getInstance().loadImgUrlNyImgLoader(context, bean.getImg().get(0).getImg(), adHolder.image_1);
                            break;
                    }
                } else {
                    adHolder.ll_image.setVisibility(View.GONE);
                }
                adHolder.ll_ad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.getImg() != null && bean.getImg().get(0) != null && bean.getImg().get(0).getLink() != null) {
                            Intent intent = new Intent(context, WebViewActivity.class);
                            intent.putExtra("loadUrl", bean.getImg().get(0).getLink());
                            context.startActivity(intent);
                            if (!bean.isRead()) {
                                bean.setRead(true);
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
            } else if (holder instanceof ThreeHolder) {
                // 三图
                ThreeHolder threeHolder = (ThreeHolder) holder;
                if (bean.isRead()) {
                    threeHolder.ad_title.setTextColor(context.getResources().getColor(R.color.gray_deep));
                } else {
                    threeHolder.ad_title.setTextColor(context.getResources().getColor(R.color.tv_black));
                }
                threeHolder.ad_title.setText(bean.getTitle());
                threeHolder.from.setText(bean.getSource());
                threeHolder.readNumber.setText(bean.getClicks() + "阅读");
                threeHolder.time.setText(bean.getCreate_time());
                GridImageAdapter adapter = new GridImageAdapter(context, bean.getL_img());
                threeHolder.gridView.setAdapter(adapter);
                threeHolder.gridView.setClickable(false);
                threeHolder.gridView.setPressed(false);
                threeHolder.gridView.setEnabled(false);
                threeHolder.ll_ad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            if (!bean.isRead()) {
                                bean.setRead(true);
                                notifyDataSetChanged();
                            }
                            listener.onClick(bean.getId(), bean.getCat_id());
                        }
                    }
                });

            } else {
                // 单图
                NormalHolder normalHolder = (NormalHolder) holder;
                if (bean.isRead()) {
                    normalHolder.title.setTextColor(context.getResources().getColor(R.color.gray_deep));
                } else {
                    normalHolder.title.setTextColor(context.getResources().getColor(R.color.tv_black));
                }
                normalHolder.title.setText(bean.getTitle());
                if (bean.getL_img() != null && bean.getL_img().get(0) != null) {
                    GlideLoading.getInstance().loadImgUrlNyImgLoader(context, bean.getL_img().get(0), normalHolder.image);
                }
                normalHolder.from.setText(bean.getSource());
                normalHolder.readNumber.setText(bean.getClicks() + "阅读");
                normalHolder.time.setText(bean.getCreate_time());
                normalHolder.rl_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            if (!bean.isRead()) {
                                bean.setRead(true);
                                notifyDataSetChanged();
                            }
                            listener.onClick(bean.getId(), bean.getCat_id());
                        }
                    }
                });
            }
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
//                LogUtils.d("头条广告：onAdShow " + ad.getTitle());
            }
        });
        switch (ad.getInteractionType()) {
            case TTAdConstant.INTERACTION_TYPE_DOWNLOAD:
//                bindDownloadListener(holder, ad);
                break;
            case TTAdConstant.INTERACTION_TYPE_DIAL:
                break;
            case TTAdConstant.INTERACTION_TYPE_LANDING_PAGE:
                break;
            case TTAdConstant.INTERACTION_TYPE_BROWSER:
                break;
            default:
        }
    }

    private void bindDownloadListener(final RecyclerView.ViewHolder adViewHolder, TTFeedAd ad) {
        TTAppDownloadListener downloadListener = new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                if (!isValid()) {
                    return;
                }
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // 7.0+以上版本
                    Uri apkUri = FileProvider.getUriForFile(context, "com.deshang.ttjx.fileProvider", new File(fileName)); //与manifest中定义的provider中的authorities="com.xxxx.fileprovider"保持一致
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                }
                context.startActivity(intent);
            }

            private boolean isValid() {
                return mTTAppDownloadListenerMap.get(adViewHolder) == this;
            }
        };
        //一个ViewHolder对应一个downloadListener, isValid判断当前ViewHolder绑定的listener是不是自己
        ad.setDownloadListener(downloadListener); // 注册下载监听器
        mTTAppDownloadListenerMap.put(adViewHolder, downloadListener);
    }

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(int id, int cat_id);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        TextView from, readNumber, time;
        RelativeLayout rl_item;

        public NormalHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            from = (TextView) itemView.findViewById(R.id.from);
            readNumber = (TextView) itemView.findViewById(R.id.read_number);
            time = (TextView) itemView.findViewById(R.id.time);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    class ThreeHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_ad, ll_image;
        TextView ad_title;
        NoScrollGridView gridView;

        TextView from, readNumber, time;

        public ThreeHolder(View itemView) {
            super(itemView);

            ll_ad = (LinearLayout) itemView.findViewById(R.id.ll_ad);
            ll_image = (LinearLayout) itemView.findViewById(R.id.ll_image);
            ad_title = (TextView) itemView.findViewById(R.id.ad_title);

            gridView = (NoScrollGridView) itemView.findViewById(R.id.grid_view);
            from = (TextView) itemView.findViewById(R.id.from);
            readNumber = (TextView) itemView.findViewById(R.id.read_number);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

    class ADHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_ad, ll_image;
        TextView ad_title;
        ImageView image_1, image_2, image_3;

        public ADHolder(View itemView) {
            super(itemView);

            ll_ad = (LinearLayout) itemView.findViewById(R.id.ll_ad);
            ll_image = (LinearLayout) itemView.findViewById(R.id.ll_image);
            ad_title = (TextView) itemView.findViewById(R.id.ad_title);

            image_1 = (ImageView) itemView.findViewById(R.id.image_1);
            image_2 = (ImageView) itemView.findViewById(R.id.image_2);
            image_3 = (ImageView) itemView.findViewById(R.id.image_3);
        }
    }

    class GDTADHolder extends RecyclerView.ViewHolder {

        ViewGroup container;

        public GDTADHolder(View itemView) {
            super(itemView);

            container = (ViewGroup) itemView.findViewById(R.id.express_ad_container);
        }
    }

    class BaiDuADHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_banner;

        public BaiDuADHolder(View itemView) {
            super(itemView);

            rl_banner = (RelativeLayout) itemView.findViewById(R.id.rl_banner);
        }
    }

    class TTSmallImageHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        TextView from;
        RelativeLayout rl_item;

        public TTSmallImageHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            from = (TextView) itemView.findViewById(R.id.from);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    class TTThreeImageHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_ad, ll_image;
        TextView ad_title;
        NoScrollGridView gridView;

        TextView from;

        public TTThreeImageHolder(View itemView) {
            super(itemView);

            ll_ad = (LinearLayout) itemView.findViewById(R.id.ll_ad);
            ll_image = (LinearLayout) itemView.findViewById(R.id.ll_image);
            ad_title = (TextView) itemView.findViewById(R.id.ad_title);

            gridView = (NoScrollGridView) itemView.findViewById(R.id.grid_view);
            from = (TextView) itemView.findViewById(R.id.from);
        }
    }

    class TTBigImageHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public TTBigImageHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
