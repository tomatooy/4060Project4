package edu.uga.cs.project4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Collections;

// This class facilitates storing and restoring of questions and quizzes
public class AppData {

    public static final String DEBUG_TAG = "AppData";

    // this is a reference to our db; used later to run SQL commands
    private SQLiteDatabase db;
    private SQLiteOpenHelper myDBHelper;


    public AppData(Context context) {
        this.myDBHelper = DBHelper.getInstance(context);
    }

    // open the database
    public void open(Context context) {
        myDBHelper = DBHelper.getInstance(context);
        db = myDBHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "AppData: db open");
    }

    // close the database
    public void close() {
        if(myDBHelper != null) {
            myDBHelper.close();
            Log.d(DEBUG_TAG, "JobLeadsData: db closed");
        }
    }

    /**
     * function to return the indicated table
     * @param table: name of table
     * @return result set
     */
    public ArrayList<String[]> readTable(String table) {
        String select = "SELECT * FROM " + table;
        ArrayList<String[]> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(select, null);
        try {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String[] s = new String[cursor.getColumnCount()];
                for (int j = 0; j < cursor.getColumnCount(); j++) {
                    s[j] = cursor.getString(j);
                }
                result.add(s);
                cursor.moveToNext();
            }
        } finally {
            try {
                cursor.close();
            }
            catch (Exception ignore) {}
        }
        return result;
    }

    public ArrayList<String[]> readTableQuery(String query) {
        ArrayList<String[]> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        try {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String[] s = new String[cursor.getColumnCount()];
                for(int j = 0; j < cursor.getColumnCount(); j++){
                    s[j] = cursor.getString(j);
                }
                result.add(s);
                cursor.moveToNext();
            }
        } finally {
            try {
                cursor.close();
            }
            catch (Exception ignore) {}
        }
        return result;
    }

    public String getLastPrimaryKey(String table) {
        String select = "SELECT  * FROM " + table;
        ArrayList<String[]> resultSet = new ArrayList<>();
        Cursor cursor = db.rawQuery(select, null);
        String id = "0";
        try {
            cursor.moveToLast();
            String[] s = new String[cursor.getColumnCount()];
            id = cursor.getString(0);


        }catch(Exception e){
            System.out.print(e.getMessage());
        } finally {
            try {
                cursor.close();
            } catch (Exception ignore) {
            }
        }
        return id;
    }

    // store a new question in database

    /*public Question storeQuestion(Question question) {
       
          ContentValues values = new ContentValues();
//        values.put(DBHelper.QUESTIONS_COLUMN_STATE, question.getState());
//        values.put(DBHelper.QUESTIONS_COLUMN_CAPITAL, question.getCapital());
//        values.put(DBHelper.QUESTIONS_COLUMN_CITY1, question.getCity1());
//        values.put(DBHelper.QUESTIONS_COLUMN_CITY2, question.getCity2());


        long id = db.insert(DBHelper.TABLE_QUESTIONS, null, values);

        question.setId(id);

        Log.d(DEBUG_TAG, "Stored new questions with id: " + String.valueOf(question.getId()));

        return question;
    }
*/

    public void deleteQuestions() {
        db.delete(DBHelper.TABLE_QUESTIONS, null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DBHelper.TABLE_QUESTIONS + "'");
    }
}
