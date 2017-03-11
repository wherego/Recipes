package com.i7gk.recipes.ui.setting;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.i7gk.recipes.app.App;
import com.i7gk.recipes.bean.RecipesToStore;
import com.i7gk.recipes.bean.SearchHistory;

import org.litepal.crud.DataSupport;

import java.io.File;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public class SettingPresenter implements SettingContract.Presenter{
    private Context context;
    private SettingContract.View view;
    public SettingPresenter(Context context,SettingContract.View view){
        this.context=context;
        this.view=view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void skimCollection() {
        if (DataSupport.findAll(RecipesToStore.class).size()==0){
            view.showNoRecipeCollected();
        }else{
            view.returnResult();
        }
    }

    @Override
    public void clearCollection() {
        DataSupport.deleteAll(RecipesToStore.class);
        view.showCollectionCleared();
    }

    @Override
    public void clearCache() {
        //删除OkHttp缓存
        File file=new File("/data/data/"+ App.getContext().getPackageName()+"/cache/response");
        if (file!=null && file.exists()&&file.isDirectory()){
            for (File item : file.listFiles()) {
                item.delete();
            }
        }
        view.showCacheCleared();
    }

    @Override
    public void clearSearchHistory() {
        DataSupport.deleteAll(SearchHistory.class);
        view.showHistoryCleared();
    }

    @Override
    public void clearImageCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
        Glide.get(context).clearMemory();
        view.showImageCacheCleared();
    }
}
