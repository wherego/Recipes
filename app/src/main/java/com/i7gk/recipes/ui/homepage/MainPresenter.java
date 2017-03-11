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
    private String title;
    private List<RecipesToStore> mRecipesList=new ArrayList<RecipesToStore>();
    private int page=1;
    public static final int MIN_CLICK_DELAY_TIME = 10000;
    private long lastClickTime = 0;

    public MainPresenter(Context context,MainContract.View view){
        this.context=context;
        this.view=view;
        imodel=new Model(this);
    }
    @Override
    public void start() {
        getData(keyWord);
    }

    @Override
    public void getData(String cid, String title) {
        noticeNetState();
        NoMoreData=false;
        view.setTitle(title);
        this.title=title;
        if (keyWord!=null){
            keyWord=null;
            page=1;
        }
        if (this.cid!=cid){
            this.cid=cid;
            page=1;
        }
        imodel.getRecipesByCid(cid,page);
    }


    @Override
    public void getData(String keyWord) {
        noticeNetState();
        NoMoreData=false;
        if (keyWord==null||keyWord.equals("")){
            view.setTitle("菜谱大全");
        }else if (this.keyWord!=keyWord){
            view.setTitle(keyWord);
            this.keyWord=keyWord;
            page=1;
        }
        if (cid!=null){
            cid=null;
            page=1;
        }
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
        if (page==1 && list.size()>0){
            mRecipesList.clear();
            System.gc();
        }
        mRecipesList.addAll(list);
        if (list.size()==20){
            page+=1;
        }else {
            NoMoreData=true;
        }
        if (list==null){
            view.showNoDataOnServer();
        }else if (list.size()>0){
            view.showChangedData(mRecipesList);
            if (page==2 && mRecipesList.size()==20){
                view.scrollToTop();
            }
        }
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
            getData(cid,title);
        }else if (cid==null){
            getData(keyWord);
        }
    }

    @Override
    public void reFresh() {
        page=1;
        if (cid!=null){
            getData(cid,title);
        }else if (keyWord!=null && keyWord.equals("全部收藏")){
            showCollection();
        }else {
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
