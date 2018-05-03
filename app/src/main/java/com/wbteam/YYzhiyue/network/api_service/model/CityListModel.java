package com.wbteam.YYzhiyue.network.api_service.model;

import com.wbteam.YYzhiyue.view.city.CityEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/5.
 */

public class CityListModel implements Serializable {
    /**
     * total : 343
     * list : [{"id":"110100","name":"北京市"}]
     */

    private String total;
    private ArrayList<CityEntity> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<CityEntity> getList() {
        return list;
    }

    public void setList(ArrayList<CityEntity> list) {
        this.list = list;
    }
}
