package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/4/20.
 */

public class GetweiboinfoModel implements Serializable {

    /**
     * info : {"id":"20","content":"看电影","create_time":"2018-04-11 03:39:05","comment_count":"0","select_time":"2017-02-08 12:00:00","city":"440100","sex":"1","tags":"2","iscontact":"1","remark":"qwertffz","cityname":"广州市","interval_time":"1周前","user":{"nickname":"哈哈","sex":"1","birthday":"2018-04-20","cityid":"440100","height":"170cm","weight":"0kg","appearance":"","job":"管理","income":"","emotion":"","description":"","headimg":"http://app.yun-nao.com/Appimage/2018/04/11/1523438327.jpg","astro":"金牛座","age":"0","piccount":"0","cityname":"广州市","rkey":"b407300856716ab6333814db41f142d1","easemob_id":"13888888888","isvip":"0","positive":"0","neutral":"0","negative":"0"},"pic":[{"id":"54","path":"http://app.yun-nao.com/Appimage/2018/04/11/1523432342.jpg"}],"video":"","like_count":"0"}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public class InfoBean implements Serializable {
        /**
         * id : 20
         * content : 看电影
         * create_time : 2018-04-11 03:39:05
         * comment_count : 0
         * select_time : 2017-02-08 12:00:00
         * city : 440100
         * sex : 1
         * tags : 2
         * iscontact : 1
         * remark : qwertffz
         * cityname : 广州市
         * interval_time : 1周前
         * user : {"nickname":"哈哈","sex":"1","birthday":"2018-04-20","cityid":"440100","height":"170cm","weight":"0kg","appearance":"","job":"管理","income":"","emotion":"","description":"","headimg":"http://app.yun-nao.com/Appimage/2018/04/11/1523438327.jpg","astro":"金牛座","age":"0","piccount":"0","cityname":"广州市","rkey":"b407300856716ab6333814db41f142d1","easemob_id":"13888888888","isvip":"0","positive":"0","neutral":"0","negative":"0"}
         * pic : [{"id":"54","path":"http://app.yun-nao.com/Appimage/2018/04/11/1523432342.jpg"}]
         * video :
         * like_count : 0
         */

        private String id;
        private String content;
        private String create_time;
        private String comment_count;
        private String select_time;
        private String city;
        private String sex;
        private String tags;
        private String iscontact;
        private String remark;
        private String cityname;
        private String interval_time;
        private UserBean user;
        private String video;
        private String like_count;
        private String islike;
        private List<PicBean> pic;

        public String getIslike() {
            return islike;
        }

        public void setIslike(String islike) {
            this.islike = islike;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getSelect_time() {
            return select_time;
        }

        public void setSelect_time(String select_time) {
            this.select_time = select_time;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getIscontact() {
            return iscontact;
        }

        public void setIscontact(String iscontact) {
            this.iscontact = iscontact;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getInterval_time() {
            return interval_time;
        }

        public void setInterval_time(String interval_time) {
            this.interval_time = interval_time;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public class UserBean implements Serializable {
            /**
             * nickname : 哈哈
             * sex : 1
             * birthday : 2018-04-20
             * cityid : 440100
             * height : 170cm
             * weight : 0kg
             * appearance :
             * job : 管理
             * income :
             * emotion :
             * description :
             * headimg : http://app.yun-nao.com/Appimage/2018/04/11/1523438327.jpg
             * astro : 金牛座
             * age : 0
             * piccount : 0
             * cityname : 广州市
             * rkey : b407300856716ab6333814db41f142d1
             * easemob_id : 13888888888
             * isvip : 0
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

        public class PicBean implements Serializable {
            /**
             * id : 54
             * path : http://app.yun-nao.com/Appimage/2018/04/11/1523432342.jpg
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
}
