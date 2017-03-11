package com.i7gk.recipes.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by 64886 on 2017/2/27 0027.
 */

public class RecipesToStore extends DataSupport implements Serializable{

    private String ctgTitles;
    private String menuId;
    private String name;
    private String thumbnail;
    private String recipe_img;
    private String recipe_ingredients;
    private String recipe_method;
    private String recipe_sumary;
    private String recipe_title;
//    private int isCollected;
//    private int isReaded;
//
//    public int getIsCollected() {
//        return isCollected;
//    }
//
//    public void setIsCollected(int isCollected) {
//        this.isCollected = isCollected;
//    }
//
//    public int getIsReaded() {
//        return isReaded;
//    }
//
//    public void setIsReaded(int isReaded) {
//        this.isReaded = isReaded;
//    }



    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getRecipe_title() {

        return recipe_title;
    }

    public void setRecipe_title(String recipe_title) {
        this.recipe_title = recipe_title;
    }

    public String getRecipe_sumary() {

        return recipe_sumary;
    }

    public void setRecipe_sumary(String recipe_sumary) {
        this.recipe_sumary = recipe_sumary;
    }

    public String getRecipe_method() {

        return recipe_method;
    }

    public void setRecipe_method(String recipe_method) {
        this.recipe_method = recipe_method;
    }

    public String getRecipe_img() {

        return recipe_img;
    }

    public void setRecipe_img(String recipe_img) {
        this.recipe_img = recipe_img;
    }

    public String getMenuId() {

        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRecipe_ingredients() {

        return recipe_ingredients;
    }

    public void setRecipe_ingredients(String recipe_ingredients) {
        this.recipe_ingredients = recipe_ingredients;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCtgTitles() {

        return ctgTitles;
    }

    public void setCtgTitles(String ctgTitles) {
        this.ctgTitles = ctgTitles;
    }






}
