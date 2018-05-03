package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;

/**
 * Created by admin on 2018/4/24.
 */

public class AuthModel implements Serializable{
    /**
     * image : /2018/04/24/1524535483.png
     * image2 : /2018/04/24/1524535483.png.png
     * image3 : /2018/04/24/1524535483.png.png.png
     * name : 111
     * id_num : 121
     * photo_url : http://app.yun-nao.com/Appimage//2018/04/24/1524535483.png
     * photo2_url : http://app.yun-nao.com/Appimage//2018/04/24/1524535483.png.png
     * photo3_url : http://app.yun-nao.com/Appimage//2018/04/24/1524535483.png.png.png
     */

    private String image;
    private String image2;
    private String image3;
    private String name;
    private String id_num;
    private String photo_url;
    private String photo2_url;
    private String photo3_url;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPhoto2_url() {
        return photo2_url;
    }

    public void setPhoto2_url(String photo2_url) {
        this.photo2_url = photo2_url;
    }

    public String getPhoto3_url() {
        return photo3_url;
    }

    public void setPhoto3_url(String photo3_url) {
        this.photo3_url = photo3_url;
    }
    //实名认证

}
