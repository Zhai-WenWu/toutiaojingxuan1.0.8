package com.deshang.ttjx.wxapi;

import android.text.TextUtils;

/**
 * Created by yshow_mdj on 2016/4/13.
 */
public class ShareParam {
    private String state;
    private String url;
    private String title;
    private String description;
    private String imageUrl;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ShareParam(String url, String title, String description, String imageUrl) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public ShareParam() {
    }

    public boolean isEmpty() {
        if (TextUtils.isEmpty(url) && TextUtils.isEmpty(title) && TextUtils.isEmpty(description) && TextUtils.isEmpty(imageUrl)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ShareParam{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShareParam that = (ShareParam) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        return !(imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null);

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }
}
