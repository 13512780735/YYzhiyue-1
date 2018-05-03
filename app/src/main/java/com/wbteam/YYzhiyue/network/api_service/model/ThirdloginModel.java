package com.wbteam.YYzhiyue.network.api_service.model;

/**
 * Created by admin on 2018/3/8.
 */

public class ThirdloginModel {
    private String ukey;
    private String easemob_id;
    private String isvip;

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    public String getEasemob_id() {
        return easemob_id;
    }

    public void setEasemob_id(String easemob_id) {
        this.easemob_id = easemob_id;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }
}
