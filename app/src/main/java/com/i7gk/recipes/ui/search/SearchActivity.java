package com.i7gk.recipes.ui.search;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.i7gk.recipes.R;
import com.i7gk.recipes.adapter.SearchHistoryAdapter;
import com.i7gk.recipes.bean.SearchHistory;
import com.i7gk.recipes.interfaze.OnRecyclerViewOnClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchContract.View{


    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.clear_text)
    ImageView clearText;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.delete_history)
    ImageView deleteHistory;
    @BindView(R.id.search_recycler_view)
    RecyclerView searchRecyclerView;
    @BindView(R.id.search_box)
    LinearLayout searchBox;
    @BindView(R.id.search_list)
    RelativeLayout searchList;

    private SearchContract.Presenter presenter;
    private SearchHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        presenter=new SearchPresenter(SearchActivity.this,this);
        presenter.start();
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    searchButton.setTextColor(0xFF888888);
                    searchButton.setText("取消");
                    clearText.setVisibility(View.GONE);
                } else {
                    searchButton.setTextColor(0xFF000000);
                    searchButton.setText("搜索");
                    clearText.setVisibility(View.VISIBLE);
                }
            }
        });
        clearText.setVisibility(View.GONE);
        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText.setText("");
            }
        });

        searchButton.setTextColor(0xFF888888);
        searchButton.setText("取消");
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchButton.getText().toString().equals("取消")) {
                    finish();
                } else if (searchButton.getText().toString().equals("搜索")) {
                    presenter.handleKeyWord(searchText.getText().toString());
                }
            }
        });
        deleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteHistory();
            }
        });
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        searchRecyclerView.setLayoutManager(layoutManager);
        showAnimation();
    }

    private void showAnimation() {
        runSearchBoxAnim();
        runSearchListAnim();
    }

    private void runSearchBoxAnim() {
        float curY=searchBox.getTranslationY();
        ObjectAnimator animator1=ObjectAnimator.ofFloat(searchBox,"translationY",curY-100f,curY);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(searchBox,"alpha",0f,1f);
        AnimatorSet set=new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animator1,animator2);
        set.start();
    }

    private void runSearchListAnim() {
        float curY=searchList.getTranslationY();
        ObjectAnimator animator1=ObjectAnimator.ofFloat(searchList,"translationY",curY+500f,curY);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(searchList,"alpha",0f,1f);
        AnimatorSet set=new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animator1,animator2);
        set.start();
    }



    @Override
    public void returnKeyWord(String keyWord) {
        Intent intent = new Intent();
        intent.putExtra("keyWord", keyWord);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void noticeChineseOnly() {
        Toast.makeText(SearchActivity.this, "请输入中文", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDataChanged(List<SearchHistory> searchHistoryList) {
        if (adapter == null) {
            adapter = new SearchHistoryAdapter(SearchActivity.this, searchHistoryList);
            adapter.setOnItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    presenter.clickItem(position);
                }
            });
            searchRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {

    }

    @Override
    public void showHistoryCleared() {
        Toast.makeText(SearchActivity.this, "搜索历史已删除", Toast.LENGTH_SHORT).show();
    }
}
