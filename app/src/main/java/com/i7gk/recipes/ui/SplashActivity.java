package com.i7gk.recipes.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.i7gk.recipes.R;
import com.i7gk.recipes.ui.homepage.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 64886 on 2017/3/4 0004.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_name)
    TextView appName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, 0);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        initAnimation();
        finishActivity();
    }

    private void initAnimation() {
        float curY=appIcon.getTranslationY();
        float curY_text=appName.getTranslationY();
        ObjectAnimator moveInTrans=ObjectAnimator.ofFloat(appIcon,"translationY",curY+220f,curY-60f,curY+30f,curY,curY+10f,curY);
        ObjectAnimator moveInAlpha=ObjectAnimator.ofFloat(appIcon,"alpha",0f,0.3f,0.6f,0.9f,1f,1f);
        ObjectAnimator scaleX=ObjectAnimator.ofFloat(appIcon,"scaleX",0.8f,1f,1.3f,1f,1.1f,1f);
        ObjectAnimator scaleY=ObjectAnimator.ofFloat(appIcon,"scaleY",1.2f,1f,0.7f,1f,0.9f,1f);
        ObjectAnimator textAlpha=ObjectAnimator.ofFloat(appName,"alpha",0f,0.2f,0.4f,0.6f,0.8f,1f);
        ObjectAnimator textTrans=ObjectAnimator.ofFloat(appName,"translationY",curY_text+220f,curY_text-60f,curY_text,curY_text,curY_text,curY_text);
        AnimatorSet set=new AnimatorSet();
        set.playTogether(scaleX,scaleY,textAlpha,moveInTrans,moveInAlpha,textTrans);
        set.setDuration(2000);
        set.start();
    }

    private void finishActivity() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
