package edu.uga.cs.project4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

/**
 * fragment that stores quiz data
 */
public class newGameFragmentContainer extends Fragment {
    private ViewPager2 pager;
    private NewGameAdapter qAdapter;
    private QuestionsData questionsData = null;
    private QuizData quizzesData = null;
    public static ArrayList<Question> the6Questions = new ArrayList<>(6);
    public static ArrayList<String> userAnswers = new ArrayList<>(6);
    public static long currentQuizID = -1;
    public static int score = 0;

    public newGameFragmentContainer() {
        // Required empty public constructor
    }

    public static newGameFragmentContainer newInstance() {
        newGameFragmentContainer fragment = new newGameFragmentContainer();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            userAnswers = savedInstanceState.getStringArrayList("userAnswers");
            the6Questions = savedInstanceState.getParcelableArrayList("the6Questions");
            currentQuizID = savedInstanceState.getLong("currentQuizID");
            score = savedInstanceState.getInt("score");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_game_fragment_container, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        questionsData = new QuestionsData(getActivity());
        quizzesData = new QuizData(getActivity());
        questionsData.open();
        quizzesData.open();
        pager = view.findViewById( R.id.viewPager );

        if (savedInstanceState != null) {
            pager.setOffscreenPageLimit(8);
            qAdapter = new NewGameAdapter( getChildFragmentManager(), getLifecycle() );
            pager.setOrientation( ViewPager2.ORIENTATION_HORIZONTAL );
            pager.setAdapter( qAdapter );
        } else {
            setUpQuiz();
            new QuizQuestionGenerator().execute();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("the6Questions", the6Questions);
        savedInstanceState.putStringArrayList("userAnswers", userAnswers);
        savedInstanceState.putInt("score", score);
        savedInstanceState.putLong("currentQuizID", currentQuizID);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(questionsData != null && !questionsData.isDBOpen()) { //Is there a reason to having 2 of theese?
            questionsData.open();
        }
        if(quizzesData != null && !quizzesData.isDBOpen()) {
            quizzesData.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * AsyncTask for reading questions from db
     */
    public class QuizQuestionGenerator extends AsyncTask<Void, ArrayList<Question>> {

        @Override
        protected ArrayList<Question> doInBackground( Void... params ) {
            return questionsData.generate6QuizQuestions();
        }

        @Override
        protected void onPostExecute( ArrayList<Question> generatedQuestions ) {
            addQuizToDB(generatedQuestions);
            the6Questions = generatedQuestions;

            pager.setOffscreenPageLimit(8);
            qAdapter = new NewGameAdapter( getChildFragmentManager(), getLifecycle() );
            pager.setOrientation( ViewPager2.ORIENTATION_HORIZONTAL );
            pager.setAdapter( qAdapter );
        }
    }

    // clears vars for new quiz
    public static void setUpQuiz() {
        the6Questions.clear();
        userAnswers.clear();
        for (int i = 0; i < 6; i++) {
            userAnswers.add("");
        }
        score = 0;
    }

    // writes new quiz to db
    public void addQuizToDB(ArrayList<Question> questionList) {
        Quiz quiz = new Quiz("", // empty date
                questionList.get(0).getId(), // q1
                questionList.get(1).getId(), // q2
                questionList.get(2).getId(), // q3
                questionList.get(3).getId(), // q4
                questionList.get(4).getId(), // q5
                questionList.get(5).getId(), // q6
                0, // result
                0); // questions answered
        new QuizDBWriter().execute(quiz);
    }

    /**
     * AsyncTask for creating quiz row in quiz table
     */
    public class QuizDBWriter extends AsyncTask<Quiz, Quiz> {

        @Override
        protected Quiz doInBackground( Quiz... quizzes ) {
            return quizzesData.storeQuiz( quizzes[0] );
        }

        @Override
        protected void onPostExecute( Quiz quiz ) {
            currentQuizID = quiz.getId();
        }
    }
}
