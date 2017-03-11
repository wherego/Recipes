package com.i7gk.recipes.ui.setting;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.i7gk.recipes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SettingContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SettingsFragment settingsFragment=new SettingsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.setting_container,settingsFragment)
                .commit();
        presenter=new SettingPresenter(SettingsActivity.this,settingsFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
