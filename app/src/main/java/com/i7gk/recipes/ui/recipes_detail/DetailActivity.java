package com.i7gk.recipes.ui.recipes_detail;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.i7gk.recipes.R;
import com.i7gk.recipes.adapter.FragmentVPAdapter;
import com.i7gk.recipes.bean.RecipesToStore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {


    @BindView(R.id.step_image_top)
    ImageView stepImageTop;
    @BindView(R.id.toolbar_detail)
    Toolbar toolbarDetail;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.step_NestedScrollView)
    NestedScrollView stepNestedScrollView;
    @BindView(R.id.fab_step)
    FloatingActionButton fabStep;
    @BindView(R.id.collection)
    ImageView collection;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private FragmentVPAdapter pagerAdapter;
    private DetailContract.Presenter presenter;
    private float curX;
    private AnimatorSet set;
    private RecipesToStore recipesToStore;
    private IntroductionFragment introductionFragment;
    private StepsFragment stepsFragment;
    private StepsPresenter stepsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_detail);
        ButterKnife.bind(this);
        stepNestedScrollView.setFillViewport(true);
        presenter = new DetailPresenter(DetailActivity.this, this);
        recipesToStore = (RecipesToStore) getIntent().getSerializableExtra("recipe");
        presenter.initData(recipesToStore);
        initView();
        curX = fabStep.getTranslationX();
        introductionFragment=IntroductionFragment.newInstance();
        stepsFragment=StepsFragment.newInstance();
        pagerAdapter = new FragmentVPAdapter(getSupportFragmentManager(), this,introductionFragment,stepsFragment);
        new IntroductionPresenter(DetailActivity.this,introductionFragment,recipesToStore);
        stepsPresenter=new StepsPresenter(DetailActivity.this,stepsFragment,recipesToStore);
        viewpager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.runCollection(collection, recipesToStore);
            }
        });
        fabStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepsPresenter.shareAsText();
            }
        });
        showFabAnimation();
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {

    }

    private void showFabAnimation() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(fabStep, "translationX", curX - 100f, curX);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(fabStep, "rotation", -80f, 0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(fabStep, "alpha", 0f, 0.7f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playTogether(animator1, animator2, animator3);
        set.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showCollected() {
        Toast.makeText(DetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotCollected() {
        Toast.makeText(DetailActivity.this, "已取消收藏", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbarDetail.setTitle(title);
    }


    @Override
    public void setRecipeImage(String address) {
        if (address == null || address.equals("")) {
            Glide.with(this).load(R.drawable.replace).into(stepImageTop);
        } else {
            Glide.with(this).load(address).into(stepImageTop);
        }
    }

    @Override
    public void setCollectedImage() {
        collection.setImageResource(R.drawable.ic_star_yellow_500_24dp);
    }


    @Override
    public void showAnimation(ImageView view) {
        view.clearAnimation();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 2f, 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 2f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        set = new AnimatorSet();
        set.setDuration(1000);
        set.playTogether(animator1, animator2, animator3);
        set.start();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(DetailActivity.this).clearMemory();
    }
}
