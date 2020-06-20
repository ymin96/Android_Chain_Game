package com.ymin.chaingame.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;

public class ActionExtendViewHolder extends ActionViewHolder {

    TextView subTitle, substance;

    public ActionExtendViewHolder(@NonNull View itemView) {
        super(itemView);
        subTitle = itemView.findViewById(R.id.sub_title);
        substance = itemView.findViewById(R.id.substance);
    }

    public TextView getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(TextView subTitle) {
        this.subTitle = subTitle;
    }

    public TextView getSubstance() {
        return substance;
    }

    public void setSubstance(TextView substance) {
        this.substance = substance;
    }
}
