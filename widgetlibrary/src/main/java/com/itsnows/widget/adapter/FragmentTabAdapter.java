package com.itsnows.widget.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.Stack;

/**
 * Author: itsnows
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2017/11/7 8:54
 * <p>
 * FragmentTabAdapter
 */
public class FragmentTabAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    private Stack<TabItem> mTabItems;

    public FragmentTabAdapter(FragmentManager mFragmentManager) {
        super(mFragmentManager);
        this.mFragmentManager = mFragmentManager;
        this.mTabItems = new Stack<>();
    }

    @Override
    public Fragment getItem(int i) {
        return mTabItems.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return mTabItems.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabItems.get(position).getTitle();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    /**
     * Add fragment tab item.
     *
     * @param label
     * @param fragment
     */
    public void addItem(CharSequence label, Fragment fragment) {
        if (fragment == null) {
            throw new IllegalArgumentException("The fragment cannot be null object");
        }
        this.mTabItems.add(new TabItem(label, fragment));
        super.notifyDataSetChanged();
    }

    private static class TabItem {
        private final CharSequence title;
        private final Fragment fragment;

        private TabItem(CharSequence title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        private CharSequence getTitle() {
            return title;
        }

        private Fragment getFragment() {
            return fragment;
        }
    }
}
