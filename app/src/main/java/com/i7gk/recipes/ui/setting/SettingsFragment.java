package com.i7gk.recipes.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.i7gk.recipes.R;

import static android.app.Activity.RESULT_OK;


public class SettingsFragment extends PreferenceFragmentCompat implements SettingContract.View{

    private SettingContract.Presenter presenter;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_fragment);
        findPreference("skim_collection").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.skimCollection();
                return false;
            }
        });
        findPreference("clear_collection").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.clearCollection();
                return false;
            }
        });
        findPreference("clear_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.clearCache();
                return false;
            }
        });
        findPreference("clear_search_history").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.clearSearchHistory();
                return false;
            }
        });
        findPreference("clear_image_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.clearImageCache();
                return false;
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        if (presenter!=null){
            this.presenter=presenter;
        }
    }

    @Override
    public void showNoRecipeCollected() {
        Toast.makeText(getActivity(),"您没有收藏过任何菜谱",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCollectionCleared() {
        Toast.makeText(getActivity(), "已清空收藏", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCacheCleared() {
        Toast.makeText(getActivity(), "清理缓存成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showImageCacheCleared() {
        Toast.makeText(getActivity(), "清理图片缓存成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHistoryCleared() {
        Toast.makeText(getActivity(), "清理搜索历史成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnResult() {
        Intent intent = new Intent();
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
    }
}
