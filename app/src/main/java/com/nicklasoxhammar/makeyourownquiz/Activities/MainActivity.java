package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nicklasoxhammar.makeyourownquiz.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startNewQuiz(View view){

        Intent intent = new Intent(MainActivity.this, CreateNewQuizActivity.class);
        startActivity(intent);

    }

    public void playQuiz(View view){

        Intent intent = new Intent(MainActivity.this, PlayQuizActivity.class);
        startActivity(intent);

    }
}
