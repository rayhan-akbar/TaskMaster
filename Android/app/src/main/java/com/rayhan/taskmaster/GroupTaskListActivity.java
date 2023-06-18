package com.rayhan.taskmaster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    public static GroupEnrollmentList currentGroup;
    CheckBox notStartedFilter;
    CheckBox inProgressFilter;
    CheckBox completedFilter;
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
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        // below line will clear
                        // the data in shared prefs.
                        editor.clear();
                        // below line will apply empty
                        // data to shared prefs.
                        editor.apply();
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
        Button applyFilterButton = (Button) findViewById(R.id.ApplyFilter);
        CardView filterCardView = findViewById(R.id.FilterCardView);

        notStartedFilter = (CheckBox) findViewById(R.id.showNotStarted);
        inProgressFilter = (CheckBox) findViewById(R.id.showInProgress);
        completedFilter = (CheckBox) findViewById(R.id.showCompleted);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        ImageView filterCardViewButton = (ImageView) findViewById(R.id.filter_button);
        ImageView searchButton = (ImageView) findViewById(R.id.search_button);
        EditText searchTask = (EditText) findViewById(R.id.search_task);

        lvItems = (ListView) findViewById(R.id.taskList);
        filterCardView.setVisibility(View.INVISIBLE);

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
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notStartedFilter.isChecked()){
                    getListByStatusRequest(currentGroup.getGroupID(), "NOT STARTED");
                }else if(inProgressFilter.isChecked()){
                    getListByStatusRequest(currentGroup.getGroupID(), "IN PROGRESS");

                }else if(completedFilter.isChecked()){
                    getListByStatusRequest(currentGroup.getGroupID(), "COMPLETED");

                }else{
                    getListRequest();
                }
                filterCardView.setVisibility(View.INVISIBLE);
            }
        });

        filterCardViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filterCardView.getVisibility() == View.VISIBLE){
                    filterCardView.setVisibility(View.INVISIBLE);
                }else if(filterCardView.getVisibility() == View.INVISIBLE) {
                    filterCardView.setVisibility(View.VISIBLE);
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searchTask.getText().toString().isEmpty()){
                    notStartedFilter.setChecked(false);
                    inProgressFilter.setChecked(false);
                    completedFilter.setChecked(false);
                    searchListByName(currentGroup.getGroupID(), searchTask.getText().toString());
                }else{
                    notStartedFilter.setChecked(false);
                    inProgressFilter.setChecked(false);
                    completedFilter.setChecked(false);
                    getListRequest();
                    Toast.makeText(mContext, "Null Entry!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.showNotStarted:
                if (checked)
                    inProgressFilter.setChecked(false);
                completedFilter.setChecked(false);
                break;
            case R.id.showInProgress:
                if (checked)
                    notStartedFilter.setChecked(false);
                completedFilter.setChecked(false);
                break;
            case R.id.showCompleted:
                if (checked)
                    notStartedFilter.setChecked(false);
                inProgressFilter.setChecked(false);
                break;
        }
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

    void getListByStatusRequest(Integer GroupID, String Status) {
        Gson gson = new Gson();
        mApiService.filterGroupTask(GroupID, Status)
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

    void searchListByName(Integer GroupID, String Nama_Tugas){
        Gson gson = new Gson();
        mApiService.searchGroupTask(GroupID, Nama_Tugas)
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

                                } else if(jsonRESULTS.getString("message").equals("No Task Found")){
                                    Toast.makeText(mContext, "No Task Found!", Toast.LENGTH_SHORT).show();
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
