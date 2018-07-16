package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/7/13.
 */

public class EaseMobUserInfoModel implements Serializable {

    /**
     * info : {"uid":"000587","nickname":"小灰爸爸","birthday":"1994-07-07","cityid":"442000","height":"178cm","weight":"0kg","description":"","headimg":"http://app.yun-nao.com/Appimage/2018/07/06/1530859777.jpg","astro":"巨蟹座","age":"24","piccount":"0","cityname":"中山市","rkey":"1e37286e5c2f1393ac68eaeb54e48439","easemob_id":"13680260576","isvip":"0","vipdeadline":"1970-01-01","positive":"2","neutral":"0","negative":"0","membertypes":"普通会员","tempid":"587","isfollow":"0"}
     * pics : []
     */

    private InfoBean info;
    private List<PicsBean> pics;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<PicsBean> getPics() {
        return pics;
    }

    public void setPics(List<PicsBean> pics) {
        this.pics = pics;
    }

    public static class InfoBean implements Serializable {
        /**
         * uid : 000587
         * nickname : 小灰爸爸
         * birthday : 1994-07-07
         * cityid : 442000
         * height : 178cm
         * weight : 0kg
         * description :
         * headimg : http://app.yun-nao.com/Appimage/2018/07/06/1530859777.jpg
         * astro : 巨蟹座
         * age : 24
         * piccount : 0
         * cityname : 中山市
         * rkey : 1e37286e5c2f1393ac68eaeb54e48439
         * easemob_id : 13680260576
         * isvip : 0
         * vipdeadline : 1970-01-01
         * positive : 2
         * neutral : 0
         * negative : 0
         * membertypes : 普通会员
         * tempid : 587
         * isfollow : 0
         */

        private String uid;
        private String nickname;
        private String birthday;
        private String cityid;
        private String height;
        private String weight;
        private String description;
        private String headimg;
        private String astro;
        private String age;
        private String piccount;
        private String cityname;
        private String rkey;
        private String easemob_id;
        private String isvip;
        private String vipdeadline;
        private String positive;
        private String neutral;
        private String negative;
        private String membertypes;
        private String tempid;
        private String isfollow;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

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

        public String getPiccount() {
            return piccount;
        }

        public void setPiccount(String piccount) {
            this.piccount = piccount;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getRkey() {
            return rkey;
        }

        public void setRkey(String rkey) {
            this.rkey = rkey;
        }

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

        public String getVipdeadline() {
            return vipdeadline;
        }

        public void setVipdeadline(String vipdeadline) {
            this.vipdeadline = vipdeadline;
        }

        public String getPositive() {
            return positive;
        }

        public void setPositive(String positive) {
            this.positive = positive;
        }

        public String getNeutral() {
            return neutral;
        }

        public void setNeutral(String neutral) {
            this.neutral = neutral;
        }

        public String getNegative() {
            return negative;
        }

        public void setNegative(String negative) {
            this.negative = negative;
        }

        public String getMembertypes() {
            return membertypes;
        }

        public void setMembertypes(String membertypes) {
            this.membertypes = membertypes;
        }

        public String getTempid() {
            return tempid;
        }

        public void setTempid(String tempid) {
            this.tempid = tempid;
        }

        public String getIsfollow() {
            return isfollow;
        }

        public void setIsfollow(String isfollow) {
            this.isfollow = isfollow;
        }
    }

    public static class PicsBean implements Serializable {
        /**
         * id : 30
         * path : http://yingyunapp.wbteam.cn/Appimage/2018/03/07/1520388592.jpg
         */

        private String id;
        private String path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
