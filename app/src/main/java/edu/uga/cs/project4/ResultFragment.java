package edu.uga.cs.project4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class ResultFragment extends Fragment {

    private QuizData quizData;
    private TextView results;
    private String time;
    private boolean alreadySetTime = false;

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            time = savedInstanceState.getString("time");
            alreadySetTime = savedInstanceState.getBoolean("alreadySetTime");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.submit_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {

        results = view.findViewById( R.id.textView5 );
        quizData = new QuizData(getActivity());
        quizData.open();

        Button homeButton = view.findViewById(R.id.buttonSubmit);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGameFragmentContainer.setUpQuiz();
                getActivity().onBackPressed();
            }
        });


    }

    public class QuizDBUpdater extends AsyncTask<String, Void> {

        /**
         * Updates database with an asynchronous task
         *
         * @param time
         * @return
         */
        @Override
        protected Void doInBackground(String... time) {
            quizData.updateQuizByID(newGameFragmentContainer.currentQuizID, time[0],
                    newGameFragmentContainer.score, 6);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("time", time);
        outState.putBoolean("alreadySetTime", alreadySetTime);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(quizData != null && !quizData.isDBOpen()) {
            quizData.open();
        }

        if (!alreadySetTime) {
            Calendar calendar = Calendar.getInstance();
            time = calendar.getTime().toString();
            new QuizDBUpdater().execute(time);
            alreadySetTime = true;
        }
        results.setText("Score: " + newGameFragmentContainer.score +
                "/6 " + "\nTime: " + time);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(quizData != null) {
            quizData.close();
        }
    }
}
