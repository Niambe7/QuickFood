package com.example.quickfood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class CommandFragment extends Fragment {

    public static String menu ;
    private Button btnCmd ;
    private EditText txtQuantity , txtLogin ;
    public static String meal ;
    private String quantity ; //login
    private Spinner spnMeal ;
    private String[] tabMeal ;
    SharedPreferences sp ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_command, container, false);
        spnMeal = view.findViewById(R.id.spn_meal);
        txtQuantity = view.findViewById(R.id.txtQuantity);
        //txtLogin = view.findViewById(R.id.txtLogin);
        tabMeal = view.getResources().getStringArray(R.array.tab_meal);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_list_item_1, tabMeal);
        spnMeal.setAdapter(adapter);


        btnCmd = view.findViewById(R.id.btnCmd);

        btnCmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meal = spnMeal.getSelectedItem().toString();
                quantity = txtQuantity.getText().toString().trim();
                //login = txtLogin.getText().toString().trim();
                command();
            }
        });

        return view ;
    }

    public void command(){

        String url = "http://192.168.1.16/dkfoodies/command.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("login", MainActivity.login)
                .add("plat", meal)
                .add("quantite", quantity)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "ERREUR", Toast.LENGTH_SHORT).show();
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "ERREUR", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    else {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //txtLogin.setText(null);
                                txtQuantity.setText(null);
                                Toast.makeText(getActivity(), R.string.success_command, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }catch (Exception e){

                }
            }
        });
    }
}