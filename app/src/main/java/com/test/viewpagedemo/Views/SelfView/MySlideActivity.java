package com.test.viewpagedemo.Views.SelfView;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;
import com.test.viewpagedemo.Reflector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);

        List<LineB> lines = new ArrayList<LineB>();
        lines.add(new LineB("4000", "五折", "节省2000"));
        lines.add(new LineB("2000", "五折", "节省1000"));
        lines.add(new LineB("1000", "五折", "节省500"));
        Line line = ((Line) findViewById(R.id.linechar));
        line.setData(lines);
        line.setOnPointClickListener(new Line.onPointClickListener() {
            @Override
            public void onClick(@NonNull LineB lineB) {
                LoggerUtils.LOGD("line = " + lineB.toString());
            }
        });

        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(1);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.BLACK);
        colors.add(Color.GRAY);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);

        ((Arc) findViewById(R.id.arc)).setData(data, colors);


        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);

        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);


        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);


        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);


        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        List<String> txs = new ArrayList<>();
        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");
        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");

        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");
        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");

        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");
        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");

        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");
        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");

        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");
        txs.add("1");
        txs.add("2");
        txs.add("3");
        txs.add("4");
        ((LineChar) findViewById(R.id.line)).setData(values, txs);
//        progressDialog("交易数据", "正在更新...");
//        simpleDialog("交易数据发现新版本,是否更新?");
    }

//    StatFs获取外部存储空间的大小

    //    proc/meminfo 获取总内存 ams获取使用内存
    @NonNull
    public Object getMeminfo() {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));

            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(mi);
            long availableSize = mi.availMem / 1024;
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent + "%";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "无结果";
    }


    //  /proc/stat记录了cpu使用的时间片和空闲时间片.计算两次极段时间内的(total-idle)/total得到使用率
    public static String getCPURateDesc_All() {
        String path = "/proc/stat";// 系统CPU信息文件
        long totalJiffies[] = new long[2];
        long totalIdle[] = new long[2];
        int firstCPUNum = 0;//设置这个参数，这要是防止两次读取文件获知的CPU数量不同，导致不能计算。这里统一以第一次的CPU数量为基准
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        Pattern pattern = Pattern.compile(" [0-9]+");
        for (int i = 0; i < 2; i++) {
            totalJiffies[i] = 0;
            totalIdle[i] = 0;
            try {
                fileReader = new FileReader(path);
                bufferedReader = new BufferedReader(fileReader, 8192);
                int currentCPUNum = 0;
                String str;
                while ((str = bufferedReader.readLine()) != null && (i == 0 || currentCPUNum < firstCPUNum)) {
                    if (str.toLowerCase().startsWith("cpu")) {
                        currentCPUNum++;
                        int index = 0;
                        Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            try {
                                long tempJiffies = Long.parseLong(matcher.group(0).trim());
                                totalJiffies[i] += tempJiffies;
                                if (index == 3) {//空闲时间为该行第4条栏目
                                    totalIdle[i] += tempJiffies;
                                }
                                index++;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (i == 0) {
                        firstCPUNum = currentCPUNum;
                        try {//暂停50毫秒，等待系统更新信息。
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        double rate = -1;
        if (totalJiffies[0] > 0 && totalJiffies[1] > 0 && totalJiffies[0] != totalJiffies[1]) {
            rate = 1.0 * ((totalJiffies[1] - totalIdle[1]) - (totalJiffies[0] - totalIdle[0])) / (totalJiffies[1] - totalJiffies[0]);
        }

        LoggerUtils.LOGD("zrx---- cpu_rate:" + rate);
        return String.format("cpu:%.2f", rate);
    }


    /**
     * uid =  0 root
     * uid = 1000 system
     * 其余 \system\core\include\private\Android_filesystem_config.h
     *
     * @param savedInstanceState
     */
    private void getBetteryState(Bundle savedInstanceState) {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo("com.androidstudypoint", PackageManager.GET_META_DATA);

            UserManager mUm = (UserManager) getSystemService(Context.USER_SERVICE);
            final List<UserHandle> profiles;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                profiles = mUm.getUserProfiles();
                Object o = Reflector.on("com.android.internal.os.BatteryStatsHelper")
                        .constructor(Context.class)
                        .newInstance(this);
                Reflector.on("com.android.internal.os.BatteryStatsHelper")
                        .method("create", Bundle.class)
                        .callByCaller(o, savedInstanceState);
//            refreshStats(int statsType, int asUser)
//            public static final int STATS_CURRENT = 1;
                Reflector.on("com.android.internal.os.BatteryStatsHelper")
                        .method("refreshStats", int.class, List.class)
                        .callByCaller(o, 0, profiles);

//            public List<BatterySipper> getUsageList() {
                List list = Reflector.on("com.android.internal.os.BatteryStatsHelper")
                        .method("getUsageList")
                        .callByCaller(o);
                Reflector getUID = Reflector.on("com.android.internal.os.BatterySipper")
                        .method("getUid");
                Reflector value = Reflector.on("com.android.internal.os.BatterySipper")
                        .field("value");
                Reflector drainType = Reflector.on("com.android.internal.os.BatterySipper")
                        .field("drainType");
                for (int i = 0; i < list.size(); ++i) {
                    BatterySipper batterySipper = new BatterySipper();
                    batterySipper.drainType = ((Enum) drainType.get(list.get(i))).ordinal();
                    batterySipper.userId = getUID.callByCaller(list.get(i));
                    batterySipper.value = value.get(list.get(i));
                    batterySipper.packageNames = pm.getPackagesForUid(batterySipper.userId);
                    LoggerUtils.LOGD("batterySipper = " + batterySipper.toString());
                }
            }
        } catch (Reflector.ReflectedException e) {
            LoggerUtils.LOGE(e);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void simpleDialog(String title) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog("交易数据", "正在更新...");
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * 进度条对话框
     *
     * @param title
     * @param msg
     */
    public void progressDialog(String title, String msg) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(false);// 设置点击空白处也不能关闭该对话框

//        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//         dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置采用圆形进度条

//        dialog.setMax(100);
//        dialog.setIndeterminate(true);//设置不显示明确的进度
//        dialog.setIndeterminate(false);// 设置显示明确的进度

        dialog.setButton(ProgressDialog.BUTTON_POSITIVE, "确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 这里添加点击后的逻辑
                    }
                });
        dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 这里添加点击后的逻辑
                    }
                });
        dialog.show();

    }
}
