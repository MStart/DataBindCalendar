package com.tiny.gcv;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by tiny on 2015/12/29.
 */
public class DateRangeIndex {



    private SparseArrayCompat<CalendarDay> dayCache =new SparseArrayCompat<>();

    CalendarDay min;
    CalendarDay max;
    int count;


    public DateRangeIndex(CalendarDay min, CalendarDay max){
        this.min= CalendarDay.from(min.getYear(),min.getMonth(),1);
        this.max= CalendarDay.from(max.getYear(),max.getMonth(),1);
        this.count=indexOf(this.max)+1;

    }
    public int indexOf(CalendarDay day){
        if((day.after(this.min)&&day.before(this.max))||day==this.min||day==this.max){
           int yearDiff=day.getYear()-this.min.getYear();
            int monthDiff=day.getMonth()-this.min.getMonth();
            return yearDiff*12+monthDiff;
        }else{
            return -1;
        }
    }
    public CalendarDay getItem(int position) {

        CalendarDay re = dayCache.get(position);
        if (re != null) {
            return re;
        }

        int numY = position / 12;
        int numM = position % 12;

        int year = min.getYear() + numY;
        int month = min.getMonth() + numM;
        if (month >= 12) {
            year += 1;
            month -= 12;
        }
        re = CalendarDay.from(year, month, 1);
        dayCache.put(position, re);
        return re;
    }

    public int getCount(){
        return count;
    }
}
