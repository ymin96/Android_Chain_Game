package com.ymin.chaingame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;
import com.ymin.chaingame.adapter.viewholder.ActionExtendViewHolder;
import com.ymin.chaingame.adapter.viewholder.ActionViewHolder;
import com.ymin.chaingame.etc.Action;

import java.util.ArrayList;

public class RecyclerActionViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NORMAL_TYPE = 0;
    private static final int EXTEND_TYPE = 1;

    private ArrayList<Action> actions = null;

    public RecyclerActionViewAdapter(ArrayList<Action> list){
        this.actions = list;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }


    @NonNull
    @Override
    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        RecyclerView.ViewHolder vh = null;

        // 뷰 타입에 따라 노말, 확장형 뷰홀더를 만들어 리턴
        switch (viewType){
            case NORMAL_TYPE:
                view = inflater.inflate(R.layout.action_cardview, parent, false);
                vh = new ActionViewHolder(view);
                break;
            case EXTEND_TYPE:
                view = inflater.inflate(R.layout.action_cardview_extend, parent, false);
                vh = new ActionExtendViewHolder(view);
                break;
        }

        return vh;
    }

    @Override
    // position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Action item = actions.get(position);
        ActionViewHolder actionHolder = (ActionViewHolder) holder;

        // 공통된 데이터는 인터페이스를 통해 넣어준다.
        actionHolder.getPreFix().setText(item.getPreFix());
        actionHolder.getContent().setText(item.getContent());
        actionHolder.getPostFix().setText(item.getPostFix());

        // 만약 확장형 타입이면 추가로 필요한 데이터를 넣어준다.
        if(item.getType() == EXTEND_TYPE){
            ((ActionExtendViewHolder) actionHolder).getSubTitle().setText(item.getSubTitle());
            ((ActionExtendViewHolder) actionHolder).getSubstance().setText(item.getSubstance());
        }
    }

    @Override
    // 전체 데이터 갯수 리턴
    public int getItemCount() {
        return actions.size();
    }

    @Override
    public int getItemViewType(int position) {
        Action action = actions.get(position);
        if(action.getType() == NORMAL_TYPE)
            return NORMAL_TYPE;
        else
            return EXTEND_TYPE;
    }


}
