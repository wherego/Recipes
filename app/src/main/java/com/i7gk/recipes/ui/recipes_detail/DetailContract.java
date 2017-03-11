package com.i7gk.recipes.ui.recipes_detail;

import android.widget.ImageView;

import com.i7gk.recipes.BasePresenter;
import com.i7gk.recipes.BaseView;
import com.i7gk.recipes.bean.RecipesToStore;

/**
 * Created by 64886 on 2017/3/10 0010.
 */

public interface DetailContract {
    interface View extends BaseView<Presenter>{
        void showCollected();
        void showNotCollected();
        void setToolbarTitle(String title);
        void setRecipeImage(String address);
        void setCollectedImage();
        void showAnimation(ImageView view);
    }

    interface Presenter extends BasePresenter{
        void runCollection(ImageView view,RecipesToStore recipesToStore);
        void initData(RecipesToStore recipesToStore);
    }
}
