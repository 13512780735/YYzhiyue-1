package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/30.
 */

public class MyVideoModel implements Serializable {

    /**
     * total : 1
     * list : [{"id":"4","photo_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532040.mp4.jpg","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532040.mp4","view_count":"0","like_count":"0","redbag_count":"0"}]
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

    public class ListBean implements Serializable{
        /**
         * id : 4
         * photo_url : http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532040.mp4.jpg
         * video_url : http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532040.mp4
         * view_count : 0
         * like_count : 0
         * redbag_count : 0
         */

        private String id;
        private String photo_url;
        private String video_url;
        private String view_count;
        private String like_count;
        private String redbag_count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public String getRedbag_count() {
            return redbag_count;
        }

        public void setRedbag_count(String redbag_count) {
            this.redbag_count = redbag_count;
        }
    }
}
