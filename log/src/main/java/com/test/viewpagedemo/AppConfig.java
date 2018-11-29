package com.test.viewpagedemo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {
    public static List<String> initApps = new ArrayList<>();

    public static Context context;
    static {
        initApps.add("com.app.ModuleApp");
        initApps.add("com.hook.App");
    }
}
