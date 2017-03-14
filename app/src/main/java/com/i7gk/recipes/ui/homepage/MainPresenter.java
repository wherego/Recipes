package com.i7gk.recipes.ui.homepage;

import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.i7gk.recipes.bean.RecipesToStore;
import com.i7gk.recipes.model.Imodel;
import com.i7gk.recipes.model.Model;
import com.i7gk.recipes.netService.NetworkState;
import com.i7gk.recipes.ui.recipes_detail.DetailActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 64886 on 2017/3/10 0010.
 */

public class MainPresenter implements MainContract.Presenter{
    private MainContract.View view;
    private Context context;
    private Imodel imodel;
    private String keyWord=null;
    private String cid=null;
    public static boolean NoMoreData=false;
    private List<RecipesToStore> mRecipesList=new ArrayList<>();
    private int page=1;
    private static final int MIN_CLICK_DELAY_TIME = 10000;
    private long lastClickTime = 0;

    public MainPresenter(Context context,MainContract.View view){
        this.context=context;
        this.view=view;
        imodel=new Model(this);
    }
    @Override
    public void start() {
        imodel.getRecipesByKeyWord(keyWord,page);
    }

    @Override
    public void getData(String cid, String title) {
        noticeNetState();
        NoMoreData=false;
        view.setTitle(title);
        if (keyWord!=null){
            keyWord=null;
            page=1;
        }
        if (this.cid==null || !this.cid.equals(cid)){
            this.cid=cid;
            page=1;
        }
        imodel.getRecipesByCid(cid,page);
    }


    @Override
    public void getData(String keyWord) {
        noticeNetState();
        NoMoreData=false;
        view.setTitle(keyWord);
        if (cid!=null){
            cid=null;
            page=1;
        }
        if (this.keyWord==null||!this.keyWord.equals(keyWord)){
            page=1;
        }
        this.keyWord=keyWord;
        imodel.getRecipesByKeyWord(keyWord,page);
    }

    @Override
    public void showDetail(int position) {
        Intent intent=new Intent(context,DetailActivity.class);
        intent.putExtra("recipe",mRecipesList.get(position));
        context.startActivity(intent);
    }

    @Override
    public void showCollection() {
        view.setTitle("全部收藏");
        NoMoreData=true;
        keyWord="全部收藏";
        cid=null;
        mRecipesList.clear();
        System.gc();
        mRecipesList.addAll(imodel.getCollection());
        view.showChangedData(mRecipesList);
        view.showAllCollectionFinished();
        Glide.get(context).clearMemory();
    }

    @Override
    public void getDataFailed() {
        if (NetworkState.networkConnected(context)){
            view.showNetError();
        }
    }

    @Override
    public void showList(List<RecipesToStore> list) {
        if (list!=null){
            if (page==1 && list.size()>0){
                mRecipesList.clear();
                System.gc();
            }
            mRecipesList.addAll(list);
            if (page==1){
                view.scrollToTop();
            }
            if (list.size()==20){
                page+=1;
            }else {
                NoMoreData=true;
            }
        }else if (NetworkState.networkConnected(context)){
            view.showNoDataOnServer();
        }
        view.showChangedData(mRecipesList);
        Glide.get(context).clearMemory();
    }

    @Override
    public void loadMore() {
        if (NoMoreData){
            if (Calendar.getInstance().getTimeInMillis()-lastClickTime>MIN_CLICK_DELAY_TIME){
                view.showNoMoreData();
                lastClickTime=Calendar.getInstance().getTimeInMillis();
            }
        }else if (cid!=null){
            imodel.getRecipesByCid(cid,page);
        }else{
            imodel.getRecipesByKeyWord(keyWord,page);
        }
    }

    @Override
    public void reFresh() {
        page=1;
        if (cid!=null){
            imodel.getRecipesByCid(cid,page);
        }else if (keyWord!=null && keyWord.equals("全部收藏")){
            showCollection();
        }else {
            imodel.getRecipesByKeyWord(keyWord,page);
        }
    }

    @Override
    public void handleReturnKeyword(String keyWord) {
        if (this.keyWord==null||!this.keyWord.equals(keyWord)){
            getData(keyWord);
        }
    }

    private void noticeNetState() {
        if (Calendar.getInstance().getTimeInMillis()-lastClickTime>MIN_CLICK_DELAY_TIME && !NetworkState.networkConnected(context)){
            view.showNoNetwork();
            lastClickTime=Calendar.getInstance().getTimeInMillis();
        }
    }
}
