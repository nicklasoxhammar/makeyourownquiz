package com.nicklasoxhammar.makeyourownquiz;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Nick on 2018-02-02.
 */

public class ExampleQuiz {

    String quizTitle;

    ArrayList<Question> questions;

    Question q1;
    Question q2;
    Question q3;
    Question q4;


    int maxScore;
    int currentScore;
    int currentQuestion;



    public ExampleQuiz(){

        questions = new ArrayList<Question>();

        String[] q1Answers = {"red", "black", "white", "green"};
        String[] q2Answers = {"nick", "black", "white", "green"};
        String[] q3Answers = {"sweden", "black", "white", "green"};
        String[] q4Answers = {"stockholm", "black", "white", "green"};

        q1 = new Question("What is the color of my desk?", q1Answers);
        q2 = new Question("Whats my name", q2Answers);
        q3 = new Question("Country?", q3Answers);
        q4 = new Question("City?", q4Answers);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);

        maxScore = questions.size();

        currentQuestion = -1;

    }

    public Question getQuestion(){

        currentQuestion++;

        return questions.get(currentQuestion);

    }

    public void increaseScore(){

        currentScore++;
    }

    public boolean lastQuestion(){

        if (currentQuestion == questions.size() - 1){
            return true;
        }else{
            return false;
        }
    }

    public int getCurrentScore(){

        return currentScore;
    }

    public int getMaxScore(){

        return maxScore;
    }

    public ArrayList<Question> getAllQuestions(){

        return questions;
    }




}
