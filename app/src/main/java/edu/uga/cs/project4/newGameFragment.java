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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newGameFragment extends Fragment {


    private static final String[] testData = {
            "13",
            "12",
            "11",
            "10",
            "Pie",
            "Oreo",
            "Nougat",
            "Marshmallow",
            "Lollipop",
            "KitKat",
            "Jelly Bean",
            "Ice Cream Sandwich",
            "Honeycomb",
            "Gingerbread",
            "Froyo",
            "Eclair",
            "Donut",
            "Cupcake",
            "Petit Four",
            "Android (No codename)"
    };

    private static int row;
    private int selected;
    private int answer;
    private boolean anySelected;

    RadioGroup radioGroup;
    TextView screenPrompt;
    Question question;
    String prompt;
    int index;


    public newGameFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static newGameFragment newInstance(int index) {
        newGameFragment fragment = new newGameFragment();
        Bundle args = new Bundle();
        args.putInt( "index", index );
        args.putBoolean( "anySelected", false );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( getArguments() != null ) {
            index = getArguments().getInt( "index" );
        }
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_game, container, false);
    }
    */
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
       int layoutid = R.layout.fragment_new_game;
       View view;

       if(getShownIndex() == 6){
           layoutid = R.layout.submit_page;
           view = inflater.inflate(layoutid, container, false);
           return  view;
       }

       view = inflater.inflate(layoutid, container, false);



       radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
       screenPrompt = (TextView) view.findViewById(R.id.TextViewQuestion);

       double percentComplete = ((double)getShownIndex() / (double)6) * 100;
       System.out.println(percentComplete + " ");

       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
       {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               int selectedId = radioGroup.getCheckedRadioButtonId();

               RadioButton radioButton = (RadioButton)getView().findViewById(selectedId);

               System.out.println(radioButton.getText());

               View rb = radioGroup.findViewById(selectedId);
               int index = radioGroup.indexOfChild(rb);
               System.out.println("Selected index: "  + index);
               ((GameActivity)getActivity()).quiz.answer(index);


           }
       });



       if (! ((GameActivity)getActivity()).quiz.isFinished()) { // If the user is still taking the quiz, load the question
           if(getShownIndex() >= 6){
               return view;
           }
           Question currentQuestion = ((GameActivity)getActivity()).quiz.questions.get(getShownIndex()); // Load the current question

           System.out.println(currentQuestion.toString());

           screenPrompt.setText(currentQuestion.prompt); // set the new p

           char[] letters = {'A', 'B', 'C'};
           for (int i = 0; i < currentQuestion.possible.size(); i++) {
               ((RadioButton) (radioGroup.getChildAt(i))).setText(letters[i] + ") " + currentQuestion.possible.get(i));
           }
       } else {
           //((GameActivity)getActivity()).goToScore();
       }

       return view;
   }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                    * */
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

        });
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public int getShownIndex() {
       return getArguments().getInt("index", 0);
    }
}