package edu.uga.cs.project4;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.opencsv.CSVReader;

public class ReadCSV extends AppCompatActivity {
    private ArrayList<String[]> Data;

    ReadCSV(){
        Data = new ArrayList<>();
    }

    public void readFile(String fileName) throws IOException {
        try{
            InputStream in_s = getAssets().open( fileName );
            CSVReader reader = new CSVReader( new InputStreamReader( in_s ) );
            String[] row;
            while((row = reader.readNext())!=null){
                Data.add(row);
            }
        }
        catch (Exception e) {
            Log.e( "readFile", e.toString() );
        }

    }

    public ArrayList<String[]> getDataArrayList(){
        return this.Data;
    }


}
