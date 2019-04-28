package com.test.viewpagedemo;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {
    @NonNull
    public static List<String> initApps = new ArrayList<>();

    public static Context context;
    static {
        initApps.add("com.app.ModuleApp");
        initApps.add("com.hook.App");
    }
}
