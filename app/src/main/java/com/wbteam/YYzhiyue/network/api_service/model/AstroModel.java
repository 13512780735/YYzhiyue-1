package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/27.
 */

public class AstroModel implements Serializable{

    /**
     * astro : 巨蟹座
     * age : 137
     */

    private String astro;
    private String age;

    public String getAstro() {
        return astro;
    }

    public void setAstro(String astro) {
        this.astro = astro;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
