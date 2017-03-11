package com.i7gk.recipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.i7gk.recipes.R;
import com.i7gk.recipes.bean.RecipesToStore;
import com.i7gk.recipes.interfaze.OnRecyclerViewOnClickListener;
import com.i7gk.recipes.ui.homepage.MainPresenter;

import java.util.List;

/**
 * Created by 64886 on 2017/2/27 0027.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<RecipesToStore> mRecipesList;
    private OnRecyclerViewOnClickListener mListener;
//    private int lastAnimatedPosition=-1;
//    private boolean animationsLocked = false;
//    private boolean delayEnterAnimation = true;

    private static final int TYPE_NORMAL=0;
    private static final int TYPE_FOOTER=1;

    public RecipesAdapter(Context context,List<RecipesToStore> recipesList){
        mContext=context;
        mRecipesList =recipesList;
        mInflater=LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener=listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_NORMAL:
                return new NormalViewHolder(mInflater.inflate(R.layout.recipes_item,parent,false),mListener);
            case TYPE_FOOTER:
                return new FooterViewHolder(mInflater.inflate(R.layout.list_foot,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        runAnimation(holder,position);
        if (holder instanceof NormalViewHolder){
            RecipesToStore recipes=mRecipesList.get(position);
            Glide.with(mContext).load(recipes.getThumbnail()).asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.replace)
                    .into(((NormalViewHolder) holder).recipesImage);
            ((NormalViewHolder) holder).recipesTitle.setText(recipes.getName());
            ((NormalViewHolder) holder).recipesListSumary.setText(recipes.getRecipe_sumary());
            ((NormalViewHolder) holder).recipesCtgTitle.setText("Tags:"+ recipes.getCtgTitles());
//            if (recipes.getIsReaded()==1){
//                ((NormalViewHolder) holder).recipesTitle.setTextColor(0xff888888);
//                ((NormalViewHolder) holder).recipesListSumary.setTextColor(0xff888888);
//                ((NormalViewHolder) holder).recipesCtgTitle.setTextColor(0xff888888);
//            }

        }
    }

    @Override
    public int getItemCount() {
        if (MainPresenter.NoMoreData){
            return mRecipesList.size();
        }else{
            return mRecipesList.size()+1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==mRecipesList.size()){
            return RecipesAdapter.TYPE_FOOTER;
        }
        return RecipesAdapter.TYPE_NORMAL;
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView recipesImage;
        private TextView recipesTitle;
        private TextView recipesListSumary;
        private TextView recipesCtgTitle;
        private OnRecyclerViewOnClickListener listener;
        public NormalViewHolder(View inflate,OnRecyclerViewOnClickListener listener) {
            super(inflate);
            recipesImage= (ImageView) inflate.findViewById(R.id.recipesListImage);
            recipesTitle= (TextView) inflate.findViewById(R.id.recipesListTitle);
            recipesListSumary= (TextView) inflate.findViewById(R.id.recipesListSumary);
            recipesCtgTitle= (TextView) inflate.findViewById(R.id.recipesCtgTitles);
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

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View inflate) {
            super(inflate);
        }
    }

//    private void runAnimation(RecyclerView.ViewHolder holder,int position){
//        if (animationsLocked) return;
//        if (position > lastAnimatedPosition) {//lastAnimatedPosition是int类型变量，一开始为-1，这两行代码确保了recycleview滚动式回收利用视图时不会出现不连续的效果
//            lastAnimatedPosition = position;
//            view.setTranslationY(500);//相对于原始位置下方500
//            view.setAlpha(0.f);//完全透明
//            //每个item项两个动画，从透明到不透明，从下方移动到原来的位置
//            //并且根据item的位置设置延迟的时间，达到一个接着一个的效果
//            view.animate()
//                    .translationY(0).alpha(1.f)//设置最终效果为完全不透明，并且在原来的位置
//                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)//根据item的位置设置延迟时间，达到依次动画一个接一个进行的效果
//                    .setInterpolator(new DecelerateInterpolator(0.5f))//设置动画效果为在动画开始的地方快然后慢
//                    .setDuration(700)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
////                            animationsLocked = true;//确保仅屏幕一开始能够显示的item项才开启动画，也就是说屏幕下方还没有显示的item项滑动时是没有动画效果
//                        }
//                    })
//                    .start();
//        }
//        if (position >lastAnimatedPosition  /* && !isFooterPosition(position)*/) {
//            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), type);
//            holder.itemView.startAnimation(animation);
//            lastAnimatedPosition = position;
//        }

//    }

}
