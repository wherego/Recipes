package com.i7gk.recipes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.i7gk.recipes.R;
import com.i7gk.recipes.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity {
    @BindView(R.id.image_viewpager)
    ViewPager imageViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_image);
        ButterKnife.bind(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        Intent intent = getIntent();
        int position = intent.getIntExtra("imagePosition", 0);
        List<String> list = intent.getStringArrayListExtra("imageAddressList");
        List<ImageView> images = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ImageView view= (ImageView) inflater.inflate(R.layout.image_item,null);
            Glide.with(this).load(list.get(i)).into(view);
            images.add(view);
        }
        ImageAdapter imageAdapter = new ImageAdapter(images);
        imageViewpager.setAdapter(imageAdapter);
        imageViewpager.setCurrentItem(position);
    }
}
