package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/6/28.
 */

public class CommentBean implements Serializable {

    /**
     * total : 6
     * list : [{"title":"主动沟通"},{"title":"情侣的感觉"},{"title":"侧脸很动人"},{"title":"主动买水或小食"},{"title":"观影后互动愉快"},{"title":"影评家"}]
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

    public static class ListBean  implements Serializable{
        /**
         * title : 主动沟通
         */

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
