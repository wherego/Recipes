package com.i7gk.recipes.ui.homepage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.i7gk.recipes.R;
import com.i7gk.recipes.adapter.RecipesAdapter;
import com.i7gk.recipes.app.App;
import com.i7gk.recipes.bean.RecipesToStore;
import com.i7gk.recipes.interfaze.OnRecyclerViewOnClickListener;
import com.i7gk.recipes.ui.search.SearchActivity;
import com.i7gk.recipes.ui.setting.SettingsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private MainContract.Presenter presenter;
    private Context context;
    private LinearLayoutManager mLayoutManager;
    private RecipesAdapter adapter;
    private int lastVisibleItem;
    private boolean isAnimFinished = true;
    private float curY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = MainActivity.this;
        presenter = new MainPresenter(context, this);
        initView();
        presenter.start();
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        curY = fab.getTranslationY();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToTop();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    presenter.loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && isAnimFinished && fab.getTranslationY() != curY + 200f) {
                    isAnimFinished = false;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "translationY", curY, curY + 200f);
                    animator.setDuration(500);
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isAnimFinished = true;
                        }
                    });
//                    fab.hide();
                } else if (dy < 0 && isAnimFinished && fab.getTranslationY() != curY) {
                    isAnimFinished = false;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "translationY", curY + 200f, curY);
                    animator.setDuration(500);
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isAnimFinished = true;
                        }
                    });
//                    fab.show();
                }
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.reFresh();
                swipeRefresh.setRefreshing(false);
            }
        });


    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void showChangedData(List<RecipesToStore> mRecipesList) {
        if (adapter == null) {
            adapter = new RecipesAdapter(MainActivity.this, mRecipesList);
            adapter.setOnItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    presenter.showDetail(position);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showNetError() {
        Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoMoreData() {
        Toast.makeText(MainActivity.this, "全部的相关菜谱已查询完毕", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoNetwork() {
        Toast.makeText(App.getContext(), "没有网络连接", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoDataOnServer() {
        Toast.makeText(MainActivity.this, "查询不到数据!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAllCollectionFinished() {
        Toast.makeText(MainActivity.this, "已加载全部收藏", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void scrollToTop() {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void getDataByCid(String cid, String title) {
        presenter.getData(cid, title);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivityForResult(intent, 2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    presenter.handleReturnKeyword(data.getStringExtra("keyWord"));
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    presenter.showCollection();
                }
            default:
        }
    }
}
