package com.wbteam.YYzhiyue.network.api_service;


import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.AddRewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.ApplyPersonModel;
import com.wbteam.YYzhiyue.network.api_service.model.AstroModel;
import com.wbteam.YYzhiyue.network.api_service.model.AuthModel;
import com.wbteam.YYzhiyue.network.api_service.model.AvatarImageModel;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.BonusModel;
import com.wbteam.YYzhiyue.network.api_service.model.BumModel;
import com.wbteam.YYzhiyue.network.api_service.model.CityListModel;
import com.wbteam.YYzhiyue.network.api_service.model.CreateRewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.DatingModel;
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

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo
 * @date 2016/12/09  17:04
 */

public interface ApiService {

    //用户注册接口
    @FormUrlEncoded
    @POST("?service=User.Register")
    Observable<BaseResponse<RegisterModel>> User_Register(@Field("mobile") String mobile,
                                                          @Field("password") String password,
                                                          @Field("code") String code,
                                                          @Field("clientid") String clientid,
                                                          @Field("invite") String invite
    );

    //用户登陆接口
    @FormUrlEncoded
    @POST("?service=User.Login")
    Observable<BaseResponse<LoginModel>> User_Login(@Field("mobile") String mobile, @Field("password") String password, @Field("clientid") String clientid
    );

    //第三方登录
    @FormUrlEncoded
    @POST("?service=User.Thirdlogin")
    Observable<BaseResponse<ThirdloginModel>> User_Thirdlogin(@Field("third_uid") String third_uid,
                                                              @Field("third_type") String third_type,
                                                              @Field("nickname") String nickname,
                                                              @Field("headimg") String headimg,
                                                              @Field("clientid") String clientid
    );

    //发送验证码
    @FormUrlEncoded
    @POST("?service=User.Verifysms")
    Observable<BaseResponse<EmptyEntity>> User_Verifysms(@Field("mobile") String mobile,
                                                         @Field("type") String type
    );

    //绑定手机号
    @FormUrlEncoded
    @POST("?service=User.Bindmobile")
    Observable<BaseResponse<EmptyEntity>> User_Bindmobile(@Field("ukey") String ukey,
                                                          @Field("mobile") String mobile,
                                                          @Field("password") String password,
                                                          @Field("code") String code
    );

    //修改密码
    @FormUrlEncoded
    @POST("?service=User.Changepass")
    Observable<BaseResponse<EmptyEntity>> User_Changepass(@Field("ukey") String ukey,
                                                          @Field("mobile") String mobile,
                                                          @Field("password") String password,
                                                          @Field("newpassword") String newpassword
    );

    //提现申请
    @FormUrlEncoded
    @POST("?service=User.Withdraw")
    Observable<BaseResponse<EmptyEntity>> User_Withdraw(@Field("ukey") String ukey,
                                                        @Field("name") String name,
                                                        @Field("content") String content,
                                                        @Field("pay_type") String pay_type,
                                                        @Field("amount") String amount
    );

    //重置密码
    @FormUrlEncoded
    @POST("?service=User.Resetpass")
    Observable<BaseResponse<EmptyEntity>> User_Resetpass(
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("code") String code

    );


    //获取首页轮播广告
    @FormUrlEncoded
    @POST("?service=Main.Ad")
    Observable<BaseResponse<ArrayList<MainAdModel>>> Main_AD(@Field("ukey") String ukey
    );

    //获取首页用户列表
    @FormUrlEncoded
    @POST("?service=Main.Getlist")
    Observable<BaseResponse<MainListFriendModel>> Main_Getlist(@Field("ukey") String ukey,
                                                               @Field("page") String page
    );

    //获取约会列表
    @FormUrlEncoded
    @POST("?service=Dating.Getlist")
    Observable<BaseResponse<DatingModel>> Dating_Getlist(@Field("ukey") String ukey,
                                                         @Field("rec") String rec,
                                                         @Field("page") String page
    );

    //获取约会信息
    @FormUrlEncoded
    @POST("?service=Dating.Getinfo")
    Observable<BaseResponse<DatingInfoModel>> Dating_Getinfo(@Field("ukey") String ukey,
                                                             @Field("rkey") String rkey
    );

    //获取动态详情信息
    @FormUrlEncoded
    @POST("?service=Weibo.Getweiboinfo")
    Observable<BaseResponse<GetweiboinfoModel>> Weibo_Getweiboinfo(@Field("ukey") String ukey,
                                                                   @Field("wid") String wid
    );

    //动态评论列表
    @FormUrlEncoded
    @POST("?service=Weibo.Weibocommentlist")
    Observable<BaseResponse<WeiboCommentModel>> Weibocommentlist(@Field("ukey") String ukey,
                                                                 @Field("wid") String wid,
                                                                 @Field("page") String page
    );

    //动态评论
    @FormUrlEncoded
    @POST("?service=Weibo.Weibocomment")
    Observable<BaseResponse<WeiboCommentModel01>> Weibocomment(@Field("ukey") String ukey,
                                                               @Field("wid") String wid,
                                                               @Field("content") String content
    );

    //关注会员
    @FormUrlEncoded
    @POST("?service=Dating.Follow")
    Observable<BaseResponse<EmptyEntity>> Dating_Follow(@Field("ukey") String ukey,
                                                        @Field("rkey") String rkey
    );

    //获取视频/围观列表
    @FormUrlEncoded
    @POST("?service=Video.Getvideolist")
    Observable<BaseResponse<OnLookerModel>> Video_Getvideolist(@Field("ukey") String ukey,
                                                               @Field("page") String page
    );

    //我的视频/围观列表
    @FormUrlEncoded
    @POST("?service=User.Myvideolist")
    Observable<BaseResponse<MyVideoModel>> User_Myvideolist(@Field("ukey") String ukey,
                                                            @Field("type") String type,
                                                            @Field("page") String page
    );

    //删除我的视频
    @FormUrlEncoded
    @POST("?service=User.Removemyvideo")
    Observable<BaseResponse<EmptyEntity>> User_Removemyvideo(@Field("ukey") String ukey,
                                                             @Field("id") String id
    );

    //我的关注/关注列表
    @FormUrlEncoded
    @POST("?service=User.Myfollowlist")
    Observable<BaseResponse<MyfollowModel>> User_Myfollowlist(@Field("ukey") String ukey,
                                                              @Field("page") String page
    );

    //我的钱包
    @FormUrlEncoded
    @POST("?service=User.Mywallet")
    Observable<BaseResponse<Mywallet>> User_Mywallet(@Field("ukey") String ukey
    );

    //视频上传
    @Multipart
    @POST("?service=Video.Uploadvideo")
    Observable<BaseResponse<VideoModel>> Video_Uploadvideo(
            @Part("ukey") RequestBody ukey,
            @Part("title") RequestBody title,
            @Part MultipartBody.Part fileimg,
            @Part MultipartBody.Part filevideo
    );

    //意见反馈
    @Multipart
    @POST("?service=User.Feedback")
    Observable<BaseResponse<EmptyEntity>> User_Feedback(
            @Part("ukey") RequestBody ukey,
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part MultipartBody.Part fileimg
    );

    //更新用户定位
    @FormUrlEncoded
    @POST("?service=User.Updategps")
    Observable<BaseResponse<EmptyEntity>> User_Updategps(@Field("ukey") String ukey,
                                                         @Field("lng") String lng,
                                                         @Field("lat") String lat
    );

    //完善个人资料
    @FormUrlEncoded
    @POST("?service=User.Demand")
    Observable<BaseResponse<EmptyEntity>> User_Demand(@Field("ukey") String ukey,
                                                      @Field("provide") String provide,
                                                      @Field("hopeful") String hopeful
    );

    //编辑个人资料
    @FormUrlEncoded
    @POST("?service=User.Editinfo")
    Observable<BaseResponse<EmptyEntity>> User_Editinfo(@Field("ukey") String ukey,
                                                        @Field("nickname") String nickname,
                                                        @Field("sex") String sex,
                                                        @Field("birthday") String birthday,
                                                        @Field("height") String height,
                                                        @Field("weight") String weight,
                                                        @Field("appearance") String appearance,
                                                        @Field("job") String job,
                                                        @Field("income") String income,
                                                        @Field("emotion") String emotion,
                                                        @Field("location") String location,
                                                        @Field("description") String description,
                                                        @Field("invite") String invite
    );

    //获取自己资料
    @FormUrlEncoded
    @POST("?service=User.Getmy")
    Observable<BaseResponse<UserInfoModel>> User_Getmy(@Field("ukey") String ukey

    );

    //上传相册
    @Multipart
    @POST("?service=Gallery.Uploadalbum")
    Observable<BaseResponse<GalleryModel>> Uploadalbum(
            @Part("ukey") RequestBody ukey,
            @Part MultipartBody.Part file
    );

    //获取相册列表
    @FormUrlEncoded
    @POST("?service=Gallery.Getgallerylist")
    Observable<BaseResponse<GalleryListModel>> Getgallerylist(@Field("ukey") String ukey


    );

    //删除相册图片
    @FormUrlEncoded
    @POST("?service=Gallery.Delalbum")
    Observable<BaseResponse<EmptyEntity>> Gallery_Delalbum(@Field("ukey") String ukey,
                                                           @Field("pid") String pid
    );

    //获取出租范围资料
    @FormUrlEncoded
    @POST("?service=User.Getleaserinfo")
    Observable<BaseResponse<ScopeModel>> User_Getleaserinfo(@Field("ukey") String ukey
    );

    //设置出租范围资料
    @FormUrlEncoded
    @POST("?service=User.Setleaserinfo")
    Observable<BaseResponse<EmptyEntity>> User_Setleaserinfo(@Field("ukey") String ukey,
                                                             @Field("price") String price,
                                                             @Field("start_time") String start_time,
                                                             @Field("end_time") String end_time,
                                                             @Field("weixin") String weixin,
                                                             @Field("contact") String contact,
                                                             @Field("iscontact") String iscontact
    );

    //获取系统标签
    @FormUrlEncoded
    @POST("?service=Tag.Gettaglist")
    Observable<BaseResponse<TagModel>> Tag_Gettaglist(@Field("ukey") String ukey
    );
    //获取悬赏标签
    @FormUrlEncoded
    @POST("?service=Tag.Gettaglist")
    Observable<BaseResponse<TagModel1>> Tag_Gettaglist01(@Field("ukey") String ukey
    );

    //设置用户标签
    @FormUrlEncoded
    @POST("?service=User.Setusertag")
    Observable<BaseResponse<EmptyEntity>> User_Setusertag(@Field("ukey") String ukey,
                                                          @Field("tagids") String tagids
    );

    //获取用户标签
    @FormUrlEncoded
    @POST("?service=User.Getusertag")
    Observable<BaseResponse<TagModel>> User_Getusertag(@Field("ukey") String ukey
    );

    //上传头像
    @Multipart
    @POST("?service=User.Uploadhead")
    Observable<BaseResponse<AvatarImageModel>> User_Uploadhead(
            @Part("ukey") RequestBody ukey,
            @Part MultipartBody.Part file
    );

    //身份认证上传
    @Multipart
    @POST("?service=User.Uploadauth")
    Observable<BaseResponse<AuthModel>> User_Uploadauth(
            @Part("ukey") RequestBody ukey,
            @Part("name") RequestBody name,
            @Part("id_num") RequestBody id_num,
            @Part MultipartBody.Part fileimg,
            @Part MultipartBody.Part fileimg2,
            @Part MultipartBody.Part fileimg3
    );

    //上传视频
    @Multipart
    @POST("?service=User.Uploadvideoauth")
    Observable<BaseResponse<VideoModel>> Uploadvideoauth(
            @Part("ukey") RequestBody ukey,
            @Part("title") RequestBody title,
            @Part MultipartBody.Part fileimg,
            @Part MultipartBody.Part filevideo
    );

    //获取星座年龄
    @FormUrlEncoded
    @POST("?service=User.Getastro")
    Observable<BaseResponse<AstroModel>> User_Getastro(@Field("ukey") String ukey,
                                                       @Field("birthday") String birthday
    );

    //获取城市列表
    @FormUrlEncoded
    @POST("?service=District.Getcity")
    Observable<BaseResponse<CityListModel>> User_DistrictGetcity(@Field("ukey") String ukey
    );

    //获取悬赏列表
    @FormUrlEncoded
    @POST("?service=Event.Geteventlist")
    Observable<BaseResponse<RewardModel>> Event_Geteventlist(@Field("ukey") String ukey,
                                                             @Field("page") String page
    );

    //获取悬赏报名
    @FormUrlEncoded
    @POST("?service=Event.Attendevent")
    Observable<BaseResponse<EmptyEntity>> Event_Attendevent(@Field("ukey") String ukey,
                                                            @Field("id") String id
    );

    //抵金卷列表
    @FormUrlEncoded
    @POST("?service=Event.Bonuslist")
    Observable<BaseResponse<BonusModel>> Event_Bonuslist(@Field("ukey") String ukey
    );

    //我的抵金卷
    @FormUrlEncoded
    @POST("?service=User.Mybonuslist")
    Observable<BaseResponse<MyBonusModel>> User_Mybonuslist(@Field("ukey") String ukey
    );

    //发布悬赏报名
    @FormUrlEncoded
    @POST("?service=Event.Publishevent")
    Observable<BaseResponse<CreateRewardModel>> Event_Publishevent(@Field("ukey") String ukey,
                                                                   @Field("amount") String amount,
                                                                   @Field("sex") String sex,
                                                                   @Field("s_time") String s_time,
                                                                   @Field("e_time") String e_time,
                                                                   @Field("s_height") String s_height,
                                                                   @Field("e_height") String e_height,
                                                                   @Field("s_age") String s_age,
                                                                   @Field("e_age") String e_age,
                                                                   @Field("title") String title,
                                                                   @Field("limit_count") String limit_count,
                                                                   @Field("tagstr") String tagstr,
                                                                   @Field("timelong") String timelong,
                                                                   @Field("address") String address,
                                                                   @Field("bonusid") String bonusid,
                                                                   @Field("lng") String lng,
                                                                   @Field("lat") String lat
    );

    //确认完成/确认结束
    @FormUrlEncoded
    @POST("?service=Event.Confirmfinish")
    Observable<BaseResponse<EmptyEntity>> Event_Confirmfinish(@Field("ukey") String ukey,
                                                              @Field("id") String id
    );

    //获取报名人员
    @FormUrlEncoded
    @POST("?service=Event.Getattendmember")
    Observable<BaseResponse<ApplyPersonModel>> Getattendmember(@Field("ukey") String ukey,
                                                               @Field("id") String id, @Field("page") String page
    );

    //确认对象
    @FormUrlEncoded
    @POST("?service=Event.Confirmattend")
    Observable<BaseResponse<EmptyEntity>> Confirmattend(@Field("ukey") String ukey,
                                                        @Field("id") String id, @Field("rkey") String rkey
    );

    //悬赏评论
    @FormUrlEncoded
    @POST("?service=Event.Eventcomment")
    Observable<BaseResponse<EmptyEntity>> Eventcomment(@Field("ukey") String ukey,
                                                       @Field("id") String id, @Field("rkey") String rkey, @Field("score") String score
    );

    //取消参与悬赏
    @FormUrlEncoded
    @POST("?service=Event.Cancelattend")
    Observable<BaseResponse<EmptyEntity>> Cancelattend(@Field("ukey") String ukey,
                                                       @Field("id") String id
    );

    //微信支付接口
    @FormUrlEncoded
    @POST("?service=Pay.Wxpay")
    Observable<BaseResponse<WeixinModel>> Pay_Wxpay(@Field("ukey") String ukey,
                                                    @Field("ordersn") String ordersn
    );

    //微信会员充值接口
    @FormUrlEncoded
    @POST("?service=User.Recharge")
    Observable<BaseResponse<WeixinModel>> Recharge(@Field("ukey") String ukey,
                                                   @Field("pay_type") String pay_type,
                                                   @Field("pay_amount") String pay_amount
    );

    //购买VIP等级
    @FormUrlEncoded
    @POST("?service=User.Buyvip")
    Observable<BaseResponse<WeixinModel>> Buyvip(@Field("ukey") String ukey,
                                                 @Field("vipid") String vipid,
                                                 @Field("pay_type") String pay_type
    );

    //支付宝支付接口
    @FormUrlEncoded
    @POST("?service=Pay.Alipay")
    Observable<BaseResponse<EmptyEntity>> Pay_Alipay(@Field("ukey") String ukey,
                                                     @Field("ordersn") String ordersn
    );

    //我发布的悬赏
    @FormUrlEncoded
    @POST("?service=Event.Myeventlist")
    Observable<BaseResponse<PostRewardModel>> Event_Myeventlist(@Field("ukey") String ukey, @Field("page") String page
    );


    //购买VIP等级
    @FormUrlEncoded
    @POST("?service=User.Viplist")
    Observable<BaseResponse<VipModel>> Viplist(@Field("ukey") String ukey
    );

    //我参加的悬赏
    @FormUrlEncoded
    @POST("?service=Event.Myattendlist")
    Observable<BaseResponse<AddRewardModel>> Event_Myattendlist(@Field("ukey") String ukey, @Field("page") String page
    );

    //上传动态相册
    @Multipart
    @POST("?service=Weibo.Uploadalbum")
    Observable<BaseResponse<BumModel>> Weibo_Uploadalbum(
            @Part("ukey") RequestBody ukey,
            @Part MultipartBody.Part file
    );

    //发布动态
    @FormUrlEncoded
    @POST("?service=Weibo.Publishweibo")
    Observable<BaseResponse<EmptyEntity>> Weibo_Publishweibo(@Field("ukey") String ukey,
                                                             @Field("select_time") String select_time,
                                                             @Field("city") String city,
                                                             @Field("sex") String sex,
                                                             @Field("tags") String tags,
                                                             @Field("iscontact") String iscontact,
                                                             @Field("remark") String remark,
                                                             @Field("pics") String pics
    );

    //删除动态相册图片
    @FormUrlEncoded
    @POST("?service=Weibo.Delalbum")
    Observable<BaseResponse<EmptyEntity>> Weibo_Delalbum(@Field("ukey") String ukey,
                                                         @Field("pid") String pid
    );

    //获取动态列表
    @FormUrlEncoded
    @POST("?service=Weibo.Getweibolist")
    Observable<BaseResponse<WeiboListModel>> Weibo_Getweibolist(@Field("ukey") String ukey,
                                                                @Field("page") String page
    );

    //获取我的列表
    @FormUrlEncoded
    @POST("?service=User.Myweibolist")
    Observable<BaseResponse<WeiboListModel>> WeiboMyweibolist(@Field("ukey") String ukey
    );

    //赞和取消赞 动态
    @FormUrlEncoded
    @POST("?service=Weibo.Like")
    Observable<BaseResponse<EmptyEntity>> Weibo_Like(@Field("ukey") String ukey,
                                                     @Field("wid") String wid
    );
//    /**
//     * 使用rx+retrofit的方式获取数据
//     */
//    @GET("ezSQL/get_user.php")
//    Observable<BaseResponse<List<UserModel>>> getUsersByRx();

//
//    @GET("api/cook/list")
//    Observable<TngouResponse<List<Cook>>> getCookList(@Query("page") int page, @Query("rows") int rows);
}
