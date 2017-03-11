package com.i7gk.recipes.model;

import com.i7gk.recipes.bean.Type;

import java.util.List;

/**
 * Created by 64886 on 2017/3/10 0010.
 */

public interface ImodelType {
    void searchTypeFromServer();
    List<Type> getTypeList0();
    List<Type> getTypeList1(String parentId);
}
