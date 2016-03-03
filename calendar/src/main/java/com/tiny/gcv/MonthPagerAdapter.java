package com.tiny.gcv;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.tiny.gcv.formatter.WeekDayFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiny on 2015/12/29.
 */
public class MonthPagerAdapter extends PagerAdapter {

    static final int DEFAULT_RANGE=1;


    final Context context;
    final GridCalenderView mCalendarView;
    final ViewPager mPager;
    DateRangeIndex mRangeIndex;
    WeekDayFormatter mWeekFormatter;
    CalendarDay min;
    CalendarDay max;
    final CalendarDay today;

    ArrayList<BaseDecorator> mDecorators =new ArrayList<>();

    SparseArray<BaseSelectedDateDecorator> mSelectedDateDecorators=new SparseArray<>();

    ArrayList<MonthView> mCurrentViews=new ArrayList<>();

    onDayItemListenerProxy mDayItemClickProxy;

    int mGridItemBackground;

    DateSelectPool mDataPool;

    SelectedDateDecoratorFactory factory;


    public MonthPagerAdapter(Context context, GridCalenderView mCalendarView, ViewPager mPager,DateSelectPool pool) {
        this.context=context;
        this.mCalendarView=mCalendarView;
        this.mPager=mPager;
        today= CalendarDay.today();
        setMinAndMaxDate(null,null);
        mWeekFormatter= WeekDayFormatter.DEFAULT;
        this.mDataPool=pool;
    }
    public void addDecorator(BaseDecorator decorator){
        mDecorators.add(decorator);
        for(MonthView view:mCurrentViews){
            view.setWeekDayFormatter(mWeekFormatter);
        }
    }


    public void setMinAndMaxDate(CalendarDay min, CalendarDay max){
        this.min=min;
        this.max=max;
        if(this.min==null){
            int year=today.getYear()-DEFAULT_RANGE;
            this.min= CalendarDay.from(year,0,1);
        }
        if(this.max==null){
            int year=today.getYear()+DEFAULT_RANGE;
            this.max= CalendarDay.from(year,11,1);
        }
        mRangeIndex=new DateRangeIndex(this.min,this.max);
        notifyDataSetChanged();
    }
    public void setWeekDayFormatter(WeekDayFormatter weekDayFormatter){
        if(weekDayFormatter!=null&&weekDayFormatter!=this.mWeekFormatter){
            return;
        }
        this.mWeekFormatter = weekDayFormatter;
        for(MonthView view:mCurrentViews){
            view.setWeekDayFormatter(mWeekFormatter);
        }
    }
    public int getIndexForDay(CalendarDay day){
        if(day==null){
            return getCount()/2;
        }
        return mRangeIndex.indexOf(day);
    }
    public void setOnDayItemClickProxy(onDayItemListenerProxy proxy){
        this.mDayItemClickProxy=proxy;

    }

    public void setGridItemBackground(int res){
        this.mGridItemBackground=res;
    }

    public void setSelectedDateDecoratorFactory(SelectedDateDecoratorFactory factory){
        this.factory=factory;
    }

    @Override
    public int getCount() {
        return mRangeIndex.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        if(!(object instanceof MonthView)){
            return POSITION_NONE;
        }
        MonthView monthView= (MonthView) object;
        CalendarDay month= monthView.getMonth();
        if(month==null){
            return POSITION_NONE;
        }
        int position=mRangeIndex.indexOf(month);
        if(position<0){
            return POSITION_NONE;
        }

        return position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        MonthView view= (MonthView) object;
        container.removeView(view);
        mCurrentViews.remove(view);
        BaseSelectedDateDecorator decorator= mSelectedDateDecorators.get(position);
        if(decorator!=null){
            mSelectedDateDecorators.delete(position);
            mDataPool.unregisterObserver(decorator.observer());
        }
    }

    public CalendarDay getCalendarByPosition(int position){
        return mRangeIndex.getItem(position);
    }

    public <T> void bindDataList(CalendarDay monthAndYear, List<T> data, DataBinder<T> binder) {
        Log.e("MonthPagerAdapter","bindDataList : data size = "+data.size());
        long time=System.currentTimeMillis();
        for (MonthView view : mCurrentViews) {
            if (view.getMonth() == null) {
                continue;
            }
            if (CalendarDay.isSameMonth(view.getMonth(), monthAndYear)) {
                view.bindDataList(data, binder);
                break;
            }
        }
        long duration=System.currentTimeMillis()-time;
        Log.e("MonthPagerAdapter","finish:duration = "+duration);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MonthView monthView=new MonthView(context);
        CalendarDay month=mRangeIndex.getItem(position);
        monthView.setMonth(month);
        monthView.setWeekDayFormatter(this.mWeekFormatter);
        monthView.setDecorators(mDecorators);
        monthView.setGridItemBackground(mGridItemBackground);
        monthView.setOnDayItemClickProxy(mDayItemClickProxy);
        BaseSelectedDateDecorator decorator=null;
        if(factory!=null){
            decorator=factory.create(monthView.getRecyclerView());
        }else{
            decorator=new  SelectedDateDecorator(monthView.getRecyclerView(),context.getResources().getColor(R.color.selected_color));
        }
        decorator.init(mDataPool,month);
        mSelectedDateDecorators.put(position,decorator);
        monthView.addDecorator(decorator);
        mDataPool.registerObserver(decorator.observer());
        container.addView(monthView);
        mCurrentViews.add(monthView);
        return monthView;
    }

}
