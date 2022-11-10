package edu.uga.cs.project4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizDBHelper";

    private static final String DB_NAME = "quiz.db";
    private static final int DB_VERSION = 1;

    // Questions
    public static final String TABLE_QUESTIONS = "questions";
    public static final String QUESTIONS_COLUMN_ID = "id";
    public static final String QUESTIONS_COLUMN_STATENAME = "stateName";
    public static final String QUESTIONS_COLUMN_CAPITALCITY = "capitalCity";
    public static final String QUESTIONS_COLUMN_SECONDCITY = "secondCity";
    public static final String QUESTIONS_COLUMN_THIRDCITY = "thirdCity";

    // Quizzes
    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "id";
    public static final String QUIZZES_COLUMN_DATE = "date";
    public static final String QUIZZES_COLUMN_QUESTION1 = "question_1";
    public static final String QUIZZES_COLUMN_QUESTION2 = "question_2";
    public static final String QUIZZES_COLUMN_QUESTION3 = "question_3";
    public static final String QUIZZES_COLUMN_QUESTION4 = "question_4";
    public static final String QUIZZES_COLUMN_QUESTION5 = "question_5";
    public static final String QUIZZES_COLUMN_QUESTION6 = "question_6";
    public static final String QUIZZES_COLUMN_RESULT = "result";
    public static final String QUIZZES_COLUMN_QUESTIONSANSWERED = "questions_answered";

    // ref to only instance for the helper
    private static DBHelper helperInstance;

    // A create sql tables
    private static final String CREATE_QUESTIONS =
            "create table " + TABLE_QUESTIONS + " ("
                    + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUESTIONS_COLUMN_STATENAME + " TEXT, "
                    + QUESTIONS_COLUMN_CAPITALCITY + " TEXT, "
                    + QUESTIONS_COLUMN_SECONDCITY + " TEXT, "
                    + QUESTIONS_COLUMN_THIRDCITY + " TEXT"
                    + ")";

    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_COLUMN_DATE + " TEXT, "
                    + QUIZZES_COLUMN_QUESTION1 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION2 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION3 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION4 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION5 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION6 + " INTEGER, "
                    + QUIZZES_COLUMN_RESULT + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTIONSANSWERED + " INTEGER"
                    + ")";

    // private constructor
    private DBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    public static synchronized DBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new DBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL(CREATE_QUESTIONS);
        db.execSQL(CREATE_QUIZZES);
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS + " created" );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_QUESTIONS );
        db.execSQL( "drop table if exists " + TABLE_QUIZZES );
        onCreate(db);
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS + " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " upgraded" );
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }
}
