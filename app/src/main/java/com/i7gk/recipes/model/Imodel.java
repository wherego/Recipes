package com.i7gk.recipes.model;

import com.i7gk.recipes.bean.RecipesToStore;

import java.util.List;

/**
 * Created by 64886 on 2017/3/10 0010.
 */

public interface Imodel {
    void getRecipesByCid(String cid,int page);
    void getRecipesByKeyWord(String keyWord,int page);
//    List<RecipesToStore> getRecipesFromDB(int count);
//    List<RecipesToStore> getAllRecipesFromDB();
    List<RecipesToStore> getCollection();
}
