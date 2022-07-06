package com.tungsten.hmclpe.launcher.dialogs.hin2n;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tungsten.hmclpe.R;
import com.tungsten.hmclpe.miltiplayer.LocalServerDetector;

public class CreateCommunityDialog extends Dialog implements View.OnClickListener {

    private ProgressBar progressBar;

    private TextView nameText;
    private TextView portText;
    private TextView errorText;
    private Button positive;
    private Button negative;

    private LocalServerDetector lanServerDetectorThread;

    public CreateCommunityDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_create_community);
        setCancelable(false);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        progressBar = findViewById(R.id.progress);

        nameText = findViewById(R.id.name);
        portText = findViewById(R.id.port);
        errorText = findViewById(R.id.error);

        positive = findViewById(R.id.create);
        negative = findViewById(R.id.exit);

        positive.setOnClickListener(this);
        negative.setOnClickListener(this);

        progressBar.setVisibility(View.VISIBLE);
        nameText.setText(getContext().getString(R.string.dialog_create_community_getting));
        portText.setText(getContext().getString(R.string.dialog_create_community_getting));
        errorText.setVisibility(View.GONE);
        positive.setEnabled(false);

        Handler handler = new Handler();
        lanServerDetectorThread = new LocalServerDetector(3);
        lanServerDetectorThread.onDetectedLanServer().register(event -> {
            handler.post(() -> {
                if (event.getLanServer() != null && event.getLanServer().isValid()) {
                    nameText.setText(event.getLanServer().getMotd());
                    portText.setText(event.getLanServer().getAd().toString());
                    positive.setEnabled(true);
                } else {
                    nameText.setText("");
                    portText.setText("");
                    errorText.setVisibility(View.VISIBLE);
                    positive.setEnabled(false);
                }
                progressBar.setVisibility(View.GONE);
            });
        });
        lanServerDetectorThread.start();
    }

    @Override
    public void onClick(View view) {
        if (view == positive) {

        }
        if (view == negative) {
            lanServerDetectorThread.interrupt();
            dismiss();
        }
    }
}