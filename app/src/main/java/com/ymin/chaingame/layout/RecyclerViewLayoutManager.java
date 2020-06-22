package com.ymin.chaingame.layout;


import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class RecyclerViewLayoutManager extends LinearLayoutManager {
    private int mParentWidth;
    private int mItemWidth;

    public RecyclerViewLayoutManager(Context context, int parentWidth, int itemWidth) {
        super(context);
        this.mParentWidth = parentWidth;
        this.mItemWidth = itemWidth;
    }

    @Override
    public int getPaddingLeft() {
        return Math.round(mParentWidth / 2f - mItemWidth / 2f);
    }

    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }
}
