package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27.
 */

public class RewardModel implements Serializable {

    /**
     * total : 1
     * list : [{"id":"13","title":"123","create_time":"2018-02-25","amount":"0","s_time":"1519401600","e_time":"1533916800","s_height":"0","e_height":"0","s_age":"0","e_age":"0","sex":"0","attend_count":"2","limitCount":"10","user":{"nickname":"admin","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"aa86e51495029e11317f52ee9fc26e00"}}]
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
         * id : 13
         * title : 123
         * create_time : 2018-02-25
         * amount : 0
         * s_time : 1519401600
         * e_time : 1533916800
         * s_height : 0
         * e_height : 0
         * s_age : 0
         * e_age : 0
         * sex : 0
         * attend_count : 2
         * limitCount : 10
         * user : {"nickname":"admin","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"aa86e51495029e11317f52ee9fc26e00"}
         */

        private String id;
        private String title;
        private String create_time;
        private String amount;
        private String s_time;
        private String e_time;
        private String s_height;
        private String e_height;
        private String s_age;
        private String e_age;
        private String sex;
        private String attend_count;
        private String limitCount;
        private UserBean user;
        private String tagstr;
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTagstr() {
            return tagstr;
        }

        public void setTagstr(String tagstr) {
            this.tagstr = tagstr;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getS_time() {
            return s_time;
        }

        public void setS_time(String s_time) {
            this.s_time = s_time;
        }

        public String getE_time() {
            return e_time;
        }

        public void setE_time(String e_time) {
            this.e_time = e_time;
        }

        public String getS_height() {
            return s_height;
        }

        public void setS_height(String s_height) {
            this.s_height = s_height;
        }

        public String getE_height() {
            return e_height;
        }

        public void setE_height(String e_height) {
            this.e_height = e_height;
        }

        public String getS_age() {
            return s_age;
        }

        public void setS_age(String s_age) {
            this.s_age = s_age;
        }

        public String getE_age() {
            return e_age;
        }

        public void setE_age(String e_age) {
            this.e_age = e_age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAttend_count() {
            return attend_count;
        }

        public void setAttend_count(String attend_count) {
            this.attend_count = attend_count;
        }

        public String getLimitCount() {
            return limitCount;
        }

        public void setLimitCount(String limitCount) {
            this.limitCount = limitCount;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public class UserBean implements Serializable {
            /**
             * nickname : admin
             * sex : 0
             * birthday : 0000-00-00
             * cityid : 0
             * height : 0cm
             * weight : 0kg
             * appearance :
             * job :
             * income :
             * emotion :
             * description :
             * headimg :
             * astro : 白羊座
             * age : 0
             * piccount : 0
             * cityname :
             * rkey : aa86e51495029e11317f52ee9fc26e00
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
