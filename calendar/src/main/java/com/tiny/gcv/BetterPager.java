package com.tiny.gcv;

import android.content.Context;
import android.support.v4.view.BetterViewPager;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Custom ViewPager that allows swiping to be disabled.
 */
public class BetterPager extends BetterViewPager {

    private boolean pagingEnabled = true;

    public BetterPager(Context context) {
        super(context);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (pagingEnabled) {
            super.scrollTo(x, y);
        }
    }

    /**
     * enable disable viewpager scroll
     *
     * @param pagingEnabled false to disable paging, true for paging (default)
     */
    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    /**
     * @return is this viewpager allowed to page
     */
    public boolean isPagingEnabled() {
        return pagingEnabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return pagingEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        super.setPageTransformer(reverseDrawingOrder, transformer);
    }
}