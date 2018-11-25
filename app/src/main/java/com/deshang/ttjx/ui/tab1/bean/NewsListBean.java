package com.deshang.ttjx.ui.tab1.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/6/11.
 */

public class NewsListBean extends BaseResponse {

    private int errcode;
    private String errinfo;
    private List<DataBean> data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrinfo() {
        return errinfo;
    }

    public void setErrinfo(String errinfo) {
        this.errinfo = errinfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String cat_name;
        private int id;
        private String title;
        private String source;
        private int clicks;
        private int cat_id;
        private String create_time;
        private List<String> l_img;
        private List<ImgBean> img;
        private boolean isRead;

        public int getCat_id() {
            return cat_id;
        }

        public void setCat_id(int cat_id) {
            this.cat_id = cat_id;
        }

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getClicks() {
            return clicks;
        }

        public void setClicks(int clicks) {
            this.clicks = clicks;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<String> getL_img() {
            return l_img;
        }

        public void setL_img(List<String> l_img) {
            this.l_img = l_img;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public static class ImgBean {

            private String title;
            private String img;
            private String link;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
