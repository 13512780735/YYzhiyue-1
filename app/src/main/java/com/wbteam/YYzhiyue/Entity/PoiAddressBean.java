package com.wbteam.YYzhiyue.Entity;

import java.io.Serializable;
import java.util.Comparator;

/**
 * author           Alpha58
 * time             2017/2/25 10:48
 * desc	            ${TODO}
 * <p>
 * upDateAuthor     $Author$
 * upDate           $Date$
 * upDateDesc       ${TODO}
 */
public class PoiAddressBean implements Serializable {

    private double longitude;//经度
    private double latitude;//纬度
    private String text;//信息内容
    public String detailAddress;//详细地址 (搜索的关键字)
    public String province;//省
    public String city;//城市
    public String district;//区域(宝安区)
    public double distance;//距离(宝安区)


    public PoiAddressBean(double lon, double lat, String detailAddress, String text, String province, String city, String district, double distance) {
        this.longitude = lon;
        this.latitude = lat;
        this.text = text;
        this.detailAddress = detailAddress;
        this.province = province;
        this.city = city;
        this.district = district;
        this.distance = distance;


    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getText() {
        return text;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}