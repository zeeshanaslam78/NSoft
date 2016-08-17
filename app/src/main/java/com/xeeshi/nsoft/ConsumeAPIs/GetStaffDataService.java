package com.xeeshi.nsoft.ConsumeAPIs;

import com.xeeshi.nsoft.Objects.User;
import com.xeeshi.nsoft.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by ZEESHAN on 13/08/16.
 */
public interface GetStaffDataService {

    @Headers("Accept: application/json")
    @GET(Constants.STAFF_LIST)
    Call<List<User>> GetStaffListData();

}
