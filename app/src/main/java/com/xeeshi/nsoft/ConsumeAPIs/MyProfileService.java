package com.xeeshi.nsoft.ConsumeAPIs;

import com.xeeshi.nsoft.Objects.UserData;
import com.xeeshi.nsoft.Utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by ZEESHAN on 16/08/16.
 */
public interface MyProfileService {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST(Constants.GET_PERSONAL_INFO)
    Call<UserData> GetPersonalInfo(@Field("uuid") String uuid);


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST(Constants.INSERT_PERSONAL_INFO)
    Call<UserData> InsertPersonalInfo(
            @Field("name") String name,
            @Field("date") String date,
            @Field("uuid") String uuid);


}
