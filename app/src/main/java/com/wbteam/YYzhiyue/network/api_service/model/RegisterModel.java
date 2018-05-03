package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/31.
 */

public class RegisterModel implements Serializable {

    /**
     * ukey : 5mHf1umPGcsFQ*YUYNz6ShSzjGFT2uAy
     */

    private String ukey;
    private String easemob_id;
    private String isvip;

    public String getEasemob_id() {
        return easemob_id;
    }

    public void setEasemob_id(String easemob_id) {
        this.easemob_id = easemob_id;
    }

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }
}
