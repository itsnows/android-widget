package com.itsnows.widget.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Stack;

/**
 * FragmentTabAdapter
 *
 * @author itsnows, xue.com.fei@gmail.com
 * @since 2017/11/7 8:54
 */
public class FragmentTabAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    private Stack<TabItem> mTabItems;

    public FragmentTabAdapter(@NonNull FragmentManager fm) {
        this(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public FragmentTabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.mFragmentManager = fm;
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

    static class TabItem {
        private final CharSequence title;
        private final Fragment fragment;

        TabItem(CharSequence title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        CharSequence getTitle() {
            return title;
        }

        Fragment getFragment() {
            return fragment;
        }
    }
}
