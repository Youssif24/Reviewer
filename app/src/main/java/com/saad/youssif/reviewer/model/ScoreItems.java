package com.saad.youssif.reviewer.model;

import android.view.View;
import android.widget.TextView;

import com.saad.youssif.reviewer.R;

/**
 * Created by youssif on 13/07/17.
 */

public class ScoreItems {
    TextView categoryTv,dateTv,scoreTv;
    public ScoreItems(View view)
    {
        categoryTv=(TextView)view.findViewById(R.id.categoryTv);
        dateTv=(TextView)view.findViewById(R.id.dateTv);
        scoreTv=(TextView)view.findViewById(R.id.scoreTv);
    }
}
