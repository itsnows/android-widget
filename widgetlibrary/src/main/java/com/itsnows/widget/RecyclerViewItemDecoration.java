package com.itsnows.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: itsnows
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2016/6/22 21:09
 * <p>
 * RecyclerViewItemDecoration
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDecoration;
    private int mDecorationHeight;
    private int mOrientation;
    private boolean mLeftEnabled;
    private boolean mTopEnabled;
    private boolean mRightEnabled;
    private boolean mBottomEnabled;

    public RecyclerViewItemDecoration(Context context, int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("Invalid orientation");
        }
        this.mOrientation = orientation;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        this.mDecoration = a.getDrawable(0);
        if (mOrientation != HORIZONTAL) {
            mDecorationHeight = mDecoration == null ? 0 : mDecoration.getIntrinsicHeight();
        } else {
            mDecorationHeight = mDecoration == null ? 0 : mDecoration.getIntrinsicWidth();
        }
        a.recycle();
        setDecorationEnabled(true, true, true, true);
    }

    public RecyclerViewItemDecoration(Context context, int orientation, int drawableId) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("Invalid orientation");
        }
        this.mOrientation = orientation;
        this.mDecoration = ContextCompat.getDrawable(context, drawableId);
        if (mOrientation != HORIZONTAL) {
            mDecorationHeight = mDecoration == null ? 0 : mDecoration.getIntrinsicHeight();
        } else {
            mDecorationHeight = mDecoration == null ? 0 : mDecoration.getIntrinsicWidth();
        }
        setDecorationEnabled(true, true, true, true);
    }

    public RecyclerViewItemDecoration(Context context, int orientation, int height, @ColorInt int color) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("Invalid orientation");
        }
        this.mOrientation = orientation;
        this.mDecoration = new ColorDrawable(color);
        this.mDecorationHeight = height;
        setDecorationEnabled(true, true, true, true);
    }

    public void setDecorationEnabled(boolean left, boolean top, boolean right, boolean bottom) {
        this.mLeftEnabled = left;
        this.mTopEnabled = top;
        this.mRightEnabled = right;
        this.mBottomEnabled = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (mOrientation == VERTICAL) {
            if (itemPosition == 0) {
                outRect.set(mLeftEnabled ? mDecorationHeight : 0,
                        mTopEnabled ? mDecorationHeight : 0,
                        mRightEnabled ? mDecorationHeight : 0,
                        mBottomEnabled ? mDecorationHeight : 0);
            } else {
                outRect.set(mLeftEnabled ? mDecorationHeight : 0,
                        0,
                        mRightEnabled ? mDecorationHeight : 0,
                        mBottomEnabled ? mDecorationHeight : 0);
            }
        } else {
            if (itemPosition == 0) {
                outRect.set(mLeftEnabled ? mDecorationHeight : 0,
                        mTopEnabled ? mDecorationHeight : 0,
                        mRightEnabled ? mDecorationHeight : 0,
                        mBottomEnabled ? mDecorationHeight : 0);
            } else {
                outRect.set(0,
                        mTopEnabled ? mDecorationHeight : 0,
                        mRightEnabled ? mDecorationHeight : 0,
                        mBottomEnabled ? mDecorationHeight : 0);
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDecorationHeight;
            mDecoration.setBounds(left, top, right, bottom);
            mDecoration.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDecorationHeight;
            mDecoration.setBounds(left, top, right, bottom);
            mDecoration.draw(c);
        }
    }

}
