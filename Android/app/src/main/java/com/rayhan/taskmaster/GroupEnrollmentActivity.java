package com.rayhan.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rayhan.taskmaster.model.GroupEnrollmentList;
import com.rayhan.taskmaster.model.Groups;
import com.rayhan.taskmaster.model.IndividualTaskList;
import com.rayhan.taskmaster.model.users;
import com.rayhan.taskmaster.request.BaseApiService;
import com.rayhan.taskmaster.request.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupEnrollmentActivity extends AppCompatActivity {
    private ArrayList<String> groups;
    private ArrayAdapter<GroupEnrollmentList> enrollmentsAdapter;
    private ArrayList<GroupEnrollmentList> showEnrollment = new ArrayList<>();
    private ListView lvItems;
    public static GroupEnrollmentList selectedEnrollmentList = null;
    Button newEnrollment;
    BaseApiService mApiService;
    Context mContext;
    public static users currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_enrollment);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        initComponents();
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(mContext, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
    private void initComponents(){
        lvItems = (ListView) findViewById(R.id.groupList);
        newEnrollment = (Button) findViewById(R.id.enrollGroup);
        getListRequest();


        newEnrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEnrollmentActivity.currentUser = currentUser;
                CreateEnrollmentActivity.isFromProfile = true;
                startActivity(new Intent(mContext, CreateEnrollmentActivity.class));
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupDetailActivity.selectedGroup = showEnrollment.get(position);
                GroupDetailActivity.currentUser = currentUser;
                Intent intent = new Intent(mContext, GroupDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    void getListRequest() {
        Gson gson = new Gson();
        mApiService.showEnrollment(currentUser.getId())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonRESULTS.getJSONArray("showEnrollment");
                                if (jsonRESULTS.getString("message").equals("Enrollment found")){
                                    showEnrollment = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<GroupEnrollmentList>>() {}.getType());
                                    enrollmentsAdapter = new ArrayAdapter<GroupEnrollmentList>(getApplicationContext(),
                                            android.R.layout.simple_list_item_1, showEnrollment);
                                    lvItems.setAdapter(enrollmentsAdapter);

                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("User not logged in");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Sudah Login", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }


}
