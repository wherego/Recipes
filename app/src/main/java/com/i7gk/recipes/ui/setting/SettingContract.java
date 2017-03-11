package com.i7gk.recipes.ui.setting;

import com.i7gk.recipes.BasePresenter;
import com.i7gk.recipes.BaseView;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public interface SettingContract {
    interface View extends BaseView<Presenter>{
        void showNoRecipeCollected();
        void showCollectionCleared();
        void showCacheCleared();
        void showImageCacheCleared();
        void showHistoryCleared();
        void returnResult();
    }

    interface Presenter extends BasePresenter{
        void skimCollection();
        void clearCollection();
        void clearCache();
        void clearSearchHistory();
        void clearImageCache();
    }
}
