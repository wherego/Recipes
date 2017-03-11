package com.i7gk.recipes.ui.search;

import com.i7gk.recipes.BasePresenter;
import com.i7gk.recipes.BaseView;
import com.i7gk.recipes.bean.SearchHistory;

import java.util.List;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public interface SearchContract {
    interface View extends BaseView<Presenter>{
        void showHistoryCleared();
        void returnKeyWord(String keyWord);
        void noticeChineseOnly();
        void showDataChanged(List<SearchHistory> searchHistoryList);
    }
    interface Presenter extends BasePresenter{
        void handleKeyWord(String keyWord);
        void deleteHistory();
        void clickItem(int position);

    }
}
