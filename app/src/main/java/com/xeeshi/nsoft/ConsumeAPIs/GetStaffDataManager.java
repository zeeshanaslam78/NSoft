package com.xeeshi.nsoft.ConsumeAPIs;

import com.xeeshi.nsoft.App;

/**
 * Created by ZEESHAN on 14/08/16.
 */
public class GetStaffDataManager {

    private GetStaffDataService getStaffDataService;

    public GetStaffDataService callGetStaffDataService() {

        if (null == getStaffDataService) {
            getStaffDataService = App.getmAppInstance().getRetrofitGSONBaseURL().create(GetStaffDataService.class);
        }

        return getStaffDataService;

    }


}
