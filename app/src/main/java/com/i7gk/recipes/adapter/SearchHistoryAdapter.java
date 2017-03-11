package com.i7gk.recipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.i7gk.recipes.R;
import com.i7gk.recipes.bean.SearchHistory;
import com.i7gk.recipes.interfaze.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by 64886 on 2017/3/1 0001.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<SearchHistory> mSearchHistoryList;
    private OnRecyclerViewOnClickListener mListener;

    public SearchHistoryAdapter(Context context,List<SearchHistory> historyList){
        mContext=context;
        mSearchHistoryList =historyList;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(mInflater.inflate(R.layout.history_item,parent,false),mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HistoryViewHolder)holder).textView.setText(mSearchHistoryList.get(position).getKeyWord());
    }

    @Override
    public int getItemCount() {
        return mSearchHistoryList.size();
    }

    public void setOnItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener=listener;
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView;
        private OnRecyclerViewOnClickListener listener;
        public HistoryViewHolder(View inflate, OnRecyclerViewOnClickListener listener) {
            super(inflate);
            textView= (TextView) inflate.findViewById(R.id.history_item);
            this.listener=listener;
            inflate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.OnItemClick(v,getLayoutPosition());
            }
        }
    }
}
