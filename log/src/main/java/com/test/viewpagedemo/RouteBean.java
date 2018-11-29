package com.test.viewpagedemo;

public class RouteBean {
    public final static String groupAroute = "arouterTest";
    public final static String modularize = "/com/modularActivity";
    public final static String startActivityWithResult = "/com/withResult";
    public final static String fragment = "/com/fragment1";
    //不同组件之间group名字不能一样，否则代码编译合并的时候会报错
    //Program type already present: com.alibaba.android.arouter.routes.ARouter$$Gr
    public final static String hook = "/test/hook";
}
