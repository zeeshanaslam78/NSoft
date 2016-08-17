package com.xeeshi.nsoft.ConsumeAPIs;

import com.xeeshi.nsoft.App;

/**
 * Created by ZEESHAN on 16/08/16.
 */
public class MyProfileManager {

    private MyProfileService myProfileService;

    public MyProfileService callMyProfileService() {

        if (null == myProfileService) {
            myProfileService = App.getmAppInstance().getRetrofitGSONBaseURL().create(MyProfileService.class);
        }

        return myProfileService;
    }

}
