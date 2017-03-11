package com.i7gk.recipes.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.i7gk.recipes.ui.recipes_detail.IntroductionFragment;
import com.i7gk.recipes.ui.recipes_detail.StepsFragment;

/**
 * Created by 64886 on 2017/3/9 0009.
 */

public class FragmentVPAdapter extends FragmentPagerAdapter {
    private String[] tabTitles = new String[]{"介绍","步骤"};
    private Context context;
    private IntroductionFragment introductionFragment;
    private StepsFragment stepsFragment;

    public FragmentVPAdapter(FragmentManager fm, Context context,IntroductionFragment introductionFragment,StepsFragment stepsFragment){
        super(fm);
        this.context = context;
        this.introductionFragment=introductionFragment;
        this.stepsFragment=stepsFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return introductionFragment;
        }
        return stepsFragment;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
