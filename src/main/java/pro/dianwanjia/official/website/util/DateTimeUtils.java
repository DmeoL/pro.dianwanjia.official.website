package pro.dianwanjia.official.website.util;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description: 日期操作帮助类
 * @version V1.0.0
 */
public class DateTimeUtils {

    /**
     * 日期格式:yyyy-MM-dd
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式:HH:mm:ss
     */
    public static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 日期时间格式:yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期时间格式:yyyy-MM-dd HH:mm:ss
     */
    public static final String STR_DATETIME = "yyyyMMddHHmmss";
    
    // 取得当前系统时间 - 根据格式
    public static String currDate(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 根据格式：yyyy-MM-dd 来格式化时间
     * 
     * @param date
     *            d
     * @return
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 根据指定格式来格式化时间
     * 
     * @param date
     *            d
     * @param pattern
     *            p
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null || !StringUtils.hasText(pattern)) {
            return null;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据格式：yyyy-MM-dd转换时间
     * 
     * @param date
     *            d
     * @return
     */
    public static Date parse(String date) {
        return parse(date, DATE_PATTERN);
    }

    /**
     * 根据指定格式转换时间
     * 
     * @param date
     *            d
     * @param pattern
     *            p
     * @return
     */
    public static Date parse(String date, String pattern) {
        if (!StringUtils.hasText(date) || !StringUtils.hasText(pattern)) {
            return null;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int today() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static String now() {
        return new SimpleDateFormat(DATETIME_PATTERN).format(new Date());
    }

    /**
     * 从现在到给定的时间戳，相差的毫秒值
     * @param timestamp
     * @return
     */
    public static long timeMillisUntil(String timestamp) {
        try {
            long endTime = new SimpleDateFormat(DATETIME_PATTERN).parse(timestamp).getTime();
            return endTime - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("时间戳解析失败");
        }
    }

    public static String cronExpression(String announceAt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse(announceAt, DATETIME_PATTERN));
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return second + " " + minute + " " + hour + " " + day + " " + month + " ?";
    }

    /**
     * 比较日期
     * 
     * @param date1
     *            d
     * @param date2
     *            d
     * @return DATE1 > DATE2 返回true, DATE1 < DATE2 返回false
     */
    public static boolean compareDate(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            return dt1.getTime() > dt2.getTime();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 当前系统时间的时分秒
     * 
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 当前系统年份
     *
     * @return
     */
    public static int getCurrYear() {
        Calendar calendar = Calendar.getInstance();// 日历对象
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到设置日期的前几个月或后几个月的日期
     * 
     * @param date
     *            设置的日期，默认为当前日期
     * @param monthCount
     *            前几个月或后几个月（如：前一个月用-1表示，后一个月用1或者+1表示） 注意：数字前面的字符表示正负的意思
     * @return
     */
    public static String getDateByAddMonth(Date date, int monthCount) {
        Calendar calendar = Calendar.getInstance();// 日历对象
        date = null == date ? new Date() : date; // 默认为当前日期
        calendar.setTime(date); // 设置当前日期
        calendar.add(Calendar.MONTH, monthCount); // 月份加或减相应的次数
        // 指定时间格式 : DATETIME_PATTERN
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        return formatter.format(calendar.getTime());
    }

    public static String getLongDateByAddMonth(Date date, int dayCount) {
        String newDate = getDateByAddMonth(date, dayCount);
        return newDate + " 00:00:00";
    }

    /**
     * 得到设置日期的前几个天或后几个天的日期
     * 
     * @param date
     *            设置的日期，默认为当前日期
     * @param dayCount
     *            前几个天或后几个天（如：前一个天用-1表示，后一个天用1或者+1表示） 注意：数字前面的字符表示正负的意思
     * @return
     */
    public static String getDateByAddDay(Date date, int dayCount) {
        Calendar calendar = Calendar.getInstance();// 日历对象
        date = null == date ? new Date() : date; // 默认为当前日期
        calendar.setTime(date); // 设置当前日期
        calendar.add(Calendar.DATE, dayCount); // 天数加或减相应的次数
        // 指定时间格式 : DATETIME_PATTERN
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        return formatter.format(calendar.getTime());
    }

    public static String getLongDateByAddDay(Date date, int dayCount) {
        String newDate = getDateByAddDay(date, dayCount);
        return newDate + " 00:00:00";
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param startDate
     *            较小的时间
     * @param endDate
     *            较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date startDate, Date endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(endDate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String startDate, String endDate)
            throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(startDate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(endDate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 传入正则规则，进行验证是否通过
     * @param reg 正则表达式规则  - 如：\\d{4},\\d{4}
     * @param info 验证信息
     * @return true : 通过，false ： 不通过
     */
    public static boolean isValidInfoByReg(String reg, String info) {
        if(org.apache.commons.lang3.StringUtils.isBlank(info)) return false;
        Pattern p1 = Pattern.compile(reg);
//    	System.out.println("reg: " + reg + ", -- info: "+info + "验证结果： "+p1.matcher(info).matches());
        return p1.matcher(info).matches();
    }

    /**
     * 校验年份:不能大于当前年份
     *
     * @param periodicalYear
     * @return
     */
    public static boolean validYear(String periodicalYear) {
        // 进行使用自定义的规则验证
        if (!isValidInfoByReg("\\d{4}", periodicalYear))
            return false;
        // 取出当前年份
        int currYear = DateTimeUtils.getCurrYear();
        // 比较的年份
        int getYear = Integer.parseInt(periodicalYear);
        // 不能大于当前年份
        if (getYear > currYear)
            return false;
        return true;
    }

    /**
     * 验证年份
     * @param year
     * @return
     */
    public static boolean isYear(String year){
        if(org.apache.commons.lang3.StringUtils.isBlank(year)){
            return false;
        }
        String regex="^(19|20|21|22)[0-9]{2}$";
        return year.matches(regex);
    }

    // 判断日期格式:yyyy-mm-dd
    public static boolean isValidDate(String sDate) {

        if(org.apache.commons.lang3.StringUtils.isBlank(sDate)) return false;

        //基本格式的时间正则表达式
        String datePattern1 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern p1 = Pattern.compile(datePattern1);
//    	Matcher m = p1.matcher(sDate);
        if(p1.matcher(sDate).matches()){
            //正确的时间正则表达式
            String datePattern2 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
            Pattern p2 = Pattern.compile(datePattern2);
            return p2.matcher(sDate).matches();
        }
        return false;
    }

    /**
     * 判断时间格式 格式必须为“yyyy-MM-dd HH:mm:ss”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     * @param sDate
     * @return
     */
    public static boolean isValidLongDate(String sDate) {
        //String str = "2007-01-02 01:01:01";
        return isValidLongDate(sDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 判断时间格式 格式必须为“yyyy-MM-dd HH:mm:ss” 或 为 “yyyy-MM-dd”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     * @param sDate
     * @return
     */
    public static boolean isValidLongDate(String sDate, String pattern) {
        try{
            DateFormat formatter = new SimpleDateFormat(pattern);
            Date date = (Date)formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        }catch(Exception e){
            return false;
        }
    }

    public static void main(String[] args) {

        String exp = cronExpression("2018-10-26 8:30:45");
        System.out.println(exp);

        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd
        // HH:mm:ss SSS");
//        SimpleDateFormat formatter = new SimpleDateFormat(
//                "yyyy-MM-dd_HH_mm_ss_SSS");
//        Date currentTime = new Date();
//        String dateString = formatter.format(currentTime);
//        System.out.println(dateString);

        // 年份
//        System.out.println("year: " + getCurrYear());

//        String startDate = "2017-09-05";
//        String endDate = "2017-08-16";
//        try {
//            System.out.println(DateTimeUtils.daysBetween(startDate, endDate));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        // 年份
//        System.out.println("year: " + compareDate(startDate, endDate));
        
    }
}
