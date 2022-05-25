package com.example.quickfood;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyCommandsFragment extends Fragment {


    private TextView tvCmd ;
    private Button btnSee ;
    private String cmd ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_commands, container, false);
        btnSee = view.findViewById(R.id.btnSee);
        tvCmd = view.findViewById(R.id.tvCmd);

        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeCommand();
            }
        });
        return view ;
    }

    public void seeCommand(){

        String url = "http://192.168.1.16/dkfoodies/mycommands.php?login="+MainActivity.login;
        cmd = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("cmd");
                    for (int i = 0; i < ja.length() ; i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String plat = element.getString("plat");
                        String quantite = element.getString("quantite");
                        cmd+= plat+ ": "+ quantite +"\n\n";
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvCmd.setText(cmd);
                        }
                    });

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}