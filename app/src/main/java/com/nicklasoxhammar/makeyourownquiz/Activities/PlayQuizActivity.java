package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nicklasoxhammar.makeyourownquiz.Quiz;
import com.nicklasoxhammar.makeyourownquiz.R;

/**
 * Created by Nick on 2018-02-02.
 */

public class PlayQuizActivity extends AppCompatActivity {

    Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);
    }

    PlayQuizActivity(Quiz quiz){

        this.quiz = quiz;

    }
}
