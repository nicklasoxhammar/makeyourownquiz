package com.nicklasoxhammar.makeyourownquiz;

/**
 * Created by Nick on 2018-02-02.
 */

public class Question {

    String question;
    String[] answers;
    String correctAnswer;
    String answer;

    boolean answeredCorrectly = false;

    Question(String question, String[] answers){

        this.question = question;
        this.answers = answers;
        this.correctAnswer = answers[0];

    }

    public String getQuestion(){

        return question;
    }

    public String getAnswers(int i){

        return answers[i];
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
