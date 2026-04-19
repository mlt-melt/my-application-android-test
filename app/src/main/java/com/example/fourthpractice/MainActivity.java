package com.example.fourthpractice;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;

import android.content.Intent;
import android.content.IntentFilter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvServiceStatus;
    private TextView tvCounter;
    private TextView tvDialogResult;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvCustom;
    private TextView tvWorkResult;
    private TextView tvDogUrl;
    private ImageView ivDog;
    private WorkInfo.State parallelFirstState;
    private WorkInfo.State parallelSecondState;

    private final BroadcastReceiver counterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (CounterService.ACTION_COUNTER_TICK.equals(intent.getAction())) {
                int seconds = intent.getIntExtra(CounterService.EXTRA_SECONDS, 0);
                tvCounter.setText("Счетчик: " + seconds + " сек.");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initServiceControls();
        initDialogs();
        initWorkButtons();

        IntentFilter filter = new IntentFilter(CounterService.ACTION_COUNTER_TICK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(counterReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(counterReceiver, filter);
        }
    }

    private void initViews() {
        tvServiceStatus = findViewById(R.id.tvServiceStatus);
        tvCounter = findViewById(R.id.tvCounter);
        tvDialogResult = findViewById(R.id.tvDialogResult);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvCustom = findViewById(R.id.tvCustom);
        tvWorkResult = findViewById(R.id.tvWorkResult);
        tvDogUrl = findViewById(R.id.tvDogUrl);
        ivDog = findViewById(R.id.ivDog);
    }

    private void initServiceControls() {
        Button btnStartService = findViewById(R.id.btnStartService);
        Button btnStopService = findViewById(R.id.btnStopService);

        btnStartService.setOnClickListener(v -> {
            startService(new Intent(this, CounterService.class));
            tvServiceStatus.setText("Статус сервиса: запущен");
        });

        btnStopService.setOnClickListener(v -> {
            stopService(new Intent(this, CounterService.class));
            tvServiceStatus.setText("Статус сервиса: остановлен");
        });
    }

    private void initDialogs() {
        findViewById(R.id.btnAlert).setOnClickListener(v -> showAlertDialog());
        findViewById(R.id.btnDate).setOnClickListener(v -> showDateDialog());
        findViewById(R.id.btnTime).setOnClickListener(v -> showTimeDialog());
        findViewById(R.id.btnCustom).setOnClickListener(v -> showCustomDialog());
    }

    private void initWorkButtons() {
        findViewById(R.id.btnRunSequential).setOnClickListener(v -> runSequentialWork());
        findViewById(R.id.btnRunParallel).setOnClickListener(v -> runParallelWork());
        findViewById(R.id.btnLoadDog).setOnClickListener(v -> loadDogImage());
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Подтверждение")
                .setMessage("Вы уверены, что хотите выполнить действие?")
                .setPositiveButton("Да", (dialog, which) -> tvDialogResult.setText("Alert: нажали Да"))
                .setNegativeButton("Отмена", (dialog, which) -> tvDialogResult.setText("Alert: отмена"))
                .show();
    }

    private void showDateDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> tvDate.setText(String.format(
                        Locale.getDefault(),
                        "Дата: %02d.%02d.%d",
                        dayOfMonth,
                        month + 1,
                        year
                )),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void showTimeDialog() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> tvTime.setText(String.format(
                        Locale.getDefault(),
                        "Время: %02d:%02d",
                        hourOfDay,
                        minute
                )),
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        dialog.show();
    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_input);

        EditText etCustomInput = dialog.findViewById(R.id.etCustomInput);
        Button btnApply = dialog.findViewById(R.id.btnApplyCustom);
        Button btnCancel = dialog.findViewById(R.id.btnCancelCustom);

        btnApply.setOnClickListener(v -> {
            String value = etCustomInput.getText().toString().trim();
            if (value.isEmpty()) {
                value = "(пусто)";
            }
            tvCustom.setText("Custom: " + value);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void runSequentialWork() {
        tvWorkResult.setText("Статус задач: запускаю 3 задачи последовательно...");

        OneTimeWorkRequest w1 = createDemoWork("Последовательная 1", 2);
        OneTimeWorkRequest w2 = createDemoWork("Последовательная 2", 2);
        OneTimeWorkRequest w3 = createDemoWork("Последовательная 3", 2);

        WorkManager.getInstance(this)
                .beginWith(w1)
                .then(w2)
                .then(w3)
                .enqueue();

        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(w3.getId())
                .observe(this, workInfo -> {
                    if (workInfo == null) {
                        return;
                    }
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        String result = workInfo.getOutputData().getString(DemoWorker.KEY_RESULT_TEXT);
                        tvWorkResult.setText("Статус задач: последовательная цепочка завершена. " + result);
                    } else {
                        tvWorkResult.setText("Статус задач: " + workInfo.getState().name());
                    }
                });
    }

    private void runParallelWork() {
        tvWorkResult.setText("Статус задач: запускаю 2 задачи параллельно...");
        parallelFirstState = null;
        parallelSecondState = null;

        OneTimeWorkRequest w1 = createDemoWork("Параллельная A", 3);
        OneTimeWorkRequest w2 = createDemoWork("Параллельная B", 3);
        List<OneTimeWorkRequest> list = Arrays.asList(w1, w2);

        WorkManager.getInstance(this).enqueue(list);

        observeParallelCompletion(w1.getId().toString(), w2.getId().toString(), w1.getId(), w2.getId());
    }

    private void observeParallelCompletion(String firstName, String secondName, java.util.UUID firstId, java.util.UUID secondId) {
        WorkManager wm = WorkManager.getInstance(this);
        wm.getWorkInfoByIdLiveData(firstId).observe(this, first -> {
            if (first != null) {
                parallelFirstState = first.getState();
                updateParallelState(firstName, secondName);
            }
        });
        wm.getWorkInfoByIdLiveData(secondId).observe(this, second -> {
            if (second != null) {
                parallelSecondState = second.getState();
                updateParallelState(firstName, secondName);
            }
        });
    }

    private void updateParallelState(String firstName, String secondName) {
        if (parallelFirstState == null || parallelSecondState == null) {
            return;
        }

        String stateLine = "Статус задач: "
                + firstName + " = " + parallelFirstState.name()
                + ", "
                + secondName + " = " + parallelSecondState.name();

        if (parallelFirstState == WorkInfo.State.SUCCEEDED && parallelSecondState == WorkInfo.State.SUCCEEDED) {
            tvWorkResult.setText(stateLine + "\nОбе параллельные задачи успешно завершены.");
        } else {
            tvWorkResult.setText(stateLine);
        }
    }

    @NonNull
    private OneTimeWorkRequest createDemoWork(String name, int delaySeconds) {
        Data input = new Data.Builder()
                .putString(DemoWorker.KEY_TASK_NAME, name)
                .putInt(DemoWorker.KEY_DELAY_SECONDS, delaySeconds)
                .build();
        return new OneTimeWorkRequest.Builder(DemoWorker.class)
                .setInputData(input)
                .build();
    }

    private void loadDogImage() {
        tvDogUrl.setText("URL: загружаю...");

        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(DogImageWorker.class).build();
        WorkManager.getInstance(this).enqueue(work);

        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(work.getId())
                .observe(this, workInfo -> {
                    if (workInfo == null) {
                        return;
                    }
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        String imageUrl = workInfo.getOutputData().getString(DogImageWorker.KEY_IMAGE_URL);
                        if (imageUrl != null) {
                            tvDogUrl.setText("URL: " + imageUrl);
                            Glide.with(this).load(imageUrl).into(ivDog);
                        }
                    } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                        tvDogUrl.setText("URL: не удалось загрузить изображение.");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(counterReceiver);
        } catch (IllegalArgumentException ignored) {
        }
    }
}