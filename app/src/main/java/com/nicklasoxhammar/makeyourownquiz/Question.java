package com.nicklasoxhammar.makeyourownquiz;

/**
 * Created by Nick on 2018-02-02.
 */

public class Question {

    String question;
    String[] answers;
    String correctAnswer;

    Question(String question, String[] answers){

        this.question = question;
        this.answers = answers;
        this.correctAnswer = answers[0];

    }
}
