package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nicklasoxhammar.makeyourownquiz.Adapters.QuestionsListAdapter;
import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.Quiz;
import com.nicklasoxhammar.makeyourownquiz.R;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.nicklasoxhammar.makeyourownquiz.Activities.AddQuestionActivity.REQUEST_CODE;

/**
 * Created by Nick on 2018-02-01.
 */

public class CreateNewQuizActivity extends AppCompatActivity {

    public EditText quizTitle;

    public ArrayList<Question> questions;

    RecyclerView questionsRecyclerView;
    RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);


        questionsRecyclerView = findViewById(R.id.questions_recycler_view);

        questions = new ArrayList<Question>();
        quizTitle = findViewById(R.id.quizNameEditText);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new QuestionsListAdapter(this, mLayoutManager, questions);
        questionsRecyclerView.setAdapter(mAdapter);

    }


    public void addQuestion(View view){

        Intent i = new Intent(this, AddQuestionActivity.class);
        startActivityForResult(i, REQUEST_CODE);

    }


    public void createQuiz(View view) {


        //TODO: check if another quiz has the same name before creating!


        if (quizTitle.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.forgotToWriteQuizName), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (questions.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.forgotToAddQuestions), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Quiz quiz = new Quiz(questions, quizTitle.getText().toString());

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("myQuizzes", "");
        Type type = new TypeToken<List<Quiz>>() {
        }.getType();
        ArrayList<Quiz> quizzes = gson.fromJson(json, type);

        //Check if the arrayList already exists, if it doesnt - create a list and add the object, if it does - just add the object.
        if (quizzes == null) {
            quizzes = new ArrayList<Quiz>();
            quizzes.add(quiz);
        } else {
            quizzes.add(quiz);
        }

        json = gson.toJson(quizzes);
        prefsEditor.putString("myQuizzes", json);
        prefsEditor.commit();

        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.quizCreated), Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(CreateNewQuizActivity.this, PlayQuizActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    public void deleteQuestion(View view){

        for (Question q : questions){

            if (q.getQuestion().equals(view.getTag())){
                questions.remove(q);
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
               String questionTitle = data.getStringExtra("question");
               ArrayList<String> answers = data.getStringArrayListExtra("answers");

                questions.add(new Question(questionTitle, answers));
                mAdapter.notifyDataSetChanged();

            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

}
