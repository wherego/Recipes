package com.i7gk.recipes.model;

import com.i7gk.recipes.bean.SearchType;
import com.i7gk.recipes.bean.Type;
import com.i7gk.recipes.netService.APIConstant;
import com.i7gk.recipes.netService.NetService;
import com.i7gk.recipes.ui.homepage.SearchTypePresenter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 64886 on 2017/3/10 0010.
 */

public class ModelType implements ImodelType{
    private SearchTypePresenter presenter;
    public ModelType(SearchTypePresenter presenter){
        this.presenter=presenter;
    }
    @Override
    public void searchTypeFromServer() {
        DataSupport.deleteAll(Type.class);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NetService netService=retrofit.create(NetService.class);
        netService.searchByType(APIConstant.KEY)
                .subscribeOn(Schedulers.io())
                .map(new Func1<SearchType, List<String>>() {
                    @Override
                    public List<String> call(SearchType searchType) {
                        List<String> list=new ArrayList<String>();
                        for (int i=0;i<searchType.getResult().getChilds().size();i++){
                            list.add(searchType.getResult().getChilds().get(i).getCategoryInfo().getName());
                            Type type=new Type();
                            type.setCtgId(searchType.getResult().getChilds().get(i).getCategoryInfo().getCtgId());
                            type.setName(searchType.getResult().getChilds().get(i).getCategoryInfo().getName());
                            type.setParentId(searchType.getResult().getChilds().get(i).getCategoryInfo().getParentId());
                            type.save();
                            for (int j=0;j<searchType.getResult().getChilds().get(i).getChilds().size();j++){
                                Type type1=new Type();
                                type1.setCtgId(searchType.getResult().getChilds().get(i).getChilds().get(j).getCategoryInfo().getCtgId());
                                type1.setName(searchType.getResult().getChilds().get(i).getChilds().get(j).getCategoryInfo().getName());
                                type1.setParentId(searchType.getResult().getChilds().get(i).getChilds().get(j).getCategoryInfo().getParentId());
                                type1.save();
                            }
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.getDataFailed();
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        presenter.getList(strings);
                    }
                });
    }

    @Override
    public List<Type> getTypeList0() {
        return DataSupport.where("parentId=?","0010001001").find(Type.class);
    }

    @Override
    public List<Type> getTypeList1(String parentId) {
        return DataSupport.where("parentId=?", parentId).find(Type.class);
    }
}
