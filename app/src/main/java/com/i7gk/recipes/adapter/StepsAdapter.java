package com.i7gk.recipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.i7gk.recipes.R;
import com.i7gk.recipes.bean.Steps;
import com.i7gk.recipes.interfaze.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by 64886 on 2017/3/1 0001.
 */

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Steps> mStepsList;
    private OnRecyclerViewOnClickListener mListener;

    private static final int TYPE_NORMAL=0;
    private static final int TYPE_TEXT=1;

    public StepsAdapter(Context context, List<Steps> stepsList){
        mContext=context;
        mStepsList=stepsList;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_NORMAL:
                return new StepViewHolder(mInflater.inflate(R.layout.step_item,parent,false),mListener);
            case TYPE_TEXT:
                return new StepNoImageViewHolder(mInflater.inflate(R.layout.step_item_no_image,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        runAnim(holder.itemView,position);
        if(holder instanceof StepViewHolder){
            ((StepViewHolder)holder).textView.setText(mStepsList.get(position).getStep());
            if (mStepsList.get(position).getImg()==null || mStepsList.get(position).getImg().equals("")){
                ((StepViewHolder)holder).imageView.setVisibility(View.GONE);
            }else{
                Glide.with(mContext).load(mStepsList.get(position).getImg()).into(((StepViewHolder)holder).imageView);
            }
        }
        if (holder instanceof StepNoImageViewHolder){
            ((StepNoImageViewHolder)holder).textView.setText(mStepsList.get(position).getStep());
        }

    }

//    private void runAnim(View view,int position) {
//        float curY=view.getTranslationY();
//        ObjectAnimator animator1=ObjectAnimator.ofFloat(view,"translationY",curY+80f,curY);
//        ObjectAnimator animator2=ObjectAnimator.ofFloat(view,"scaleX",0.7f,1f);
//        ObjectAnimator animator3=ObjectAnimator.ofFloat(view,"scaleY",0.7f,1f);
//        ObjectAnimator animator4=ObjectAnimator.ofFloat(view,"alpha",0f,1f);
//        AnimatorSet set=new AnimatorSet();
//        set.playTogether(animator1,animator2,animator3,animator4);
//        set.setDuration(500);
//        set.setStartDelay(50*position);
//        set.start();
//    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mStepsList.get(position).getImg()==null || mStepsList.get(position).getImg().equals("")){
            return StepsAdapter.TYPE_TEXT;
        }
        return StepsAdapter.TYPE_NORMAL;
    }

    public void setOnItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener=listener;
    }

    private class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView;
        private ImageView imageView;
        private OnRecyclerViewOnClickListener listener;
        public StepViewHolder(View inflate, OnRecyclerViewOnClickListener listener) {
            super(inflate);
            textView= (TextView) inflate.findViewById(R.id.step_text);
            imageView= (ImageView) inflate.findViewById(R.id.step_image);
            this.listener=listener;
            inflate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.OnItemClick(v,getLayoutPosition());
        }
    }

    private class StepNoImageViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public StepNoImageViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.step_only_text);
        }
    }
}
