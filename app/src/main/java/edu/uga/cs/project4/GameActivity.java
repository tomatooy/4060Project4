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
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.google.android.material.navigation.NavigationView;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {
    final String TAG = "db";
    Quiz quiz;
    final int NUM_QUESTIONS = 6;
    ArrayList<Question> questions = new ArrayList<Question>();

    ArrayList<String[]> Data;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Data = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //loadQuiz(db);

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
        try {
            InputStream in_s = getAssets().open("state_capitals.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] row;
            while ((row = reader.readNext()) != null) {
                Data.add(row);
            }
        } catch (Exception e) {
            Log.e( "error", e.toString() );
        }
        importStateCSV(Data);
        Log.d("arr",Data.toString());

        new loadQuiz().execute();
    }

    public void importStateCSV(ArrayList<String[]> data) {
        AppData appData = new AppData();
        Cursor cursor = appData.db.rawQuery("SELECT * FROM " + "state", null);
        if(cursor.getCount() > 0) {
            Log.d(TAG, "data has been inserted already");
        }
        else {
            for(int i = 0; i < data.size(); i++) {
                Log.d(TAG, "insert " + i + " row");
                ContentValues values = new ContentValues();
                values.put(DBHelper.STATE_COLUMN_STATE, data.get(i)[0]);
                values.put(DBHelper.STATE_COLUMN_CAPITAL, data.get(i)[1]);
                values.put(DBHelper.STATE_COLUMN_CITY1, data.get(i)[2]);
                values.put(DBHelper.STATE_COLUMN_CITY2, data.get(i)[3]);
                appData.db.insert(DBHelper.TABLE_STATE, null, values);
            }
            Log.d(TAG, "data first time insert");
        }
        if(cursor != null) {
            cursor.close();
        }
    }

    private class loadQuiz extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            AppData db = new AppData();
            db.open(GameActivity.this);
            ArrayList<String[]> stateSet = db.readTable("state");

            Collections.shuffle(stateSet);

            // get fist 6 random questions and add to ArrayList
            for (int i = 0; i < NUM_QUESTIONS; i++) {
                String row[] = stateSet.get(i);
                String id = row[0];
                String state = row[1];
                String prompt = "What is the capital of " + state + "?";
                String possible[] = {row[3], row[4]};
                String correct = row[2];
                Question q = new Question(prompt, state, correct, possible, id);
                questions.add(q);
            }
            db.close();
            quiz = new Quiz(questions);
            return "exec";
        }

        @Override
        protected void onPostExecute(Void unused) {}
    }


}