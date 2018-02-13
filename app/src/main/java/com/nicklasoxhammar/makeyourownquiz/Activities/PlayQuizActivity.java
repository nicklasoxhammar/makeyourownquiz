package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
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
import android.widget.Toast;

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

    String TAG = "PlayQuizActivity";

    Quiz quiz;

    TextView question;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;

    ArrayList<Quiz> quizzes;

    RelativeLayout questionLayout;
    RelativeLayout endScreenLayout;
    RelativeLayout myQuizzesLayout;

    RecyclerView answersRecyclerView;
    RecyclerView.Adapter mAdapter;

    RecyclerView quizzesRecyclerView;
    RecyclerView.Adapter mAdapterQuiz;

    Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);

        myQuizzesLayout = findViewById(R.id.my_quizzes_layout);
        quizzesRecyclerView = findViewById(R.id.quizzes_recycler_view);
        questionLayout = findViewById(R.id.questionLayout);
        endScreenLayout = findViewById(R.id.endScreenLayout);
        answersRecyclerView = findViewById(R.id.answers_recycler_view);

        myQuizzesLayout.setVisibility(View.VISIBLE);


        question = findViewById(R.id.questionTextView);
        answer1 = findViewById(R.id.answer1Button);
        answer2 = findViewById(R.id.answer2Button);
        answer3 = findViewById(R.id.answer3Button);
        answer4 = findViewById(R.id.answer4Button);



        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("myQuizzes", "");
        Type type = new TypeToken<List<Quiz>>(){}.getType();
        quizzes = gson.fromJson(json, type);

        if (quizzes != null) {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            quizzesRecyclerView.setLayoutManager(mLayoutManager);
            mAdapterQuiz = new QuizListAdapter(this, mLayoutManager, quizzes);
            quizzesRecyclerView.setAdapter(mAdapterQuiz);
        }else{

            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.noQuizzesCreated), Toast.LENGTH_LONG);
            toast.show();

        }

    }

    public void quizClicked(View view){


        for(Quiz q : quizzes){

            if (q.getQuizTitle().equals(view.getTag())){
                quiz = q;
                newGame(quiz);
            }
        }

    }

    public void newGame(Quiz quiz){

        quiz.shuffleAllAnswers();

        questionLayout.setVisibility(View.VISIBLE);
        endScreenLayout.setVisibility(View.GONE);
        myQuizzesLayout.setVisibility(View.GONE);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        answersRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AnswerListAdapter(this, mLayoutManager, quiz.getAllQuestions());
        answersRecyclerView.setAdapter(mAdapter);


        showNextQuestion();
    }


    public void showNextQuestion(){
        currentQuestion = quiz.getQuestion();

        answer1.setText(currentQuestion.getAnswers(0));
        answer1.setBackground(getResources().getDrawable(R.drawable.answer_button));

        answer2.setText(currentQuestion.getAnswers(1));
        answer2.setBackground(getResources().getDrawable(R.drawable.answer_button));

        answer3.setText(currentQuestion.getAnswers(2));
        answer3.setBackground(getResources().getDrawable(R.drawable.answer_button));

        answer4.setText(currentQuestion.getAnswers(3));
        answer4.setBackground(getResources().getDrawable(R.drawable.answer_button));


        question.setText(currentQuestion.getQuestion());

    }

    public void checkAnswer(View view){

        Button button = (Button) view;
        currentQuestion.setAnswer(button.getText().toString());

        if(button.getText().toString().equals(currentQuestion.getCorrectAnswer())) {

            quiz.increaseScore();
            currentQuestion.setAnsweredCorrectly(true);
            button.setBackground(getResources().getDrawable(R.drawable.answer_button_right));
        }else{
            currentQuestion.setAnsweredCorrectly(false);
            button.setBackground(getResources().getDrawable(R.drawable.answer_button_wrong));
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (quiz.lastQuestion()){
                    showEndScreen();
                }else{
                    showNextQuestion();
                }
            }
        }, 300);

    }

    public void showEndScreen(){

        TextView score = findViewById(R.id.scoreTextView);

        score.setText(quiz.getCurrentScore() + "/" + quiz.getMaxScore());

        questionLayout.setVisibility(View.GONE);
        endScreenLayout.setVisibility(View.VISIBLE);

    }

    public void mainMenu(View view){

        Intent intent = new Intent(PlayQuizActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    public void playAgain(View view){

        quiz.resetValues();
        newGame(quiz);

    }


    public void editQuiz(View view){

        View cardView = (View) view.getParent().getParent();
        String cardViewTag = cardView.getTag().toString();


        Intent intent = new Intent(PlayQuizActivity.this, EditQuizActivity.class);
        intent.putExtra("quizName", cardViewTag);
        startActivity(intent);

    }


}
