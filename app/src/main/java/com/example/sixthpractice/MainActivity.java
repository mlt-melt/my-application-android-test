package com.example.sixthpractice;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "practice_channel";
    private static final int REQUEST_NOTIFICATION_PERMISSION = 100;
    private static final String AUDIO_URL = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

    private WebView webView;
    private MediaPlayer mediaPlayer;
    private boolean mediaPrepared;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupWebView();
        setupAnimations();
        setupMediaPlayer();

        createNotificationChannel();
        requestNotificationPermissionIfNeeded();

        Button notifyButton = findViewById(R.id.notifyButton);
        notifyButton.setOnClickListener(v -> sendImmediateNotification());

        Button delayedNotifyButton = findViewById(R.id.delayedNotifyButton);
        delayedNotifyButton.setOnClickListener(v -> scheduleNotification(10_000));
    }

    private void setupWebView() {
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("https://online-edu.mirea.ru");
    }

    private void setupMediaPlayer() {
        playButton = findViewById(R.id.playButton);
        playButton.setEnabled(false);
        playButton.setText("Загрузка аудио...");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
        );

        mediaPlayer.setOnPreparedListener(mp -> {
            mediaPrepared = true;
            playButton.setEnabled(true);
            playButton.setText("Воспроизвести музыку");
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(this, "Ошибка воспроизведения аудио", Toast.LENGTH_SHORT).show();
            return true;
        });

        try {
            mediaPlayer.setDataSource(AUDIO_URL);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            playButton.setEnabled(false);
            playButton.setText("Не удалось загрузить аудио");
            Toast.makeText(this, "Ошибка загрузки аудио", Toast.LENGTH_SHORT).show();
        }

        playButton.setOnClickListener(v -> {
            if (!mediaPrepared) {
                Toast.makeText(this, "Аудио еще не готово", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playButton.setText("Продолжить музыку");
            } else {
                mediaPlayer.start();
                playButton.setText("Пауза");
            }
        });
    }

    private void setupAnimations() {
        ImageView rotateImageView = findViewById(R.id.rotateImageView);
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(rotateImageView, "rotation", 0f, 360f);
        rotateAnim.setDuration(2000);
        rotateAnim.setRepeatCount(ObjectAnimator.INFINITE);
        rotateAnim.setRepeatMode(ObjectAnimator.RESTART);
        rotateAnim.start();

        Button moveButton = findViewById(R.id.moveButton);
        moveButton.setOnClickListener(v -> {
            float target = moveButton.getTranslationX() == 0f ? 300f : 0f;
            ObjectAnimator moveAnim = ObjectAnimator.ofFloat(moveButton, "translationX", moveButton.getTranslationX(), target);
            moveAnim.setDuration(1000);
            moveAnim.start();
        });

        TextView scaleText = findViewById(R.id.scaleTextView);
        scaleText.setOnClickListener(v -> {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(scaleText, "scaleX", 1f, 2f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(scaleText, "scaleY", 1f, 2f, 1f);
            scaleX.setDuration(1000);
            scaleY.setDuration(1000);
            scaleX.start();
            scaleY.start();
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Practice Notifications";
            String description = "Channel for sixth practice notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendImmediateNotification() {
        if (!hasNotificationPermission()) {
            Toast.makeText(this, "Разрешите уведомления", Toast.LENGTH_SHORT).show();
            requestNotificationPermissionIfNeeded();
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Обычное уведомление")
                .setContentText("Это тестовое уведомление из практики 6")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat.from(this).notify(1, builder.build());
    }

    private void scheduleNotification(long delayMillis) {
        if (!hasNotificationPermission()) {
            Toast.makeText(this, "Разрешите уведомления", Toast.LENGTH_SHORT).show();
            requestNotificationPermissionIfNeeded();
            return;
        }

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long futureInMillis = System.currentTimeMillis() + delayMillis;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
        }

        Toast.makeText(this, "Отложенное уведомление через 10 секунд", Toast.LENGTH_SHORT).show();
    }

    private boolean hasNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true;
        }
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && !hasNotificationPermission()) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_NOTIFICATION_PERMISSION
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение на уведомления получено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Без разрешения уведомления не будут показаны", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (webView != null) {
            webView.destroy();
        }
    }
}