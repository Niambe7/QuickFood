package com.example.quickfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InscriptionActivity extends AppCompatActivity {

    private EditText txtFirstName, txtLastName, txtAddress, txtNumTel , txtLogin, txtPassword;
    private Button btnSignUp;
    private String firstName, lastName, address, numTel , login , password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtAddress = findViewById(R.id.txtAddress);
        txtNumTel = findViewById(R.id.txtNumTel);
        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = txtFirstName.getText().toString().trim();
                lastName = txtLastName.getText().toString().trim();
                address = txtAddress.getText().toString().trim();
                numTel = txtNumTel.getText().toString().trim();
                login = txtLogin.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if (firstName.isEmpty()|| lastName.isEmpty()|| address.isEmpty() || numTel.isEmpty() || login.isEmpty() || password.isEmpty()){
                    Toast.makeText(InscriptionActivity.this, "Veuillez Remplir tous les champs svp", Toast.LENGTH_SHORT).show();
                }
                 else {
                     inscription();
                }
            }
        });

    }

    public void inscription(){
        String url = "http://192.168.1.16/dkfoodies/inscription.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("first_name", firstName)
                .add("last_name", lastName)
                .add("address", address)
                .add("num_tel", numTel)
                .add("login", login)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

client.newCall(request).enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InscriptionActivity.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(InscriptionActivity.this, R.string.error_inscription, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtFirstName.setText(null);
                        txtLastName.setText(null);
                        txtAddress.setText(null);
                        txtNumTel.setText(null);
                        txtLogin.setText(null);
                        txtPassword.setText(null);
                        Toast.makeText(InscriptionActivity.this, R.string.success_inscription, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(InscriptionActivity.this, "Veuillez vous connecter avec vos identifiant", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
});
    }
}