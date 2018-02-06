package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nicklasoxhammar.makeyourownquiz.Adapters.AnswerListAdapter;
import com.nicklasoxhammar.makeyourownquiz.Adapters.QuestionsListAdapter;
import com.nicklasoxhammar.makeyourownquiz.DBHelper;
import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.Quiz;
import com.nicklasoxhammar.makeyourownquiz.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2018-02-01.
 */

public class CreateNewQuizActivity extends AppCompatActivity {

    public EditText quizTitle;

    private DBHelper mDatabase;

    public ArrayList<String> questionAnswers;

    File f;

    public EditText questionTitle;
    public EditText questionAnswer1;
    public EditText questionAnswer2;
    public EditText questionAnswer3;
    public EditText questionAnswer4;

    private String mFileName = null;
    private String mFilePath = null;


    public ArrayList<Question> questions;

    RelativeLayout newQuizMainLayout;
    RelativeLayout newQuestionLayout;

    RecyclerView questionsRecyclerView;
    RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        newQuizMainLayout = findViewById(R.id.newQuizMainLayout);
        newQuestionLayout = findViewById(R.id.newQuestionLayout);

        questionsRecyclerView = findViewById(R.id.questions_recycler_view);

        questionTitle = findViewById(R.id.newQuestionTitle);
        questionAnswer1 = findViewById(R.id.newQuestionAnswer1);
        questionAnswer2 = findViewById(R.id.newQuestionAnswer2);
        questionAnswer3 = findViewById(R.id.newQuestionAnswer3);
        questionAnswer4 = findViewById(R.id.newQuestionAnswer4);

        questions = new ArrayList<Question>();
        questionAnswers = new ArrayList<String>();

        mDatabase = new DBHelper(getApplicationContext());

        quizTitle = findViewById(R.id.quizNameEditText);


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

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new QuestionsListAdapter(this, mLayoutManager, questions);
        questionsRecyclerView.setAdapter(mAdapter);

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

    public void createQuiz(View view){


        if (quizTitle.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "You forgot to write the quiz name!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (questions.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Add some questions!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Quiz quiz = new Quiz(questions, quizTitle.getText().toString());


        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("myQuizzes", "");
        Type type = new TypeToken<List<Quiz>>(){}.getType();
        ArrayList<Quiz> quizzes = gson.fromJson(json, type);

        //Check if the arrayList already exists, if it doesnt - create a list and add the object, if it does - just add the object.
        if (quizzes == null){
            quizzes = new ArrayList<Quiz>();
            quizzes.add(quiz);
        }else{
            quizzes.add(quiz);
        }

        json = gson.toJson(quizzes);
        prefsEditor.putString("myQuizzes", json);
        prefsEditor.commit();



        /*setFileNameAndPath();

        try {
            FileOutputStream fileOutput = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            mDatabase.addQuiz(mFileName, mFilePath);

        } catch (Exception e){
            Log.e("CreateNewQuizActivity", "exception", e);
        }*/
    }

    public void setFileNameAndPath(){
        int count = 0;

        do{
            count++;

            mFileName = "myquiz_"
                    + (mDatabase.getCount() + count) + ".java";
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFilePath += "/makeyourownquiz/" + mFileName;

            f = new File(mFilePath);
        }while (f.exists() && !f.isDirectory());
    }


}
