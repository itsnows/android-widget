package com.itsnows.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerViewAdapter
 *
 * @author itsnows, xue.com.fei@gmail.com
 * @since 2016/6/22 19:09
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context mContext;
    private List<T> mItems;
    private int[] mItemViews;
    private int mSelection;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public RecyclerViewAdapter(Context context, List<T> items) {
        this.mContext = context;
        this.mItems = items;
        this.mSelection = -1;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mItemViews == null) {
            mItemViews = onCreateItemView();
        }
        View itemView = mInflater.inflate(mItemViews[viewType], parent, false);
        final RecyclerViewHolder viewHolder = new RecyclerViewHolder(mContext, itemView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(RecyclerViewAdapter.this,
                            viewHolder.itemView, viewHolder.getLayoutPosition());
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mOnItemLongClickListener != null) {
                    return mOnItemLongClickListener.onItemLongClick(RecyclerViewAdapter.this,
                            viewHolder.itemView, viewHolder.getLayoutPosition());
                }
                return false;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        onBindItemView(viewHolder, getItemViewType(position), position, mSelection, mItems.get(position));
    }

    public Context getContext() {
        return mContext;
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(mItems.get(position));
    }

    /**
     * 获取清单视图数量
     *
     * @return
     */
    public int getViewTypeCount() {
        return onCreateItemView().length;
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
        if (mSelection != position) {
            super.notifyItemChanged(mSelection);
            this.mSelection = position;
            super.notifyItemChanged(mSelection);
        }
    }

    /**
     * 设置单击事件监听接口
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按事件监听接口
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 按位置添加清单项
     *
     * @param position
     * @param item
     */
    public void add(int position, T item) {
        mItems.add(position, item);
        super.notifyItemInserted(position);
    }

    /**
     * 按位置删除清单项
     *
     * @param position
     */
    public void delete(int position) {
        mItems.remove(position);
        super.notifyItemRemoved(position);
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
    public abstract void onBindItemView(RecyclerViewHolder viewHolder, int viewType,
                                        int position, int selection, T item);

    /**
     * 单击事件监听接口
     */
    public interface OnItemClickListener {

        void onItemClick(RecyclerView.Adapter adapter, View view, int position);

    }

    /**
     * 长按事件监听接口
     */
    public interface OnItemLongClickListener {

        boolean onItemLongClick(RecyclerView.Adapter adapter, View view, int position);

    }


}
