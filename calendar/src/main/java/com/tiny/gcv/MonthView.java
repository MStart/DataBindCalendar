package com.tiny.gcv;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.tiny.gcv.formatter.WeekDayFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiny on 2015/12/29.
 */
public class MonthView extends LinearLayout {

    RecyclerView mRecyclerView;
    LinearLayout mWeekDayLayout;
    GridCalendarAdapter mAdapter;
    WeekDayFormatter weekDayFormatter;
    ArrayList<WeekDayView> mWeekDayViews=new ArrayList<>();
     CalendarDay month;
    onDayItemListenerProxy mDayItemClickListener;
    int mGridItemBackground;


    public MonthView(Context context) {
        this(context,null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mRecyclerView=new RecyclerView(getContext());
        setupChildView();
    }
    public void setWeekDayFormatter(WeekDayFormatter weekDayFormatter){
        if(weekDayFormatter!=null&&weekDayFormatter!=this.weekDayFormatter){
            return;
        }
        this.weekDayFormatter=weekDayFormatter;
        for(WeekDayView v:mWeekDayViews){
            v.setWeekDayFormatter(this.weekDayFormatter);
        }
    }
    private void setupChildView(){
        mWeekDayLayout=new LinearLayout(getContext());
        mWeekDayLayout.setGravity(LinearLayout.HORIZONTAL);
        for (int dayOfWeek : CalendarDay.WEEK_DAYS) {
            WeekDayView view = new WeekDayView(getContext(), dayOfWeek);
            view.setWeekDayFormatter(weekDayFormatter);
            LayoutParams lp = new LayoutParams(0, 100, 1);
            mWeekDayLayout.addView(view, lp);
            mWeekDayViews.add(view);
        }
        addView(mWeekDayLayout,createLayoutParams());
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),7));
        addView(mRecyclerView,createLayoutParams());
        mAdapter=new GridCalendarAdapter(getContext(),this);
        mAdapter.setGridItemBackground(mGridItemBackground);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setDecorators(ArrayList<BaseDecorator> decorators){
        for(BaseDecorator decorator:decorators){
            mRecyclerView.addItemDecoration(decorator);
        }
    }
    public void addDecorator(RecyclerView.ItemDecoration decorator){
        this.mRecyclerView.addItemDecoration(decorator);
    }
    private LayoutParams createLayoutParams(){
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public void setOnDayItemClickProxy(onDayItemListenerProxy proxy){
        this.mDayItemClickListener=proxy;
        this.mAdapter.setonItemClickProxy(mDayItemClickListener);
    }
    public <T>void bindDataList(List<T> list ,DataBinder<T> binder){
        if(binder==null){
            throw  new IllegalArgumentException("DataBinder can`t be null!");
        }
        List<CalendarDay> dayList= mAdapter.mDataList;
        for(T obj:list){
            for(CalendarDay day:dayList){
                if(binder.shouldBindData(obj,day)){
                    day.bindData(obj);
                    break;
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }
    public void setGridItemBackground(int res){
        this.mGridItemBackground=res;
        mAdapter.setGridItemBackground(this.mGridItemBackground);

    }


    public CalendarDay getMonth(){
        return month;
    }
    public void setMonth(CalendarDay calendar){
        this.month=calendar;
        this.mAdapter.setCalendar(calendar);
        this.mAdapter.notifyDataSetChanged();
    }

    RecyclerView getRecyclerView(){
        return mRecyclerView;
    }
}
