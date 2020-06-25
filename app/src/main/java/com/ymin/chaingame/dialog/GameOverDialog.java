package com.ymin.chaingame.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ymin.chaingame.R;
import com.ymin.chaingame.activity.MainActivity;

public class GameOverDialog extends Dialog {
    Context context;

    public GameOverDialog(@NonNull final Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_dialog);

        ImageButton mainPage = (ImageButton) findViewById(R.id.main_page);
        ImageButton resultPage = (ImageButton) findViewById(R.id.result_page);

        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Intent main = new Intent(context, MainActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                main.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(main);
            }
        });

        resultPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
