package com.lvbo.template.network;


import com.lvbo.template.entity.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lvbo on 16/7/19.
 */
public interface APISercive {


    //Get请求
    /*@GET("content/AboutGoWild")
    Observable<AboutGoWildResult> aboutGoWild(@Query("token") String token, @Query("lang") String lang);
    */

    //post请求 一
    /*@FormUrlEncoded
    @POST("member/register")
    Observable<RegisterResult> register(@FieldMap Map<String, String> map);
    */

    //post请求 二
    @FormUrlEncoded
    @POST("member/login")
    Observable<LoginResult> login(@Field("username") String username, @Field("password") String password, @Field("push_token") String push_token
            , @Field("device_type") String device_type, @Field("fb_id") String fb_id, @Field("lang") String lang);


    //上传文件
    /*@Multipart
    @POST("member/updateProfile")
    Observable<UpdateProfileResult> updateProfile(@Part MultipartBody.Part  file, @PartMap Map<String, RequestBody> params);
    */

    //下载文件
    /*
    * @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
    * */


    //返回String
    /*
    *
    * @GET("/iMan0.1/index.php")
    Observable<String> getAdList(@Query("m")String m, @Query("c")String controller, @Query("a")String GetAdviertList, @Query("DeviceName")String DeviceName, @Query("X")String X, @Query("Y")String Y);
    *
    * */
}
