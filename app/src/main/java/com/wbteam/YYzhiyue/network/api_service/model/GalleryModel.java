package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;

/**
 * Created by admin on 2018/4/17.
 */

public class GalleryModel implements Serializable {

    /**
     * id : 92
     * path : http://app.yun-nao.com/Appimage//2018/04/17/1523935776.png
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
