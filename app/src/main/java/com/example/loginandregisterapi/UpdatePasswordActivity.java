package com.example.loginandregisterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginandregisterapi.ServerResponseModels.LoginResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdatePasswordActivity extends AppCompatActivity {
    EditText userid, newpassword, confirmpassword;
    Button submit;
    ProgressDialog progressDialog;
    String tokenValue="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        progressDialog = new ProgressDialog(UpdatePasswordActivity.this);
        progressDialog.setTitle("Loading....");
        actions();
        Controller.getInstance().fillcontext(getApplicationContext());
        getTokenFromSharedPreference();
    }
    private void getTokenFromSharedPreference(){
        SharedPreferences sharedPref =  getSharedPreferences("tokenPrefs", MODE_PRIVATE);
       tokenValue= sharedPref.getString("token",null);
    }

    private void actions() {
        userid = findViewById(R.id.userid2);
        newpassword = findViewById(R.id.New_password);
        confirmpassword = findViewById(R.id.confirm_Password);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Controller.getInstance().checkNetwork()) {
                    progressDialog.show();
                    loadUpdateApiParams();
                } else {
                    Toast.makeText(UpdatePasswordActivity.this, "No internet connection.", Toast.LENGTH_LONG).show();
                }
               /* if (newpassword.getText().toString().equals(confirmpassword.getText().toString())) {
                    Toast.makeText(UpdatePasswordActivity.this, "Update Password Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                } else {
                    Toast.makeText(UpdatePasswordActivity.this, "Check your password entered", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Controller.MessageEvent messageEvent) {
        progressDialog.dismiss();
        Log.e("response", "call" + messageEvent.body);
        if (messageEvent.body != null && messageEvent.msg.equals("updatepswApi")) {
            try {
                JSONObject jObj = new JSONObject(messageEvent.body);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            Gson gson = new Gson();
            LoginResponseModel loginResponseModel = gson.fromJson(messageEvent.body, LoginResponseModel.class);
            Log.e("loginrespoise", "call" + loginResponseModel.getResponse());
            if (loginResponseModel.getResponse() == 3) {
                finish();
                Toast.makeText(getApplicationContext(), loginResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
            } else if (loginResponseModel.getResponse() == 0) {
                Toast.makeText(getApplicationContext(), loginResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), loginResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadUpdateApiParams() {
        JsonObject CheckUserObj = new JsonObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", userid.getText().toString());
            jsonObject.put("Password", newpassword.getText().toString());
            JsonParser jsonParser = new JsonParser();
            CheckUserObj = (JsonObject) jsonParser.parse(jsonObject.toString());
            Log.e("loadUpdateApiParams:", " " + CheckUserObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Controller.getInstance().ApiCallBackForPutMethods(UpdatePasswordActivity.this,tokenValue ,"seller/SellerUpdatePassword", CheckUserObj,"updatepswApi");
    }
}