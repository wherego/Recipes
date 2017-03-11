package com.i7gk.recipes.ui.homepage;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.i7gk.recipes.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTypeFragment extends Fragment implements SearchTypeContract.View{
    public static final int LEVEL_0 = 0;
    public static final int LEVEL_1 = 1;

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.back_button)
    Button backButton;
    @BindView(R.id.list_view)
    ListView listView;

    private SearchTypeContract.Presenter presenter;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private int currentLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_type, container, false);
        ButterKnife.bind(this, view);
        presenter=new SearchTypePresenter(getActivity(),this);
        presenter.isTypeStored();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_0) {
                    presenter.queryTypeDetail(position);
                    currentLevel =LEVEL_1;
                } else if (currentLevel == LEVEL_1) {
                    presenter.queryByCid(position);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_0) {
                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                } else if (currentLevel == LEVEL_1) {
                    presenter.queryType();
                    currentLevel = LEVEL_0;
                }
            }
        });
        presenter.queryType();
        currentLevel = LEVEL_0;
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setPresenter(SearchTypeContract.Presenter presenter) {

    }

    @Override
    public void showNetError() {
        Toast.makeText(getActivity(),"网络连接失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTitle(String title) {
        titleText.setText(title);
    }

    @Override
    public void showChangedList(List<String> list) {
        if (adapter == null) {
            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showRecipesList(String cid, String title) {
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        MainActivity activity= (MainActivity) getActivity();
        activity.getDataByCid(cid ,title);
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载……");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

}
