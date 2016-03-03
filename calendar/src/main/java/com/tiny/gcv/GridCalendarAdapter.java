package com.tiny.gcv;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tiny on 2015/12/28.
 */
public class GridCalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> implements View.OnClickListener{

    List<CalendarDay> mDataList;

    Context context;
    /**
     * 用于标记当前年份月份的
     * 一个adapter绑定一个view
     * 一旦创建不可更改
     */
    CalendarDay mCurrent;

    MonthView monthView;

    onDayItemListenerProxy mItemProxy;

    int mGridItemBackground;


    public GridCalendarAdapter(Context context, MonthView monthView) {
        this.context = context;
        mDataList=new ArrayList<>();
        this.monthView=monthView;
    }

    public void setCalendar(CalendarDay calendar){
        this.mCurrent=calendar;
        initial();
    }
     void setonItemClickProxy(onDayItemListenerProxy proxy){
        this.mItemProxy=proxy;
    }
    private void initial() {
        mDataList.clear();
        Calendar c= mCurrent.transfer();
        c.set(Calendar.DAY_OF_MONTH,1);
        int firstDay=c.get(Calendar.DAY_OF_MONTH);
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        c.roll(Calendar.DAY_OF_MONTH,-1);
        int lastDay=c.get(Calendar.DAY_OF_MONTH);
        for(int i=firstDay;i<=lastDay;i++){
            CalendarDay d= CalendarDay.from(year,month,i);
            mDataList.add(d);
        }
        CalendarDay first=mDataList.get(0);
        List<CalendarDay> supplements= CalendarDay.makeUpFirstWeek(first);
        if(!supplements.isEmpty()){
            mDataList.addAll(0,supplements);
        }
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CalendarViewHolder vh=new CalendarViewHolder(View.inflate(context,R.layout.item_cell_calendar,null));
        vh.itemView.setOnClickListener(this);
        if(mGridItemBackground!=0){
            vh.itemView.setBackgroundResource(mGridItemBackground);
        }
        return vh;
    }

    @Override
    public void onClick(View v) {
        Integer position= (Integer) v.getTag();
        if(mItemProxy!=null){
            mItemProxy.onDayItemClick(position,mDataList.get(position),mCurrent);
        }
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        holder.itemView.setTag(position);
        CalendarDay day=mDataList.get(position);
        int viewType=getItemViewType(position);
        holder.bindData(position,viewType,day);
    }


    public void setGridItemBackground(int res){
        this.mGridItemBackground=res;
    }

    public CalendarDay getItem(int position){
        return mDataList.get(position);
    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public CalendarDay getCurrent(){
        return mCurrent;
    }



}
