package com.nicklasoxhammar.makeyourownquiz;

import java.util.ArrayList;

/**
 * Created by Nick on 2018-02-02.
 */

public class Quiz {

    String quizTitle;

    ArrayList<Question> questions;

    int maxPoints;
    int currentPoints;


    public Quiz(ArrayList<Question> questions, String quizTitle){

        this.questions = questions;
        maxPoints = questions.size();
        this.quizTitle = quizTitle;
    }
}
