package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;

/**
 * 获取json数据基类
 *
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo
 * @date 2016/12/09  17:05
 */

public class BaseResponse<T> implements Serializable {
    public int ret;
    public String total;
    public String msg;
    public String permissions;
    public T data;

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setData(T data) {
        this.data = data;
    }


    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

}
