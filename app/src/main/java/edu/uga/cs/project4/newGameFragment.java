package edu.uga.cs.project4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newGameFragment extends Fragment {

    boolean changedOrientation;

    private ArrayList<String> answerChoices;
    private int questionNumber;
    private boolean hasSelectedCorrectAnswer = false;
    private boolean alreadySelectedAnswer = false;
    private boolean firstTimeLoading = true;

    private TextView question;
    private TextView results;
    private TextView header;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private QuizData quizData;


    public newGameFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static newGameFragment newInstance(int questionNumber) {
        newGameFragment fragment = new newGameFragment();
        Bundle args = new Bundle();
        args.putInt( "questionNumber", questionNumber);
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( getArguments() != null ) {
            questionNumber = getArguments().getInt( "questionNumber" );
        }
        if(savedInstanceState != null) {
            questionNumber = savedInstanceState.getInt("questionNumber");
            answerChoices = savedInstanceState.getStringArrayList("answerChoices");
            firstTimeLoading = savedInstanceState.getBoolean("firstTimeLoading");
            hasSelectedCorrectAnswer = savedInstanceState.getBoolean("hasSelectedCorrectAnswer");
            changedOrientation = savedInstanceState.getBoolean("changedOrientation");
        }
    }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
       //int layoutid = R.layout.fragment_new_game;
       //View view;
       // ***
       return inflater.inflate(R.layout.fragment_new_game, container, false );
   }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 pager = view.findViewById(R.id.viewPager);
        if (pager != null) {
            pager.setUserInputEnabled(false);
        }
        quizData = new QuizData(getActivity());
        quizData.open();

        header = view.findViewById(R.id.textView5);
        results = (TextView) getActivity().findViewById(R.id.textView6);
        question = view.findViewById(R.id.textView2);
        radioButton = view.findViewById(R.id.radioButton5);
        radioButton2 = view.findViewById(R.id.radioButton6);
        radioButton3 = view.findViewById(R.id.radioButton7);

        question.setText("What is the state capital of: " +
                newGameFragmentContainer.the6Questions.get(questionNumber).getStateName());

        if (savedInstanceState == null) {
            answerChoices = new ArrayList<>();
            answerChoices.add(newGameFragmentContainer.the6Questions.get(questionNumber).getCapitalCity());
            answerChoices.add(newGameFragmentContainer.the6Questions.get(questionNumber).getSecondCity());
            answerChoices.add(newGameFragmentContainer.the6Questions.get(questionNumber).getThirdCity());
            Collections.shuffle(answerChoices);
        }

        radioButton.setText(answerChoices.get(0));
        radioButton2.setText(answerChoices.get(1));
        radioButton3.setText(answerChoices.get(2));

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!changedOrientation || newGameFragmentContainer.userAnswers.get(questionNumber).equals("")) {
                    if (i == R.id.radioButton5) {
                        newGameFragmentContainer.userAnswers.set(questionNumber, answerChoices.get(0));
                        //selectedAnswer = answerChoices.get(0);
                    } else if (i == R.id.radioButton6) {
                        newGameFragmentContainer.userAnswers.set(questionNumber, answerChoices.get(1));
                        //selectedAnswer = answerChoices.get(1);
                    } else if (i == R.id.radioButton7) {
                        newGameFragmentContainer.userAnswers.set(questionNumber, answerChoices.get(2));
                        //selectedAnswer = answerChoices.get(2);
                    }
                    updateScore();
                }
                changedOrientation = false;
/*
        if(index==6){
            TextView Header = view.findViewById(R.id.textView5);
            TextView Score = view.findViewById(R.id.textView6);
            Button submit = view.findViewById(R.id.buttonSubmit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Header.setText("Your Score:");
                    Score.setText(String.valueOf(100)); //placeholder to read calculated score
                    /*
                    * place for inset quiz reslut to databse
                    *
                }
            });
        }
        else{
        TextView titleView = view.findViewById( R.id.TextViewQuestion );
        titleView.setText(testData[row]);
        ViewPager2 page2 = ((GameActivity) getActivity()).findViewById(R.id.viewpager);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        page2.setUserInputEnabled(false);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                page2.setUserInputEnabled(true);
            }
*/
            }
        });
    }

        // opens db if closed and runs function to show toast if correct or not correct
    @Override
    public void onResume() {
        super.onResume();
        if(quizData != null && !quizData.isDBOpen()) {
            quizData.open();
        }
        if (firstTimeLoading) {
            displayCorrectOrIncorrect(questionNumber);
        }
    }

    // closes db on pause
    @Override
    public void onPause() {
        super.onPause();
        new QuizDBUpdater().execute();
        if(quizData != null && !quizData.isDBOpen()) {
            quizData.close();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("questionNumber", questionNumber);
        outState.putStringArrayList("answerChoices", answerChoices);
        outState.putBoolean("hasSelectedCorrectAnswer", hasSelectedCorrectAnswer);
        outState.putBoolean("firstTimeLoading", firstTimeLoading);
        outState.putBoolean("changedOrientation", true);
    }


    // updates score using container fragment
    public void updateScore() {
        if (newGameFragmentContainer.userAnswers.get(questionNumber)
                .equals(newGameFragmentContainer.the6Questions.get(questionNumber).getCapitalCity())) {
            hasSelectedCorrectAnswer = true;
            newGameFragmentContainer.score++;
        } else {
            if (hasSelectedCorrectAnswer) {
                newGameFragmentContainer.score--;
                hasSelectedCorrectAnswer = false;
            }
        }
    }

    // displays toast showing if answer is right or wrong
    public void displayCorrectOrIncorrect(int questionNumber) {
        String text = "";
        if (questionNumber == 0) {
            return;
        }
        if (newGameFragmentContainer.userAnswers.get(questionNumber - 1)
                .equals(newGameFragmentContainer.the6Questions.get(questionNumber - 1).getCapitalCity())) {
            text = "Correct!!";
        } else {
            text = "Wrong!!";
        }
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        toast.show();
        firstTimeLoading = false;
    }


    // updates db with async
    public class QuizDBUpdater extends AsyncTask<Void, Void> {

        @Override
        protected Void doInBackground(Void... arguments) {
            quizData.updateQuizByID(newGameFragmentContainer.currentQuizID, "", newGameFragmentContainer.score, questionNumber);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

        }
    }
}