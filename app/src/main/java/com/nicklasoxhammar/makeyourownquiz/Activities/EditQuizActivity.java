package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nicklasoxhammar.makeyourownquiz.Adapters.QuestionsListAdapter;
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

    RelativeLayout mainLayout;
    RelativeLayout newQuestionLayout;

    SharedPreferences appSharedPrefs;
    Gson gson;
    String json;

    ArrayList<Quiz> quizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quiz);

        Intent intent = getIntent();
        quizName = intent.getExtras().getString("quizName");

        setQuiz();

        mainLayout = findViewById(R.id.editQuizMainLayout);
        newQuestionLayout = findViewById(R.id.editNewQuestionLayout);

        questionsRecyclerView = findViewById(R.id.edit_quiz_questions_recycler_view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new QuestionsListAdapter(this, mLayoutManager, questions);
        questionsRecyclerView.setAdapter(mAdapter);

        TextView quizNameTextView = findViewById(R.id.quizNameTextView);
        quizNameTextView.setText(quizName);

    }

    public void setQuiz(){

        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        gson = new Gson();
        json = appSharedPrefs.getString("myQuizzes", "");
        Type type = new TypeToken<List<Quiz>>(){}.getType();
        quizzes = gson.fromJson(json, type);

        for (Quiz q : quizzes){

            if(quizName.equals(q.getQuizTitle())){
                quiz = q;
            }
        }

        questions = quiz.getAllQuestions();


    }

    public void deleteQuestion(View view){

        for (Question q : questions){

            if (q.getQuestion().equals(view.getTag())){
                questions.remove(q);
                mAdapter.notifyDataSetChanged();
            }
        }



    }

    public void addQuestion(View view){

        mainLayout.setVisibility(View.GONE);
        newQuestionLayout.setVisibility(View.VISIBLE);

    }

    public void deleteQuiz(View view){

        for (Quiz q : quizzes){

            if (q.getQuizTitle().equals(quizName)){
                quizzes.remove(q);
            }
        }

        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.remove("myQuizzes");
        prefsEditor.commit();


        json = gson.toJson(quizzes);
        prefsEditor.putString("myQuizzes", json);
        prefsEditor.commit();

        Intent intent = new Intent(EditQuizActivity.this, PlayQuizActivity.class);
        startActivity(intent);
    }
}
