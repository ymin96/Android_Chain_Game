package com.ymin.chaingame.layout;


import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

// 리사이클려뷰의 자식 아이템들을 가운데 수평 정렬 시키기 위해 만든 커스텀 클래스
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
