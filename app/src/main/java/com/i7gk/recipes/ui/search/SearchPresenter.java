package com.i7gk.recipes.ui.search;

import android.content.Context;

import com.i7gk.recipes.bean.SearchHistory;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public class SearchPresenter implements SearchContract.Presenter{
    private Context context;
    private SearchContract.View view;
    private List<SearchHistory> searchHistoryList = new ArrayList<SearchHistory>();

    public SearchPresenter(Context context,SearchContract.View view){
        this.context=context;
        this.view=view;
    }

    @Override
    public void start() {
        searchHistoryList.clear();
        searchHistoryList = DataSupport.order("id desc").find(SearchHistory.class);
    }

    @Override
    public void handleKeyWord(String keyWord) {
        keyWord = keyWord.replaceAll("[\n\r]", "");
        if (isAllChinese(keyWord)) {
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setKeyWord(keyWord);
            if (DataSupport.where("keyWord=?", keyWord).find(SearchHistory.class).size() != 0) {
                DataSupport.deleteAll(SearchHistory.class, "keyWord=?", keyWord);
            }
            searchHistory.save();
            view.returnKeyWord(keyWord);
        } else {
            view.noticeChineseOnly();
        }
    }

    @Override
    public void deleteHistory() {
        DataSupport.deleteAll(SearchHistory.class);
        searchHistoryList.clear();
        view.showDataChanged(searchHistoryList);
        view.showHistoryCleared();
    }

    @Override
    public void clickItem(int position) {
        view.returnKeyWord(searchHistoryList.get(position).getKeyWord());
    }

    private boolean isAllChinese(String string) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(string);
        boolean flg = true;
        if (!matcher.find()) {
            flg = false;
        }
        return flg;
    }
}
