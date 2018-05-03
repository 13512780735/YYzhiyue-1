package com.wbteam.YYzhiyue.Entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/31.
 */

public class UserInfoModel implements Serializable {


    /**
     * info : {"nickname":"tt","sex":"1","birthday":"2017-02-01","cityid":"442000","height":"180cm","weight":"0kg","appearance":"","job":"gg","income":"","emotion":"","description":"","invitation_code":"1SU7MHC8","lng":"113.371886","lat":"22.513604","account":"0.01","headimg":"http://app.yun-nao.com/Appimage/2018/04/08/1523179577.jpg","astro":"水瓶座","age":"1","piccount":"0","cityname":"中山市","rkey":"214263c21ad4d499295ceee917f203a9","easemob_id":"13823925879","isvip":"0","positive":"3","neutral":"0","negative":"1","exist_parent":"0","price":"0","start_time":"","end_time":"","weixin":"","contact":"","iscontact":"","videoauth":"2"}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public  class InfoBean implements Serializable {
        /**
         * nickname : tt
         * sex : 1
         * birthday : 2017-02-01
         * cityid : 442000
         * height : 180cm
         * weight : 0kg
         * appearance :
         * job : gg
         * income :
         * emotion :
         * description :
         * invitation_code : 1SU7MHC8
         * lng : 113.371886
         * lat : 22.513604
         * account : 0.01
         * headimg : http://app.yun-nao.com/Appimage/2018/04/08/1523179577.jpg
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
         * exist_parent : 0
         * price : 0
         * start_time :
         * end_time :
         * weixin :
         * contact :
         * iscontact :
         * videoauth : 2
         */

        private String nickname;
        private String sex;
        private String birthday;
        private String cityid;
        private String height;
        private String weight;
        private String appearance;
        private String job;
        private String income;
        private String emotion;
        private String description;
        private String invitation_code;
        private String lng;
        private String lat;
        private String account;
        private String headimg;
        private String astro;
        private String age;
        private String piccount;
        private String cityname;
        private String rkey;
        private String easemob_id;
        private String isvip;
        private String positive;
        private String neutral;
        private String negative;
        private String exist_parent;
        private String price;
        private String start_time;
        private String end_time;
        private String weixin;
        private String contact;
        private String iscontact;
        private String videoauth;
        private String auth;
        private String vipdeadline;

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getVipdeadline() {
            return vipdeadline;
        }

        public void setVipdeadline(String vipdeadline) {
            this.vipdeadline = vipdeadline;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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

        public String getAppearance() {
            return appearance;
        }

        public void setAppearance(String appearance) {
            this.appearance = appearance;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getEmotion() {
            return emotion;
        }

        public void setEmotion(String emotion) {
            this.emotion = emotion;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getInvitation_code() {
            return invitation_code;
        }

        public void setInvitation_code(String invitation_code) {
            this.invitation_code = invitation_code;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
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

        public String getExist_parent() {
            return exist_parent;
        }

        public void setExist_parent(String exist_parent) {
            this.exist_parent = exist_parent;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getIscontact() {
            return iscontact;
        }

        public void setIscontact(String iscontact) {
            this.iscontact = iscontact;
        }

        public String getVideoauth() {
            return videoauth;
        }

        public void setVideoauth(String videoauth) {
            this.videoauth = videoauth;
        }
    }
}
