package com.tiny.gcv;

import android.widget.TextView;

import com.tiny.gcv.formatter.TitleFormatter;


/**
 * Created by tiny on 2015/12/29.
 */
public class TitleChanger {

    TextView mTitleView;
    TitleFormatter formatter;
    CalendarDay mCurrent;


    public TitleChanger(TextView mTitleView){
        this.mTitleView=mTitleView;
        formatter=TitleFormatter.DEFAULT;
    }

    public void setTitleFormatter(TitleFormatter formatter){
        if(formatter!=null&&formatter!=this.formatter){
            this.formatter=formatter;
            update();
        }
    }
    public void changed(CalendarDay day){
        this.mCurrent=day;
        update();
    }
    private void update(){
        mTitleView.setText(this.formatter.format(mCurrent));
    }


}
