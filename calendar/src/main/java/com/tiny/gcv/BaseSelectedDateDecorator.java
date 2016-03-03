package com.tiny.gcv;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by tiny on 2016/2/29.
 *
 */
public  abstract  class BaseSelectedDateDecorator extends BaseDecorator{

    private DateSelectPool mDateSelectedPool;

    private List<CalendarDay> mSelectedDays;

    private RecyclerView recyclerView;

    protected Drawable drawable;

    CalendarDay month;

    public BaseSelectedDateDecorator(RecyclerView recyclerView , Drawable drawable) {
        this.drawable=drawable;
        this.recyclerView=recyclerView;
    }
    public BaseSelectedDateDecorator(RecyclerView recyclerView , int color) {
        this(recyclerView,new ColorDrawable(color));
    }

    void init( DateSelectPool pool,CalendarDay month){
        this.mDateSelectedPool=pool;
        this.month=month;
        this.mSelectedDays=pool.getSelectedDays();
    }


    Observer observer(){
        return observer;
    }

    /**
     * 这里向DateSelectedPool 同步获取一份当前分月的被选择日期的本地备份
     * 然后刷新ui
     */
     void syncSelectedDate(){
        mSelectedDays=mDateSelectedPool.getSelectedDays();
    }

    public void notifyDataSetChanged(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean shouldDecorate(int position, CalendarDay monthAndYear, CalendarDay day) {
        if(day.isSupplement()){
            return false;
        }
        return mSelectedDays!=null&&mSelectedDays.contains(day);
    }

    Observer observer =new Observer() {
        @Override
        public void update(Observable observable, Object data) {
            if(data==null||data.equals(month)){
                syncSelectedDate();
                notifyDataSetChanged();
            }
        }
    };

}
