package com.rayhan.taskmaster;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rayhan.taskmaster.model.Groups;
import com.rayhan.taskmaster.model.users;
import com.rayhan.taskmaster.request.BaseApiService;
import com.rayhan.taskmaster.request.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class CreateTaskActivity extends AppCompatActivity {
    private EditText addTaskName, addTaskDescription;
    private Button addTaskButton, addTaskDate;
    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;
    public static users currentUser;
    public static Groups currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().hide();
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();
    }
    private void initComponents(){
        addTaskName = (EditText) findViewById(R.id.addTaskName);
        addTaskDescription = (EditText) findViewById(R.id.addTaskDescription);
        addTaskDate = (Button) findViewById(R.id.addTaskDate);
        addTaskButton = (Button) findViewById(R.id.addTaskButton);

        addTaskButton.setEnabled(false);


        addTaskName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addTaskButton.setEnabled(s.toString().trim().length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addTaskDescription.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addTaskButton.setEnabled(s.toString().trim().length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addTaskDate.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addTaskButton.setEnabled(s.toString().trim().length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null && currentGroup == null){
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                    try {
                        addIndividualTask();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else if(currentUser == null && currentGroup != null){
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                    addGroupTask();
                }

            }
        });

        addTaskDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                DatePickerDialog taskDatePicker = new DatePickerDialog(CreateTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        addTaskDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                taskDatePicker.show();
            }
        });
    }

    private void addIndividualTask() throws ParseException {
        if (addTaskName.getText().toString().isEmpty() || addTaskDescription.getText().toString().isEmpty() || addTaskDate.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else {
            mApiService.addIndividualTask(currentUser.getId(), addTaskName.getText().toString(), addTaskDescription.getText().toString(), addTaskDate.getText().toString())
                    .enqueue(new retrofit2.Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("message").equals("Individual Task Created")) {
                                        Toast.makeText(mContext, "Task Created", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(mContext, "Failed to add Task", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(mContext, "Failed to add Task", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(mContext, "Failed to add Income", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mContext, "Failed to add Task", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void addGroupTask() {
        if (addTaskName.getText().toString().isEmpty() || addTaskDescription.getText().toString().isEmpty() || addTaskDate.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else {
            mApiService.addGroupTask(currentGroup.getId(), addTaskName.getText().toString(), addTaskDescription.getText().toString(), addTaskDate.getText().toString())
                    .enqueue(new retrofit2.Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("message").equals("Group Task Created")) {
                                        Toast.makeText(mContext, "Task Created", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, GroupTaskListActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(mContext, "Failed to add Task", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(mContext, "Failed to add Task", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mContext, "Failed to add Task", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
