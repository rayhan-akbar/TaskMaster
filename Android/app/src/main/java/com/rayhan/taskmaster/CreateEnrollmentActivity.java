package com.rayhan.taskmaster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rayhan.taskmaster.model.GroupEnrollmentList;
import com.rayhan.taskmaster.model.GroupTaskList;
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

public class CreateEnrollmentActivity extends AppCompatActivity {
    private ArrayList<String> groups;
    private ArrayAdapter<Groups> groupsAdapter;
    private ArrayList<Groups> showGroup = new ArrayList<>();
    private ListView lvItems;
    public static boolean isFromProfile;
    public static Groups selectedGroupList = null;
    Button newGroup;
    BaseApiService mApiService;
    EditText searchGroup;
    ImageView searchButton;
    Context mContext;
    public static users currentUser;
    ProgressDialog loading;

    @Override
    public void onBackPressed(){
        Intent intent;
        if(isFromProfile){
            intent = new Intent(mContext, GroupEnrollmentActivity.class);
        }else{
            intent = new Intent(mContext, GroupSelectionActivity.class);

        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_enrollment);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        initComponents();
    }

    private void initComponents(){
        lvItems = (ListView) findViewById(R.id.groupList);
        newGroup = (Button) findViewById(R.id.createGroup);
        searchButton = (ImageView) findViewById(R.id.search_button);
        searchGroup = (EditText) findViewById(R.id.search_group);
        getListRequest();

        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CreateGroupActivity.class));
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Are You Sure?");
                builder.setMessage("You want to enroll to this group?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);
                                enrollGroup(currentUser.getId(), showGroup.get(position));
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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searchGroup.getText().toString().isEmpty()){
                    searchGroupByName(searchGroup.getText().toString());
                }else{
                    getListRequest();
                    Toast.makeText(mContext, "Null Entry!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void getListRequest() {
        Gson gson = new Gson();
        mApiService.showGroup().enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonRESULTS.getJSONArray("showGroup");
                                if (jsonRESULTS.getString("message").equals("Groups found")){
                                    showGroup = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Groups>>() {}.getType());
                                    groupsAdapter = new ArrayAdapter<Groups>(getApplicationContext(),
                                            android.R.layout.simple_list_item_1, showGroup);
                                    lvItems.setAdapter(groupsAdapter);

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

    void enrollGroup(int userID, Groups currentGroup){
        mApiService.enrollGroup(currentGroup.getId(), userID)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("message").equals("Group Enrolled")){
                                    Toast.makeText(mContext, "Group Enrolled", Toast.LENGTH_SHORT).show();
                                    Intent intent;
                                    if(isFromProfile){
                                        intent = new Intent(mContext, GroupEnrollmentActivity.class);
                                    }else{
                                        intent = new Intent(mContext, GroupSelectionActivity.class);

                                    }
                                    startActivity(intent);
                                    finish();

                                } else if(jsonRESULTS.getString("message").equals("User already enrolled in this group")){
                                    Toast.makeText(mContext, "You are already enrolled to this group!", Toast.LENGTH_SHORT).show();
                                } else{
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

    void searchGroupByName(String Nama_Tugas){
        Gson gson = new Gson();
        mApiService.searchGroupByName(Nama_Tugas).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonRESULTS.getJSONArray("searchGroupByName");
                        if (jsonRESULTS.getString("message").equals("Group found")){
                            showGroup = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Groups>>() {}.getType());
                            groupsAdapter = new ArrayAdapter<Groups>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, showGroup);
                            lvItems.setAdapter(groupsAdapter);

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
