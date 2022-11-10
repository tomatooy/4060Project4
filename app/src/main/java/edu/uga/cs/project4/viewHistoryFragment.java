package edu.uga.cs.project4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viewHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryRecyclerAdapter recyclerAdapter;
    private ArrayList<Quiz> quizList = new ArrayList<>();
    private QuizData quizData;

    public viewHistoryFragment() {
        // Required empty public constructor
    }

    public static viewHistoryFragment newInstance() {
        viewHistoryFragment fragment = new viewHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_history_recycler_adapter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById( R.id.recyclerView );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager( layoutManager );

        quizData = new QuizData(getActivity());
        quizData.open();

        new QuizDBReader().execute();
        /*

        List<Quiz> QuizList = new ArrayList<>();
        QuizList.add(new Quiz());
        QuizList.add(new Quiz());
        recyclerAdapter = new HistoryRecyclerAdapter( getActivity(), QuizList );
        recyclerView.setAdapter( recyclerAdapter );
        */

    }

    public class QuizDBReader extends AsyncTask<Void, List<Quiz>> {

        @Override
        protected List<Quiz> doInBackground( Void... params ) {
            List<Quiz> allQuizzes = quizData.retrieveAllQuizzes();
            return allQuizzes;
        }

        @Override
        protected void onPostExecute(List<Quiz> allQuizzes) {
            quizList.addAll(allQuizzes);
            recyclerAdapter = new HistoryRecyclerAdapter( getActivity(), quizList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (quizData != null) {
            quizData.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (quizData != null) {
            quizData.close();
        }
    }
}