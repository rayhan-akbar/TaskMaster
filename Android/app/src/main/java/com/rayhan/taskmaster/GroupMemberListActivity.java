package com.rayhan.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rayhan.taskmaster.model.GroupEnrollmentList;
import com.rayhan.taskmaster.model.GroupMemberList;
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

public class GroupMemberListActivity extends AppCompatActivity {
    private ArrayList<String> groups;
    private ArrayAdapter<GroupMemberList> enrollmentsAdapter;
    private ArrayList<GroupMemberList> showGroupMember = new ArrayList<>();
    private ListView lvItems;
    public static GroupMemberList selectedMemberList = null;
    BaseApiService mApiService;
    Context mContext;
    public static GroupEnrollmentList currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        initComponents();
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(mContext, GroupDetailActivity.class);
        startActivity(intent);
        finish();
    }
    private void initComponents(){
        lvItems = (ListView) findViewById(R.id.groupList);
        getListRequest();
    }

    void getListRequest() {
        Gson gson = new Gson();
        mApiService.showGroupMembers(currentGroup.getGroupID())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonRESULTS.getJSONArray("showGroupMembers");
                                if (jsonRESULTS.getString("message").equals("Group Member found")){
                                    showGroupMember = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<GroupMemberList>>() {}.getType());
                                    enrollmentsAdapter = new ArrayAdapter<GroupMemberList>(getApplicationContext(),
                                            android.R.layout.simple_list_item_1, showGroupMember);
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
