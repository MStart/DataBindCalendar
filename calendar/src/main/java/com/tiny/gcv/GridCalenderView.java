package com.tiny.gcv;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiny.gcv.formatter.TitleFormatter;
import com.tiny.gcv.formatter.WeekDayFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tiny on 2015/12/28.
 */
public class GridCalenderView extends LinearLayout implements ViewPager.OnPageChangeListener {



    public  enum Mode{
        Normal,Single,Multi;
    }
    BetterPager mViewPager;

    MonthPagerAdapter mAdapter;

    LinearLayout mTitleLayout;

    TitleChanger mTitleChanger;

    TextView mTitleView;

    ActionButton mPreviousButton, mNextButton;

    final CalendarDay today;

     CalendarDay currentDate;

    OnDayItemClickListener mDayItemClickListener;

    OnMonthChangedListener mMonthChangedListener;


    int mGridItemBackground;

    int mScreenWidthPx;

    Mode mode;

    DateSelectPool mDateSelectedPool;


    public GridCalenderView(Context context) {
        this(context, null);
    }

    public GridCalenderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridCalenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.GridCalenderView);
        this.mGridItemBackground= a.getResourceId(R.styleable.GridCalenderView_gcv_itemBackground,0);
        a.recycle();
        today=CalendarDay.today();
        mode=Mode.Normal;
        mScreenWidthPx=context.getResources().getDisplayMetrics().widthPixels;
        mDateSelectedPool=new SimpleDataSelectPool();
        setupChildren();
        currentDate=today;
        setCurrentDate(currentDate,true);
    }

    private void setupChildren() {
        setupTitleLayout();
        mViewPager = new BetterPager(getContext());
//        mViewPager.setOffscreenPageLimit(3);
        LayoutParams lp2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mScreenWidthPx*0.85));
        addView(mViewPager, lp2);
        mAdapter = new MonthPagerAdapter(getContext(), this, mViewPager,mDateSelectedPool);
        mAdapter.setGridItemBackground(mGridItemBackground);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        mViewPager.addOnPageChangeListener(this);
        mAdapter.setOnDayItemClickProxy(proxy);
    }

    /**
     * setup to ViewPager setOffscreenPageLimit
     */
    public void setPagerOffscreenPageLimit(int count){
        mViewPager.setOffscreenPageLimit(count);
    }

    public void setMinimumDate(@NonNull  Calendar calendar){
        setMinimumDate(CalendarDay.from(calendar));
    }
    public void setMinimumDate(CalendarDay day){
        setMinAndMaxDate(day,null);
    }
    public void setMaximumDate(@NonNull  Calendar calendar){
        setMaximumDate(CalendarDay.from(calendar));
    }
    public void setMaximumDate(CalendarDay day){
        setMinAndMaxDate(null,day);
    }
    private void setMinAndMaxDate(CalendarDay min,CalendarDay max){
        CalendarDay current=this.currentDate;
        mAdapter.setMinAndMaxDate(min,max);
        this.currentDate=current;
        int position=mAdapter.getIndexForDay(this.currentDate);
        mViewPager.setCurrentItem(position,false);
        updateUi();
    }

    public void setOnDayItemClickListener(OnDayItemClickListener listener){
        this.mDayItemClickListener=listener;
    }
    public void setSelectedItemDrawable(Drawable drawable){

    }

    private void setupTitleLayout() {
        mTitleLayout = new LinearLayout(getContext());
        mTitleLayout.setOrientation(LinearLayout.HORIZONTAL);
        mTitleLayout.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPreviousButton = new ActionButton(getContext());
        mPreviousButton.setOnClickListener(mPreviousClickListener);
        mPreviousButton.setImageResource(R.drawable.gcv_action_previous);
        mNextButton = new ActionButton(getContext());
        mNextButton.setOnClickListener(mNextClickListener);
        mNextButton.setImageResource(R.drawable.gcv_action_next);
        mTitleView = new TextView(getContext());
        mTitleView.setText("Title");
        mTitleView.setGravity(Gravity.CENTER);
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        LayoutParams lpForTitle = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT , 1);
        LayoutParams lpForButton = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleLayout.addView(mPreviousButton, lpForButton);
        mTitleLayout.addView(mTitleView, lpForTitle);
        mTitleLayout.addView(mNextButton, lpForButton);
        mTitleChanger=new TitleChanger(mTitleView);
        addView(mTitleLayout, lp);
        setArrowColor(Color.BLACK);
    }

    public void setSelectedMode(Mode mode){
        if(this.mode!=mode){
            //这里模式切换后先清空数据
            mDateSelectedPool.clear();
            this.mode=mode;
            mDateSelectedPool.notifyDataChanged(null);
//            mDateSelectedPool.notifyDataChanged();
        }
    }
    public void setArrowColor(int color){
        this.mNextButton.setColorFilter(color);
        this.mPreviousButton.setColorFilter(color);
    }

    public void addDecorator(BaseDecorator decorator) {
        mAdapter.addDecorator(decorator);
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        mAdapter.setWeekDayFormatter(formatter);
    }

    public void setTitleFormatter(TitleFormatter formatter) {
        this.mTitleChanger.setTitleFormatter(formatter);
    }

    public <T> void bindDataList(CalendarDay monthAndYear, List<T> data, DataBinder<T> binder) {
        mAdapter.bindDataList(monthAndYear, data, binder);
    }

    public void setSelectedDateDecoratorFactory(SelectedDateDecoratorFactory factory) {
        mAdapter.setSelectedDateDecoratorFactory(factory);
    }
    public void setCurrentDate(CalendarDay day, boolean smooth) {
        int index = mAdapter.getIndexForDay(day);
        mViewPager.setCurrentItem(index, smooth);
        updateUi();
    }
    public void setOnMonthChangedListener(OnMonthChangedListener listener){
        this.mMonthChangedListener=listener;
    }
    public  List<CalendarDay> getSelectedDays(){
        List<CalendarDay> list=new ArrayList<>(mDateSelectedPool.getSelectedDays());
        return list;
    }

    private void updateUi() {
        mTitleChanger.changed(this.currentDate);
        mPreviousButton.setEnabled(canGoBack());
        mNextButton.setEnabled(canGoForward());
    }

    private boolean canGoForward() {
        return mViewPager.isPagingEnabled() && mViewPager.getCurrentItem() < (mAdapter.getCount() - 1);
    }

    /**
     *
     *
     * @return true if there is a previous month that can be shown
     */
    private boolean canGoBack() {
        return mViewPager.isPagingEnabled() && mViewPager.getCurrentItem() > 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.currentDate=mAdapter.getCalendarByPosition(position);
        updateUi();
        if(mMonthChangedListener!=null){
            mMonthChangedListener.onMonthSelected(this.currentDate);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    OnClickListener mNextClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);

        }
    };
    OnClickListener mPreviousClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
        }
    };
    onDayItemListenerProxy proxy=new onDayItemListenerProxy() {
        @Override
        public void onDayItemClick(int position, CalendarDay day, CalendarDay monthAndYear) {
            switch (mode){
                case Normal:
                    if(mDayItemClickListener!=null){
                        mDayItemClickListener.onClick(position,day,monthAndYear);
                    }
                    break;
                case Single:
                    if(mDateSelectedPool.containDay(day)){
                        mDateSelectedPool.remove(day);
                    }else{
                        mDateSelectedPool.clear();
                        mDateSelectedPool.put(day);
                    }
                    mDateSelectedPool.notifyDataChanged(monthAndYear);
                    break;
                case Multi:
                    if(mDateSelectedPool.containDay(day)){
                        mDateSelectedPool.remove(day);
                    }else{
                        mDateSelectedPool.put(day);
                    }
                    mDateSelectedPool.notifyDataChanged(monthAndYear);
                    break;
            }
        }
    };
    public interface OnMonthChangedListener{
        void onMonthSelected(CalendarDay monthAndYear);
    }

}
