package com.saad.youssif.reviewer.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.saad.youssif.reviewer.R;
import com.saad.youssif.reviewer.model.Score;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    Animation animation;
    private TextView statusTv;
    private Button returnBtn;
    private GraphView graphView;
    double xInterval=1.0;
    private float correct,qNum;
    public static float success,fail;
    //private ArrayList<Integer>arrayList=new ArrayList<>();
    BarGraphSeries<DataPoint>series;
    String fileName="ScoresFile";
    private SharedPreferences scoresFile;
    private String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        statusTv=(TextView)findViewById(R.id.statusTv);
        returnBtn=(Button)findViewById(R.id.go_to_home);
        scoresFile=getSharedPreferences(fileName,MODE_PRIVATE);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,HomeActivity.class));
                ResultActivity.this.finish();
            }
        });

        getIntentData();
        calculateScore();

        saveScore();


        graphView=(GraphView)findViewById(R.id.graphView);
        series=new BarGraphSeries<>(new DataPoint[]
                {
                        new DataPoint(0, 0),
                        new DataPoint(1, fail),
                        new DataPoint(2, success)
                }
        );
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        series.setSpacing(10);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if(data.getY()>50)
                {
                    if(success==data.getY())
                        return Color.GREEN;
                    else
                        return Color.RED;
                }

                else
                {
                    if(success==data.getY())
                        return Color.GREEN;
                    else
                        return Color.RED;
                }

            }
        });

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        if (series instanceof BarGraphSeries ) {
            // Shunt the viewport, per v3.1.3 to show the full width of the first and last bars.
            graphView.getViewport().setMinX(series.getLowestValueX() - (xInterval/2.0));
            graphView.getViewport().setMaxX(series.getHighestValueX() + (xInterval/2.0));
            graphView.getViewport().setMaxY(100);
        } else {
            graphView.getViewport().setMinX(series.getLowestValueX() );
            graphView.getViewport().setMaxX(series.getHighestValueX());
            graphView.getViewport().setMaxY(100);
        }

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Start","Fail","Success"});
        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);
        //staticLabelsFormatter.setVerticalLabels(new String[] {"old", "middle", "new"});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        graphView.addSeries(series);

        determineStatus();


    }


    private void getIntentData()
    {
        Intent intent=getIntent();
        correct=intent.getExtras().getInt("correct");
        //wrong=intent.getExtras().getInt("wrong");
        qNum=intent.getExtras().getInt("qNum");
        category=intent.getExtras().getString("category");

        if(category.equals("lawEnfo"))
            category="Law Enforcement Administration";
        else if(category.equals("crimin"))
            category="Criminalistics";
        else if(category.equals("crinJur"))
            category="Criminal Jurisprudence";
        else if(category.equals("crimDet"))
            category="Crime Detection";
        else if(category.equals("socOfCrimes"))
            category="Sociology of Crimes";
        else
            category="Correctional Administration";

    }


    private void calculateScore()
    {

        success=(correct/qNum)*100;
        fail= (float) (100.0-success);
    }

    private void saveScore()
    {
        Date date=new Date();
        String stringDate = DateFormat.getDateTimeInstance().format(date);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = scoresFile.edit();
        Score score=new Score();
        score.setScore("Success: "+(int)success+"%"+"\nfail : "+(int)fail+"%");
        score.setDate(stringDate);
        score.setCategory(category);
        String objectAsString = gson.toJson(score);
        editor.putString(stringDate,objectAsString).commit();
    }

    public void determineStatus()
    {
        if(success>=fail)
        {
            statusTv.setText(statusTv.getText()+" Successful");
            statusTv.setTextColor(Color.GREEN);
        }
        else
        {
            statusTv.setText(statusTv.getText()+" Failed");
            statusTv.setTextColor(Color.RED);
        }
        animation= AnimationUtils.loadAnimation(this,R.anim.scale_anim);
        statusTv.setAnimation(animation);
    }
}
