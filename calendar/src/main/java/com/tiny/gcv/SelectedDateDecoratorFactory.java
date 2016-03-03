package com.tiny.gcv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tiny on 2016/2/29.
 * 允许app添加自定义的SelectedDateDecorator
 */
public interface SelectedDateDecoratorFactory {
    @NonNull BaseSelectedDateDecorator create(RecyclerView recyclerView);
}
