package com.wbteam.YYzhiyue.network.api_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class OnLookerModel implements Serializable{

    /**
     * total : 10
     * list : [{"photo_url":"http://yingyunapp.wbteam.cn/AppimageAppVideo/1.jpg","video_url":"http://yingyunapp.wbteam.cn/AppimageAppVideo/1.mp4","user":{"nickname":"13456788888","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"642e455d1c63b58c0486b00100e992f6","easemob_id":"13456788888","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}},{"photo_url":"http://yingyunapp.wbteam.cn/Appimagehttp://yingyunapp.wbteam.cn/Appimage//2018/03/20/1521517890.mp4.png","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521517890.mp4","user":{"nickname":"admin","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"aa86e51495029e11317f52ee9fc26e00","easemob_id":"","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}},{"photo_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521529601.mp4.jpg","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521529601.mp4","user":{"nickname":"美丽","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":null,"headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"07f22a7487b722bc809075d35c5f59eb","easemob_id":"13600000001","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}},{"photo_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532040.mp4.jpg","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532040.mp4","user":{"nickname":"13823925879","sex":"1","birthday":"2017-02-01","cityid":"442000","height":"170cm","weight":"60kg","appearance":"哦哦哦","job":"呃呃","income":"6000","emotion":"恋爱中","description":"","headimg":"http://yingyunapp.wbteam.cn/Appimage/2018/03/08/1520497418.jpg","astro":"水瓶座","age":"1","piccount":"0","cityname":"中山市","rkey":"214263c21ad4d499295ceee917f203a9","easemob_id":"13823925879","isvip":"0","positive":"3","neutral":"0","negative":"1","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}},{"photo_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532296.mp4.jpg","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532296.mp4","user":{"nickname":"123456","sex":"0","birthday":"1993-09-06","cityid":"0","height":"179cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"http://yingyunapp.wbteam.cn/Appimage/2018/03/14/1521031954.jpg","astro":"处女座","age":"24","piccount":"0","cityname":"","rkey":"8520679ff0cb1a2c4ff6d3431b9fdf38","easemob_id":"13600000002","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}},{"photo_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532809.mp4.jpg","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521532809.mp4","user":{"nickname":"安卓","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"b407300856716ab6333814db41f142d1","easemob_id":"13888888888","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}},{"photo_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521533541.mp4.png","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521533541.mp4","user":{"nickname":"13800138010","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"12c6eeb6deda12a0a50581afb06b3455","easemob_id":"13800138010","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}},{"photo_url":"http://yingyunapp.wbteam.cn/Appimage","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521537037.mp4","user":{"nickname":"15918254234","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"cb93adebca074324854b84d9bccbcc83","easemob_id":"15918254234","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}},{"photo_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521540314.mp4.jpg","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521540314.mp4","user":{"nickname":"18637172989","sex":"0","birthday":"0000-00-00","cityid":"0","height":"170cm","weight":"60kg","appearance":"帅气","job":"互联网","income":"10000","emotion":"单身","description":"","headimg":"http://yingyunapp.wbteam.cn/Appimage/2018/03/20/1521544412.jpg","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"fb9fcb622fe76d342bc044e96ff51753","easemob_id":"18637172989","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}},{"photo_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/26/1522048790.mp4.jpg","video_url":"http://yingyunapp.wbteam.cn/Appimage/2018/03/26/1522048790.mp4","user":{"nickname":"零零仪","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":null,"headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"127519c4c11d0506caa4799f68f96cdc","easemob_id":"18912340001","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}}]
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

    public class ListBean implements Serializable{
        /**
         * photo_url : http://yingyunapp.wbteam.cn/AppimageAppVideo/1.jpg
         * video_url : http://yingyunapp.wbteam.cn/AppimageAppVideo/1.mp4
         * user : {"nickname":"13456788888","sex":"0","birthday":"0000-00-00","cityid":"0","height":"0cm","weight":"0kg","appearance":"","job":"","income":"","emotion":"","description":"","headimg":"","astro":"白羊座","age":"0","piccount":"0","cityname":"","rkey":"642e455d1c63b58c0486b00100e992f6","easemob_id":"13456788888","isvip":"0","positive":"0","neutral":"0","negative":"0","isauth":"0","view_count":"0","like_count":"0","redbag_count":"0"}
         */

        private String photo_url;
        private String video_url;
        private UserBean user;

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public class UserBean implements Serializable{
            /**
             * nickname : 13456788888
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
             * rkey : 642e455d1c63b58c0486b00100e992f6
             * easemob_id : 13456788888
             * isvip : 0
             * positive : 0
             * neutral : 0
             * negative : 0
             * isauth : 0
             * view_count : 0
             * like_count : 0
             * redbag_count : 0
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
            private String isauth;
            private String view_count;
            private String like_count;
            private String redbag_count;

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

            public String getIsauth() {
                return isauth;
            }

            public void setIsauth(String isauth) {
                this.isauth = isauth;
            }

            public String getView_count() {
                return view_count;
            }

            public void setView_count(String view_count) {
                this.view_count = view_count;
            }

            public String getLike_count() {
                return like_count;
            }

            public void setLike_count(String like_count) {
                this.like_count = like_count;
            }

            public String getRedbag_count() {
                return redbag_count;
            }

            public void setRedbag_count(String redbag_count) {
                this.redbag_count = redbag_count;
            }
        }
    }
}
