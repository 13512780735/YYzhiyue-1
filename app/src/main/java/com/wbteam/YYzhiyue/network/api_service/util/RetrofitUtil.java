package com.wbteam.YYzhiyue.network.api_service.util;


import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.network.api_service.ApiService;
import com.wbteam.YYzhiyue.network.api_service.consts.Consts;
import com.wbteam.YYzhiyue.network.api_service.model.AddRewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.ApplyPersonModel;
import com.wbteam.YYzhiyue.network.api_service.model.AstroModel;
import com.wbteam.YYzhiyue.network.api_service.model.AuthModel;
import com.wbteam.YYzhiyue.network.api_service.model.AvatarImageModel;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.BonusModel;
import com.wbteam.YYzhiyue.network.api_service.model.BumModel;
import com.wbteam.YYzhiyue.network.api_service.model.CityListModel;
import com.wbteam.YYzhiyue.network.api_service.model.CommentBean;
import com.wbteam.YYzhiyue.network.api_service.model.CreateRewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.DatingModel;
import com.wbteam.YYzhiyue.network.api_service.model.EaseMobUserInfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.GalleryListModel;
import com.wbteam.YYzhiyue.network.api_service.model.GalleryModel;
import com.wbteam.YYzhiyue.network.api_service.model.GetweiboinfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.LoginModel;
import com.wbteam.YYzhiyue.network.api_service.model.MainAdModel;
import com.wbteam.YYzhiyue.network.api_service.model.MainListFriendModel;
import com.wbteam.YYzhiyue.network.api_service.model.MyBonusModel;
import com.wbteam.YYzhiyue.network.api_service.model.MyVideoModel;
import com.wbteam.YYzhiyue.network.api_service.model.MyfollowModel;
import com.wbteam.YYzhiyue.network.api_service.model.Mywallet;
import com.wbteam.YYzhiyue.network.api_service.model.NotifyModel;
import com.wbteam.YYzhiyue.network.api_service.model.OnLookerModel;
import com.wbteam.YYzhiyue.network.api_service.model.PostRewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.RegisterModel;
import com.wbteam.YYzhiyue.network.api_service.model.RewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.ScopeModel;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel1;
import com.wbteam.YYzhiyue.network.api_service.model.ThirdloginModel;
import com.wbteam.YYzhiyue.network.api_service.model.VideoModel;
import com.wbteam.YYzhiyue.network.api_service.model.VipModel;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboCommentModel;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboCommentModel01;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboListModel;
import com.wbteam.YYzhiyue.network.api_service.model.WeixinModel;
import com.wbteam.YYzhiyue.network.api_service.model.WithdrawModel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo.util
 * @date 2016/12/12  10:38
 */

public class RetrofitUtil {

    public static final int DEFAULT_TIMEOUT = 5;

    private Retrofit mRetrofit;
    private ApiService mApiService;

    private static RetrofitUtil mInstance;

    /**
     * 私有构造方法
     */
    private RetrofitUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(builder.build())
                .baseUrl(Consts.APP_HOST)
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }

    /**
     * 用于用户注册
     *
     * @param subscriber
     */
    public void getUsersRegister(String mobile, String password, String code, String clientid, String invite, String retype, Subscriber<BaseResponse<RegisterModel>> subscriber) {
        mApiService.User_Register(mobile, password, code, clientid, invite, retype)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用户提现记录
     *
     * @param subscriber
     */
    public void Withdrawlog(String ukey, Subscriber<BaseResponse<WithdrawModel>> subscriber) {
        mApiService.Withdrawlog(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据环信ID获取用户资料
     *
     * @param subscriber
     */
    public void Getuserinfo(String ukey, String easemobid, Subscriber<BaseResponse<DatingInfoModel>> subscriber) {
        mApiService.Getuserinfo(ukey, easemobid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用户系统通知
     *
     * @param subscriber
     */
    public void Getnotifylist(String ukey, String page, Subscriber<BaseResponse<NotifyModel>> subscriber) {
        mApiService.Getnotifylist(ukey, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于用户登录
     *
     * @param subscriber
     */
    public void getUsersLogin(String mobile, String password, String clientid, Subscriber<BaseResponse<LoginModel>> subscriber) {
        mApiService.User_Login(mobile, password, clientid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发送验证码
     *
     * @param subscriber
     */
    public void getVerifysms(String mobile, String type, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Verifysms(mobile, type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 绑定手机号
     *
     * @param subscriber
     */
    public void User_Bindmobile(String ukey, String mobile, String password, String code, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Bindmobile(ukey, mobile, password, code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改密码
     *
     * @param subscriber
     */
    public void User_Changepass(String ukey, String mobile, String password, String newpassword, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Changepass(ukey, mobile, password, newpassword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * //提现申请
     *
     * @param subscriber
     */
    public void User_Withdraw(String ukey, String name, String content, String pay_type, String amount, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Withdraw(ukey, name, content, pay_type, amount)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 充值密码
     *
     * @param subscriber
     */
    public void getResetpass(String mobile, String password, String code, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Resetpass(mobile, password, code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 第三方登录
     *
     * @param subscriber
     */
    public void getUsersLogin(String third_uid, String third_type, String nickname, String headimg, String clientid, Subscriber<BaseResponse<ThirdloginModel>> subscriber) {
        mApiService.User_Thirdlogin(third_uid, third_type, nickname, headimg, clientid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取首页轮播广告
     *
     * @param subscriber
     */
    public void Main_AD(String ukey, Subscriber<BaseResponse<ArrayList<MainAdModel>>> subscriber) {
        mApiService.Main_AD(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取首页用户列表
     *
     * @param subscriber
     */
    public void MainGetlist(String ukey, String page, Subscriber<BaseResponse<MainListFriendModel>> subscriber) {
        mApiService.Main_Getlist(ukey, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取约会列表
     *
     * @param subscriber
     */
    public void DatingGetlist(String ukey, String rec, String page, Subscriber<BaseResponse<DatingModel>> subscriber) {
        mApiService.Dating_Getlist(ukey, rec, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取约会详情
     *
     * @param subscriber
     */
    public void DatingGetinfo(String ukey, String rkey, Subscriber<BaseResponse<DatingInfoModel>> subscriber) {
        mApiService.Dating_Getinfo(ukey, rkey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取约会评论列表
     *
     * @param subscriber
     */
    public void Weibocommentlist(String ukey, String wid, String page, Subscriber<BaseResponse<WeiboCommentModel>> subscriber) {
        mApiService.Weibocommentlist(ukey, wid, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取约会动态评论
     *
     * @param subscriber
     */
    public void Weibocomment(String ukey, String wid, String content, Subscriber<BaseResponse<WeiboCommentModel01>> subscriber) {
        mApiService.Weibocomment(ukey, wid, content)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取好友信息
     *
     * @param subscriber
     */
    public void Weibo_Getweiboinfo(String ukey, String wid, Subscriber<BaseResponse<GetweiboinfoModel>> subscriber) {
        mApiService.Weibo_Getweiboinfo(ukey, wid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 关注会员
     *
     * @param subscriber
     */
    public void DatingFollow(String ukey, String rkey, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Dating_Follow(ukey, rkey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取视频/围观列表
     *
     * @param subscriber
     */
    public void VideoGetvideolist(String ukey, String page, Subscriber<BaseResponse<OnLookerModel>> subscriber) {
        mApiService.Video_Getvideolist(ukey, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的视频/围观列表
     *
     * @param subscriber
     */
    public void UserMyvideolist(String ukey, String type, String page, Subscriber<BaseResponse<MyVideoModel>> subscriber) {
        mApiService.User_Myvideolist(ukey, type, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的视频/围观列表
     *
     * @param subscriber
     */
    public void User_Removemyvideo(String ukey, String id, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Removemyvideo(ukey, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的关注/关注列表
     *
     * @param subscriber
     */
    public void UserMyfollowlist(String ukey, String page, Subscriber<BaseResponse<MyfollowModel>> subscriber) {
        mApiService.User_Myfollowlist(ukey, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的钱包
     *
     * @param subscriber
     */
    public void User_Mywallet(String ukey, Subscriber<BaseResponse<Mywallet>> subscriber) {
        mApiService.User_Mywallet(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 视频上传
     *
     * @param subscriber
     */
    public void VideoUploadvideo(RequestBody ukey, RequestBody title, MultipartBody.Part fileimg, MultipartBody.Part filevideo, Subscriber<BaseResponse<VideoModel>> subscriber) {
        mApiService.Video_Uploadvideo(ukey, title, fileimg, filevideo)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 意见反馈
     *
     * @param subscriber
     */
    public void UserFeedback(RequestBody ukey, RequestBody title, RequestBody content, MultipartBody.Part fileimg, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Feedback(ukey, title, content, fileimg)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用户举报
     *
     * @param ukey
     * @param title
     * @param content
     * @param subscriber
     */
    public void User_Informer(String ukey, String title, String content, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Informer(ukey, title, content)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 更新用户定位
     *
     * @param subscriber
     */
    public void getUserUpdategps(String ukey, String lng, String lat, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Updategps(ukey, lng, lat)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 完善个人资料
     *
     * @param subscriber
     */
    public void getDemand(String ukey, String provide, String hopeful, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Demand(ukey, provide, hopeful)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 编辑个人资料
     *
     * @param subscriber
     */
    public void getUserEditinfo(String ukey, String nickname, String sex, String birthday, String height, String weight, String appearance, String job, String income, String emotion, String location, String description, String invite, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Editinfo(ukey, nickname, sex, birthday, height, weight, appearance, job, income, emotion, location, description, invite)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取自己资料
     *
     * @param subscriber
     */
    public void getUserGetmy(String ukey, Subscriber<BaseResponse<UserInfoModel>> subscriber) {
        mApiService.User_Getmy(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 上传相册
     *
     * @param subscriber
     */
    public void Uploadalbum(RequestBody ukey, MultipartBody.Part file, Subscriber<BaseResponse<GalleryModel>> subscriber) {
        mApiService.Uploadalbum(ukey, file)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取自己资料
     *
     * @param subscriber
     */
    public void Getgallerylist(String ukey, Subscriber<BaseResponse<GalleryListModel>> subscriber) {
        mApiService.Getgallerylist(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 删除相册图片
     *
     * @param subscriber
     */
    public void Gallery_Delalbum(String ukey, String pid, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Gallery_Delalbum(ukey, pid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取出租范围资料
     *
     * @param subscriber
     */
    public void getGetleaserinfo(String ukey, Subscriber<BaseResponse<ScopeModel>> subscriber) {
        mApiService.User_Getleaserinfo(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 设置出租范围资料
     *
     * @param subscriber
     */
    public void getSetleaserinfo(String ukey, String price, String start_time, String end_time, String weixin, String contact, String iscontact, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Setleaserinfo(ukey, price, start_time, end_time, weixin, contact, iscontact)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取系统标签
     *
     * @param subscriber
     */
    public void getGettaglist(String ukey, Subscriber<BaseResponse<TagModel>> subscriber) {
        mApiService.Tag_Gettaglist(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取系统标签
     *
     * @param subscriber
     */
    public void getGettaglist1(String ukey, Subscriber<BaseResponse<TagModel1>> subscriber) {
        mApiService.Tag_Gettaglist01(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 设置用户标签
     *
     * @param subscriber
     */
    public void getSetusertag(String ukey, String tagids, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.User_Setusertag(ukey, tagids)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取用户标签
     *
     * @param subscriber
     */
    public void getSetusertag(String ukey, Subscriber<BaseResponse<TagModel>> subscriber) {
        mApiService.User_Getusertag(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 上传视频
     *
     * @param subscriber
     */
    public void Uploadvideoauth(RequestBody ukey, RequestBody title, MultipartBody.Part fileimg, MultipartBody.Part filevideo, Subscriber<BaseResponse<VideoModel>> subscriber) {
        mApiService.Uploadvideoauth(ukey, title, fileimg, filevideo)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 上传头像
     *
     * @param subscriber
     */
    public void getUploadhead(RequestBody ukey, MultipartBody.Part file, Subscriber<BaseResponse<AvatarImageModel>> subscriber) {
        mApiService.User_Uploadhead(ukey, file)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 实名认证
     *
     * @param subscriber
     */
    public void User_Uploadauth(RequestBody ukey, RequestBody name, RequestBody id_num, MultipartBody.Part fileimg, MultipartBody.Part fileimg2, MultipartBody.Part fileimg3, Subscriber<BaseResponse<AuthModel>> subscriber) {
        mApiService.User_Uploadauth(ukey, name, id_num, fileimg, fileimg2, fileimg3)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取星座年龄
     *
     * @param subscriber
     */
    public void GetGetastro(String ukey, String birthday, Subscriber<BaseResponse<AstroModel>> subscriber) {
        mApiService.User_Getastro(ukey, birthday)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取城市列表
     *
     * @param subscriber
     */
    public void GetcityDistrict(String ukey, Subscriber<BaseResponse<CityListModel>> subscriber) {
        mApiService.User_DistrictGetcity(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 获取悬赏報名
     *
     * @param subscriber
     */
    public void GetAttendevent(String ukey, String id, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Event_Attendevent(ukey, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 抵金卷列表
     *
     * @param subscriber
     */
    public void Event_Bonuslist(String ukey, Subscriber<BaseResponse<BonusModel>> subscriber) {
        mApiService.Event_Bonuslist(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的抵金卷
     *
     * @param subscriber
     */
    public void User_Mybonuslist(String ukey, Subscriber<BaseResponse<MyBonusModel>> subscriber) {
        mApiService.User_Mybonuslist(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发布悬赏
     *
     * @param subscriber
     */
    public void GetPublishevent(String ukey, String amount, String sex, String s_time, String e_time, String s_height
            , String e_height, String s_age, String e_age, String title, String limit_count, String tagstr, String timelong, String address, String bonusid, String lng, String lat, Subscriber<BaseResponse<CreateRewardModel>> subscriber) {
        mApiService.Event_Publishevent(ukey, amount, sex, s_time, e_time, s_height, e_height, s_age, e_age, title, limit_count, tagstr, timelong, address, bonusid, lng, lat)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 确认完成
     *
     * @param subscriber
     */
    public void GetConfirmfinish(String ukey, String id, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Event_Confirmfinish(ukey, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 悬赏评论
     *
     * @param subscriber
     */
    public void Eventcomment(String ukey, String id, String rkey, String score, String remark, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Eventcomment(ukey, id, rkey, score, remark)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 悬赏评论
     *
     * @param subscriber
     */
    public void Commentcategory(String ukey, String category, Subscriber<BaseResponse<CommentBean>> subscriber) {
        mApiService.Commentcategory(ukey, category)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 取消参与悬赏
     *
     * @param subscriber
     */
    public void Cancelattend(String ukey, String id, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Cancelattend(ukey, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 触发我到了,发送通知
     *
     * @param subscriber
     */
    public void Wentto(String ukey, String id, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Wentto(ukey, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * Event.Confirmfinish
     *
     * @param subscriber
     */
    public void Confirmattend(String ukey, String id, String rkey, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Confirmattend(ukey, id, rkey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 微信支付接口
     *
     * @param subscriber
     */
    public void PayWxpay(String ukey, String ordersn, Subscriber<BaseResponse<WeixinModel>> subscriber) {
        mApiService.Pay_Wxpay(ukey, ordersn)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 余额支付接口
     *
     * @param subscriber
     */
    public void Pay_Balance(String ukey, String ordersn, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Pay_Balance(ukey, ordersn)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 微信会员充值接口
     *
     * @param subscriber
     */
    public void Recharge(String ukey, String pay_type, String pay_amount, Subscriber<BaseResponse<WeixinModel>> subscriber) {
        mApiService.Recharge(ukey, pay_type, pay_amount)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 购买VIP等级
     *
     * @param subscriber
     */
    public void Buyvip(String ukey, String vipid, String pay_type, Subscriber<BaseResponse<WeixinModel>> subscriber) {
        mApiService.Buyvip(ukey, vipid, pay_type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 支付宝支付接口
     *
     * @param subscriber
     */
    public void PayAlipay(String ukey, String ordersn, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Pay_Alipay(ukey, ordersn)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我发布的悬赏
     *
     * @param subscriber
     */
    public void Myeventlist(String ukey, String page, Subscriber<BaseResponse<PostRewardModel>> subscriber) {
        mApiService.Event_Myeventlist(ukey, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 会员等级列表
     *
     * @param subscriber
     */
    public void Viplist(String ukey, Subscriber<BaseResponse<VipModel>> subscriber) {
        mApiService.Viplist(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我参加的悬赏
     *
     * @param subscriber
     */
    public void Myattendlist(String ukey, String page, Subscriber<BaseResponse<AddRewardModel>> subscriber) {
        mApiService.Event_Myattendlist(ukey, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取报名人员
     *
     * @param subscriber
     */
    public void MyGetattendmember(String ukey, String id, String page, Subscriber<BaseResponse<ApplyPersonModel>> subscriber) {
        mApiService.Getattendmember(ukey, id, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 上传动态相册
     *
     * @param subscriber
     */
    public void getUploadalbum(RequestBody ukey, MultipartBody.Part file, Subscriber<BaseResponse<BumModel>> subscriber) {
        mApiService.Weibo_Uploadalbum(ukey, file)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发布动态
     *
     * @param subscriber
     */
    public void Publishweibo(String ukey, String select_time, String city, String sex, String tags, String iscontact, String remark, String pics, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Weibo_Publishweibo(ukey, select_time, city, sex, tags, iscontact, remark, pics)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 删除动态相册图片
     *
     * @param subscriber
     */
    public void Delalbum(String ukey, String pid, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Weibo_Delalbum(ukey, pid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取悬赏列表
     *
     * @param subscriber
     */
    public void Geteventlist(String ukey, String page, Subscriber<BaseResponse<RewardModel>> subscriber) {
        mApiService.Event_Geteventlist(ukey, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取动态列表
     *
     * @param subscriber
     */
    public void Getweibolist(String ukey, String page, Subscriber<BaseResponse<WeiboListModel>> subscriber) {
        mApiService.Weibo_Getweibolist(ukey, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取我的列表
     *
     * @param subscriber
     */
    public void GetMyweibolist(String ukey, Subscriber<BaseResponse<WeiboListModel>> subscriber) {
        mApiService.WeiboMyweibolist(ukey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 赞和取消赞 动态
     *
     * @param subscriber
     */
    public void GetLike(String ukey, String wid, Subscriber<BaseResponse<EmptyEntity>> subscriber) {
        mApiService.Weibo_Like(ukey, wid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
