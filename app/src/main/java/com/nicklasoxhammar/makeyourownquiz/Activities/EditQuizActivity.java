package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nicklasoxhammar.makeyourownquiz.R;

/**
 * Created by Nick on 2018-02-07.
 */

public class EditQuizActivity extends AppCompatActivity {

    String quizName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quiz);

        Intent intent = getIntent();
        quizName = intent.getExtras().getString("quizName");

    }
}
