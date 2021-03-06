package com.nicklasoxhammar.makeyourownquiz.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.nicklasoxhammar.makeyourownquiz.Question;
import com.nicklasoxhammar.makeyourownquiz.R;

import java.util.ArrayList;

/**
 * Created by Nick on 2018-02-02.
 */

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.ViewHolder> {

    Question question;
    Context mContext;
    LinearLayoutManager llm;
    ArrayList<Question> questions;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView vQuestion;
        protected TextView vAnswer;
        protected TextView vCorrectAnswer;
        protected LinearLayout cardBackground;

        public ViewHolder(View v) {
            super(v);

            vQuestion = (TextView) v.findViewById(R.id.answer_card_question_text);
            vAnswer = (TextView) v.findViewById(R.id.answer_card_answer_text);
            vCorrectAnswer = (TextView) v.findViewById(R.id.answer_card_correctanswer_text);
            cardBackground = v.findViewById(R.id.answerCardBackground);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AnswerListAdapter(Context context, LinearLayoutManager linearLayoutManager, ArrayList<Question> questions) {
        mContext = context;
        this.questions = questions;
        llm = linearLayoutManager;



    }

    // Create new views (invoked by the layout manager)
    @Override
    public AnswerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_card_view, parent, false);

        mContext = parent.getContext();

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        question = questions.get(position);

        holder.vQuestion.setText(Html.fromHtml("<b>"+ mContext.getResources().getString(R.string.question) + "</b>" + question.getQuestion()));
        holder.vAnswer.setText(Html.fromHtml("<b>"+ mContext.getResources().getString(R.string.yourAnswer) + "</b>" + question.getAnswer()));
        holder.vCorrectAnswer.setText(Html.fromHtml("<b>" + mContext.getResources().getString(R.string.correctAnswer) + "</b> " + question.getCorrectAnswer()));

        if(question.getAnsweredCorrectly()){

            holder.cardBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.rightAnswerColor));
        }else{
            holder.cardBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.wrongAnswerColor));
        }

    }


    @Override
    public int getItemCount() {
        return questions.size();
    }
}
