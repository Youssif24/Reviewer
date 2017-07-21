package com.saad.youssif.reviewer.controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.saad.youssif.reviewer.R;
import com.saad.youssif.reviewer.model.Score;
import com.saad.youssif.reviewer.model.ScoreAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoresActivity extends AppCompatActivity {

    ListView listView;
    List<Score> arrayList=new ArrayList();
    ScoreAdapter scoreAdapter;
    String fileName = "ScoresFile";
    SharedPreferences scoresFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        this.setTitle("Scores");
        listView=(ListView)findViewById(R.id.scoresListView);
        scoresFile = getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = scoresFile.edit();
        showData(scoresFile);
    }


    public void showData(SharedPreferences sharedPref) {
        Map<String, ?> allEntries = sharedPref.getAll();
        arrayList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Score score = new Score();
            try {
                String jsonKey = sharedPref.getString(entry.getKey(), null);

                JSONObject jsonRoot = new JSONObject(jsonKey);
                score.setCategory(jsonRoot.getString("category").toString());
                score.setScore(jsonRoot.getString("score").toString());
                score.setDate(jsonRoot.getString("date").toString());
                arrayList.add(score);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
         scoreAdapter= new ScoreAdapter(arrayList,this);
        listView.setAdapter(scoreAdapter);


    }
}
