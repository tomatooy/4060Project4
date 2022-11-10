package edu.uga.cs.project4;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all job leads.
 */
public class HistoryRecyclerAdapter
        extends RecyclerView.Adapter<HistoryRecyclerAdapter.QuizHolder> {

    public static final String DEBUG_TAG = "JobLeadRecyclerAdapter";

    private final Context context;

    private List<Quiz> values;

    public HistoryRecyclerAdapter(Context context, List<Quiz> quizList) {
        this.context = context;
        this.values = quizList;
    }

    // reset the originalValues to the current contents of values
    //public void sync() {
       // originalValues = new ArrayList<Quiz>(values);
    //}


    // The adapter must have a ViewHolder class to "hold" one item to show.
    public static class QuizHolder extends RecyclerView.ViewHolder {

        TextView Date;
        TextView Score;


        public QuizHolder(@NonNull View itemView) {
            super(itemView);

            Date = itemView.findViewById(R.id.date);
            Score = itemView.findViewById(R.id.score);
        }
    }

    @NonNull
    @Override
    public HistoryRecyclerAdapter.QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz, parent, false);
        return new QuizHolder(view);
    }

    // This method fills in the values of a holder to show a JobLead.
    // The position parameter indicates the position on the list of jobs list.
    @Override
    public void onBindViewHolder(QuizHolder holder, int position) {

        Quiz quiz = values.get( position );

        holder.Score.setText( "Score: " + String.valueOf(quiz.getResult()));
        holder.Date.setText( "Date: " + quiz.getDate() );
    }

    @Override
    public int getItemCount() {
        if (values != null)
            return values.size();
        else
            return 0;
    }

//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                List<JobLead> list = new ArrayList<JobLead>( originalValues );
//                FilterResults filterResults = new FilterResults();
//                if(constraint == null || constraint.length() == 0) {
//                    filterResults.count = list.size();
//                    filterResults.values = list;
//                }
//                else{
//                    List<JobLead> resultsModel = new ArrayList<>();
//                    String searchStr = constraint.toString().toLowerCase();
//
//                    for( JobLead jobLead : list ) {
//                        // check if either the company name or the comments contain the search string
//                        if( jobLead.getCompanyName().toLowerCase().contains( searchStr )
//                                || jobLead.getComments().toLowerCase().contains( searchStr ) ) {
//                            resultsModel.add( jobLead );
//                        }
///*
//                        // this may be a faster approach with a long list of items to search
//                        if( jobLead.getCompanyName().regionMatches( true, i, searchStr, 0, length ) )
//                            return true;
//
// */
//                    }
//
//                    filterResults.count = resultsModel.size();
//                    filterResults.values = resultsModel;
//                }
//
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                values = (ArrayList<JobLead>) results.values;
//                notifyDataSetChanged();
//                if( values.size() == 0 ) {
//                    Toast.makeText( context, "Not Found", Toast.LENGTH_LONG).show();
//                }
//            }
//
//        };
//        return filter;
//    }
}
