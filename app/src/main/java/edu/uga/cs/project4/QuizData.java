package edu.uga.cs.project4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// handles storing and retrieving of stored quizzes
public class QuizData {

    public static final String DEBUG_TAG = "QuizData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    private static final String[] allColumns = {
            DBHelper.QUIZZES_COLUMN_ID,
            DBHelper.QUIZZES_COLUMN_DATE,
            DBHelper.QUIZZES_COLUMN_QUESTION1,
            DBHelper.QUIZZES_COLUMN_QUESTION2,
            DBHelper.QUIZZES_COLUMN_QUESTION3,
            DBHelper.QUIZZES_COLUMN_QUESTION4,
            DBHelper.QUIZZES_COLUMN_QUESTION5,
            DBHelper.QUIZZES_COLUMN_QUESTION6,
            DBHelper.QUIZZES_COLUMN_RESULT,
            DBHelper.QUIZZES_COLUMN_QUESTIONSANSWERED
    };

    public QuizData(Context context) {
        this.dbHelper = DBHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuizzesData: db open" );
    }

    public void close() {
        if( dbHelper != null ) {
            dbHelper.close();
            Log.d(DEBUG_TAG, "QuizzesData: db closed");
        }
    }

    /**
     * check is db is open
     * @return true if open
     */
    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    /**
     * retrieves all quizzes and returns them as a list
     * @return List<Quiz>
     */
    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query( DBHelper.TABLE_QUIZZES, allColumns,
                    null, null, null, null, null );

            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 10) {

                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_ID);
                        long id = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_DATE );
                        String date = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_QUESTION1 );
                        long q1 = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_QUESTION2 );
                        long q2 = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_QUESTION3 );
                        long q3 = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_QUESTION4 );
                        long q4 = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_QUESTION5 );
                        long q5 = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_QUESTION6 );
                        long q6 = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_RESULT );
                        long result = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUIZZES_COLUMN_QUESTIONSANSWERED );
                        long questionsAnswered = cursor.getLong( columnIndex );

                        Quiz quiz = new Quiz(date, q1, q2, q3, q4, q5, q6, result, questionsAnswered);
                        quiz.setId(id);
                        quizzes.add(quiz);
                        Log.d(DEBUG_TAG, "Retrieved Quiz: " + quiz);
                    }
                }
            }
            if( cursor != null )
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return quizzes;
    }

    /**
     * store a new quiz in the db
     * @param quiz
     * @return quiz
     */
    public Quiz storeQuiz(Quiz quiz) {
        ContentValues values = new ContentValues();
        values.put( DBHelper.QUIZZES_COLUMN_DATE, quiz.getDate());
        values.put( DBHelper.QUIZZES_COLUMN_QUESTION1, quiz.getQuestion1());
        values.put( DBHelper.QUIZZES_COLUMN_QUESTION2, quiz.getQuestion2());
        values.put( DBHelper.QUIZZES_COLUMN_QUESTION3, quiz.getQuestion3());
        values.put( DBHelper.QUIZZES_COLUMN_QUESTION4, quiz.getQuestion4());
        values.put( DBHelper.QUIZZES_COLUMN_QUESTION5, quiz.getQuestion5());
        values.put( DBHelper.QUIZZES_COLUMN_QUESTION6, quiz.getQuestion6());
        values.put( DBHelper.QUIZZES_COLUMN_RESULT, quiz.getResult());
        values.put( DBHelper.QUIZZES_COLUMN_QUESTIONSANSWERED, quiz.getQuestionsAnswered());

        long id = db.insert( DBHelper.TABLE_QUIZZES, null, values );

        quiz.setId( id );

        Log.d( DEBUG_TAG, "Stored new quiz with id: " + String.valueOf( quiz.getId() ) );

        return quiz;
    }

    /**
     * update quiz in db
     * @param id
     * @param date
     * @param result
     * @param questionsAnswered
     */
    public void updateQuizByID(long id, String date, long result, long questionsAnswered) {
        String update = "UPDATE " + DBHelper.TABLE_QUIZZES
                + " SET " + DBHelper.QUIZZES_COLUMN_DATE + "='" + date + "'"
                + ", " + DBHelper.QUIZZES_COLUMN_RESULT + "=" + result
                + ", " + DBHelper.QUIZZES_COLUMN_QUESTIONSANSWERED + "=" + questionsAnswered
                + " WHERE " + DBHelper.QUIZZES_COLUMN_ID + "=" + id;
        db.execSQL(update);
    }

}
