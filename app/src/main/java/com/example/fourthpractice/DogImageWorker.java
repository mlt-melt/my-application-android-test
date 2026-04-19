package com.example.fourthpractice;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DogImageWorker extends Worker {

    public static final String KEY_IMAGE_URL = "image_url";
    private static final String API_URL = "https://random.dog/woof.json";

    public DogImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String imageUrl = tryFetchImageUrl();
        if (imageUrl == null) {
            return Result.failure();
        }

        Data output = new Data.Builder()
                .putString(KEY_IMAGE_URL, imageUrl)
                .build();
        return Result.success(output);
    }

    private String tryFetchImageUrl() {
        for (int i = 0; i < 5; i++) {
            String candidate = fetchSingleUrl();
            if (candidate != null && isImage(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private String fetchSingleUrl() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(API_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject json = new JSONObject(builder.toString());
            return json.optString("url", null);
        } catch (Exception ignored) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ignored) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private boolean isImage(String url) {
        String lower = url.toLowerCase();
        return lower.endsWith(".jpg")
                || lower.endsWith(".jpeg")
                || lower.endsWith(".png")
                || lower.endsWith(".gif")
                || lower.endsWith(".webp");
    }
}
