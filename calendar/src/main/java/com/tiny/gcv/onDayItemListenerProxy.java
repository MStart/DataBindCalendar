package com.tiny.gcv;

/**
 * Created by tiny on 2016/2/29.
 * 日期单元点击事件的proxy，统一在GridCalenderView中处理点击事件
 */
public interface onDayItemListenerProxy {

    void onDayItemClick(int position, CalendarDay day, CalendarDay monthAndYear);


}
