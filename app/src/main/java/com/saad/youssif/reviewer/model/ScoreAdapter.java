package com.saad.youssif.reviewer.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.saad.youssif.reviewer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youssif on 13/07/17.
 */

public class ScoreAdapter extends BaseAdapter {

    List<Score>scoreList=new ArrayList<>();
    Context mContext;
    LayoutInflater layoutInflater;

    public ScoreAdapter(List<Score> scoreList, Context mContext) {
        this.scoreList = scoreList;
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return scoreList.size();
    }

    @Override
    public Score getItem(int position) {

        return (Score) scoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ScoreItems scoreItems;
        if(view==null)
        {
            view=layoutInflater.inflate(R.layout.score_items,null);
            scoreItems=new ScoreItems(view);
            view.setTag(scoreItems);
        }
        else
            scoreItems=(ScoreItems)view.getTag();


        Score score=getItem(position);
        scoreItems.categoryTv.setText(score.getCategory().toString());
        scoreItems.scoreTv.setText(score.getScore().toString());
        scoreItems.dateTv.setText(score.getDate().toString());

        return view;
    }
}
