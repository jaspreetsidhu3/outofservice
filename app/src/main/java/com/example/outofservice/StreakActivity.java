package com.example.outofservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class StreakActivity extends AppCompatActivity {
    private TextView txtStreak;
    private TextView txtPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streak);
        txtPoints=findViewById(R.id.dbpoints);
        txtStreak=findViewById(R.id.stagelabel);
        InitSharedPreference();
    }
    public void InitSharedPreference(){
        SharedPreferences sharedPreferences=getSharedPreferences("dbpoints",MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        int points=sharedPreferences.getInt("points",-1);
        if(points==-1){
            editor.putInt("points",0);
            setTextValues(0);
        }
        else{
            setTextValues(points);
        }
        editor.apply();
    }
    public void setTextValues(int value){
        txtPoints.setText(""+value);
        if(value<350){
            txtStreak.setText("Bronze");
        }
        else if(value>=350 && value<650){
            txtStreak.setText("Iron");
        }
        else if(value>=650 && value<950){
            txtStreak.setText("Silver");
        }
        else if(value>=950 && value<1250){
            txtStreak.setText("Platinum");
        }
        else if(value>=1250 && value<1850){
            txtStreak.setText("Gold");
        }
        else if(value>=1850 && value<2450){
            txtStreak.setText("Diamond");
        }
        else if(value>=2450 && value<=3000){
            txtStreak.setText("Master");
        }
        else{
            txtStreak.setText("Legend");
        }
    }
}