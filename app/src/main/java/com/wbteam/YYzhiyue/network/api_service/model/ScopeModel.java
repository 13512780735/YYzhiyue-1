package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/1.
 */

public class ScopeModel implements Serializable {

    /**
     * info : {"price":"","start_time":"","end_time":"","weixin":"","contact":"","iscontact":""}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * price :
         * start_time :
         * end_time :
         * weixin :
         * contact :
         * iscontact :
         */

        private String price;
        private String start_time;
        private String end_time;
        private String weixin;
        private String contact;
        private String iscontact;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getIscontact() {
            return iscontact;
        }

        public void setIscontact(String iscontact) {
            this.iscontact = iscontact;
        }
    }
}
