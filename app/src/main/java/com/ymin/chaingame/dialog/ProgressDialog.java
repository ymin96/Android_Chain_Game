package com.ymin.chaingame.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.ymin.chaingame.R;

public class ProgressDialog extends Dialog {


    public ProgressDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.matching_progress_dialog);

        ImageButton cancelButton = (ImageButton) findViewById(R.id.matching_dlg_cancel);

        // 클릭 이벤트
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog.this.dismiss();
            }
        });
    }
}
