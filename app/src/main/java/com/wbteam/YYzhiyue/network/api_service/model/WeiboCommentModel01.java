package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/4/24.
 */

public class WeiboCommentModel01 implements Serializable{

    /**
     * total : 13
     * list : [{"content":"123","create_time":"2018-04-24","user":{"nickname":"tt","sex":"1","birthday":"2017-02-01","cityid":"442000","height":"180cm","weight":"0kg","appearance":"","job":"gg","income":"","emotion":"","description":"","headimg":"http://app.yun-nao.com/Appimage/2018/04/08/1523179577.jpg","astro":"水瓶座","age":"1","piccount":"0","cityname":"中山市","rkey":"214263c21ad4d499295ceee917f203a9","easemob_id":"13823925879","isvip":"0","vipdeadline":"1970-01-01","positive":"0","neutral":"0","negative":"0"}}]
     */

    private String total;
    private List<ListBean> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public  class ListBean implements Serializable{
        /**
         * content : 123
         * create_time : 2018-04-24
         * user : {"nickname":"tt","sex":"1","birthday":"2017-02-01","cityid":"442000","height":"180cm","weight":"0kg","appearance":"","job":"gg","income":"","emotion":"","description":"","headimg":"http://app.yun-nao.com/Appimage/2018/04/08/1523179577.jpg","astro":"水瓶座","age":"1","piccount":"0","cityname":"中山市","rkey":"214263c21ad4d499295ceee917f203a9","easemob_id":"13823925879","isvip":"0","vipdeadline":"1970-01-01","positive":"0","neutral":"0","negative":"0"}
         */

        private String content;
        private String create_time;
        private UserBean user;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public  class UserBean implements Serializable{
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
             * headimg : http://app.yun-nao.com/Appimage/2018/04/08/1523179577.jpg
             * astro : 水瓶座
             * age : 1
             * piccount : 0
             * cityname : 中山市
             * rkey : 214263c21ad4d499295ceee917f203a9
             * easemob_id : 13823925879
             * isvip : 0
             * vipdeadline : 1970-01-01
             * positive : 0
             * neutral : 0
             * negative : 0
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
        }
    }
}
