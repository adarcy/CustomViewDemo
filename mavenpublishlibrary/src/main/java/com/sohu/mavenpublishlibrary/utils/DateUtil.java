package com.sohu.mavenpublishlibrary.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * 提供基础时间处理类
 *
 * @author lee
 */
public class DateUtil {

    private final static int MILL = 1000;
    private final static int SECOND = 60;
    private final static int HOUR = 24;
    private final static String PATTERN_YEAR_MONTH_DAY = "yyyy-MM-dd";
    private final static String PATTERN_YEAR = "yyyy";

    /**
     * 传日期得时间戳
     *
     * @param dateStr
     * @return
     */
    public static long parseStringDate(String dateStr) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 传整数得分:秒格式字符串 (计时器)
     *
     * @param time
     * @return
     */
    public static String getMSFromTimer2(long time) {
        String str = "00:00";
        if (time > 0) {
            int minute = 0;
            if (time >= MILL * SECOND) {
                minute = (int) (time / (MILL * SECOND));
                time -= minute * MILL * SECOND;
            }
            int sec = (int) (time / MILL);
            str = unitTimerFormat2(minute) + ":" + unitTimerFormat2(sec);
        }
        return str;
    }

    /**
     * 时间格式 00:00
     *
     * @param time
     * @return
     */
    public static String unitTimerFormat2(int time) {
        return String.format("%02d", time);
    }

    public static String longToDateHM(long time) {
//        String str = "00:00";
//        if (time > 0) {
//            int minute = 0;
//            if (time >= MILL * SECOND) {
//                minute = (int) (time / (MILL * SECOND));
//                time -= minute * MILL * SECOND;
//            }
//            int sec = (int) (time / MILL);
//            str = unitTimerFormat2(minute) + "分" + unitTimerFormat2(sec) + "秒";
//        }
//        return str;
        return longToDateHM(time, "00:00");
    }

    /**
     * 时间格式 MM:SS
     *
     * @param time
     * @param defaultResult time <= 0 时的值
     * @return
     */
    public static String longToDateHM(long time, String defaultResult) {
        String str = defaultResult;
        if (time > 0) {
            int minute = 0;
            if (time >= MILL * SECOND) {
                minute = (int) (time / (MILL * SECOND));
                time -= minute * MILL * SECOND;
            }
            int sec = (int) (time / MILL);
            str = unitTimerFormat2(minute) + "分" + unitTimerFormat2(sec) + "秒";
        }
        return str;
    }

    /**
     * 把毫秒数转换成时分秒
     * 规则：
     * 1、精确到分钟即可
     * 2、不满一分钟，按一分钟算。超过1分钟，不足两分钟的，取2分钟的时间，依此类推。
     * 3、超过1小时的，显示“xx小时xx分钟”
     *
     * @param millis
     * @return
     */
    public static String millisToStringShort(long millis) {
        StringBuilder strBuilder = new StringBuilder();
        long temp = millis;
        long hper = SECOND * SECOND * MILL;
        long mper = SECOND * MILL;
        long sper = MILL;
        if (temp < mper) {
            strBuilder.append(1).append("分钟");
            return strBuilder.toString();
        }
        if (temp / hper > 0) {
            strBuilder.append(temp / hper).append("小时");
        }
        temp = temp % hper;
        if (temp / mper > 0) {
            if (temp % mper / sper > 0) {
                strBuilder.append((temp / mper) + 1).append("分钟");
            } else {
                strBuilder.append(temp / mper).append("分钟");
            }
        }
//        temp = temp % mper;
//        if (temp / sper > 0) {
//            strBuilder.append(temp / sper).append("秒");
//        }
        return strBuilder.toString();
    }

    /**
     * yyyy-MM-dd HH:mm
     *
     * @return
     */
    public static String longToDateYMDHM(long currTime) {
        // 根据long类型的毫秒数生命一个date类型的时间
        Date dateOld = new Date(currTime);
        String dateTime = new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(dateOld);
        return dateTime;
    }

    /**
     * yyyy-MM-dd
     *
     * @param currTime
     * @return
     */
    public static String longToDateYDM(long currTime) {
        // 根据long类型的毫秒数生命一个date类型的时间
        Date dateOld = new Date(currTime);
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(dateOld);
        return dateTime;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String longToDateYMDHMSS(long currTime) {
        Date dateOld = new Date(currTime); // 根据long类型的毫秒数生命一个date类型的时间
        String dateTime = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(dateOld);
        return dateTime;
    }

    /**
     * yyyy年MM月dd日
     *
     * @param currTime
     * @return
     */
    public static String longToDateYDM2(long currTime) {
        // 根据long类型的毫秒数生命一个date类型的时间
        Date dateOld = new Date(currTime);
        String dateTime = new SimpleDateFormat("yyyy年MM月dd日").format(dateOld);
        return dateTime;
    }

    /**
     * HH:mm
     *
     * @param currTime
     * @return
     */
    public static String longToDateHM2(long currTime) {
        // 根据long类型的毫秒数生命一个date类型的时间
        Date dateOld = new Date(currTime);
        String dateTime = new SimpleDateFormat("HH:mm").format(dateOld);
        return dateTime;
    }

    /**
     * 毫秒转成时分秒
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {
        if (timeMs <= 0 || timeMs >= HOUR * SECOND * SECOND * MILL) {
            return "00:00";
        }
        int totalSeconds = timeMs / MILL;
        int seconds = totalSeconds % SECOND;
        int minutes = (totalSeconds / SECOND) % SECOND;
        int hours = totalSeconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 毫秒转成时00:00:00
     *
     * @param timeMs
     * @return
     */
    public static String evaluationStringForTime(int timeMs) {
        if (timeMs <= 0 || timeMs >= HOUR * SECOND * SECOND * MILL) {
            return "00:00";
        }
        int totalSeconds = timeMs / MILL;
        int seconds = totalSeconds % SECOND;
        int minutes = (totalSeconds / SECOND) % SECOND;
        int hours = totalSeconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 毫秒转成秒 62 * 1000 ms return 01:02
     *
     * @param timeMs
     * @return
     */
    public static String stringForTimeS(int timeMs) {
        if (timeMs <= 0) {
            return "0";
        }
        float timeMsf = timeMs;
        if (timeMs > SECOND * MILL) {
            return stringForTime(timeMs);
        }
        float seconds = timeMsf / MILL;
        return ((int) Math.ceil(seconds)) + "";
    }

    public static int stringForTime(long timeMs) {
        if (timeMs <= 0) {
            return 0;
        }
        float timeMsf = timeMs;
        float seconds = timeMsf / MILL;
        return (int) Math.ceil(seconds);
    }

    public static boolean isToaday(long time) {
        return longToDateYDM2(time).equals(longToDateYDM2(System.currentTimeMillis()));
    }

    public static String second2Time(int timeMs) {
        return stringForTime(timeMs * 1000);
    }

    public static String longToDateYMDHMS(long timeMillis) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            return df.format(timeMillis);
        } catch (Exception e) {
            Log.d("DateUtil", Log.getStackTraceString(e));
        }
        return "";
    }

    /**
     * @param ts
     * @return true 包含毫秒的13位时间戳
     */
    public static boolean isSSS(long ts) {
        return ts > 999999999999L && ts < 10000000000000L;
    }

    /**
     * 转换成包含毫秒的13位时间戳
     *
     * @param ts
     * @return
     */
    public static long change2SSS(long ts) {
        if (!isSSS(ts)) {
            int size = 1;
            int TEN = 10;
            int THIRTEEN = 13;
            long tsTemp = ts;
            while ((tsTemp = tsTemp / TEN) != 0) {
                size++;
            }
            if (size < THIRTEEN) {
                ts = (long) (ts * Math.pow(10, (THIRTEEN - size)));
            } else {
                ts = (long) (ts / Math.pow(10, (size - THIRTEEN)));
            }

        }
        return ts;
    }

    /**
     * 起始时间转换
     *
     * @param currentTime 当前时间
     * @param startTime   起始时间
     * @return
     */
    public static String startTime(long currentTime, long startTime) {
        //当前年份
        if (isThisTime(currentTime, startTime, PATTERN_YEAR)) {
            if(isYesterDay(currentTime, startTime)){
                //昨天
                return new SimpleDateFormat("昨天 HH:mm~", Locale.CHINESE).format(startTime);
            } else if (isThisTime(currentTime, startTime, PATTERN_YEAR_MONTH_DAY)) {
                //今天
                return new SimpleDateFormat("今天 HH:mm~ ", Locale.CHINESE).format(startTime);
            }else{
                //本年  不显示年份
                return new SimpleDateFormat("MM-dd HH:mm~", Locale.CHINESE).format(startTime);
            }
        } else {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm~", Locale.CHINESE).format(startTime);
        }
    }

    /**
     * 结束时间转换
     *
     * @param startTime 当前时间
     * @param endTime   起始时间
     * @return
     */
    public static String endTime(long startTime, long endTime) {
        if (isThisTime(startTime, endTime, PATTERN_YEAR_MONTH_DAY)) {
            //同天的时间段 结束时间只显示 时分
            return new SimpleDateFormat("HH:mm", Locale.CHINESE).format(endTime);
        } else if (isThisTime(startTime, endTime, PATTERN_YEAR)) {
            //同年的时间段 结束时间只显示 月日：时分
            return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINESE).format(endTime);
        } else {
            //不同年的 结束时间显示年份
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINESE).format(endTime);
        }
    }

    private static boolean isYesterDay(long currentTime, long time) {
        boolean isYesterday = false;
        try {
            long day = HOUR * SECOND * SECOND * MILL;
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YEAR_MONTH_DAY);
            Date date = sdf.parse(sdf.format(new Date(currentTime)));
            if (time < date.getTime() && time > (date.getTime() - day)) {
                isYesterday = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isYesterday;
    }

    /**
     * 给的时间按照一定的格式转换后  进行比对
     *
     * @param currentTime
     * @param time
     * @return
     */
    private static boolean isThisTime(long currentTime, long time, String pattern) {
        Date date = new Date(currentTime);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param1 = sdf.format(date);

        date.setTime(time);
        String param2 = sdf.format(date);
        return param1.equals(param2);
    }

    /**
     * 返回测评时间
     * @param day1
     * @param day2
     * @return
     */
    public static String getEvaluationPublicTime(long day1, long day2){
        if(isSameDay(day1,day2)){
            return "今天 "+new SimpleDateFormat("HH:mm", Locale.CHINESE).format(day2);
        }else if(isTomorrow(day1,day2)){
            return "明天 "+new SimpleDateFormat("HH:mm", Locale.CHINESE).format(day2);
        }else{
            return new SimpleDateFormat(" MM-dd HH:mm", Locale.CHINESE).format(day2);
        }
    }

    /**
     * 是否是同一天
     * @param day1
     * @param day2
     * @return
     */
    public static boolean isSameDay(long day1, long day2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(day1);
        int d1 = instance.get(Calendar.DAY_OF_YEAR);
        instance.setTimeInMillis(day2);
        int d2 = instance.get(Calendar.DAY_OF_YEAR);
        return d1 == d2;
    }

    /**
     * 是否是明天
     * @param day1
     * @param day2
     * @return
     */
    public static boolean isTomorrow(long day1, long day2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(day1);
        int y1 = instance.get(Calendar.YEAR);
        int m1 = instance.get(Calendar.MONTH)+1;
        int maxM = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
        int d1 = instance.get(Calendar.DAY_OF_YEAR);
        instance.setTimeInMillis(day2);
        int y2 = instance.get(Calendar.YEAR);
        int m2 = instance.get(Calendar.MONTH)+1;
        int d2 = instance.get(Calendar.DAY_OF_YEAR);
        if(y1 == y2){
            return d1+1 == d2;
        }else{
            //如果正好2021 12 31 - 2022 01 01 相邻的两年
            if(y1+1 == y2){
                //day1是12月另day2是一月
                if(m1 == 12 && m2 == 1){
                    //day1是31号day2是相邻年的第1天
                    if(maxM == 31 && d2==1){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    /**
     * 通过格式化的字符串返回毫秒
     *
     * @param dateStr
     * @return
     */
    public static long getMisByFormatStr(String dateStr) {
        if(TextUtils.isEmpty(dateStr)){
            return 0;
        }
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
   