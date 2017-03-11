package com.i7gk.recipes.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by 64886 on 2017/3/3 0003.
 */

public class Steps extends DataSupport{
    private String step;

    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
