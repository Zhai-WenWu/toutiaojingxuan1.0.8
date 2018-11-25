package com.deshang.ttjx.ui.tab4.activity;


import android.webkit.WebView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;

import butterknife.BindView;

public class MyWhiteBookActivity extends BaseActivity {

    @BindView(R.id.book_title)
    TitleBar bookTitle;
    @BindView(R.id.web_book)
    WebView webBook;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_my_white_book);
    }

    @Override
    public void initView() {
        bookTitle.setBack(true);
        bookTitle.setTitle("");

        webBook.loadUrl("https://block.deshangkeji.com/api/home/baipishu");
    }
}
