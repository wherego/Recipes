package com.i7gk.recipes.ui.homepage;

import android.content.Context;

import com.i7gk.recipes.bean.Type;
import com.i7gk.recipes.model.ImodelType;
import com.i7gk.recipes.model.ModelType;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 64886 on 2017/3/10 0010.
 */

public class SearchTypePresenter implements SearchTypeContract.Presenter{
    private Context context;
    private SearchTypeContract.View view;
    private ImodelType imodelType;
    private List<Type> typeList0;
    private List<Type> typeList1;
    private List<String> dataList = new ArrayList<>();

    public SearchTypePresenter(Context context, SearchTypeContract.View view){
        this.context=context;
        this.view=view;
        imodelType=new ModelType(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void isTypeStored() {
        List<Type> typeList= DataSupport.findAll(Type.class);
        if (typeList.size()<=0){
            view.showProgressDialog();
            imodelType.searchTypeFromServer();
        }
        typeList0= imodelType.getTypeList0();
    }

    @Override
    public void queryType() {
        view.setTitle("全部菜谱");
        typeList0 = imodelType.getTypeList0();
        dataList.clear();
        for (Type type : typeList0) {
            dataList.add(type.getName());
        }
        view.showChangedList(dataList);

    }

    @Override
    public void queryTypeDetail(int position) {
        view.setTitle(dataList.get(position));
        String parentId = typeList0.get(position).getCtgId();
        typeList1 = imodelType.getTypeList1(parentId);
        dataList.clear();
        for (Type type : typeList1) {
            dataList.add(type.getName());
        }
        view.showChangedList(dataList);
    }

    @Override
    public void queryByCid(int position) {
        String cid=typeList1.get(position).getCtgId();
        String title=dataList.get(position);
        view.showRecipesList(cid,title);
    }

    @Override
    public void getList(List<String> list) {
        dataList.clear();
        dataList.addAll(list);
        view.closeProgressDialog();
        view.showChangedList(dataList);
    }

    @Override
    public void getDataFailed() {
        view.showNetError();
    }
}
