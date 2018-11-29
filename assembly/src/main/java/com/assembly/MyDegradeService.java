package com.assembly;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.launcher.ARouter;
import com.test.viewpagedemo.LoggerUtils;

@Route(path = "/error/*")
public class MyDegradeService implements DegradeService {
    @Override
    public void onLost(Context context, Postcard postcard) {
        LoggerUtils.LOGD("degrade lost");
        ARouter.getInstance().build("/error/errorActivity").navigation();
    }

    @Override
    public void init(Context context) {

    }
}
