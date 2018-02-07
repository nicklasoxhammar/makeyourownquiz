package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nicklasoxhammar.makeyourownquiz.Adapters.QuestionsListAdapter;
import com.nicklasoxhammar.makeyourownquiz.Adapters.QuizListAdapter;
import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.Quiz;
import com.nicklasoxhammar.makeyourownquiz.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2018-02-07.
 */

public class EditQuizActivity extends AppCompatActivity {

    String quizName;

    Quiz quiz;

    ArrayList<Question> questions;

    RecyclerView questionsRecyclerView;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quiz);

        Intent intent = getIntent();
        quizName = intent.getExtras().getString("quizName");

        setQuiz();


        questionsRecyclerView = findViewById(R.id.edit_quiz_questions_recycler_view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new QuestionsListAdapter(this, mLayoutManager, questions);
        questionsRecyclerView.setAdapter(mAdapter);

        TextView quizNameTextView = findViewById(R.id.quizNameTextView);
        quizNameTextView.setText(quizName);

    }

    public void setQuiz(){

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("myQuizzes", "");
        Type type = new TypeToken<List<Quiz>>(){}.getType();
        ArrayList<Quiz> quizzes = gson.fromJson(json, type);

        for (Quiz q : quizzes){

            if(quizName.equals(q.getQuizTitle())){
                quiz = q;
            }
        }

        questions = quiz.getAllQuestions();


    }

    public void deleteQuestion(View view){



    }
}
