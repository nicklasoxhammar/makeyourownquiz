package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.Quiz;
import com.nicklasoxhammar.makeyourownquiz.R;

import java.util.ArrayList;

/**
 * Created by Nick on 2018-02-01.
 */

public class CreateNewQuizActivity extends AppCompatActivity {

    public String quizTitle;

    public ArrayList<Question> questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
    }


    public void createQuiz(){



    }


}
