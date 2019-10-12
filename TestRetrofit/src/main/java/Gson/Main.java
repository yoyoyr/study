package Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) {
//        String data = "        {\n" +
//                "            \"title\": \"Java Puzzlers: Traps, Pitfalls, and Corner Cases\",\n" +
//                "                \"isbn-10\": \"032133678X\",\n" +
//                "                \"isbn-13\": \"978-0321336781\",\n" +
//                "                \"authors\": [\n" +
//                "            \"Joshua Bloch\",\n" +
//                "                    \"Neal Gafter\"\n" +
//                "            ]\n" +
//                "        }";
//
//        System.out.println("---------------------------------------api");
//        Gson gson = new Gson();
//        Book book = gson.fromJson(data, Book.class);
//        System.out.println("book = " + book.toString());
//        System.out.println("book to json = " + gson.toJson(book));
//
//
//        System.out.println("---------------------------------------serializer");
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Book.class, new BookSerializer());
//        Gson myGson = gsonBuilder.create();
//        Book b = myGson.fromJson(data, Book.class);
//        String result = myGson.toJson(b);
//        System.out.println("b = " + b.toString());
//        System.out.println("serialize result = " + result);
//        System.out.println("serialize result = " + myGson.fromJson(result, Book.class));
//
//
//        System.out.println("---------------------------------------TypeAdapter");
//        GsonBuilder gb = new GsonBuilder();
//        gb.registerTypeAdapter(Book.class, new BookTypeAdapter());
//        Gson gsAdapter = gb.create();
//        Book bAdapter = gsAdapter.fromJson(data, Book.class);
//        result = gsAdapter.toJson(bAdapter);
//        System.out.println("bAdapter = " + bAdapter.toString());
//        System.out.println("adapter result = " + result);
//        System.out.println("serialize result = " + gsAdapter.fromJson(result, Book.class));
        double provision = 18450913092639.922093576931431680;
        double bonded = 17790091376203.3;
        double rate = 9.1;
        System.out.println("result = "+(provision/bonded)*rate);
    }

    public static String UTCToCST(String UTCStr) throws ParseException {
        Date date = null;
        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        date = sdf.parse(UTCStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR_OF_DAY) + 8);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        //calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
        return calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
    }

    /**
     * 函数功能描述:UTC时间转本地时间格式
     *
     * @param utcTime          UTC时间
     * @param localTimePattern 本地时间格式(要转换的本地时间格式)
     * @return 本地时间格式的时间
     */
    public static String utc2Local(String utcTime, String localTimePattern) {
        String utcTimePattern = "yyyy-MM-dd";
        String subTime = utcTime.substring(10);//UTC时间格式以 yyyy-MM-dd 开头,将utc时间的前10位截取掉,之后是含有多时区时间格式信息的数据

        //处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
        if (subTime.indexOf("+") != -1) {
            subTime = changeUtcSuffix(subTime, "+");
        }
        if (subTime.indexOf("-") != -1) {
            subTime = changeUtcSuffix(subTime, "-");
        }
        utcTime = utcTime.substring(0, 10) + subTime;

        //依据传入函数的utc时间,得到对应的utc时间格式
        //步骤一:处理 T
        if (utcTime.indexOf("T") != -1) {
            utcTimePattern = utcTimePattern + "'T'";
        }

        //步骤二:处理毫秒SSS
        if (utcTime.indexOf(".") != -1) {
            utcTimePattern = utcTimePattern + " HH:mm:ss.SSS";
        } else {
            utcTimePattern = utcTimePattern + " HH:mm:ss";
        }

        //步骤三:处理时区问题
        if (subTime.indexOf("+") != -1 || subTime.indexOf("-") != -1) {
            utcTimePattern = utcTimePattern + "XXX";
        } else if (subTime.indexOf("Z") != -1) {
            utcTimePattern = utcTimePattern + "'Z'";
        }

        if ("yyyy-MM-dd HH:mm:ss".equals(utcTimePattern) || "yyyy-MM-dd HH:mm:ss.SSS".equals(utcTimePattern)) {
            return utcTime;
        }

        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePattern);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUtcDate = null;
        try {
            gpsUtcDate = utcFormater.parse(utcTime);
        } catch (Exception e) {
            e.printStackTrace();
            return utcTime;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePattern);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUtcDate.getTime());
        return localTime;
    }

    /**
     * 函数功能描述:修改时间格式后缀
     * 函数使用场景:处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
     *
     * @param subTime
     * @param sign
     * @return
     */
    private static String changeUtcSuffix(String subTime, String sign) {
        String timeSuffix = null;
        String[] splitTimeArrayOne = subTime.split("\\" + sign);
        String[] splitTimeArrayTwo = splitTimeArrayOne[1].split(":");
        if (splitTimeArrayTwo[0].length() < 2) {
            timeSuffix = "+" + "0" + splitTimeArrayTwo[0] + ":" + splitTimeArrayTwo[1];
            subTime = splitTimeArrayOne[0] + timeSuffix;
            return subTime;
        }
        return subTime;
    }
}
