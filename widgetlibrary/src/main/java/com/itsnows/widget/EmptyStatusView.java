package com.itsnows.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/**
 * EmptyStatusView
 *
 * @author itsnows, xue.com.fei@gmail.com
 * @since 2018/9/7 20:10
 */
public class EmptyStatusView extends FrameLayout {
    private static final float DEFAULT_ICON_SIZE = 96.0f;
    private static final float DEFAULT_MESSAGE_SIZE = 14.0f;
    private static final float DEFAULT_DESCRIPTION_SIZE = 14.0f;
    private static final int DEFAULT_MESSAGE_COLOR = Color.parseColor("#FF757575");
    private static final int DEFAULT_DESCRIPTION_COLOR = Color.parseColor("#FF212121");
    private static final int DEFAULT_ICON = android.R.drawable.sym_def_app_icon;
    private static final CharSequence DEFAULT_MESSAGE = "Empty status message";
    private static final CharSequence DEFAULT_DESCRIPTION = "Empty status description";

    private int mIcon;
    private float mIconSize;
    private CharSequence mMessage;
    private float mMessageSize;
    private int mMessageColor;
    private CharSequence mDescription;
    private float mDescriptionSize;
    private int mDescriptionColor;
    private View mTargetView;
    private ImageView mIconView;
    private TextView mMessageView;
    private TextView mDescriptionView;
    private DisplayMetrics mDisplayMetrics;

    public EmptyStatusView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyStatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mDisplayMetrics = context.getResources().getDisplayMetrics();

        if (attrs == null) {
            mIcon = DEFAULT_ICON;
            mIconSize = DEFAULT_ICON_SIZE * mDisplayMetrics.density;
            mMessage = DEFAULT_MESSAGE;
            mMessageSize = DEFAULT_MESSAGE_SIZE * mDisplayMetrics.density;
            mMessageColor = DEFAULT_MESSAGE_COLOR;
            mDescription = DEFAULT_DESCRIPTION;
            mDescriptionSize = DEFAULT_DESCRIPTION_SIZE * mDisplayMetrics.density;
            mDescriptionColor = DEFAULT_DESCRIPTION_COLOR;
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyStatusView, defStyleAttr, 0);
        mIcon = typedArray.getResourceId(R.styleable.EmptyStatusView_empty_status_icon, DEFAULT_ICON);
        mIconSize = typedArray.getDimensionPixelSize(R.styleable.EmptyStatusView_empty_status_icon_size,
                (int) (DEFAULT_ICON_SIZE * mDisplayMetrics.density));
        mMessage = typedArray.getText(R.styleable.EmptyStatusView_empty_status_message);
        mMessageSize = typedArray.getDimensionPixelSize(R.styleable.EmptyStatusView_empty_status_message_size,
                (int) (DEFAULT_MESSAGE_SIZE * mDisplayMetrics.density));
        mMessageColor = typedArray.getColor(R.styleable.EmptyStatusView_empty_status_message_size,
                DEFAULT_MESSAGE_COLOR);
        mDescription = typedArray.getText(R.styleable.EmptyStatusView_empty_status_description);
        mDescriptionSize = typedArray.getDimensionPixelSize(R.styleable.EmptyStatusView_empty_status_message_size,
                (int) (DEFAULT_DESCRIPTION_SIZE * mDisplayMetrics.density));
        mDescriptionColor = typedArray.getColor(R.styleable.EmptyStatusView_empty_status_message_size,
                DEFAULT_DESCRIPTION_COLOR);

        if (mMessage == null) {
            mMessage = DEFAULT_MESSAGE;
        }
        if (mDescription == null) {
            mDescription = DEFAULT_DESCRIPTION;
        }
        typedArray.recycle();

        if (getParent() != null) {
            bindTarget((ViewGroup) getParent());
        }
    }

    /**
     * Binding target view
     *
     * @param targetView
     * @return
     */
    public void bindTarget(View targetView) {
        if (targetView == null) {
            throw new IllegalArgumentException("Parent view can not be null");
        }
        this.mTargetView = targetView;

        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        LayoutParams flChild = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        flChild.gravity = Gravity.CENTER;
        View vChild = createChildView();
        vChild.setLayoutParams(flChild);
        addView(vChild);
        if (targetView instanceof LinearLayout) {
            LinearLayout llParent = (LinearLayout) targetView;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            setLayoutParams(params);
            llParent.addView(this, 0);
        } else if (targetView instanceof RelativeLayout) {
            RelativeLayout rlParent = (RelativeLayout) targetView;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            setLayoutParams(params);
            rlParent.addView(this, 0);
        } else if (targetView instanceof FrameLayout) {
            FrameLayout flParent = (FrameLayout) targetView;
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            setLayoutParams(params);
            flParent.addView(this, 0);
        } else if (targetView instanceof CoordinatorLayout) {
            CoordinatorLayout clParent = (CoordinatorLayout) targetView;
            CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,
                    CoordinatorLayout.LayoutParams.MATCH_PARENT);
            AppBarLayout.ScrollingViewBehavior behavior = new AppBarLayout.ScrollingViewBehavior();
            // params.setBehavior(behavior);
            setLayoutParams(params);
            clParent.addView(this, 0);
        } else if (targetView instanceof ViewGroup) {
            ViewGroup vgParent = (ViewGroup) targetView;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            setLayoutParams(params);
            vgParent.addView(this, 0);
        } else {
            ViewParent vParent = targetView.getParent();
            if (vParent == null) {
                throw new IllegalStateException("TargetView must is ViewGroup or TargetView must have a parent");
            }
            bindTarget((ViewGroup) vParent);
            return;
        }
        setVisibility(View.GONE);
    }

    /**
     * Get target view
     *
     * @return View
     */
    public View getTargetView() {
        return mTargetView;
    }

    /**
     * Get empty status icon
     *
     * @return
     */
    public Drawable getIcon() {
        return mIconView.getDrawable();
    }

    /**
     * Set empty status icon
     *
     * @param resId
     * @return
     */
    public void setIcon(int resId) {
        if (resId != 0) {
            this.mIcon = resId;
            mIconView.setImageResource(resId);
        }
    }

    /**
     * Set empty status icon size
     *
     * @param value
     */
    public void setIconSize(float value) {
        this.mIconSize = value;
        ViewGroup.LayoutParams lp = mIconView.getLayoutParams();
        lp.width = (int) mIconSize;
        lp.height = (int) mIconSize;
        mIconView.setLayoutParams(lp);
    }

    /**
     * Get empty status message
     *
     * @return
     */
    public CharSequence getMessage() {
        return this.mMessage;
    }

    /**
     * Set empty status message
     *
     * @param message
     * @return
     */
    public void setMessage(CharSequence message) {
        if (message != null) {
            this.mMessage = message;
            mMessageView.setText(message);
        }
    }

    /**
     * Set empty status message text size
     *
     * @param value
     * @return
     */
    public void setMessageSize(float value) {
        this.mMessageSize = value;
        mMessageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, value);
    }

    /**
     * Set empty status message text color
     *
     * @param value
     * @return
     */
    public void setMessageColor(int value) {
        this.mMessageColor = value;
        mMessageView.setTextColor(value);
    }

    /**
     * Get empty status description
     *
     * @return
     */
    public CharSequence getDescription() {
        return mDescription;
    }

    /**
     * Set empty status description text color
     *
     * @param description
     * @return
     */
    public void setDescription(CharSequence description) {
        if (description != null) {
            this.mDescription = description;
            mDescriptionView.setText(description);
        }
    }

    /**
     * Get empty status description text size
     *
     * @return
     */
    public void setDescriptionSize(float value) {
        this.mDescriptionSize = value;
        mDescriptionView.setTextSize(TypedValue.COMPLEX_UNIT_PX, value);
    }

    /**
     * Get empty status description text color
     *
     * @return
     */
    public void setDescriptionColor(int value) {
        this.mDescriptionColor = value;
        mDescriptionView.setTextColor(value);
    }

    /**
     * Showing empty status
     */
    public void showed() {
        if (checkViewStatus()) {
            if (getVisibility() == GONE) {
                setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Hideing empty status
     */
    public void hided() {
        if (checkViewStatus()) {
            if (getVisibility() == VISIBLE) {
                setVisibility(View.GONE);
            }
        }
    }

    /**
     * Check view status
     *
     * @return
     */
    private boolean checkViewStatus() {
        return mTargetView != null;
    }

    /**
     * Create child view
     *
     * @return
     */
    private View createChildView() {
        LinearLayout llParent = new LinearLayout(getContext());
        llParent.setGravity(Gravity.CENTER_HORIZONTAL);
        llParent.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lpIcon = new LinearLayout.LayoutParams((int) mIconSize,
                (int) mIconSize);
        ImageView ivIcon = new ImageButton(getContext());
        ivIcon.setBackgroundColor(Color.TRANSPARENT);
        ivIcon.setImageResource(mIcon);
        ivIcon.setLayoutParams(lpIcon);
        llParent.addView(ivIcon);

        LinearLayout.LayoutParams lpMessage = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lpMessage.setMargins(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics), 0, 0);
        TextView tvMessage = new TextView(getContext());
        tvMessage.setText(mMessage);
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMessageSize);
        tvMessage.setTextColor(mMessageColor);
        tvMessage.setLayoutParams(lpMessage);
        llParent.addView(tvMessage);

        LinearLayout.LayoutParams lpDescripton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lpDescripton.setMargins(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics), 0, 0);
        TextView tvDescription = new TextView(getContext());
        tvDescription.setText(mDescription);
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDescriptionSize);
        tvMessage.setTextColor(mDescriptionColor);
        tvDescription.setTextColor(Color.parseColor("#FF9E9E9E"));
        tvDescription.setLayoutParams(lpDescripton);
        llParent.addView(tvDescription);

        this.mIconView = ivIcon;
        this.mMessageView = tvMessage;
        this.mDescriptionView = tvDescription;
        return llParent;
    }

}
