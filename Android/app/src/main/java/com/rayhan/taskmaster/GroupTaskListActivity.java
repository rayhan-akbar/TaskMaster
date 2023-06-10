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

public class GroupTaskListActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<GroupTaskList> itemsAdapter;
    private ArrayList<GroupTaskList> showGroupTask = new ArrayList<>();
    private ListView lvItems;
    ProgressDialog loading;
    public static GroupTaskList selectedGroupTaskList = null;
    BaseApiService mApiService;
    Context mContext;
    public static users currentUser;
    public static GroupEnrollmentList currentGroup;

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("You want to logout?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_task);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        initComponents();
    }

    private void initComponents(){
        ImageView goToProfile = (ImageView) findViewById(R.id.gotoprofile);
        Button goToIndividualTask = (Button) findViewById(R.id.gotoindividualtasak);
        Button addNewTask = (Button) findViewById(R.id.addnewtask);
        lvItems = (ListView) findViewById(R.id.taskList);
        getListRequest();

        goToIndividualTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupSelectionActivity.currentUser = currentUser;
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });

        goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.currentUser = currentUser;
                ProfileActivity.isFromMain = false;
                startActivity(new Intent(mContext, ProfileActivity.class));
            }
        });

        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTaskActivity.currentUser = null;
                Groups group = new Groups(currentGroup.getGroupID(), currentGroup.getGroupName(), currentGroup.getDescription());
                CreateTaskActivity.currentGroup = group;
                System.out.println(CreateTaskActivity.currentGroup.getId());
                startActivity(new Intent(mContext, CreateTaskActivity.class));
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskDetailActivity.selectedIndividualTask = null;
                TaskDetailActivity.selectedGroupTask = showGroupTask.get(position);
                Intent intent = new Intent(mContext, TaskDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    void getListRequest() {
        Gson gson = new Gson();
        mApiService.showGroupTask(currentGroup.getGroupID())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {

                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonRESULTS.getJSONArray("showGroupTask");
                                if (jsonRESULTS.getString("message").equals("Task Found")){
                                    showGroupTask = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<GroupTaskList>>() {}.getType());
                                    itemsAdapter = new ArrayAdapter<GroupTaskList>(getApplicationContext(),
                                            android.R.layout.simple_list_item_1, showGroupTask);
                                    lvItems.setAdapter(itemsAdapter);

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
