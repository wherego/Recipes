package com.i7gk.recipes.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by 64886 on 2017/2/26 0026.
 */

public class Type extends DataSupport {

    /**
     * ctgId : 0010001007
     * name : 荤菜
     * parentId : 0010001002
     */

    private String ctgId;
    private String name;
    private String parentId;


    public String getCtgId() {
        return ctgId;
    }

    public void setCtgId(String ctgId) {
        this.ctgId = ctgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
