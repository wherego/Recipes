package com.i7gk.recipes.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.i7gk.recipes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.step_image_activity)
    ImageView stepImageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_image);
        ButterKnife.bind(this);
        Glide.with(this).load(getIntent().getStringExtra("image_address"))
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .into(stepImageActivity);
        stepImageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
