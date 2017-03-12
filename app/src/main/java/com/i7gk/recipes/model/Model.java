package com.i7gk.recipes.model;

import com.i7gk.recipes.app.App;
import com.i7gk.recipes.bean.RecipesToStore;
import com.i7gk.recipes.bean.SearchKeyWord;
import com.i7gk.recipes.netService.APIConstant;
import com.i7gk.recipes.netService.NetService;
import com.i7gk.recipes.netService.NetworkState;
import com.i7gk.recipes.ui.homepage.MainPresenter;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 64886 on 2017/3/9 0009.
 */

public class Model implements Imodel{
    private MainPresenter presenter;
    public Model(MainPresenter presenter){
        this.presenter=presenter;
    }

    @Override
    public void getRecipesByCid(String cid,int page) {
        Retrofit retrofit=new Retrofit.Builder()
                .client(client())
                .baseUrl(APIConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NetService netService=retrofit.create(NetService.class);
        netService.searchByCid(APIConstant.KEY,cid,page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<SearchKeyWord, List<RecipesToStore>>() {
                    @Override
                    public List<RecipesToStore> call(SearchKeyWord searchKeyWord) {
                        if (searchKeyWord.getMsg().equals("success")){
                            return transFormAnd(searchKeyWord);
                        }else{
                            return null;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RecipesToStore>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.getDataFailed();
                    }

                    @Override
                    public void onNext(List<RecipesToStore> list) {
                        presenter.showList(list);
                    }
                });
    }

    @Override
    public void getRecipesByKeyWord(String keyWord,int page) {
        Retrofit retrofit=new Retrofit.Builder()
                .client(client())
                .baseUrl(APIConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NetService netService=retrofit.create(NetService.class);
        netService.searchByKeyWord(APIConstant.KEY,keyWord,page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<SearchKeyWord, List<RecipesToStore>>() {
                    @Override
                    public List<RecipesToStore> call(SearchKeyWord searchKeyWord) {
                        if (searchKeyWord.getMsg().equals("success")){
                            return transFormAnd(searchKeyWord);
                        }else{
                            return null;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RecipesToStore>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.getDataFailed();
                    }

                    @Override
                    public void onNext(List<RecipesToStore> list) {
                        presenter.showList(list);
                    }
                });
    }

    private List<RecipesToStore> transFormAnd(SearchKeyWord searchKeyWord) {
        List<RecipesToStore> list=new ArrayList<RecipesToStore>();
        for (SearchKeyWord.ResultBean.ListBean listBean:searchKeyWord.getResult().getList()){
            RecipesToStore recipes=new RecipesToStore();
            recipes.setCtgTitles(listBean.getCtgTitles());
            recipes.setMenuId(listBean.getMenuId());
            recipes.setName(listBean.getName());
            recipes.setThumbnail(listBean.getThumbnail());
            recipes.setRecipe_img(listBean.getRecipe().getImg());
            recipes.setRecipe_ingredients(listBean.getRecipe().getIngredients());
            recipes.setRecipe_method(listBean.getRecipe().getMethod());
            recipes.setRecipe_sumary(listBean.getRecipe().getSumary());
            recipes.setRecipe_title(listBean.getRecipe().getTitle());
            list.add(recipes);
        }
        return list;
    }

    @Override
    public List<RecipesToStore> getCollection() {
        return DataSupport.order("id desc").find(RecipesToStore.class);
    }

    public static OkHttpClient client(){
        File cacheFile=new File(App.getContext().getCacheDir(),"response");
        Cache cache=new Cache(cacheFile,1024*1024*10);
        OkHttpClient httpClient=new OkHttpClient.Builder()
                //防止频繁访问服务器相同的内容，间隔20s
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request=chain.request();
                        Response response=chain.proceed(request);
                        int maxAge=20;
                        return response.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control","public,max-age=" + maxAge)
                                .build();

                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        int maxAge=60;
                        int maxStale=60*60*24*7;
                        Request request=chain.request();
                        //没网时访问缓存
                        if (!NetworkState.networkConnected(App.getContext())){
                            request=request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_CACHE)
                                    .build();
                        }
                        Response response=chain.proceed(request);
                        if (NetworkState.networkConnected(App.getContext())){
                            response=response.newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache_Control","public,max-age=" + maxAge)
                                    .build();
                        }else{
                            response= response.newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }
                        return response;
                    }
                })
                .connectTimeout(15, TimeUnit.SECONDS)
                .cache(cache)
                .build();
        return httpClient;
    }
}
