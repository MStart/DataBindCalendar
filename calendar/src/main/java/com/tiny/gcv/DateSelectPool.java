package com.tiny.gcv;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.Observer;

/**
 * Created by tiny on 2016/2/29.
 * 定义在单选和多选情况下的选择数据的保存读取的方法
 * 这里加入观察者模式，当数据发生变化时，通知观察者（这里在decorator中）刷新ui
 *
 */
public interface DateSelectPool {



    void put(@NonNull CalendarDay day);

    void remove(@NonNull CalendarDay day);

    void clear();

    boolean containDay(@NonNull CalendarDay day);

    boolean containMonth(@NonNull CalendarDay yearAndMonth);

    @NonNull
    List<CalendarDay> get(@NonNull CalendarDay yearAndMonth);


    @NonNull
    List<CalendarDay> getSelectedDays();


    void registerObserver(Observer observer);

    void unregisterObserver(Observer observer);


    void notifyDataChanged(CalendarDay month);


}
