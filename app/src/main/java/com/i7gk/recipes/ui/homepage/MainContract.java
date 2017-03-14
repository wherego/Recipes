package com.i7gk.recipes.ui.homepage;

import com.i7gk.recipes.BasePresenter;
import com.i7gk.recipes.BaseView;
import com.i7gk.recipes.bean.RecipesToStore;

import java.util.List;

/**
 * Created by 64886 on 2017/3/9 0009.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void showChangedData(List<RecipesToStore> mRecipesList);
        void showNetError();
        void showNoMoreData();
        void showNoNetwork();
        void showNoDataOnServer();
        void showAllCollectionFinished();
        void setTitle(String title);
        void scrollToTop();
        void getDataByCid(String cid,String title);
    }
    interface Presenter extends BasePresenter {
        void getData(String cid,String title);
        void getData(String keyWord);
        void showDetail(int position);
        void showCollection();
        void getDataFailed();
        void showList(List<RecipesToStore> list);
        void loadMore();
        void reFresh();
        void handleReturnKeyword(String keyword);
    }
}
