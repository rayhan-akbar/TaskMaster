package com.rayhan.taskmaster;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rayhan.taskmaster.model.IndividualTaskList;
import com.rayhan.taskmaster.model.users;
import com.rayhan.taskmaster.request.BaseApiService;
import com.rayhan.taskmaster.request.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity{
    EditText loginUsername, loginPassword;
    FrameLayout loginButton;
    TextView loginSignup;
    Context mContext;
    BaseApiService mApiService;
    ProgressDialog loading;

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("You want to exit?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.this.finish();
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
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();

    }
    private void initComponents(){
        loginUsername = (EditText) findViewById(R.id.loginusername);
        loginPassword = (EditText) findViewById(R.id.loginpassword);
        loginButton = (FrameLayout) findViewById(R.id.loginbutton);
        loginSignup = (TextView) findViewById(R.id.loginsignup);

        /*
         * Login button click event to login user and validate the credentials and redirect to dashboard activity if valid credentials are given else display error message to user and keep the user in login activity
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin(loginUsername.getText().toString(), loginPassword.getText().toString());
            }
        });

        /*
         * Signup button click event to redirect to signup activity if user is not registered else display error message to user and keep the user in login activity
         */
        loginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });
    }

    private void requestLogin(String username, String password){

        mApiService.login(username, password).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("message").equals("Login successful")){
                                    Gson gson = new Gson();
                                    users user = new users(0,"", "");
                                    user = (users) gson.fromJson(jsonRESULTS.getString("user"), user.getClass());
                                    MainActivity.currentUser = user;
                                    System.out.println(MainActivity.currentUser.getId());
                                    Toast.makeText(mContext, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                                    // String nama = jsonRESULTS.getJSONObject("user").getString("nama");
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    // intent.putExtra("result_nama", nama);
                                   startActivity(intent);
                                } else {
                                    // Jika login gagal
                                    Toast.makeText(mContext, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                            Toast.makeText(mContext, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }
}
