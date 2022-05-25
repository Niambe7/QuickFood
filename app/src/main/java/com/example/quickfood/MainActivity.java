package com.example.quickfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtLogin, txtPassword;
    private Button btnSignIn , btnSignUp;
    public static String login;
    private String password;
    //SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        //sp = getSharedPreferences("Prefs", Context.MODE_PRIVATE);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = txtLogin.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if (login.isEmpty() || password.isEmpty()){
                    String message = "Veuillez remplir les champs".toString();
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }else {
                    authentification();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });
    }

    public void authentification(){

        String url = "http://192.168.1.16/dkfoodies/connexion.php?login="+login+"&password="+password;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String status = jo.getString("status");
                    if (status.equals("KO")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, R.string.error_parameters, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        //SharedPreferences.Editor editor = sp.edit();
                        //editor.putString("login",login);
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        intent.putExtra("LOGIN",login);
                        startActivity(intent);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}