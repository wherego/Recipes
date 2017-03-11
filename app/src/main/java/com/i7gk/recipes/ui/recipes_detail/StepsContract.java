package com.i7gk.recipes.ui.recipes_detail;

import com.i7gk.recipes.BasePresenter;
import com.i7gk.recipes.BaseView;
import com.i7gk.recipes.bean.Steps;

import java.util.List;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public interface StepsContract {
    interface View extends BaseView<Presenter>{
        void setSteps(List<Steps> stepsList);
    }
    interface Presenter extends BasePresenter{
        void initData();
        void showImage(int position);
    }
}
