package com.tiny.gcv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by tiny on 2015/12/29.
 */
public class CalendarViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;



    public CalendarViewHolder(View itemView) {
        super(itemView);
        mTextView= (TextView) itemView.findViewById(R.id.calendar_text_item);
    }


    public void bindData(int position, int viewType, CalendarDay calenderDay){
        if(calenderDay.isSupplement){
            mTextView.setText("");
        }else{
            mTextView.setText(calenderDay.getDay()+"");
        }
    }
}
