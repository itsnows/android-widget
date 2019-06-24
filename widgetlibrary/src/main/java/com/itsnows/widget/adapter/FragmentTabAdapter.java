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
    private FragmentManager fragmentManager;
    private Stack<TabItem> tabItems;

    public FragmentTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.tabItems = new Stack<>();
    }

    @Override
    public Fragment getItem(int i) {
        return tabItems.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return tabItems.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabItems.get(position).getTitle();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    public void addItem(CharSequence title, Fragment fragment) {
        if (title == null) {
            throw new IllegalArgumentException("The title cannot be null object");
        }
        if (fragment == null) {
            throw new IllegalArgumentException("The fragment cannot be null object");
        }
        this.tabItems.add(new TabItem(title, fragment));
        super.notifyDataSetChanged();
    }

    private static class TabItem {
        private final CharSequence title;
        private final Fragment fragment;

        private TabItem(CharSequence title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public CharSequence getTitle() {
            return title;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }
}
