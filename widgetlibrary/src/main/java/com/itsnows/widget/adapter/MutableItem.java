package com.itsnows.widget.adapter;

import java.util.Comparator;

/**
 * MutableItem
 *
 * @author itsnows, xue.com.fei@gmail.com
 * @since 2018/10/10 10:46
 */
public class MutableItem implements Comparator<MutableItem> {
    private int mNo;
    private int mType;
    private Object mContent;

    public MutableItem() {
    }

    public MutableItem(int no, int type, Object content) {
        this.mNo = no;
        this.mType = type;
        this.mContent = content;
    }

    public int getNo() {
        return mNo;
    }

    public void setNo(int no) {
        this.mNo = no;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public Object getContent() {
        return mContent;
    }

    public void setContent(Object content) {
        this.mContent = content;
    }

    public <T> T getContent(Class<T> type) {
        Class<?> contentClass = mContent.getClass();
        if (contentClass.equals(type) ||
                contentClass.isAssignableFrom(type)) {
            return (T) mContent;
        }
        return null;
    }

    @Override
    public int compare(MutableItem o1, MutableItem o2) {
        return o1.getNo() - o2.getNo();
    }
}
