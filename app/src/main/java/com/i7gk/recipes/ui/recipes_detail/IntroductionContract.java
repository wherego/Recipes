package com.i7gk.recipes.ui.recipes_detail;

import com.i7gk.recipes.BasePresenter;
import com.i7gk.recipes.BaseView;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public interface IntroductionContract {
    interface View extends BaseView<Presenter>{
        void setRecipeTitle(String title);
        void setCtgTitles(String title);
        void setRecipeIngredients(String title);
        void setRecipeSumary(String title);
    }
    interface Presenter extends BasePresenter{
        void initData();
    }
}
