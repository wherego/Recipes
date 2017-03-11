package com.i7gk.recipes.ui.recipes_detail;

import android.content.Context;

import com.i7gk.recipes.bean.RecipesToStore;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public class IntroductionPresenter implements IntroductionContract.Presenter{
    private Context context;
    private IntroductionContract.View view;
    private RecipesToStore recipesToStore;
    public IntroductionPresenter(Context context, IntroductionContract.View view, RecipesToStore recipesToStore){
        this.context=context;
        this.view=view;
        this.recipesToStore=recipesToStore;
        this.view.setPresenter(this);
    }
    @Override
    public void start() {

    }

    @Override
    public void initData() {
        String ctgTitles = recipesToStore.getCtgTitles();
        String recipe_ingredients = recipesToStore.getRecipe_ingredients();
        String recipe_sumary = recipesToStore.getRecipe_sumary();
        String recipe_title = recipesToStore.getRecipe_title();
        if (recipe_ingredients != null && !recipe_ingredients.equals("")) {
            recipe_ingredients = recipe_ingredients.replace("[\"", "");
            recipe_ingredients = recipe_ingredients.replace("\"]", "");
            recipe_ingredients = recipe_ingredients.replace("\",\"", "");
        }
        view.setCtgTitles(ctgTitles);
        view.setRecipeTitle(recipe_title);
        view.setRecipeIngredients(recipe_ingredients);
        view.setRecipeSumary(recipe_sumary);
    }
}
