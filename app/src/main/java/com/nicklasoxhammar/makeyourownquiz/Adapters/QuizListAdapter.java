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
import com.nicklasoxhammar.makeyourownquiz.Quiz;
import com.nicklasoxhammar.makeyourownquiz.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Nick on 2018-02-06.
 */

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.ViewHolder> {

    Quiz quiz;
    Context mContext;
    LinearLayoutManager llm;
    ArrayList<Quiz> quizzes;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView vQuizTitle;
        protected View cardView;

        public ViewHolder(View v) {
            super(v);

            vQuizTitle = (TextView) v.findViewById(R.id.quiz_card_quiztitle_text);
            cardView = v.findViewById(R.id.card_view_quiz);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuizListAdapter(Context context, LinearLayoutManager linearLayoutManager, ArrayList<Quiz> quizzes) {
        mContext = context;
        this.quizzes = quizzes;
        llm = linearLayoutManager;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuizListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_card_view, parent, false);

        mContext = parent.getContext();

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(QuizListAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        quiz = quizzes.get(position);

        holder.vQuizTitle.setText(quiz.getQuizTitle());
        Log.d(TAG, "onBindViewHolder: DIIIID WEWWWWEWEW REEEEEEEAAAACH?!!?!?!?!??! " + quiz.getQuizTitle());

    }


    @Override
    public int getItemCount() {
        return quizzes.size();
    }

}
