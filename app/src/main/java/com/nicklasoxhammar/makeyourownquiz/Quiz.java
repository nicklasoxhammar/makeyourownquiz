package com.nicklasoxhammar.makeyourownquiz;

import android.content.Context;
import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Nick on 2018-02-02.
 */

public class Quiz {

    private String mFileName = null;
    private String mFilePath = null;

    private DBHelper mDatabase;

    String quizTitle;

    ArrayList<Question> questions;

    int maxPoints;
    int currentPoints;


    public Quiz(ArrayList<Question> questions, String quizTitle, Context context){

        mDatabase = new DBHelper(context);

        this.questions = questions;
        maxPoints = questions.size();
        this.quizTitle = quizTitle;
    }

    public void setFileNameAndPath(){
        int count = 0;
        File f;

        do{
            count++;

            mFileName = "quiz_" + (mDatabase.getCount() + count);
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFilePath += "/makeyourownquiz/" + mFileName;

            f = new File(mFilePath);
        }while (f.exists() && !f.isDirectory());
    }

    public void storeQuiz(){

        try{
            mDatabase.addQuiz(mFileName, mFilePath);
        } catch (Exception e){
            Log.e(TAG, "exception", e);
        }


    }


}
