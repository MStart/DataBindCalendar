package com.tiny.gcv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by tiny on 2016/1/6.
 */
public class GCVRecyclerView extends RecyclerView {


    public GCVRecyclerView(Context context) {
        super(context);
    }

    public GCVRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GCVRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int width=MeasureSpec.getSize(widthSpec);
        int height=width/7*6;
        super.onMeasure(widthSpec, MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
    }
}
