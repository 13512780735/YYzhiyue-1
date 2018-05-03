package com.wbteam.YYzhiyue.citypicker.model;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/21.
 */

public class RechargeModel implements Serializable {
    String name;
    private boolean isChecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
