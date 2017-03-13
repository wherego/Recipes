package com.i7gk.recipes.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 64886 on 2017/3/14 0014.
 */

public class ImageAdapter extends PagerAdapter {
    private List<ImageView> images;

    public ImageAdapter(List<ImageView> images){
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
        //轮播返回下面
//        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
//        position %= images.size();
//        if (position<0){
//            position = images.size()+position;
//        }
        ImageView view = images.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        return view;
    }
}
