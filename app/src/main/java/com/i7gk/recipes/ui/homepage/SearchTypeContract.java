package com.i7gk.recipes.ui.homepage;

import com.i7gk.recipes.BasePresenter;
import com.i7gk.recipes.BaseView;

import java.util.List;

/**
 * Created by 64886 on 2017/3/10 0010.
 */

public interface SearchTypeContract {
    interface View extends BaseView<Presenter>{
        void showNetError();
        void setTitle(String title);
        void showChangedList(List<String> list);
        void showRecipesList(String cid,String title);
        void showProgressDialog();
        void closeProgressDialog();
    }


    interface Presenter extends BasePresenter {
        void isTypeStored();
        void queryType();
        void queryTypeDetail(int position);
        void queryByCid(int position);
        void getList(List<String> list);
        void getDataFailed();
    }
}
