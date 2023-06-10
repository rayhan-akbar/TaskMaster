package com.rayhan.taskmaster;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rayhan.taskmaster.model.GroupTaskList;
import com.rayhan.taskmaster.model.IndividualTaskList;
import com.rayhan.taskmaster.request.BaseApiService;
import com.rayhan.taskmaster.request.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTaskActivity extends AppCompatActivity {
    public static IndividualTaskList selectedIndividualTask;
    public static GroupTaskList selectedGroupTask;
    BaseApiService mApiService;
    Context mContext;
    EditText taskName, taskDescription, taskStatus;
    Button updateTask, taskDate;
    ProgressDialog loading;
    public static final DateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    public static final SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        initComponents();
    }

    private void initComponents(){
        taskName = (EditText) findViewById(R.id.TaskName);
        taskDescription = (EditText) findViewById(R.id.TaskDescription);
        taskDate = (Button) findViewById(R.id.taskDate);
        taskStatus = (EditText) findViewById(R.id.taskStatus);
        updateTask = (Button) findViewById(R.id.updateTask);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        if(selectedIndividualTask != null && selectedGroupTask == null){
            try {
                Date date = dateFormatInput.parse(selectedIndividualTask.getTaskDate());
                taskName.setText(selectedIndividualTask.getTaskName());
                taskDescription.setText(selectedIndividualTask.getTaskDescription());
                assert date != null;
                taskDate.setText(dateFormatOutput.format(date));
                taskStatus.setText(selectedIndividualTask.getTaskStatus());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(selectedIndividualTask == null && selectedGroupTask != null){
            try {
                Date date = dateFormatInput.parse(selectedGroupTask.getTaskDate());

                taskName.setText(selectedGroupTask.getTaskName());
                taskDescription.setText(selectedGroupTask.getTaskDescription());
                assert date != null;
                taskDate.setText(dateFormatOutput.format(date));
                taskStatus.setText(selectedGroupTask.getTaskStatus());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);
                UpdateTask();
            }
        });

        taskDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                DatePickerDialog taskDatePicker = new DatePickerDialog(UpdateTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        taskDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                taskDatePicker.show();
            }
        });
    }

    private void UpdateTask() {
        if(selectedIndividualTask != null && selectedGroupTask == null){
            mApiService.updateIndividualTask(selectedIndividualTask.getUserID(), selectedIndividualTask.getTaskId(), taskName.getText().toString(), taskDescription.getText().toString(), taskDate.getText().toString(), taskStatus.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("message").equals("Task Updated")) {
                                        Toast.makeText(mContext, "Task Updated", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(mContext, "Task Update Failed", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                loading.dismiss();
                                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
        }else if(selectedIndividualTask == null && selectedGroupTask != null){
            mApiService.updateGroupTask(selectedGroupTask.getGroupID(), selectedGroupTask.getTaskId(), taskName.getText().toString(), taskDescription.getText().toString(), taskDate.getText().toString(), taskStatus.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        loading.dismiss();
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            if (jsonRESULTS.getString("message").equals("Group Task Updated")) {
                                Toast.makeText(mContext, "Task Updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, GroupTaskListActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(mContext, "Task Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        loading.dismiss();
                        Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) { }
            });
        }

    }
}
