package com.ymin.chaingame.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;
import com.ymin.chaingame.etc.Action;

import java.util.ArrayList;

public class RecyclerActionViewAdapter extends RecyclerView.Adapter<RecyclerActionViewAdapter.ViewHoler> {

    private ArrayList<Action> mData = null;

    public RecyclerActionViewAdapter(ArrayList<Action> list){
        this.mData = list;
    }

    public void setmData(ArrayList<Action> mData) {
        this.mData = mData;
    }


    @NonNull
    @Override
    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.action_cardview, parent, false);
        RecyclerActionViewAdapter.ViewHoler vh = new RecyclerActionViewAdapter.ViewHoler(view);

        return vh;
    }

    @Override
    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        Action item = mData.get(position);

        holder.preFix.setText(item.getPreFix());
        holder.content.setText(item.getContent());
        holder.postFix.setText(item.getPostFix());
    }

    @Override
    // 전체 데이터 갯수 리턴
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHoler extends RecyclerView.ViewHolder{
        TextView postFix, preFix, content;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조.
            preFix = itemView.findViewById(R.id.action_preFix);
            content = itemView.findViewById(R.id.action_content);
            postFix = itemView.findViewById(R.id.action_postFix);
        }
    }
}
