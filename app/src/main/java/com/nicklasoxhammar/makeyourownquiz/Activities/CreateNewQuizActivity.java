package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.Quiz;
import com.nicklasoxhammar.makeyourownquiz.R;

import java.util.ArrayList;

/**
 * Created by Nick on 2018-02-01.
 */

public class CreateNewQuizActivity extends AppCompatActivity {

    public String quizTitle;

    public ArrayList<String> questionAnswers;

    public EditText questionTitle;
    public EditText questionAnswer1;
    public EditText questionAnswer2;
    public EditText questionAnswer3;
    public EditText questionAnswer4;

    Question question;

    public ArrayList<Question> questions;

    RelativeLayout newQuizMainLayout;
    RelativeLayout newQuestionLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        newQuizMainLayout = findViewById(R.id.newQuizMainLayout);
        newQuestionLayout = findViewById(R.id.newQuestionLayout);

        questionTitle = findViewById(R.id.newQuestionTitle);
        questionAnswer1 = findViewById(R.id.newQuestionAnswer1);
        questionAnswer2 = findViewById(R.id.newQuestionAnswer2);
        questionAnswer3 = findViewById(R.id.newQuestionAnswer3);
        questionAnswer4 = findViewById(R.id.newQuestionAnswer4);

        questions = new ArrayList<Question>();
        questionAnswers = new ArrayList<String>();


    }


    public void createQuiz(){


    }

    public void addQuestion(View view){

        newQuizMainLayout.setVisibility(View.GONE);
        newQuestionLayout.setVisibility(View.VISIBLE);


    }


    public void createNewQuestion(View view){

        questionAnswers.clear();

        questionAnswers.add(questionAnswer1.getText().toString());
        questionAnswers.add(questionAnswer2.getText().toString());
        questionAnswers.add(questionAnswer3.getText().toString());
        questionAnswers.add(questionAnswer4.getText().toString());

        if(questionTitle.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "You forgot to write the question!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        for (String answer : questionAnswers){

            if (answer.equals("")){

                Toast toast = Toast.makeText(getApplicationContext(), "Please fill in all answers!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        }

        questions.add(new Question(questionTitle.getText().toString(), questionAnswers));

        questionTitle.setText("");
        questionAnswer1.setText("");
        questionAnswer2.setText("");
        questionAnswer3.setText("");
        questionAnswer4.setText("");

        newQuizMainLayout.setVisibility(View.VISIBLE);
        newQuestionLayout.setVisibility(View.GONE);

    }

    public void cancelNewQuestion(View view){

        questionTitle.setText("");
        questionAnswer1.setText("");
        questionAnswer2.setText("");
        questionAnswer3.setText("");
        questionAnswer4.setText("");

        newQuizMainLayout.setVisibility(View.VISIBLE);
        newQuestionLayout.setVisibility(View.GONE);

    }


}
