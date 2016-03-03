package com.tiny.gcv.formatter;


import com.tiny.gcv.CalendarDay;

/**
 * Created by tiny on 2015/12/29.
 */
public interface TitleFormatter {

    String format(CalendarDay day);


    TitleFormatter DEFAULT=new TitleFormatter() {
        @Override
        public String format(CalendarDay day) {
            return (day.getMonth()+1)+" 月  "+day.getYear();
        }
    };
}
