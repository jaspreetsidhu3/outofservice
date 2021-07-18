package com.example.outofservice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingFragment extends Fragment {

    private Button setTimeBtn;
    private EditText edtHours;
    private EditText edtMins;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        setTimeBtn = view.findViewById(R.id.setTime);
        edtHours = view.findViewById(R.id.editHours);
        edtMins = view.findViewById(R.id.editMins);
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String THours = edtHours.getText().toString();
                String TMins = edtMins.getText().toString();
                int computeSecond = 0;
                if (THours.equals("") && TMins.equals("")) {
                    Toast.makeText(getActivity(), "Enter the Time", Toast.LENGTH_LONG).show();
                } else {
                    if (!TMins.equals("")) {
                        computeSecond += Integer.parseInt(TMins) * 60;
                    }
                    if (!THours.equals("")) {
                        computeSecond += Integer.parseInt(THours) * 3600;
                    }
                    MainActivity mainActivity = (MainActivity) getActivity();
                    assert mainActivity != null;
                    mainActivity.PassingData(computeSecond);
                    //getActivity().onBackPressed();
                }
            }
        });
        return view;
    }
}