package com.nicklasoxhammar.makeyourownquiz;

import java.util.ArrayList;



/**
 * Created by Nick on 2018-02-02.
 */

public class Quiz {

    int maxScore;
    int currentScore;
    int currentQuestion;

    String quizTitle;

    ArrayList<Question> questions;


    public Quiz(ArrayList<Question> questions, String quizTitle){

        this.questions = questions;
        maxScore = questions.size();
        this.quizTitle = quizTitle;

        currentQuestion = -1;
    }

    public String getQuizTitle(){

        return quizTitle;
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

    public void resetValues(){

        currentQuestion = -1;
        currentScore = 0;
    }


}
