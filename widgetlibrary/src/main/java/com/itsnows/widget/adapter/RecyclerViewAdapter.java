package com.itsnows.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author: itsnows
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2016/6/22 19:09
 * <p>
 * RecyclerViewAdapter
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context mContext;
    private List<T> mItems;
    private View[] mItemViews;
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
            mItemViews = onCreateItemView(mInflater, parent);
        }
        View itemView = mItemViews[viewType];
        final RecyclerViewHolder viewHolder = new RecyclerViewHolder(mContext, itemView);

        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(RecyclerViewAdapter.this,
                            viewHolder.itemView, viewHolder.getLayoutPosition());
                }
            });
        }

        if (mOnItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemLongClickListener.onItemLongClick(RecyclerViewAdapter.this,
                            viewHolder.itemView, viewHolder.getLayoutPosition());

                    return false;
                }
            });
        }

        final ViewGroup viewGroup = parent;
        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
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
        return mItemViews.length;
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
    public abstract View[] onCreateItemView(LayoutInflater inflater, ViewGroup parent);

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

        void onItemLongClick(RecyclerView.Adapter adapter, View view, int position);

    }


}
