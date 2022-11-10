package edu.uga.cs.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.android.material.navigation.NavigationView;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class GameActivity extends AppCompatActivity {

    ArrayList<String[]> Data;
    Toolbar toolbar;
    QuestionsData questionsData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Data = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ViewPager2 pager = findViewById(R.id.viewpager);
        NewGameAdapter avpAdapter = new
                NewGameAdapter(
                getSupportFragmentManager(), getLifecycle());
        pager.setOrientation(
                ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(avpAdapter);

        //questionsData = new QuestionsData(GameActivity.this);
        //setUpInitialData();

        /*
        try {
            InputStream in_s = getAssets().open("state_capitals.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] row;
            while ((row = reader.readNext()) != null) {
                Data.add(row);
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("arr", Data.toString());

         */
    }

    //public void setUpInitialData() {
        //readAndStoreValuesFromCSV();
    //}



    private void readAndStoreValuesFromCSV() {
        try {
            InputStream in_s = getAssets().open( "state_capitals.csv" );
            CSVReader reader = new CSVReader( new InputStreamReader( in_s ) );
            reader.skip(1);
            String[] nextRow;

            while( ( nextRow = reader.readNext() ) != null ) {
                Question question = new Question(nextRow[0], nextRow[1], nextRow[2], nextRow[3]);
                questionsData.storeQuestion(question);
                //return question;
            }
        } catch (Exception e) {

        }
    }



    @Override
    public void onPause() {
        super.onPause();
        if (questionsData != null) {
            questionsData.close();
        }
    }
}