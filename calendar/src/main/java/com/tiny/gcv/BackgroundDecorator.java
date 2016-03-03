package com.tiny.gcv;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tiny on 2015/12/29.
 */
public class BackgroundDecorator extends BaseDecorator {


    Drawable drawable;

    public BackgroundDecorator(Drawable drawable) {
        this.drawable = drawable;
    }

    public BackgroundDecorator(int color) {
        this.drawable = new ColorDrawable(color);
    }

    @Override
    public boolean shouldDecorate(int position, CalendarDay monthAndYear, CalendarDay day) {
        return true;
    }

    @Override
    public void drawDecorator(Canvas c, RecyclerView parent, Rect rect, RecyclerView.State state, int position) {
        drawable.setBounds(rect);
        drawable.draw(c);
    }
}
