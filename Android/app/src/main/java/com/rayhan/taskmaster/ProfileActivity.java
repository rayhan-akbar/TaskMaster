package com.rayhan.taskmaster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rayhan.taskmaster.model.users;
import com.rayhan.taskmaster.request.BaseApiService;
import com.rayhan.taskmaster.request.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private TextView username, showEnrollment;
    private Button logout, deleteProfile;
    public static boolean isFromMain;
    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    public static users currentUser;

    @Override
    public void onBackPressed(){
        Intent intent;
        if(isFromMain){
            intent = new Intent(mContext, MainActivity.class);

        }else{
            intent = new Intent(mContext, GroupTaskListActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = (TextView) findViewById(R.id.profileUsername);
        deleteProfile = (Button) findViewById(R.id.deleteprofile);
        logout = (Button) findViewById(R.id.logoutbtn);
        showEnrollment = (TextView) findViewById(R.id.showEnrollment);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        username.setText(currentUser.getUsername());

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Are You Sure?");
                builder.setMessage("You want to delete this account?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);
                                deleteuser();
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                // below line will clear
                // the data in shared prefs.
                editor.clear();
                // below line will apply empty
                // data to shared prefs.
                editor.apply();
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        showEnrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupEnrollmentActivity.currentUser = currentUser;
                Intent intent = new Intent(ProfileActivity.this, GroupEnrollmentActivity.class);
                startActivity(intent);
            }
        });
    }

    void deleteuser(){
        mApiService.deleteUser(currentUser.getId()).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("message").equals("User deleted")) {
                            Toast.makeText(mContext, "User Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            loading.dismiss();
                            Toast.makeText(mContext, "User Deleted Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "Gagal Delete", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
