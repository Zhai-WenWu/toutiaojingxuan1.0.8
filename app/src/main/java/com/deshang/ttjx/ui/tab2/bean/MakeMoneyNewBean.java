package com.deshang.ttjx.ui.tab2.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/9/28.
 */

public class MakeMoneyNewBean extends BaseResponse {

    private int errcode;
    private String errinfo;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {

        private String title;
        private String title_mark;
        private String title1;
        private String title_mark1;
        private List<DataBean> data;
        private List<DataBean> data1;
        private List<BannerBean> banner;
        private List<MessageBean> message;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_mark() {
            return title_mark;
        }

        public void setTitle_mark(String title_mark) {
            this.title_mark = title_mark;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getTitle_mark1() {
            return title_mark1;
        }

        public void setTitle_mark1(String title_mark1) {
            this.title_mark1 = title_mark1;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public List<DataBean> getData1() {
            return data1;
        }

        public void setData1(List<DataBean> data1) {
            this.data1 = data1;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<MessageBean> getMessage() {
            return message;
        }

        public void setMessage(List<MessageBean> message) {
            this.message = message;
        }

        public static class DataBean {
            /**
             * title : 分享文章0/1
             * content : 新手最低+2.4红钻
             * gold : 40000
             * link : 立即分享
             * state : 0
             * taskurl : http://block.deshangkeji.com//api/Task/noviceTask/statu/article_share/userid/2
             */

            private String title;
            private String content;
            private int gold;
            private String link;
            private int state;
            private String taskurl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getGold() {
                return gold;
            }

            public void setGold(int gold) {
                this.gold = gold;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getTaskurl() {
                return taskurl;
            }

            public void setTaskurl(String taskurl) {
                this.taskurl = taskurl;
            }
        }

        public static class BannerBean {
            /**
             * img : http://block.deshangkeji.com/public/uploads/20180929/af9aad814f6cbe34056083ab1672b55b6673.png
             * url :
             */

            private String img;
            private String url;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class MessageBean {
            /**
             * id : 7
             * type : 7
             * title : 阅读分享文章可获得更多红钻
             */

            private int id;
            private int type;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
