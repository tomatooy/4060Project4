package edu.uga.cs.project4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizDBHelper";

    private static final String DB_NAME = "quiz.db";
    private static final int DB_VERSION = 1;

    // states table
    public static final String TABLE_STATE = "state";
    public static final String STATE_COLUMN_ID = "_id";
    public static final String STATE_COLUMN_STATE = "state";
    public static final String STATE_COLUMN_CAPITAL = "capital";
    public static final String STATE_COLUMN_CITY1 = "city1";
    public static final String STATE_COLUMN_CITY2 = "city2";
    public static final String CREATE_STATE =
            "CREATE TABLE " +  TABLE_STATE + "("
                    + STATE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + STATE_COLUMN_STATE + " TEXT,"
                    + STATE_COLUMN_CAPITAL  +" TEXT,"
                    + STATE_COLUMN_CITY1 + " TEXT,"
                    + STATE_COLUMN_CITY2 + " TEXT"
                    + ")";

    // results table
    public static final String TABLE_RESULT = "result";
    public static final String RESULT_COLUMN_ID = "_id";
    public static final String RESULT_COLUMN_DATE = "date";
    public static final String RESULT_COLUMN_SCORE = "score";
    public static final String CREATE_RESULT =
            "CREATE TABLE " + TABLE_RESULT  + "("
                    + RESULT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + RESULT_COLUMN_DATE + " TEXT, "
                    + RESULT_COLUMN_SCORE + " TEXT "
                    + ")";

    // question table
    public static final String TABLE_QUESTIONS = "questions";
    public static final String QUESTIONS_COLUMN_ID = "_id";
    public static final String QUESTIONS_COLUMN_RESULT_ID = "result_id";
    public static final String QUESTIONS_COLUMN_STATE_ID = "state_id";
    public static final String QUESTIONS_COLUMN_ANSWER = "answer";
    public static final String QUESTIONS_COLUMN_CORRECTNESS = "correctness";
    public static final String CREATE_QUESTIONS =
            "CREATE TABLE " + TABLE_QUESTIONS  + "("
                    + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUESTIONS_COLUMN_RESULT_ID + " INTEGER,"
                    + QUESTIONS_COLUMN_STATE_ID + " INTEGER,"
                    + QUESTIONS_COLUMN_ANSWER + " TEXT,"
                    + QUESTIONS_COLUMN_CORRECTNESS + " TEXT" + ")";

    // ref to only instance for the helper
    private static DBHelper helperInstance;


    // private constructor
    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        // check if the instance already exists and if not, create instance
        if(helperInstance == null) {
            helperInstance = new DBHelper(context.getApplicationContext());
        }

        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATE);
        db.execSQL(CREATE_QUESTIONS);
        db.execSQL(CREATE_RESULT);
        Log.d(DEBUG_TAG,"created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "drop table if exists capital" );
        onCreate(db);
        Log.d(DEBUG_TAG,"upgraded");
    }
}
