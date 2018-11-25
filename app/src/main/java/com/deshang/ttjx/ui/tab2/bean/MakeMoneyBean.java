package com.deshang.ttjx.ui.tab2.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/6/15.
 */

public class MakeMoneyBean extends BaseResponse {

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

        private String totalgold;
        private String total_income;
        private List<BannerBean> banner;
        private List<TaskBean> task;
        private List<VerbBean> verb;

        public String getTotal_income() {
            return total_income;
        }

        public void setTotal_income(String total_income) {
            this.total_income = total_income;
        }

        public String getTotalgold() {
            return totalgold;
        }

        public void setTotalgold(String totalgold) {
            this.totalgold = totalgold;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<TaskBean> getTask() {
            return task;
        }

        public void setTask(List<TaskBean> task) {
            this.task = task;
        }

        public List<VerbBean> getVerb() {
            return verb;
        }

        public void setVerb(List<VerbBean> verb) {
            this.verb = verb;
        }

        public static class BannerBean {

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

        public static class TaskBean {

            private String title;
            private List<DataBean> data;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {

                private String title;
                private String content;
                private String gold;
                private String link;
                private int state;
                private String taskurl;
                private boolean isFirst;
                private String groupTitle;
                private int isOpen;

                public int getIsOpen() {
                    return isOpen;
                }

                public void setIsOpen(int isOpen) {
                    this.isOpen = isOpen;
                }

                public boolean isFirst() {
                    return isFirst;
                }

                public void setFirst(boolean first) {
                    isFirst = first;
                }

                public String getGroupTitle() {
                    return groupTitle;
                }

                public void setGroupTitle(String groupTitle) {
                    this.groupTitle = groupTitle;
                }

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

                public String getGold() {
                    return gold;
                }

                public void setGold(String gold) {
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
        }

        public static class VerbBean {

            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
