package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/27.
 */

public class DatingInfoModel implements Serializable {

    /**
     * info : {"nickname":"13823925879","birthday":"2017-02-01","cityid":"442000","height":"170cm","weight":"60kg","headimg":"http://yingyunapp.wbteam.cn/Appimage/2018/03/08/1520497418.jpg","astro":"水瓶座","age":"1","piccount":"0","cityname":"中山市","rkey":"214263c21ad4d499295ceee917f203a9","easemob_id":"13823925879","isvip":"0","positive":"3","neutral":"0","negative":"1","isfollow":"0"}
     * pics : [{"id":"30","path":"http://yingyunapp.wbteam.cn/Appimage/2018/03/07/1520388592.jpg"},{"id":"31","path":"http://yingyunapp.wbteam.cn/Appimage/2018/03/07/1520388887.jpg"}]
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

    public  class InfoBean implements Serializable{
        /**
         * nickname : 13823925879
         * birthday : 2017-02-01
         * cityid : 442000
         * height : 170cm
         * weight : 60kg
         * headimg : http://yingyunapp.wbteam.cn/Appimage/2018/03/08/1520497418.jpg
         * astro : 水瓶座
         * age : 1
         * piccount : 0
         * cityname : 中山市
         * rkey : 214263c21ad4d499295ceee917f203a9
         * easemob_id : 13823925879
         * isvip : 0
         * positive : 3
         * neutral : 0
         * negative : 1
         * isfollow : 0
         */

        private String nickname;
        private String birthday;
        private String cityid;
        private String height;
        private String weight;
        private String headimg;
        private String astro;
        private String age;
        private String description;
        private String piccount;
        private String cityname;
        private String rkey;
        private String easemob_id;
        private String isvip;
        private String positive;
        private String neutral;
        private String negative;
        private String isfollow;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getIsfollow() {
            return isfollow;
        }

        public void setIsfollow(String isfollow) {
            this.isfollow = isfollow;
        }
    }

    public static class PicsBean implements Serializable{
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
