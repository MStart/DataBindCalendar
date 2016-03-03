package com.tiny.gcv;

/**
 * Created by tiny on 2015/12/30.
 */
public interface DataBinder<T> {
    boolean shouldBindData(T t, CalendarDay day);
}
