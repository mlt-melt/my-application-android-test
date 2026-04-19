package com.example.fourthpractice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

public class CounterService extends Service {

    public static final String ACTION_COUNTER_TICK = "com.example.fourthpractice.ACTION_COUNTER_TICK";
    public static final String EXTRA_SECONDS = "extra_seconds";

    private Handler handler;
    private int seconds;
    private boolean running;

    private final Runnable counterRunnable = new Runnable() {
        @Override
        public void run() {
            if (!running) {
                return;
            }
            seconds++;
            Intent intent = new Intent(ACTION_COUNTER_TICK);
            intent.setPackage(getPackageName());
            intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
            intent.putExtra(EXTRA_SECONDS, seconds);
            sendBroadcast(intent);
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        seconds = 0;
        running = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!running) {
            running = true;
            handler.post(counterRunnable);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        running = false;
        handler.removeCallbacks(counterRunnable);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
