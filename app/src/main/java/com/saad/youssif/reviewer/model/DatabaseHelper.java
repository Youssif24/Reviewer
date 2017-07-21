package com.saad.youssif.reviewer.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youssif on 09/07/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME="questions_bank.db";
    public static final String DBLOCATION= Environment.getDataDirectory()+"/data/com.saad.youssif.reviewer/databases/";
    private static final int DBVERSION=7;
    private Context myContext;
    private SQLiteDatabase myDatabase;
    List<Question>questionList=new ArrayList<>();


    public DatabaseHelper(Context context)
    {
        super(context,DBNAME,null,DBVERSION);
        this.myContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void openDatabase()
    {
        String dbPath=myContext.getDatabasePath(DBNAME).getPath();
        if(myDatabase!=null&&myDatabase.isOpen())
            return;
        myDatabase=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase()
    {
        if(myDatabase!=null)
            myDatabase.close();
    }

    public List<Question> getCriminal_Jurisprudence() {
        questionList=new ArrayList<>();
        openDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM Criminal_Jurisprudence", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question=new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1).trim());
            question.setCh1(cursor.getString(2).trim());
            question.setCh2(cursor.getString(3).trim());
            question.setCh3(cursor.getString(4).trim());
            question.setCh4(cursor.getString(5).trim());
            question.setAnswer(cursor.getString(6).trim());
            questionList.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return questionList;
    }

    public List<Question> getCriminalistics() {
        questionList=new ArrayList<>();
        openDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM Criminalistics", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question=new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1).trim());
            question.setCh1(cursor.getString(2).trim());
            question.setCh2(cursor.getString(3).trim());
            question.setCh3(cursor.getString(4).trim());
            question.setCh4(cursor.getString(5).trim());
            question.setAnswer(cursor.getString(6).trim());
            questionList.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return questionList;
    }

    public List<Question> getCrime_Detection() {
        questionList=new ArrayList<>();
        openDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM Crime_Detection", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question=new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1).trim());
            question.setCh1(cursor.getString(2).trim());
            question.setCh2(cursor.getString(3).trim());
            question.setCh3(cursor.getString(4).trim());
            question.setCh4(cursor.getString(5).trim());
            question.setAnswer(cursor.getString(6).trim());
            questionList.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return questionList;
    }

    public List<Question> getLaw_Enforcement() {
        questionList=new ArrayList<>();
        openDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM Law_Enforcement", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question=new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1).trim());
            question.setCh1(cursor.getString(2).trim());
            question.setCh2(cursor.getString(3).trim());
            question.setCh3(cursor.getString(4).trim());
            question.setCh4(cursor.getString(5).trim());
            question.setAnswer(cursor.getString(6).trim());
            questionList.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return questionList;
    }

    public List<Question> getSociology_of_Crimes() {
        questionList=new ArrayList<>();
        openDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM Sociology_of_Crimes", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question=new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1).trim());
            question.setCh1(cursor.getString(2).trim());
            question.setCh2(cursor.getString(3).trim());
            question.setCh3(cursor.getString(4).trim());
            question.setCh4(cursor.getString(5).trim());
            question.setAnswer(cursor.getString(6).trim());
            questionList.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return questionList;
    }

    public List<Question> getCorrectional_Administration() {
        questionList=new ArrayList<>();
        openDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM Correctional_Administration", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question=new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1).trim());
            question.setCh1(cursor.getString(2).trim());
            question.setCh2(cursor.getString(3).trim());
            question.setCh3(cursor.getString(4).trim());
            question.setCh4(cursor.getString(5).trim());
            question.setAnswer(cursor.getString(6).trim());
            questionList.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return questionList;
    }
}
