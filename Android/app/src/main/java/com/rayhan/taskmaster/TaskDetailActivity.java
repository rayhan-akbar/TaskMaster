package com.rayhan.taskmaster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDetailActivity extends AppCompatActivity {
    public static IndividualTaskList selectedIndividualTask;
    public static GroupTaskList selectedGroupTask;
    BaseApiService mApiService;
    Context mContext;
    TextView taskName, taskDescription, taskDate, taskStatus;
    Button editTask, deleteTask;
    ProgressDialog loading;
    public static final DateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    public static final SimpleDateFormat dateFormatOutput = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        initComponents();
    }

    private void initComponents(){
        taskName = (TextView) findViewById(R.id.TaskName);
        taskDescription = (TextView) findViewById(R.id.TaskDescription);
        taskDate = (TextView) findViewById(R.id.taskDate);
        taskStatus = (TextView) findViewById(R.id.taskStatus);
        editTask = (Button) findViewById(R.id.editTask);
        deleteTask = (Button) findViewById(R.id.deleteTask);
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


        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateTaskActivity.selectedIndividualTask = selectedIndividualTask;
                UpdateTaskActivity.selectedGroupTask = selectedGroupTask;
                startActivity(new Intent(mContext, UpdateTaskActivity.class));

            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Are You Sure?");
                builder.setMessage("You want to delete this task?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);
                                DeleteTask();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private void DeleteTask() {
        if(selectedIndividualTask != null && selectedGroupTask == null){
            mApiService.deleteIndividualTask(selectedIndividualTask.getUserID(), selectedIndividualTask.getTaskId())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("message").equals("Individual Task Deleted")) {
                                        Toast.makeText(mContext, "Task Deleted", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(mContext, "Task Delete Failed", Toast.LENGTH_SHORT).show();
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
            mApiService.deleteGroupTask(selectedGroupTask.getGroupID(), selectedGroupTask.getTaskId()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        loading.dismiss();
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            if (jsonRESULTS.getString("message").equals("Group Task Deleted")) {
                                Toast.makeText(mContext, "Task Deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, GroupTaskListActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(mContext, "Task Delete Failed", Toast.LENGTH_SHORT).show();
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
