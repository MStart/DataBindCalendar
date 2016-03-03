package com.tiny.gcv;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tiny on 2015/12/28.
 */
public class CalendarDay {



    public static final int SUNDAY = 1;

    public static final int MONDAY = 2;

    public static final int TUESDAY = 3;

    public static final int WEDNESDAY = 4;


    public static final int THURSDAY = 5;


    public static final int FRIDAY = 6;


    public static final int SATURDAY = 7;

    public static final int[] WEEK_DAYS=new int[]{SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY};

    private int year;
    private int month;
    private int day;

    private int dayOfWeek;

    /**
     * 是否仅用于填充
     */
    boolean isSupplement;

    Object extra;



    public  Calendar transfer(){
        Calendar c=Calendar.getInstance();
        c.set(year,month,day);
        return c;
    }

    public static CalendarDay from(Calendar calendar){
        return new CalendarDay(calendar);
    }
    public static CalendarDay from(int year, int month, int day){
        return new CalendarDay(year,month,day);
    }
    public static  CalendarDay createMonth(CalendarDay day){
        return CalendarDay.from(day.getYear(),day.getMonth(),1);
    }
    public static CalendarDay today(){
        return new CalendarDay(Calendar.getInstance());
    }

    static CalendarDay supplement(){
        return new CalendarDay();
    }


    public void bindData(Object data){
        this.extra=data;
    }
    public void unBind(){
        this.extra=null;
    }
    public Object getBindData(){
        return extra;
    }
    private CalendarDay(){
        isSupplement =true;
    }
    public CalendarDay(Calendar calendar){
        this.year=calendar.get(Calendar.YEAR);
        this.month=calendar.get(Calendar.MONTH);
        this.day= calendar.get(Calendar.DAY_OF_MONTH);
        this.dayOfWeek= calendar.get(Calendar.DAY_OF_WEEK);
    }

    public CalendarDay(int year, int month, int day) {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayOfWeek= calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


    public boolean isWeekend(){
       return dayOfWeek==SATURDAY||dayOfWeek==SUNDAY;
    }
    public boolean isSupplement(){
        return isSupplement;
    }

    /**
     * 每个月的第一周开始不是周日的时候需要填满空余天数
     */
    public static List<CalendarDay> makeUpFirstWeek(CalendarDay firstOfMonth){
        int dayOfWeek=firstOfMonth.getDayOfWeek();
        ArrayList<CalendarDay> supplements=new ArrayList<>();
        for(int i=0;i<dayOfWeek-1;i++){
            supplements.add(supplement());
        }
        return supplements;
    }
    public static boolean isSameMonth(CalendarDay d1,CalendarDay d2){
        return d1.year==d2.year&&d1.month==d2.month;
    }

    public boolean after(CalendarDay day){
        if(this.year<day.year){
            return false;
        }
        if(this.year>day.year){
            return true;
        }
        if(this.month<day.month){
            return false;
        }
        if(this.month>day.month){
            return true;
        }
        if(this.day<day.day){
            return false;
        }
        if(this.day>day.day){
            return true;
        }
        return false;
    }
    public boolean isSameDay(CalendarDay day){
        return  day.year==this.year
                &&day.month==this.month
                &&day.day==this.day
                &&day.dayOfWeek==this.dayOfWeek;
    }
    public boolean isSameMonth(CalendarDay day){
        return  day.year==this.year
                &&day.month==this.month;
    }
    @Override
    public boolean equals(Object o) {
        if(o==null){
            return false;
        }
        if(!(o instanceof CalendarDay)){
            return false;
        }
        CalendarDay day= (CalendarDay) o;
        return  day.year==this.year
                &&day.month==this.month
                &&day.day==this.day
                &&day.dayOfWeek==this.dayOfWeek;
    }

    /**
     * 很重要，别改
     * 在  SimpleDataSelectPool 中 作为key的时候用到
     * @return
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public boolean before(CalendarDay day){
        return !after(day);
    }
    @Override
    public String toString() {
        return year+"&"+month+"&"+day;
    }
}
