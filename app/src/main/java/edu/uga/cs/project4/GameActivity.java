package edu.uga.cs.project4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    ArrayList<String[]> Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Data = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
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


}