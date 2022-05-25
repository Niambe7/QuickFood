package com.example.quickfood;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment {

    private ListView listMenu  ;
    private String[] tabMenu , tabDetails ;
    private String menu , details ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);
        listMenu = view.findViewById(R.id.list_menu);
        tabMenu = view.getResources().getStringArray(R.array.tab_menu);
        tabDetails = view.getResources().getStringArray(R.array.tab_details);
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, tabMenu);
        listMenu.setAdapter(adapter);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menu = tabMenu[i];
                details = tabDetails[i];
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setTitle(menu);
                dialog.setMessage(details);
                dialog.setNegativeButton(getString(R.string.cancel), null);
                dialog.setPositiveButton(getString(R.string.more), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CommandFragment.menu = menu ;
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_home , new CommandFragment()).addToBackStack(null).commit();
                    }
                });
                dialog.show();
            }
        });
        return view ;
    }
}