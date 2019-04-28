package com.hotfix;

import android.support.annotation.NonNull;

import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerServiceInternals;
import com.test.viewpagedemo.LoggerUtils;

import java.io.File;

/**
 * 本类的作用：决定在patch安装完以后的后续操作，默认实现是杀进程，这里改成不杀死进程
 * app重启后更新
 */
public class CustomResultService extends DefaultTinkerResultService {
    private static final String TAG = "Tinker.SampleResultService";

    //返回patch文件的最终安装结果
    @Override
    public void onPatchResult(@NonNull PatchResult result) {
        LoggerUtils.LOGD("patch result = " + result.toString());
        if (result == null) {
            LoggerUtils.LOGE("DefaultTinkerResultService received null result!!!!");
            return;
        }

        //first, we want to kill the recover process
        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());

        // if success and newPatch, it is nice to delete the raw file, and restart at once
        // only main process can load an upgrade patch!
        if (result.isSuccess) {
            deleteRawPatchFile(new File(result.rawPatchFilePath));
            if (checkIfNeedKill(result)) {
            } else {
                LoggerUtils.LOGW("I have already install the newly patch version!");
            }
        }
    }
}
