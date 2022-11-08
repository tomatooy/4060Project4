package edu.uga.cs.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentValues;
import android.os.AsyncTask;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Configuration;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;

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

    public final String TAG = "GameActivity";
    RadioGroup radioGroup;
    TextView question;

    int numSelected = 0;

    Quiz quiz;
    final int NUM_QUESTIONS = 6;
    ArrayList<Question> questions = new ArrayList<Question>();
    FragmentStateAdapter NewGameAdapter;
    ViewPager vpPager;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Data = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Connect DrawerLayout events to the ActionBarToggle

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
        Log.d("arr",Data.toString());
    }


    private class loadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            AppData db = new AppData();
            db.open(GameActivity.this);
            ArrayList<String[]> stateSet = db.readTable("state");

            Collections.shuffle(stateSet);

            // get fist 6 random questions and add to ArrayList
            for(int i = 0; i < NUM_QUESTIONS; i++) {
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
            return "Executed";
        }
    }

    private class saveTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            AppData db = new AppData();
            db.open(GameActivity.this);

            // get last result key
            int lastResultID = Integer.parseInt(db.getLastPrimaryKey("result"));
            lastResultID += 1;
            ContentValues values = new ContentValues();

            // get date for result row insert
            Date currentTime = Calendar.getInstance().getTime();
            String d = DateFormat.getDateInstance(DateFormat.SHORT).format(currentTime);

            // format to 2 decimal places
            double score = quiz.percentCorrect * 10000;
            score = (int) (score) / 100;

            // insert data into result table
            values.put(DBHelper.RESULT_COLUMN_DATE, d);
            values.put(DBHelper.RESULT_COLUMN_SCORE, score);
            db.db.insert(DBHelper.TABLE_RESULT, null, values);

            // insert all questions from this quiz into question table
            for (Question q : questions) {
                ContentValues insertValues = new ContentValues();
                insertValues.put(DBHelper.QUESTIONS_COLUMN_RESULT_ID, lastResultID);
                insertValues.put(DBHelper.QUESTIONS_COLUMN_STATE_ID, Integer.parseInt(q.id));
                insertValues.put(DBHelper.QUESTIONS_COLUMN_ANSWER, q.userAnswer);
                insertValues.put(DBHelper.QUESTIONS_COLUMN_CORRECTNESS, (q.answeredCorrect) ? "true" : "false");
                db.db.insert(DBHelper.TABLE_QUESTIONS, null, insertValues);
            }

            db.close();
            return "executed";
        }
    }


}