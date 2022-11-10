package edu.uga.cs.project4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this class handles retrieving questions from db
 */
public class QuestionsData {

    public static final String DEBUG_TAG = "QuestionsData";

    // reference to our database, used to run SQL commands
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    private static final String[] allColumns = {
            DBHelper.QUESTIONS_COLUMN_ID,
            DBHelper.QUESTIONS_COLUMN_STATENAME,
            DBHelper.QUESTIONS_COLUMN_CAPITALCITY,
            DBHelper.QUESTIONS_COLUMN_SECONDCITY,
            DBHelper.QUESTIONS_COLUMN_THIRDCITY
    };

    public QuestionsData( Context context ) {
        this.dbHelper = DBHelper.getInstance( context );
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuestionsData: db open" );
    }

    public void close() {
        if( dbHelper != null ) {
            dbHelper.close();
            Log.d(DEBUG_TAG, "QuestionsData: db closed");
        }
    }

    /**
     * checks if db is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DBHelper.TABLE_QUESTIONS, null);
        if(cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if (count > 0) {
                return false;
            }
            cursor.close();
        }
        return true;
    }

    /**
     * checks if db is open
     * @return true if dp is open
     */
    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    /**
     * retrives all questions and return them as an ArrayList
     * @return ArrayList<Question>
     */
    public ArrayList<Question> retrieveAllQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query( DBHelper.TABLE_QUESTIONS, allColumns,
                    null, null, null, null, null );

            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 5) {

                        columnIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_ID);
                        long id = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_STATENAME);
                        String stateName = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_CAPITALCITY);
                        String capitalCity = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_SECONDCITY);
                        String secondCity = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_THIRDCITY);
                        String thirdCity = cursor.getString( columnIndex );

                        Question question = new Question(stateName, capitalCity, secondCity, thirdCity);
                        question.setId(id);

                        questions.add(question);
                        Log.d(DEBUG_TAG, "Retrieved Question: " + question);
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
        return questions;
    }

    /**
     * shuffles questions and returns six of them(without duplicates)
     * @return ArrayList<Question>
     */
    public ArrayList<Question> generate6QuizQuestions() {
        ArrayList<Question> allQuestions = retrieveAllQuestions();
        Collections.shuffle(allQuestions);
        ArrayList<Question> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(allQuestions.get(i));
        }
        return list;
    }

    /**
     * insert new question into db
     * @param question to insert
     */
    public void storeQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.QUESTIONS_COLUMN_STATENAME, question.getStateName());
        values.put(DBHelper.QUESTIONS_COLUMN_CAPITALCITY, question.getCapitalCity());
        values.put(DBHelper.QUESTIONS_COLUMN_SECONDCITY, question.getSecondCity());
        values.put(DBHelper.QUESTIONS_COLUMN_THIRDCITY, question.getThirdCity());
        db.insert(DBHelper.TABLE_QUESTIONS, null, values );
    }
}
