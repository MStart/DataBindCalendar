package com.tiny.gcv;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tiny on 2015/12/29.
 */
public class GridDividerDecoration extends BaseDecorator {


    GradientDrawable mDrawable;


    public GridDividerDecoration(int color) {
        mDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, null);
        mDrawable.setStroke(1, color);
    }

    @Override
    public boolean shouldDecorate(int position, CalendarDay monthAndYear, CalendarDay day) {
        return !day.isSupplement();
    }

    @Override
    public void drawDecorator(Canvas c, RecyclerView parent, Rect rect, RecyclerView.State state, int position) {
        mDrawable.setBounds(rect);
        mDrawable.draw(c);
    }


}
