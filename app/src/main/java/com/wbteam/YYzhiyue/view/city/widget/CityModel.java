package com.wbteam.YYzhiyue.view.city.widget;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/5.
 */

public class CityModel implements Serializable{
    String id;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
