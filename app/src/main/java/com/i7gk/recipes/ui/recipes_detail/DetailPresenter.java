package com.i7gk.recipes.ui.recipes_detail;

import android.content.Context;
import android.widget.ImageView;

import com.i7gk.recipes.R;
import com.i7gk.recipes.bean.RecipesToStore;

import org.litepal.crud.DataSupport;


/**
 * Created by 64886 on 2017/3/10 0010.
 */

public class DetailPresenter implements DetailContract.Presenter{
    private Context context;
    private DetailContract.View view;
    private String menuId;
    private int isCollected=0;

    public DetailPresenter(Context context,DetailContract.View view){
        this.context=context;
        this.view=view;
    }

    @Override
    public void start() {

    }

    @Override
    public void runCollection(ImageView imageView,RecipesToStore recipesToStore) {
        if (isCollected == 1) {
            isCollected = 0;
            DataSupport.deleteAll(RecipesToStore.class,"menuId=?", menuId);
            imageView.setImageResource(R.drawable.ic_star_border_white_24dp);
            view.showNotCollected();
        } else {
            isCollected = 1;
            recipesToStore.save();
            imageView.setImageResource(R.drawable.ic_star_yellow_500_24dp);
            view.showCollected();
        }
        view.showAnimation(imageView);
    }

    @Override
    public void initData(RecipesToStore recipesToStore) {
        String name = recipesToStore.getName();
        String recipe_image = recipesToStore.getRecipe_img();
        menuId = recipesToStore.getMenuId();
        if (DataSupport.where("menuId=?", menuId).find(RecipesToStore.class).size()==1){
            isCollected = 1;
        }
        if (isCollected == 1) {
            view.setCollectedImage();
        }
        view.setToolbarTitle(name);
        view.setRecipeImage(recipe_image);
    }
}
