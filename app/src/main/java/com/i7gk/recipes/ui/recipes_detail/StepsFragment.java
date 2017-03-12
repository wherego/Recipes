package com.i7gk.recipes.ui.recipes_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.i7gk.recipes.R;
import com.i7gk.recipes.adapter.StepsAdapter;
import com.i7gk.recipes.bean.Steps;
import com.i7gk.recipes.interfaze.OnRecyclerViewOnClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 64886 on 2017/3/11 0011.
 */

public class StepsFragment extends Fragment implements StepsContract.View {
    @BindView(R.id.step_recycler_view)
    RecyclerView stepRecyclerView;


    private StepsContract.Presenter presenter;
    private StepsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public static StepsFragment newInstance() {
        StepsFragment stepsFragment = new StepsFragment();
        return stepsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_fragment, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        stepRecyclerView.setLayoutManager(linearLayoutManager);
        stepRecyclerView.setHasFixedSize(true);
//        解决recyclerview和NestedScrollView滑动冲突问题
//        (NestedScrollView嵌套viewpager，viewpager的fragment中存在RecyclerView时不能用这条代码，有反效果，此程序是个例子)
//        stepRecyclerView.setNestedScrollingEnabled(false);
        presenter.initData();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setPresenter(StepsContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void setSteps(List<Steps> stepsList) {
        if (adapter == null) {
            adapter = new StepsAdapter(getActivity(), stepsList);
            adapter.setOnItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    presenter.showImage(position);
                }
            });
            stepRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showShareError() {
        Toast.makeText(getActivity(), "找不到分享方式", Toast.LENGTH_SHORT).show();
    }
}
