package com.example.fourthpractice;

import android.content.Context;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DemoWorker extends Worker {

    public static final String KEY_TASK_NAME = "task_name";
    public static final String KEY_DELAY_SECONDS = "delay_seconds";
    public static final String KEY_RESULT_TEXT = "result_text";

    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String taskName = getInputData().getString(KEY_TASK_NAME);
        int delaySeconds = getInputData().getInt(KEY_DELAY_SECONDS, 2);

        if (taskName == null || taskName.trim().isEmpty()) {
            taskName = "Unnamed task";
        }

        SystemClock.sleep(delaySeconds * 1000L);

        Data output = new Data.Builder()
                .putString(KEY_RESULT_TEXT, taskName + " завершена")
                .build();

        return Result.success(output);
    }
}
