package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;

/**
 * Created by admin on 2018/4/13.
 */

public class Mywallet implements Serializable{


    /**
     * total : 0.00
     * withdraw : 0.00
     * wait : 0.00
     * coupon : 0.00
     * yesterday : 0.00
     * week : 0.00
     */

    private String total;
    private String withdraw;
    private String wait;
    private String coupon;
    private String yesterday;
    private String week;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getWait() {
        return wait;
    }

    public void setWait(String wait) {
        this.wait = wait;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
