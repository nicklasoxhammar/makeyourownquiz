package com.nicklasoxhammar.makeyourownquiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Nick on 2018-02-02.
 */

public class Question {

    String question;
    ArrayList<String> answers;
    String correctAnswer;
    String answer;

    boolean answeredCorrectly = false;

    Question(String question, ArrayList<String> answers){

        this.question = question;
        this.answers = answers;
        this.correctAnswer = answers.get(0);

        Collections.shuffle(answers);

    }

    public String getQuestion(){

        return question;
    }

    public String getAnswers(int i){

        return answers.get(i);
    }

    public String getCorrectAnswer(){

        return correctAnswer;
    }

    public void setAnsweredCorrectly(boolean answer){

        answeredCorrectly = answer;

    }

    public boolean getAnsweredCorrectly(){

        return answeredCorrectly;
    }

    public void setAnswer(String answer){

        this.answer = answer;
    }

    public String getAnswer(){

        return answer;
    }



}
