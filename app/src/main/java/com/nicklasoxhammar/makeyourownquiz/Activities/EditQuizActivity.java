package com.nicklasoxhammar.makeyourownquiz.Activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nicklasoxhammar.makeyourownquiz.Adapters.QuestionsListAdapter;
import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.Quiz;
import com.nicklasoxhammar.makeyourownquiz.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
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

    public EditText questionTitle;
    public EditText questionAnswer1;
    public EditText questionAnswer2;
    public EditText questionAnswer3;
    public EditText questionAnswer4;

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

        questionTitle = findViewById(R.id.editNewQuestionTitle);
        questionAnswer1 = findViewById(R.id.editNewQuestionAnswer1);
        questionAnswer2 = findViewById(R.id.editNewQuestionAnswer2);
        questionAnswer3 = findViewById(R.id.editNewQuestionAnswer3);
        questionAnswer4 = findViewById(R.id.editNewQuestionAnswer4);

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

    public void createNewQuestion(View view){

        ArrayList<String> questionAnswers = new ArrayList<String>();

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

        mainLayout.setVisibility(View.VISIBLE);
        newQuestionLayout.setVisibility(View.GONE);

    }

    public void deleteQuiz(View view){

        for (Iterator<Quiz> it = quizzes.iterator(); it.hasNext();) {
            Quiz q = it.next();
            if(q.getQuizTitle().equals(quizName)) {
                it.remove();
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
