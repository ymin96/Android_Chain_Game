package com.ymin.chaingame.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ymin.chaingame.R;
import com.ymin.chaingame.client.ActionCreator;
import com.ymin.chaingame.client.MatchingClient;

public class ProgressDialog extends Dialog {
    ActionCreator actionCreator = new ActionCreator();

    public ProgressDialog(@NonNull Context context, final MatchingClient client) {
        super(context);
        setContentView(R.layout.matching_progress_dialog);

        ImageButton cancelButton = (ImageButton) findViewById(R.id.matching_dlg_cancel);

        // 클릭 이벤트
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.send(actionCreator.matchingConnectBreak());
                dismiss();
            }
        });
    }


}
