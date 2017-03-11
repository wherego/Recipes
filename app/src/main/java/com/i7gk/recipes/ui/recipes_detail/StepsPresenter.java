package com.i7gk.recipes.ui.recipes_detail;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.i7gk.recipes.bean.RecipesToStore;
import com.i7gk.recipes.bean.Steps;
import com.i7gk.recipes.ui.ImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public class StepsPresenter implements StepsContract.Presenter{
    private Context context;
    private StepsContract.View view;
    private RecipesToStore recipesToStore;
    private List<Steps> stepsList=new ArrayList<Steps>();

    public StepsPresenter(Context context, StepsContract.View view, RecipesToStore recipesToStore){
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
        String recipe_method = recipesToStore.getRecipe_method();
        if (recipe_method != null && !recipe_method.equals("")) {
            stepsList = getListStepsByGson(recipe_method);
            view.setSteps(stepsList);
        }
    }

    private List<Steps> getListStepsByGson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, new TypeToken<List<Steps>>(){}.getType());
    }

    @Override
    public void showImage(int position) {
        if (stepsList.get(position).getImg() != null && !stepsList.get(position).getImg().equals("")) {
            Intent intent = new Intent(context,ImageActivity.class);
            intent.putExtra("image_address", stepsList.get(position).getImg());
            context.startActivity(intent);
        }
    }
}
