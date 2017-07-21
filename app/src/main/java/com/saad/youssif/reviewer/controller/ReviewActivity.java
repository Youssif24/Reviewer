package com.saad.youssif.reviewer.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.saad.youssif.reviewer.R;
import com.saad.youssif.reviewer.model.DatabaseHelper;
import com.saad.youssif.reviewer.model.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ReviewActivity extends AppCompatActivity {

    private TextView questionTv,timerTv;
    private RadioGroup radioGroup;
    private RadioButton ch1, ch2, ch3, ch4;
    private Button nextBtn;
    private List<Question> questionsList = new ArrayList<>();
    DatabaseHelper myDbHelper = new DatabaseHelper(this);
    public String category,type;
    public int qNum,sqNum,correct=0,wrong=0;
    public static int count=0;
    Intent intent;
    private ArrayList<Integer>selectedOptions=new ArrayList<>();
    private ArrayList<String>answersList=new ArrayList<>();
    private static final String FORMAT = "%02d:%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        gatherControls();

        myDbHelper = new DatabaseHelper(ReviewActivity.this);
        checkDb();

        intent=getIntent();
        getIntentData();

        selectCategory(category);
        firstQuestion();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(ReviewActivity.this,"Select one choice",Toast.LENGTH_LONG).show();
                }
                else
                {

                    if(count<qNum)
                    {
                        if(count==qNum-1)
                        {
                            nextBtn.setText("Show Result");
                        }
                        checkAnswers();
                        count++;
                        radioGroup.clearCheck();
                        mapData();
                    }
                    else
                    {
                        Intent resultIntent=new Intent(ReviewActivity.this,ResultActivity.class);
                        resultIntent.putExtra("correct",correct);
                        //resultIntent.putExtra("wrong",wrong);
                        resultIntent.putExtra("qNum",qNum);
                        resultIntent.putExtra("category",category);
                        startActivity(resultIntent);
                        ReviewActivity.this.finish();

                    }
                }

            }
        });
    }


    private void checkDb()
    {
        File database = getApplicationContext().getDatabasePath(myDbHelper.DBNAME);
        if (false == database.exists()) {
            myDbHelper.getReadableDatabase();
            if (copyDatabase(ReviewActivity.this)) {
                //Toast.makeText(ReviewActivity.this,"Success Copying Database",Toast.LENGTH_LONG).show();
                //myDbHelper.openDatabase();
            }
            else
            {
                Toast.makeText(ReviewActivity.this,"Error Copying Database",Toast.LENGTH_LONG).show();

            }
        }

    }

    private void gatherControls()
    {
        questionTv = (TextView) findViewById(R.id.questionTv);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ch1 = (RadioButton) findViewById(R.id.choice1_radioBtn);
        ch2 = (RadioButton) findViewById(R.id.choice2_radioBtn);
        ch3 = (RadioButton) findViewById(R.id.choice3_radioBtn);
        ch4 = (RadioButton) findViewById(R.id.choice4_radioBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        timerTv=(TextView)findViewById(R.id.timerTextView);
    }
    @Override
    protected void onResume() {
        super.onResume();
        randomData();
        if(type.equals("test"))
        {
            new CountDownTimer(1200000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerTv.setVisibility(View.VISIBLE);
                    timerTv.setText(""+String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                @Override
                public void onFinish() {
                    Toast.makeText(ReviewActivity.this,"Time Out",Toast.LENGTH_SHORT).show();
                    Intent resultIntent=new Intent(ReviewActivity.this,ResultActivity.class);
                    resultIntent.putExtra("correct",correct);
                    //resultIntent.putExtra("wrong",wrong);
                    resultIntent.putExtra("qNum",qNum);
                    resultIntent.putExtra("category",category);
                    startActivity(resultIntent);
                    ReviewActivity.this.finish();
                }

            }.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        count=0;
        myDbHelper.closeDatabase();
    }

    public void selectCategory(String categ)
    {
        if(categ.equals("lawEnfo"))
        {
            questionsList=myDbHelper.getLaw_Enforcement();
        }
        else if(categ.equals("crinJur"))
        {
            questionsList=myDbHelper.getCriminal_Jurisprudence();
        }
        else if(categ.equals("crimin"))
        {
            questionsList=myDbHelper.getCriminalistics();
        }
        else if(categ.equals("crimDet"))
        {
            questionsList=myDbHelper.getCrime_Detection();
        }
        else if(categ.equals("socOfCrimes"))
        {
            questionsList=myDbHelper.getSociology_of_Crimes();
        }
        else
        {
            questionsList=myDbHelper.getCorrectional_Administration();
        }
    }

    public void  mapData() {
        if(questionsList!=null)
        {
                questionTv.setText(questionsList.get(selectedOptions.get(count)).getQuestion());
                ch1.setText(questionsList.get(selectedOptions.get(count)).getCh1());
                ch2.setText(questionsList.get(selectedOptions.get(count)).getCh2());
                ch3.setText(questionsList.get(selectedOptions.get(count)).getCh3());
                ch4.setText(questionsList.get(selectedOptions.get(count)).getCh4());
            answersList.add(questionsList.get(selectedOptions.get(count)).getAnswer());
        }

    }


    public void checkAnswers()
    {
            if(radioGroup.getCheckedRadioButtonId()==R.id.choice1_radioBtn)
            {
                if(ch1.getText().equals(answersList.get(count).trim()))
                {
                    correct++;
                    //Toast.makeText(ReviewActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    wrong++;
                    //Toast.makeText(ReviewActivity.this,"Wrong",Toast.LENGTH_SHORT).show();

                }
                //ch1.setChecked(false);
            }
            else if(radioGroup.getCheckedRadioButtonId()==R.id.choice2_radioBtn)
            {
                if(ch2.getText().equals(answersList.get(count).trim()))
                {
                    //Toast.makeText(ReviewActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                    correct++;

                }
                else
                {
                    wrong++;
                    //Toast.makeText(ReviewActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                }
                //ch2.setChecked(false);
            }
            else if(radioGroup.getCheckedRadioButtonId()==R.id.choice3_radioBtn)
            {
                if(ch3.getText().equals(answersList.get(count).trim()))
                {
                    correct++;
                    //Toast.makeText(ReviewActivity.this,"Correct",Toast.LENGTH_SHORT).show();

                }

                else
                {
                    wrong++;
                    //Toast.makeText(ReviewActivity.this,"Wrong",Toast.LENGTH_SHORT).show();

                }
                //ch3.setChecked(false);

            }
            else if(radioGroup.getCheckedRadioButtonId()==R.id.choice4_radioBtn)
            {
                if(ch4.getText().equals(answersList.get(count).trim()))
                {
                    //Toast.makeText(ReviewActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                    correct++;

                }
                else
                {
                    wrong++;
                    //Toast.makeText(ReviewActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                }
                //sch4.setChecked(false);
            }
        }



    public void firstQuestion()
    {

        for(;;)
        {
            Question question=questionsList.get(new Random().nextInt(questionsList.size()));
            sqNum=question.getId();
            if(sqNum<questionsList.size())
            {
                selectedOptions.add(sqNum);
                questionTv.setText(questionsList.get(sqNum).getQuestion());
                ch1.setText(questionsList.get(sqNum).getCh1());
                ch2.setText(questionsList.get(sqNum).getCh2());
                ch3.setText(questionsList.get(sqNum).getCh3());
                ch4.setText(questionsList.get(sqNum).getCh4());
                answersList.add(questionsList.get(sqNum).getAnswer());
                break;
            }

            }


        //count++;

    }

    public void randomData()
    {
        Question selectedQuest=new Question();
        while(selectedOptions.size()<=qNum)
        {
            selectedQuest=questionsList.get(new Random().nextInt(questionsList.size()));
            sqNum=selectedQuest.getId();
            if(sqNum<questionsList.size())
            {
                if(selectedOptions.contains(selectedQuest.getId()))
                {
                    continue;
                }
                else
                {
                    selectedOptions.add(sqNum);

                }
            }
            else
                continue;

        }


    }

    public void getIntentData()
    {
        category=intent.getExtras().getString("category");
        type=intent.getExtras().getString("type");
        qNum=intent.getExtras().getInt("questionNum");
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(myDbHelper.DBNAME);
            String outFileName = myDbHelper.DBLOCATION + myDbHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(type.equals("test"))
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(ReviewActivity.this);
            builder.setMessage("Do you want to end Test ?")
                    .setIcon(android.R.drawable.ic_delete)
                    .setTitle("Exit Test").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    count=0;
                    ReviewActivity.this.finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
        }

        return super.onKeyDown(keyCode, event);

    }



}






