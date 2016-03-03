package com.tiny.gcv.formatter;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by tiny on 2015/12/29.
 */
public interface WeekDayFormatter {


    String formatWeekDay(int dayOfWeek);


    public  WeekDayFormatter DEFAULT=new WeekDayFormatter() {

        Calendar calendar=Calendar.getInstance();

        @Override
        public String formatWeekDay(int dayOfWeek) {
            calendar.set(Calendar.DAY_OF_WEEK,dayOfWeek);
            return calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT, Locale.getDefault());
        }
    };
}
