package com.tiny.gcv;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tiny on 2016/2/29.
 */
public class SelectedDateDecorator extends BaseSelectedDateDecorator {

    public SelectedDateDecorator(RecyclerView recyclerView, Drawable drawable) {
        super(recyclerView, drawable);
    }

    public SelectedDateDecorator(RecyclerView recyclerView, int color) {
        super(recyclerView, color);
    }

    @Override
    public void drawDecorator(Canvas c, RecyclerView parent, Rect rect, RecyclerView.State state, int position) {
        drawable.setBounds(rect);
        drawable.draw(c);
    }
}
