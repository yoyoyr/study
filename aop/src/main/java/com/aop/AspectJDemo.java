package com.aop;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;

import com.test.viewpagedemo.AppConfig;
import com.test.viewpagedemo.LoggerUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect   //必须使用@AspectJ标注，这样class DemoAspect就等同于 aspect DemoAspect了
public class AspectJDemo {

    private boolean doClick;

    //防止重复点击
    @Around("execution(* android.view.View.OnClickListener+.onClick(..)) " +
            "&& within(com.test.viewpagedemo.AOP.AOPActivity)")
    public void click(ProceedingJoinPoint joinPoint) {
        LoggerUtils.LOGD("position = " + joinPoint.getSourceLocation()
                + ",thread = " + Thread.currentThread().getName());
        if (doClick) {
            LoggerUtils.LOGD("do with click.ignore");
        } else {
            doClick = true;
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                LoggerUtils.LOGE(throwable);
            }
            doClick = false;
        }
    }

    //获取输入输出参数
    @Around("execution(* com.test.viewpagedemo.AOP.AOPActivity.param(..))")
    public Object param(ProceedingJoinPoint joinPoint) {
        LoggerUtils.LOGD("hook param()");
        Object[] params = joinPoint.getArgs();
        for (int i = 0; i < params.length; ++i) {
            LoggerUtils.LOGD("param" + i + " = " + params[i]);
        }
        try {
            Object ret = joinPoint.proceed(params);
            LoggerUtils.LOGD("return " + ret);
            return ret;
        } catch (Throwable throwable) {
            LoggerUtils.LOGE(throwable);
            return "";
        }
    }

    //根据注解筛选
    @Around("execution(@com.aop.CheckPermission * *(..))&&@annotation(checkPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, CheckPermission checkPermission) {
        String permisson = checkPermission.permission();
        int result = AppConfig.context.checkCallingPermission(permisson);
        if (PackageManager.PERMISSION_GRANTED == result) {
            return true;
        }
        LoggerUtils.LOGD("checkPermission -> checkCallingPermission = false");
        String[] pkgs = AppConfig.context.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        PackageManager pm = AppConfig.context.getPackageManager();
        if (pkgs != null && pkgs.length > 0) {
            for (String pkgName : pkgs) {
                try {
                    PackageInfo packageInfo = pm.getPackageInfo(pkgName, PackageManager.GET_PERMISSIONS);
                    String[] permissions = packageInfo.requestedPermissions;
                    for (String str : permissions) {
                        if (str.equals(permisson)) {
                            LoggerUtils.LOGD("has permission");
                            return joinPoint.proceed();
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        LoggerUtils.LOGD("checkPermission -> PackageInfo = false");
        throw new NullPointerException("no permission");
    }

    //打印输入输出函数
    @Around("execution(public * com.test.viewpagedemo.AOP.AOPActivity.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; ++i) {
            LoggerUtils.LOGD("param = " + args[i]);
        }

        Object ret = joinPoint.proceed();
        LoggerUtils.LOGD("return " + ret);
        return ret;
    }

}
