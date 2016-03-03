package com.tiny.gcv;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tiny on 2016/2/29.
 */
public class CurrentDayDecorator extends BaseDecorator {

    final Drawable drawable;
    CalendarDay current;
    public CurrentDayDecorator(Drawable drawable) {
        this.drawable=drawable;
        current=CalendarDay.today();
    }
    public CurrentDayDecorator(int color){
        this(new ColorDrawable(color));
    }

    @Override
    public boolean shouldDecorate(int position, CalendarDay monthAndYear, CalendarDay day) {
        return day.isSameDay(current);
    }

    @Override
    public void drawDecorator(Canvas c, RecyclerView parent, Rect rect, RecyclerView.State state, int position) {
        drawable.setBounds(rect);
        drawable.draw(c);
    }
}
