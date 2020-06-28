package com.ymin.chaingame.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;

public class ResultViewHolder extends RecyclerView.ViewHolder{
    TextView title, content;

    public ResultViewHolder(@NonNull View itemView) {
        super(itemView);

        // 뷰 객체에 대한 참조.
        title = itemView.findViewById(R.id.result_title);
        content = itemView.findViewById(R.id.result_content);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getContent() {
        return content;
    }

    public void setContent(TextView content) {
        this.content = content;
    }
}
