package com.tiny.gcv;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by tiny on 2016/2/29.
 * 用于保存单选和多选的日期
 * 这里保存的单选和多选的日期，不是元数据，只是保存所选日期的备份
 * 但是该备份和所选择的日期 是 equal==true，对象不是同一个
 * 日期列表中的元数据，如果有外部数据绑定，这里的备份会没有该绑定数据
 *
 */
public class SimpleDataSelectPool implements DateSelectPool {


    public static final String TAG="SimpleDataSelectPool";

    final ArrayList<CalendarDay> arrayList;

    final  Map<CalendarDay,Integer> mSelectedMonth;


    final DataObserableImpl observable=new DataObserableImpl();
    public SimpleDataSelectPool() {
        arrayList=new ArrayList<>();
        mSelectedMonth=new HashMap<>();
    }

    @Override
    public void put(@NonNull CalendarDay day) {
        if(arrayList.contains(day)){
            return;
        }
        CalendarDay month= CalendarDay.from(day.getYear(),day.getMonth(),1);

        Integer count=mSelectedMonth.get(month);
        if(count==null){
            mSelectedMonth.put(month,1);
        }else
        mSelectedMonth.put(month,count+1);
        arrayList.add(day);
    }

    @Override
    public void remove(@NonNull CalendarDay day) {
        if(arrayList.remove(day)){
            CalendarDay month=CalendarDay.createMonth(day);
             ensureMonthValue(month,1);
            Integer count=mSelectedMonth.get(month);
            mSelectedMonth.put(month,count-1);
        }
    }

    @Override
    public void clear() {
        arrayList.clear();
        mSelectedMonth.clear();
    }

    @Override
    public boolean containDay(@NonNull CalendarDay day) {
        return arrayList.contains(day);
    }

    @Override
    public boolean containMonth(@NonNull CalendarDay yearAndMonth) {
        Integer count= mSelectedMonth.get(yearAndMonth);
       return count==null||count==0;
    }

    @Override
    public @NonNull List<CalendarDay> get(@NonNull CalendarDay yearAndMonth) {
        List<CalendarDay> result=new ArrayList<>();
        List<CalendarDay> list=arrayList;
        for(CalendarDay day:list){
           if(yearAndMonth.isSameMonth(yearAndMonth)){
               result.add(day);
           } ;
        }
        return result;
    }


    private void ensureMonthValue(CalendarDay month){
        ensureMonthValue(month,0);
    }
    private void ensureMonthValue(CalendarDay month,int min){
        Integer savedCount=mSelectedMonth.get(month);
        if(savedCount==null){
            error(month,min);
            return;
        }
        if(savedCount <min){
            error(month,min);
        }
    }



    @Override
    public void registerObserver(Observer observer) {
        observable.addObserver(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observable.deleteObserver(observer);
    }

    @Override
    public void notifyDataChanged(CalendarDay month) {
        observable.perfromChanged();
        observable.notifyObservers(month);
    }

    @Override
    public @NonNull  List<CalendarDay> getSelectedDays() {
        return new ArrayList<>(arrayList);
    }



    public String state() {
        return TAG+" state ";
    }
    private void error(CalendarDay toEnsure,int min){
        state();
        throw new IllegalStateException("SimpleDataSelectPool saved state error: "+toEnsure+" | to ensure count "+min);
    }


    static class DataObserableImpl extends Observable{
        public DataObserableImpl() {
        }

        @Override
        protected void setChanged() {
            super.setChanged();
        }
        public void perfromChanged(){
            setChanged();;
        }
    }
}
