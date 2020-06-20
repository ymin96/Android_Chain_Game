package com.ymin.chaingame.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;

// 아이템 뷰를 저장하는 뷰홀더 클래스
public class ActionViewHolder extends RecyclerView.ViewHolder{
    TextView postFix, preFix, content;

    public ActionViewHolder(@NonNull View itemView) {
        super(itemView);

        // 뷰 객체에 대한 참조.
        preFix = itemView.findViewById(R.id.action_preFix);
        content = itemView.findViewById(R.id.action_content);
        postFix = itemView.findViewById(R.id.action_postFix);
    }

    public TextView getPostFix() {
        return postFix;
    }

    public void setPostFix(TextView postFix) {
        this.postFix = postFix;
    }

    public TextView getPreFix() {
        return preFix;
    }

    public void setPreFix(TextView preFix) {
        this.preFix = preFix;
    }

    public TextView getContent() {
        return content;
    }

    public void setContent(TextView content) {
        this.content = content;
    }
}