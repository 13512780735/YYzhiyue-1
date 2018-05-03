package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/4/17.
 */

public class GalleryListModel implements Serializable{


    /**
     * total : 1
     * list : [{"id":"22","create_time":"2018-04-17 09:53:21","pic":[{"id":"62","path":"http://app.yun-nao.com/Appimage/2018/04/17/1523930001.png"}]}]
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

    public  class ListBean implements Serializable{
        /**
         * id : 22
         * create_time : 2018-04-17 09:53:21
         * pic : [{"id":"62","path":"http://app.yun-nao.com/Appimage/2018/04/17/1523930001.png"}]
         */

        private String id;
        private String create_time;
        private List<PicBean> pic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public class PicBean implements Serializable{
            /**
             * id : 62
             * path : http://app.yun-nao.com/Appimage/2018/04/17/1523930001.png
             */

            private String id;
            private String path;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
    }
}
