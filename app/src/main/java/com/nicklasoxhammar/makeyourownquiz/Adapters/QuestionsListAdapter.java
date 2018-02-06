package com.nicklasoxhammar.makeyourownquiz.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Nick on 2018-02-05.
 */

public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.ViewHolder> {

        Question question;
        Context mContext;
        LinearLayoutManager llm;
        ArrayList<Question> questions;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class ViewHolder extends RecyclerView.ViewHolder {

    protected TextView vQuestion;
    protected TextView vCorrectAnswer;
    protected View cardView;

    public ViewHolder(View v) {
        super(v);

        vQuestion = (TextView) v.findViewById(R.id.question_card_question_text);
        vCorrectAnswer = (TextView) v.findViewById(R.id.question_card_correctanswer_text);
        cardView = v.findViewById(R.id.card_view_question);
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuestionsListAdapter(Context context, LinearLayoutManager linearLayoutManager, ArrayList<Question> questions) {
        mContext = context;
        this.questions = questions;
        llm = linearLayoutManager;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_card_view, parent, false);

        mContext = parent.getContext();

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        question = questions.get(position);

        holder.vQuestion.setText(question.getQuestion());
        holder.vCorrectAnswer.setText("Correct answer: " + question.getCorrectAnswer());


    }


    @Override
    public int getItemCount() {
        return questions.size();
    }
}
