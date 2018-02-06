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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nicklasoxhammar.makeyourownquiz.Adapters.AnswerListAdapter;
import com.nicklasoxhammar.makeyourownquiz.Adapters.QuizListAdapter;
import com.nicklasoxhammar.makeyourownquiz.ExampleQuiz;
import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.Quiz;
import com.nicklasoxhammar.makeyourownquiz.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2018-02-02.
 */

public class PlayQuizActivity extends AppCompatActivity {

    ExampleQuiz quiz;

    TextView question;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;

    RelativeLayout questionLayout;
    RelativeLayout endScreenLayout;
    RelativeLayout answersLayout;
    RelativeLayout myQuizzesLayout;

    RecyclerView answersRecyclerView;
    RecyclerView.Adapter mAdapter;

    RecyclerView quizzesRecyclerView;
    RecyclerView.Adapter mAdapterQuiz;

    Question currentQuestion;

    ArrayAdapter<Question> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);

        myQuizzesLayout = findViewById(R.id.my_quizzes_layout);
        quizzesRecyclerView = findViewById(R.id.quizzes_recycler_view);
        questionLayout = findViewById(R.id.questionLayout);
        endScreenLayout = findViewById(R.id.endScreenLayout);
        answersLayout = findViewById(R.id.answersLayout);
        answersRecyclerView = findViewById(R.id.answers_recycler_view);


        question = findViewById(R.id.questionTextView);
        answer1 = findViewById(R.id.answer1Button);
        answer2 = findViewById(R.id.answer2Button);
        answer3 = findViewById(R.id.answer3Button);
        answer4 = findViewById(R.id.answer4Button);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("myQuiz", "");
        Quiz mQuizObject = gson.fromJson(json, Quiz.class);
        /*Type type = new TypeToken<List<Quiz>>(){}.getType();
        List<Quiz> quizzes = gson.fromJson(json, type);*/

        ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
        quizzes.add(mQuizObject);
        quizzes.add(mQuizObject);
        quizzes.add(mQuizObject);

        Log.d("TAG", "onCreate: " + mQuizObject.getQuizTitle());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        quizzesRecyclerView.setLayoutManager(mLayoutManager);
        mAdapterQuiz = new QuizListAdapter(this, mLayoutManager, quizzes);
        quizzesRecyclerView.setAdapter(mAdapterQuiz);


        Log.d("OKEEEJ", "onCreate: DAADSAADA" + String.valueOf(mAdapterQuiz.getItemCount()));


        //this.quiz = new ExampleQuiz();



        //newGame(quiz);
    }

   /* public void newGame(ExampleQuiz Quiz){

        this.quiz = Quiz;

        questionLayout.setVisibility(View.VISIBLE);
        endScreenLayout.setVisibility(View.GONE);
        answersLayout.setVisibility(View.GONE);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        answersRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AnswerListAdapter(this, mLayoutManager, quiz.getAllQuestions());
        answersRecyclerView.setAdapter(mAdapter);

        showNextQuestion();
    }*/


    public void showNextQuestion(){

        currentQuestion = quiz.getQuestion();

        question.setText(currentQuestion.getQuestion());
        answer1.setText(currentQuestion.getAnswers(0));
        answer2.setText(currentQuestion.getAnswers(1));
        answer3.setText(currentQuestion.getAnswers(2));
        answer4.setText(currentQuestion.getAnswers(3));

    }

    public void checkAnswer(View view){

        Button v = (Button) view;

        currentQuestion.setAnswer(v.getText().toString());
        Button button = (Button) view;

        if(button.getText().toString().equals(currentQuestion.getCorrectAnswer())) {

            quiz.increaseScore();
            currentQuestion.setAnsweredCorrectly(true);
        }




            if (quiz.lastQuestion()){
                showEndScreen();
            }else{

                showNextQuestion();

            }


    }

    public void showEndScreen(){

        TextView score = findViewById(R.id.scoreTextView);

        score.setText(quiz.getCurrentScore() + "/" + quiz.getMaxScore());

        questionLayout.setVisibility(View.GONE);
        endScreenLayout.setVisibility(View.VISIBLE);

    }

    public void mainMenu(View view){

        Intent intent = new Intent(PlayQuizActivity.this, MainActivity.class);
        startActivity(intent);

    }

    /*public void playAgain(View view){

        newGame(new ExampleQuiz());

    }*/

    public void showAnswers(View view){

        questionLayout.setVisibility(View.GONE);
        endScreenLayout.setVisibility(View.GONE);
        answersLayout.setVisibility(View.VISIBLE);

    }
}
