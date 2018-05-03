package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/14.
 */

public class ApplyPersonModel implements Serializable {


    /**
     * total : 2
     * list : [{"isbid":"0","user":{"nickname":"13823925879","sex":"1","birthday":"1997-02-01","cityid":"442000","height":"170cm","weight":"60kg","appearance":"哦哦哦","job":"呃呃","income":"6000","emotion":"恋爱中","description":"","headimg":"http://yingyunapp.wbteam.cn/Appimage/2018/03/08/1520497418.jpg","astro":"水瓶座","age":"21","piccount":"0","cityname":"中山市","rkey":"214263c21ad4d499295ceee917f203a9"}},{"isbid":"0","user":{"nickname":"18939538052","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"2ec0830e9a33c51cda3ec8c9f00f4938"}}]
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

    public class ListBean implements Serializable {
        /**
         * isbid : 0
         * user : {"nickname":"13823925879","sex":"1","birthday":"1997-02-01","cityid":"442000","height":"170cm","weight":"60kg","appearance":"哦哦哦","job":"呃呃","income":"6000","emotion":"恋爱中","description":"","headimg":"http://yingyunapp.wbteam.cn/Appimage/2018/03/08/1520497418.jpg","astro":"水瓶座","age":"21","piccount":"0","cityname":"中山市","rkey":"214263c21ad4d499295ceee917f203a9"}
         */

        private String isbid;
        private UserBean user;
        private String iscomment;

        public String getIscomment() {
            return iscomment;
        }

        public void setIscomment(String iscomment) {
            this.iscomment = iscomment;
        }

        public String getIsbid() {
            return isbid;
        }

        public void setIsbid(String isbid) {
            this.isbid = isbid;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public class UserBean implements Serializable {
            /**
             * nickname : 13823925879
             * sex : 1
             * birthday : 1997-02-01
             * cityid : 442000
             * height : 170cm
             * weight : 60kg
             * appearance : 哦哦哦
             * job : 呃呃
             * income : 6000
             * emotion : 恋爱中
             * description :
             * headimg : http://yingyunapp.wbteam.cn/Appimage/2018/03/08/1520497418.jpg
             * astro : 水瓶座
             * age : 21
             * piccount : 0
             * cityname : 中山市
             * rkey : 214263c21ad4d499295ceee917f203a9
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
        }
    }
}
