package com.example.loginandregisterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandregisterapi.ServerResponseModels.RegisterResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    TextView signin;
    EditText name, email, phonenumber, bankname, accountnumber, ifsccode, accountname, password;
    Button Register, Cancel;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Registration under process please wait....");
        actions();
        Controller.getInstance().fillcontext(getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(RegisterActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(RegisterActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Controller.MessageEvent messageEvent) {
        progressDialog.dismiss();
        Log.e("response", "call" + messageEvent.body);
        if (messageEvent.body != null && messageEvent.msg.equals("registerApi")) {
            try {
                JSONObject jObj = new JSONObject(messageEvent.body);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            Gson gson = new Gson();
            RegisterResponseModel registerResponseModel = gson.fromJson(messageEvent.body, RegisterResponseModel.class);
            if (registerResponseModel.getResponse() == 3) {
                Log.e("register Response", "call" + registerResponseModel.getResponse());
                Toast.makeText(getApplicationContext(), registerResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                action();
            } else {
                Toast.makeText(getApplicationContext(), registerResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void action() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    private void actions() {
        name = findViewById(R.id.Name);
        email = findViewById(R.id.userId);
        phonenumber = findViewById(R.id.PhoneNumber);
        bankname = findViewById(R.id.BankName);
        accountnumber = findViewById(R.id.AccountNumber);
        ifsccode = findViewById(R.id.IFSCCode);
        accountname = findViewById(R.id.AccountName);
        password = findViewById(R.id.Password);
        Register = findViewById(R.id.register);
        signin = findViewById(R.id.sign_in);
        Cancel = findViewById(R.id.cancel);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty() ||
                        phonenumber.getText().toString().isEmpty() || bankname.getText().toString().isEmpty() ||
                        accountnumber.getText().toString().isEmpty() || ifsccode.getText().toString().isEmpty() ||
                        accountname.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please Enter Required Fields", Toast.LENGTH_SHORT).show();

                } else {
                    if (Controller.getInstance().checkNetwork()) {
                        progressDialog.show();
                        LoadRegisterApiParams();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.getText().clear();
                email.getText().clear();
                phonenumber.getText().clear();
                bankname.getText().clear();
                accountnumber.getText().clear();
                ifsccode.getText().clear();
                accountname.getText().clear();
                password.getText().clear();
            }
        });

    }

    public void LoadRegisterApiParams() {
        JsonObject CheckUserObj = new JsonObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name", name.getText().toString());
            jsonObject.put("userID", email.getText().toString());
            jsonObject.put("PhoneNumber", phonenumber.getText().toString());
            jsonObject.put("BankName", bankname.getText().toString());
            jsonObject.put("AccountNumber", accountnumber.getText().toString());
            jsonObject.put("IFSCCode", ifsccode.getText().toString());
            jsonObject.put("AccountName", accountname.getText().toString());
            jsonObject.put("Password", password.getText().toString());
            JsonParser jsonParser = new JsonParser();
            CheckUserObj = (JsonObject) jsonParser.parse(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Controller.getInstance().ApiCallBackForPostMethods(RegisterActivity.this, "seller/SellerRegister", CheckUserObj,"registerApi");

    }

}