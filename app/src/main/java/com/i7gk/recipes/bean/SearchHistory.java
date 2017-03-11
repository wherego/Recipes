package com.i7gk.recipes.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by 64886 on 2017/2/28 0028.
 */

public class SearchHistory extends DataSupport{
    private String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
