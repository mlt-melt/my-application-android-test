package com.example.sixthpractice;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.database.Cursor;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "practice_channel";
    private static final int REQUEST_NOTIFICATION_PERMISSION = 100;
    private static final String AUDIO_URL = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
    private static final String JSON_FILE_NAME = "books.json";

    private WebView webView;
    private MediaPlayer mediaPlayer;
    private boolean mediaPrepared;
    private Button playButton;
    private TextView titleField;
    private TextView authorField;
    private TextView yearField;
    private TextView resultText;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupWebView();
        setupAnimations();
        setupMediaPlayer();
        setupProviderAndJsonDemo();

        createNotificationChannel();
        requestNotificationPermissionIfNeeded();

        Button notifyButton = findViewById(R.id.notifyButton);
        notifyButton.setOnClickListener(v -> sendImmediateNotification());

        Button delayedNotifyButton = findViewById(R.id.delayedNotifyButton);
        delayedNotifyButton.setOnClickListener(v -> scheduleNotification(10_000));
    }

    private void setupProviderAndJsonDemo() {
        titleField = findViewById(R.id.titleField);
        authorField = findViewById(R.id.authorField);
        yearField = findViewById(R.id.yearField);
        resultText = findViewById(R.id.resultText);

        Button providerReadButton = findViewById(R.id.providerReadButton);
        Button exportJsonButton = findViewById(R.id.exportJsonButton);
        Button importJsonButton = findViewById(R.id.importJsonButton);

        providerReadButton.setOnClickListener(v -> {
            List<Book> books = readBooksFromProvider();
            if (books.isEmpty()) {
                resultText.setText("Провайдер не вернул данных");
                return;
            }
            resultText.setText(formatBooks(books));
            Toast.makeText(this, "Получено книг: " + books.size(), Toast.LENGTH_SHORT).show();
        });

        exportJsonButton.setOnClickListener(v -> saveBooksToJsonFile());
        importJsonButton.setOnClickListener(v -> loadBooksFromJsonFile());
    }

    private List<Book> readBooksFromProvider() {
        List<Book> books = new ArrayList<>();

        Cursor cursor = getContentResolver().query(
                BookProvider.CONTENT_URI,
                new String[]{
                        BookDbHelper.COL_ID,
                        BookDbHelper.COL_TITLE,
                        BookDbHelper.COL_AUTHOR,
                        BookDbHelper.COL_YEAR
                },
                null,
                null,
                BookDbHelper.COL_TITLE + " ASC"
        );

        if (cursor == null) {
            return books;
        }

        try {
            int idColumn = cursor.getColumnIndexOrThrow(BookDbHelper.COL_ID);
            int titleColumn = cursor.getColumnIndexOrThrow(BookDbHelper.COL_TITLE);
            int authorColumn = cursor.getColumnIndexOrThrow(BookDbHelper.COL_AUTHOR);
            int yearColumn = cursor.getColumnIndexOrThrow(BookDbHelper.COL_YEAR);

            while (cursor.moveToNext()) {
                books.add(new Book(
                        cursor.getLong(idColumn),
                        cursor.getString(titleColumn),
                        cursor.getString(authorColumn),
                        cursor.getInt(yearColumn)
                ));
            }
        } finally {
            cursor.close();
        }

        return books;
    }

    private void saveBooksToJsonFile() {
        List<Book> books = readBooksFromProvider();
        if (books.isEmpty()) {
            Toast.makeText(this, "Нет данных для сохранения", Toast.LENGTH_SHORT).show();
            return;
        }

        String json = gson.toJson(books);

        try (FileOutputStream fos = openFileOutput(JSON_FILE_NAME, MODE_PRIVATE);
             OutputStreamWriter writer = new OutputStreamWriter(fos)) {
            writer.write(json);
            resultText.setText("JSON сохранен в файл " + JSON_FILE_NAME + "\n\n" + json);
            Toast.makeText(this, "JSON сохранен", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка записи JSON", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadBooksFromJsonFile() {
        StringBuilder jsonBuilder = new StringBuilder();

        try (FileInputStream fis = openFileInput(JSON_FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Файл JSON пока не создан", Toast.LENGTH_SHORT).show();
            return;
        }

        Type listType = new TypeToken<List<Book>>() {
        }.getType();
        List<Book> books = gson.fromJson(jsonBuilder.toString(), listType);

        if (books == null || books.isEmpty()) {
            Toast.makeText(this, "В JSON нет книг", Toast.LENGTH_SHORT).show();
            return;
        }

        Book firstBook = books.get(0);
        titleField.setText("Название: " + firstBook.title);
        authorField.setText("Автор: " + firstBook.author);
        yearField.setText("Год: " + firstBook.year);

        resultText.setText("JSON прочитан из файла " + JSON_FILE_NAME + "\n\n" + formatBooks(books));
        Toast.makeText(this, "JSON загружен", Toast.LENGTH_SHORT).show();
    }

    private String formatBooks(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append("ID: ").append(book.id)
                    .append(", Название: ").append(book.title)
                    .append(", Автор: ").append(book.author)
                    .append(", Год: ").append(book.year)
                    .append("\n");
        }
        return sb.toString();
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