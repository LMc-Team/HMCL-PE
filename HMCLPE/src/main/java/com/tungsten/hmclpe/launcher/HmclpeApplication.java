package com.tungsten.hmclpe.launcher;

import android.app.Application;

import com.github.gzuliyujiang.oaid.DeviceIdentifier;

public class HmclpeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DeviceIdentifier.register(this);
    }
}