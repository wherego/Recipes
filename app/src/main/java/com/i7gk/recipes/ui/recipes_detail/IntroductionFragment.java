package com.i7gk.recipes.ui.recipes_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.i7gk.recipes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public class IntroductionFragment extends Fragment implements IntroductionContract.View {

    @BindView(R.id.recipe_title)
    TextView recipeTitle;
    @BindView(R.id.ctgTitles)
    TextView ctgTitles;
    @BindView(R.id.recipe_ingredients)
    TextView recipeIngredients;
    @BindView(R.id.recipe_sumary)
    TextView recipeSumary;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;


    private IntroductionContract.Presenter presenter;

    public static IntroductionFragment newInstance() {
        IntroductionFragment introductionFragment = new IntroductionFragment();
        return introductionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.introduction_fragment, container, false);
        ButterKnife.bind(this, view);
        presenter.initData();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setPresenter(IntroductionContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void setRecipeTitle(String title) {
        recipeTitle.setText(title);
    }

    @Override
    public void setCtgTitles(String title) {
        ctgTitles.setText(title);
    }

    @Override
    public void setRecipeIngredients(String title) {
        recipeIngredients.setText(title);
    }

    @Override
    public void setRecipeSumary(String title) {
        recipeSumary.setText(title);
    }
}
