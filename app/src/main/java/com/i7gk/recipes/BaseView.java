package com.i7gk.recipes;

/**
 * Created by 64886 on 2017/3/9 0009.
 */

public interface BaseView<T> {
    void initView();
    void setPresenter(T presenter);
}
