package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;

/**
 * Created by admin on 2018/4/16.
 */

public class MainAdModel implements Serializable{

    /**
     * title : 02
     * img : http://app.yun-nao.com/Uploads/Picture/2018-04-13/5ad026c9e1ade.png
     * width : 1125
     * height : 328
     * url :
     */

    private String title;
    private String img;
    private String width;
    private String height;
    private String url;

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

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
