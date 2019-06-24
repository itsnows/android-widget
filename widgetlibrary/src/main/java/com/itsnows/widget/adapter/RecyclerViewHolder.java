package com.itsnows.widget.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author: itsnows
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2016/6/22 21:09
 * <p>
 * RecyclerViewHolder
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private SparseArray<View> mViews;

    public RecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mViews = new SparseArray<View>();
    }

    /**
     * 按id查找视图 {@link View}.
     *
     * @param viewId
     * @return
     */
    private View findViewById(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    /**
     * @return Root view.
     */
    public View getView() {
        return itemView;
    }

    /**
     * 按id查找视图 {@link View}.
     *
     * @param viewId
     * @return
     */
    public View getView(@IdRes int viewId) {
        return findViewById(viewId);
    }

    /**
     * 按id查找视图 {@link TextView}.
     *
     * @param viewId
     * @return
     */
    public TextView getTextView(@IdRes int viewId) {
        return (TextView) getView(viewId);
    }

    /**
     * 按id查找视图 {@link EditText}.
     *
     * @param viewId
     * @return
     */
    public EditText getEditText(@IdRes int viewId) {
        return (EditText) getView(viewId);
    }

    /**
     * 按id查找视图 {@link ImageView}.
     *
     * @param viewId
     * @return
     */

    public ImageView getImageView(@IdRes int viewId) {
        return (ImageView) getView(viewId);
    }

    /**
     * 按id查找视图 {@link Button}.
     *
     * @param viewId
     * @return
     */

    public Button getButton(@IdRes int viewId) {
        return (Button) getView(viewId);
    }

    /**
     * 按id查找视图 {@link ImageButton}.
     *
     * @param viewId
     * @return
     */
    public ImageButton getImageButton(@IdRes int viewId) {
        return (ImageButton) getView(viewId);
    }

    /**
     * 按id查找视图 {@link RadioButton}.
     *
     * @param viewId
     * @return
     */
    public RadioButton getRadioButton(int viewId) {
        return (RadioButton) getView(viewId);
    }

    /**
     * 按id查找视图 {@link RadioGroup}.
     *
     * @param viewId
     * @return
     */
    public RadioGroup getRadioGroup(int viewId) {
        return (RadioGroup) getView(viewId);
    }

    /**
     * 按id查找视图 {@link ToggleButton}.
     *
     * @param viewId
     * @return
     */
    public ToggleButton getToggleButton(@IdRes int viewId) {
        return (ToggleButton) getView(viewId);
    }

    /**
     * 按id查找视图 {@link Switch}.
     *
     * @param viewId
     * @return
     */
    public Switch getSwitch(@IdRes int viewId) {
        return (Switch) getView(viewId);
    }

    /**
     * 按id查找视图 {@link View} 并且设置指定属性值.
     *
     * @param viewId
     * @param method
     * @param values
     * @return
     */
    public RecyclerViewHolder setViewValue(@IdRes int viewId, String method, Object... values) {
        View view = getView(viewId);
        Class<?>[] parameterTypes = new Class[values.length];
        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
            Object value = values[i];
            if (value != null) {
                parameterTypes[i] = value.getClass();
            }
        }
        Class<?> viewClass = view.getClass();
        try {
            Method viewMethod = viewClass.getMethod(method, parameterTypes);
            if (viewMethod != null) {
                viewMethod.invoke(view, values);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return this;
    }

}
