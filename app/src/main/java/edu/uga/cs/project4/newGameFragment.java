package edu.uga.cs.project4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

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


    public newGameFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static newGameFragment newInstance(int rowNumber) {
        newGameFragment fragment = new newGameFragment();
        Bundle args = new Bundle();
        args.putInt( "rowNum", rowNumber );
        args.putBoolean( "anySelected", false );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( getArguments() != null ) {
            row = getArguments().getInt( "rowNum" );
            anySelected = getArguments().getBoolean("anySelected");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}