package com.tiny.gcv;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tiny on 2015/12/29.
 */
public abstract class BaseDecorator extends RecyclerView.ItemDecoration {


    public BaseDecorator() {
    }

    public abstract boolean shouldDecorate(int position, CalendarDay monthAndYear, CalendarDay day);


    public abstract void drawDecorator(Canvas c, RecyclerView parent,Rect rect, RecyclerView.State state, int position);

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int childCount = parent.getChildCount();
        int left, right, top, bottom;
        GridCalendarAdapter mAdapter= (GridCalendarAdapter) parent.getAdapter();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (shouldDecorate(position,mAdapter.getCurrent(), mAdapter.getItem(position))) {
                left = child.getLeft();
                right = child.getRight();
                top = child.getTop();
                bottom = child.getBottom();
                Rect rect = new Rect(left, top, right, bottom);
                drawDecorator(c, parent,rect, state, position);
            }
        }
    }
    public CalendarDay getCalendarDay(RecyclerView parent,int positionInAdapter){
        GridCalendarAdapter mAdapter= (GridCalendarAdapter) parent.getAdapter();
        return mAdapter.getItem(positionInAdapter);
    }
}
