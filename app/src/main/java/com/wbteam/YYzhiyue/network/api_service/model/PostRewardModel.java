package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/13.
 */

public class PostRewardModel implements Serializable {

    /**
     * total : 37
     * list : [{"id":"24","title":"时光机","create_time":"2018-03-07","amount":"366.00","s_time":"2018-03-07 13:38:00","e_time":"2018-03-11 13:38:00","s_height":"150","e_height":"150","s_age":"18","e_age":"26","sex":"0","attend_count":"0","limitCount":"10","tagstr":"","status":"2"},{"id":"23","title":"擦擦","create_time":"2018-03-07","amount":"500.00","s_time":"2018-03-07 13:31:00","e_time":"2018-03-10 13:31:00","s_height":"151","e_height":"162","s_age":"18","e_age":"40","sex":"0","attend_count":"0","limitCount":"10","tagstr":"","status":"2"},{"id":"38","title":"11","create_time":"2018-03-13","amount":"100.00","s_time":"2038-01-19 11:14:07","e_time":"2038-01-19 11:14:07","s_height":"0","e_height":"0","s_age":"0","e_age":"0","sex":"0","attend_count":"0","limitCount":"1","tagstr":"","status":"0"},{"id":"43","title":"广州","create_time":"2018-03-13","amount":"0.00","s_time":"2018-03-13 14:20:00","e_time":"1970-01-01 08:00:00","s_height":"0","e_height":"0","s_age":"0","e_age":"0","sex":"0","attend_count":"0","limitCount":"1","tagstr":"","status":"0"},{"id":"44","title":"广州","create_time":"2018-03-13","amount":"0.00","s_time":"2018-03-13 14:20:00","e_time":"1970-01-01 08:00:00","s_height":"0","e_height":"0","s_age":"0","e_age":"0","sex":"0","attend_count":"0","limitCount":"1","tagstr":"","status":"0"},{"id":"45","title":"广州","create_time":"2018-03-13","amount":"0.00","s_time":"2018-03-13 14:20:00","e_time":"1970-01-01 08:00:00","s_height":"0","e_height":"0","s_age":"0","e_age":"0","sex":"0","attend_count":"0","limitCount":"1","tagstr":"","status":"0"},{"id":"46","title":"广州","create_time":"2018-03-13","amount":"0.00","s_time":"2018-03-13 14:20:00","e_time":"1970-01-01 08:00:00","s_height":"0","e_height":"0","s_age":"0","e_age":"0","sex":"0","attend_count":"0","limitCount":"1","tagstr":"","status":"0"},{"id":"47","title":"广州","create_time":"2018-03-13","amount":"0.00","s_time":"2018-03-13 14:20:00","e_time":"1970-01-01 08:00:00","s_height":"0","e_height":"0","s_age":"0","e_age":"0","sex":"0","attend_count":"0","limitCount":"1","tagstr":"","status":"0"},{"id":"48","title":"广州","create_time":"2018-03-13","amount":"0.00","s_time":"2018-03-13 14:20:00","e_time":"1970-01-01 08:00:00","s_height":"0","e_height":"0","s_age":"0","e_age":"0","sex":"0","attend_count":"0","limitCount":"1","tagstr":"","status":"0"},{"id":"49","title":"广州","create_time":"2018-03-13","amount":"0.00","s_time":"2018-03-13 14:20:00","e_time":"1970-01-01 08:00:00","s_height":"0","e_height":"0","s_age":"0","e_age":"0","sex":"0","attend_count":"0","limitCount":"1","tagstr":"","status":"0"}]
     */

    private String total;
    private List<ListBean> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean implements Serializable {
        /**
         * id : 24
         * title : 时光机
         * create_time : 2018-03-07
         * amount : 366.00
         * s_time : 2018-03-07 13:38:00
         * e_time : 2018-03-11 13:38:00
         * s_height : 150
         * e_height : 150
         * s_age : 18
         * e_age : 26
         * sex : 0
         * attend_count : 0
         * limitCount : 10
         * tagstr :
         * status : 2
         */

        private String id;
        private String title;
        private String create_time;
        private String amount;
        private String s_time;
        private String e_time;
        private String s_height;
        private String e_height;
        private String s_age;
        private String e_age;
        private String sex;
        private String attend_count;
        private String limitCount;
        private String tagstr;
        private String status;
        private String address;
        private String ordersn;
        private String orderamount;

        public String getOrderamount() {
            return orderamount;
        }

        public void setOrderamount(String orderamount) {
            this.orderamount = orderamount;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getS_time() {
            return s_time;
        }

        public void setS_time(String s_time) {
            this.s_time = s_time;
        }

        public String getE_time() {
            return e_time;
        }

        public void setE_time(String e_time) {
            this.e_time = e_time;
        }

        public String getS_height() {
            return s_height;
        }

        public void setS_height(String s_height) {
            this.s_height = s_height;
        }

        public String getE_height() {
            return e_height;
        }

        public void setE_height(String e_height) {
            this.e_height = e_height;
        }

        public String getS_age() {
            return s_age;
        }

        public void setS_age(String s_age) {
            this.s_age = s_age;
        }

        public String getE_age() {
            return e_age;
        }

        public void setE_age(String e_age) {
            this.e_age = e_age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAttend_count() {
            return attend_count;
        }

        public void setAttend_count(String attend_count) {
            this.attend_count = attend_count;
        }

        public String getLimitCount() {
            return limitCount;
        }

        public void setLimitCount(String limitCount) {
            this.limitCount = limitCount;
        }

        public String getTagstr() {
            return tagstr;
        }

        public void setTagstr(String tagstr) {
            this.tagstr = tagstr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
