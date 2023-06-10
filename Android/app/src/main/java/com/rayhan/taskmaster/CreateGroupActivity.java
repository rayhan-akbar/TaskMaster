package com.rayhan.taskmaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rayhan.taskmaster.request.BaseApiService;
import com.rayhan.taskmaster.request.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class CreateGroupActivity extends AppCompatActivity {
    private EditText addGroupName, addGroupDescription;
    private Button addGroupButton;
    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        getSupportActionBar().hide();
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();
    }

    private void initComponents() {
        addGroupName = (EditText) findViewById(R.id.addGroupName);
        addGroupDescription = (EditText) findViewById(R.id.addGroupDescription);
        addGroupButton = (Button) findViewById(R.id.addGroupButton);

        addGroupButton.setEnabled(false);

        addGroupName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addGroupButton.setEnabled(s.toString().trim().length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addGroupDescription.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addGroupButton.setEnabled(s.toString().trim().length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                AddGroup();
            }
        });
    }

    private void AddGroup() {
        if (addGroupName.getText().toString().isEmpty() || addGroupDescription.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else {
            mApiService.addGroup(addGroupName.getText().toString(), addGroupDescription.getText().toString())
                    .enqueue(new retrofit2.Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("message").equals("Group Created")) {
                                        Toast.makeText(mContext, "Group Created", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, CreateEnrollmentActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(mContext, "Failed to add Group", Toast.LENGTH_SHORT).show();
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
}


