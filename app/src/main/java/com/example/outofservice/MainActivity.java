package com.example.outofservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView textTimer;
    private TextView customToast;
    private boolean isTimerOn=false;
    private ImageButton streakBtn;
    private ImageButton settingsBtn;


    SettingFragment settingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        InitSharedPreference();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        textTimer = findViewById(R.id.timesprint);
        customToast = findViewById(R.id.customtoast);
        streakBtn=findViewById(R.id.streak);
        settingsBtn=findViewById(R.id.settings);


        settingFragment=new SettingFragment();
    }

    public void openSettings(View view) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.container, settingFragment).addToBackStack(null).commit();
    }

    public void PassingData(long Time) {
        textTimer = findViewById(R.id.timesprint);
        textTimer.setText("" + Time);
        ComputingTimer computingTimer = new ComputingTimer();
        computingTimer.execute(Time);
        isTimerOn=true;
        streakBtn.setVisibility(View.GONE);
        settingsBtn.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
    }

    public void openStreak(View view) {
        Intent intent = new Intent(getApplicationContext(), StreakActivity.class);
        startActivity(intent);
    }

    public class ComputingTimer extends AsyncTask<Long, Long, Void> {

        @Override
        protected Void doInBackground(Long... value) {
            for (long i = value[0]; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            textTimer.setText("" + values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            textTimer.setText("00");
            computeSharedPreference(50);
            customToast.setVisibility(View.VISIBLE);
            customToast.setText("Points gained +50");
            isTimerOn=false;
            streakBtn.setVisibility(View.VISIBLE);
            settingsBtn.setVisibility(View.VISIBLE);
        }
    }

    public void InitSharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("dbpoints", MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int points = sharedPreferences.getInt("points", -1);
        if (points == -1) {
            editor.putInt("points", 0);
        }
        editor.apply();
    }

    public void computeSharedPreference(int value) {
        SharedPreferences sharedPreferences = getSharedPreferences("dbpoints", MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int points = sharedPreferences.getInt("points", -1);
        if (points != -1) {
            int updatedPoints = value + points;
            if (updatedPoints < 0) {
                editor.putInt("points", 0);
            } else {
                editor.putInt("points", updatedPoints);
            }
            editor.apply();

        } else {
            Toast.makeText(getApplicationContext(), "Error OSS1", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isTimerOn){
            Toast.makeText(getApplicationContext(),"Points plenty -20",Toast.LENGTH_LONG).show();
            customToast.setVisibility(View.VISIBLE);
            customToast.setText("Points plenty -20");
            computeSharedPreference(-20);
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if(isTimerOn){
            Toast.makeText(getApplicationContext(),"Points plenty -20",Toast.LENGTH_LONG).show();
            customToast.setVisibility(View.VISIBLE);
            customToast.setText("Points plenty -20");
            computeSharedPreference(-20);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isTimerOn){
            Toast.makeText(getApplicationContext(),"Points plenty -40",Toast.LENGTH_LONG).show();
            customToast.setVisibility(View.VISIBLE);
            customToast.setText("Points plenty -40");
            computeSharedPreference(-40);
        }
    }
}