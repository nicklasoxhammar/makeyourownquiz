package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.R;
import java.util.ArrayList;

/**
 * Created by Nick on 2018-02-11.
 */

public class AddQuestionActivity extends AppCompatActivity{

    public static final int REQUEST_CODE = 1;

    public EditText quizTitle;

    public ArrayList<String> questionAnswers;

    public EditText questionTitle;
    public EditText questionAnswer1;
    public EditText questionAnswer2;
    public EditText questionAnswer3;
    public EditText questionAnswer4;

    Question question;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        questionTitle = findViewById(R.id.newQuestionTitle);
        questionAnswer1 = findViewById(R.id.newQuestionAnswer1);
        questionAnswer2 = findViewById(R.id.newQuestionAnswer2);
        questionAnswer3 = findViewById(R.id.newQuestionAnswer3);
        questionAnswer4 = findViewById(R.id.newQuestionAnswer4);

        quizTitle = findViewById(R.id.quizNameEditText);

    }


    public void createNewQuestion(View view){

        questionAnswers = new ArrayList<String>();

        questionAnswers.add(questionAnswer1.getText().toString());
        questionAnswers.add(questionAnswer2.getText().toString());
        questionAnswers.add(questionAnswer3.getText().toString());
        questionAnswers.add(questionAnswer4.getText().toString());


        if(questionTitle.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.forgotToWriteQuestion), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        for (String answer : questionAnswers){

            if (answer.equals("")){

                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.fillInAnswers), Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        }

        Intent intent = new Intent();
        intent.putExtra("question", questionTitle.getText().toString());
        intent.putExtra("answers", questionAnswers);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    public void cancelNewQuestion(View view){

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();

    }

}
