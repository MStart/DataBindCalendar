package com.tiny.gcv;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.tiny.gcv.formatter.WeekDayFormatter;


/**
 * Created by tiny on 2015/12/29.
 */
public class WeekDayView extends TextView {


    WeekDayFormatter formatter;
    int dayOfWeek;

    public WeekDayView(Context context,int dayOfWeek) {
        super(context);
        setGravity(Gravity.CENTER);
        this.dayOfWeek=dayOfWeek;
        this.formatter=WeekDayFormatter.DEFAULT;
        updateText();
    }
    public void setWeekDayFormatter(WeekDayFormatter formatter){
        if(formatter!=null){
            this.formatter=formatter;
            updateText();
        }
    }
    public int getDayOfWeek() {
        return dayOfWeek;


    }
    private void updateText(){
        setText(formatter.formatWeekDay(dayOfWeek));

    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        updateText();
    }


}
