package com.nicklasoxhammar.makeyourownquiz.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import static com.nicklasoxhammar.makeyourownquiz.Activities.AddQuestionActivity.REQUEST_CODE;

/**
 * Created by Nick on 2018-02-07.
 */

public class EditQuizActivity extends AppCompatActivity {

    String quizName;

    Quiz quiz;

    ArrayList<Question> questions;

    RecyclerView questionsRecyclerView;
    RecyclerView.Adapter mAdapter;

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

        for (Iterator<Question> it = questions.iterator(); it.hasNext();) {
            Question q = it.next();
            if(q.getQuestion().equals(view.getTag())) {
                it.remove();
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    public void addQuestion(View view){

        Intent i = new Intent(this, AddQuestionActivity.class);
        startActivityForResult(i, REQUEST_CODE);

    }

    public void deleteQuiz(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.confirmDelete));
        builder.setMessage(getResources().getString(R.string.areYouSureDeleteQuiz));

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                for (Iterator<Quiz> it = quizzes.iterator(); it.hasNext(); ) {
                    Quiz q = it.next();
                    if (q.getQuizTitle().equals(quizName)) {
                        it.remove();
                    }
                }

                SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                prefsEditor.remove("myQuizzes");
                prefsEditor.commit();

                json = gson.toJson(quizzes);
                prefsEditor.putString("myQuizzes", json);
                prefsEditor.commit();

                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.quizDeleted), Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(EditQuizActivity.this, PlayQuizActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                return;
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void applyChange(View view){

        if (questions.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.forgotToAddQuestions), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Quiz changedQuiz = new Quiz(questions, quizName);

        for (Iterator<Quiz> it = quizzes.iterator(); it.hasNext();) {
            Quiz q = it.next();
            if(q.getQuizTitle().equals(quizName)) {
                it.remove();
            }
        }

        quizzes.add(changedQuiz);

        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.remove("myQuizzes");
        prefsEditor.commit();


        json = gson.toJson(quizzes);
        prefsEditor.putString("myQuizzes", json);
        prefsEditor.commit();

        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.changesApplied), Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(EditQuizActivity.this, PlayQuizActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

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
