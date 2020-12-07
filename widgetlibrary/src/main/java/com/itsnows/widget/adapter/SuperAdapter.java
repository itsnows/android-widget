package com.itsnows.widget.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 * SuperAdapter
 *
 * @author itsnows, xue.com.fei@gmail.com
 * @since 2016/3/11 15:09
 */
public abstract class SuperAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mItems;
    private int[] mItemViews;
    private int mSelection;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public SuperAdapter(Context context, List<T> items) {
        this.mContext = context;
        this.mItems = items;
        this.mSelection = -1;
        this.mInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(getItem(position));
    }

    @Override
    public int getViewTypeCount() {
        return onCreateItemView().length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (mItemViews == null) {
                mItemViews = onCreateItemView();
            }
            convertView = mInflater.inflate(mItemViews[getItemViewType(position)], parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        onBindItemView(viewHolder, getItemViewType(position), position,
                this.mSelection, this.mItems.get(position));

        OnClickEventListener onClickEventListener = new OnClickEventListener(position, parent);
        viewHolder.view.setOnClickListener(onClickEventListener);
        viewHolder.view.setOnLongClickListener(onClickEventListener);

        return convertView;
    }

    /**
     * 获取清单项视图类型
     *
     * @param item
     * @return
     */
    public int getItemViewType(T item) {
        return 0;
    }

    /**
     * 获取选中位置
     *
     * @return
     */
    public int getSelection() {
        return mSelection;
    }

    /**
     * 设置选中位置
     *
     * @param position
     */
    public void setSelection(int position) {
        this.mSelection = position;
        super.notifyDataSetChanged();
    }

    /**
     * 设置单击事件接口
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按事件接口
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 按位置添加清单项
     *
     * @param position
     * @param data
     */
    public void add(int position, T data) {
        this.mItems.add(position, data);
        super.notifyDataSetChanged();
    }

    /**
     * 按位置删除清单项
     *
     * @param position
     */
    public void delete(int position) {
        this.mItems.remove(position);
        super.notifyDataSetChanged();
    }

    /**
     * 清除清单项
     */
    public void clear() {
        mItems.clear();
        super.notifyDataSetChanged();
    }

    /**
     * 创建清单视图
     *
     * @return
     */
    public abstract int[] onCreateItemView();

    /**
     * 绑定清单视图
     *
     * @param viewHolder
     * @param position
     * @param item
     */
    public abstract void onBindItemView(ViewHolder viewHolder, int viewType, int position, int selection, T item);

    /**
     * 单击事件监听接口
     */
    public interface OnItemClickListener {

        void onItemClick(AdapterView<?> parent, View view, int position, long id);

    }

    /**
     * 长按事件监听接口
     */
    public interface OnItemLongClickListener {

        boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id);

    }

    /**
     * 视图固定器
     */
    public static class ViewHolder {
        private View view;
        private SparseArray<View> items;

        public ViewHolder(View view) {
            this.view = view;
            this.items = new SparseArray<>();
        }

        /**
         * 按id查找视图 {@link View}.
         *
         * @param viewId
         * @return
         */
        private View findViewById(int viewId) {
            View view = items.get(viewId);
            if (view == null) {
                view = this.view.findViewById(viewId);
                items.put(viewId, view);
            } else {
                items.put(viewId, view);
            }
            return view;
        }

        /**
         * @return Root view.
         */
        public View getView() {
            return view;
        }

        /**
         * 按id查找视图 {@link View}.
         *
         * @param viewId
         * @return
         */
        public View getView(int viewId) {
            return findViewById(viewId);
        }

        /**
         * 按id查找视图 {@link TextView}.
         *
         * @param viewId
         * @return
         */
        public TextView getTextView(int viewId) {
            return (TextView) getView(viewId);
        }

        /**
         * 按id查找视图 {@link EditText}.
         *
         * @param viewId
         * @return
         */
        public EditText getEditText(int viewId) {
            return (EditText) getView(viewId);
        }

        /**
         * 按id查找视图 {@link Button}.
         *
         * @param viewId
         * @return
         */
        public Button getButton(int viewId) {
            return (Button) getView(viewId);
        }

        /**
         * 按id查找视图 {@link ToggleButton}.
         *
         * @param viewId
         * @return
         */
        public ToggleButton getToogleButton(int viewId) {
            return (ToggleButton) getView(viewId);
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
         * 按id查找视图 {@link ImageButton}.
         *
         * @param viewId
         * @return
         */
        public ImageButton getImageButton(int viewId) {
            return (ImageButton) getView(viewId);
        }

        /**
         * 按id查找视图 {@link ImageView}.
         *
         * @param viewId
         * @return
         */
        public ImageView getImageView(int viewId) {
            return (ImageView) getView(viewId);
        }

        /**
         * 按id查找视图 {@link ListView}.
         *
         * @param viewId
         * @return
         */
        public ListView getListView(int viewId) {
            return (ListView) getView(viewId);
        }

        /**
         * 按id查找视图 {@link GridView}.
         *
         * @param viewId
         * @return
         */
        public GridView getGridView(int viewId) {
            return (GridView) getView(viewId);
        }
    }

    /**
     * 点击事件监听接口
     */
    public class OnClickEventListener implements OnClickListener, OnLongClickListener {
        private int position;
        private ViewGroup viewGroup;

        public OnClickEventListener(int position, ViewGroup viewGroup) {
            super();
            this.position = position;
            this.viewGroup = viewGroup;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick((AdapterView<Adapter>) viewGroup,
                        v, position, getItemId(position));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClick((AdapterView<Adapter>) viewGroup,
                        v, position, getItemId(position));
            }
            return false;
        }

    }

}
