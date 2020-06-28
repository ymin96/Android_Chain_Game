package com.ymin.chaingame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;
import com.ymin.chaingame.adapter.viewholder.ResultViewHolder;
import com.ymin.chaingame.etc.Action;

import java.util.ArrayList;

public class RecyclerResultViewAdapter extends RecyclerView.Adapter {
    private ArrayList<Action> actions = null;

    public RecyclerResultViewAdapter(ArrayList<Action> list){
        this.actions = list;
    }

    @NonNull
    @Override
    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.result_item_view, parent, false);
        RecyclerView.ViewHolder vh = new ResultViewHolder(view);
        return vh;
    }

    @Override
    // position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Action action = actions.get(position);
        ResultViewHolder resultViewHolder = (ResultViewHolder) holder;

        resultViewHolder.getTitle().setText(action.getSubTitle());
        resultViewHolder.getContent().setText(action.getSubstance());
    }

    @Override
    // 전체 데이터 갯수 리턴
    public int getItemCount() {
        return actions.size();
    }
}
