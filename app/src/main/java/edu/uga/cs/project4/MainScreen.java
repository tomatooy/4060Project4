package edu.uga.cs.project4;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;


public class MainScreen extends Fragment {

    QuestionsData questionsData = null;

    public MainScreen() {
        // Required empty public constructor
    }

    public static MainScreen newInstance() {
        MainScreen fragment = new MainScreen();
        return fragment;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_main_screen, container, false );
    }

    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        Button startButton = view.findViewById(R.id.button2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // *****
                Intent intent = new Intent( getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        questionsData = new QuestionsData(getActivity());
        setUpInitialData();
    }

    public void setUpInitialData() {
        if (questionsData != null) {
            questionsData.open();
            new QuestionDBInitializer().execute();
        }
    }

    private void readAndStoreValuesFromCSV() {
        try {
            InputStream in_s = getActivity().getAssets().open( "state_capitals.csv" );
            CSVReader reader = new CSVReader( new InputStreamReader( in_s ) );
            reader.skip(1);
            String[] nextRow;

            while( ( nextRow = reader.readNext() ) != null ) {
                Question question = new Question(nextRow[0], nextRow[1], nextRow[2], nextRow[3]);
                new QuestionDBWriter().execute(question);
            }
        } catch (Exception e) {

        }
    }

    public class QuestionDBWriter extends AsyncTask<Question, Question> {
        /**
         * This method will run as a background process to write into db.
         *
         * @param questions
         * @return
         */
        @Override
        protected Question doInBackground( Question... questions ) {
            questionsData.storeQuestion(questions[0]);
            return questions[0];
        }

        /**
         * This method will be automatically called by Android once the writing to the database
         * in a background process has finished.
         *
         * @param question
         */
        @Override
        protected void onPostExecute( Question question ) {

        }
    }

    public class QuestionDBInitializer extends AsyncTask<Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arguments) {
            if (questionsData != null && !questionsData.isEmpty()) {
                return false; // means that the question table already exists and has data
            }
            return true; // means the question table is empty and needs to be populated with data
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {
                readAndStoreValuesFromCSV(); // populate question table with data from CSV
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (questionsData != null) {
            questionsData.close();
        }
    }

    /*
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

     */
}