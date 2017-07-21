package com.saad.youssif.reviewer.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.saad.youssif.reviewer.R;

public class OptionsActivity extends AppCompatActivity {

    private TextView tvType,tvNum;
    private RadioButton revOption,testOption,num25,num50;
    private Button startBtn;
    private RadioGroup typesGroup,numGroup;
    private String category;
    Intent intent;
    private int num_25=25,num_50=50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        gatherControls();
        intent=getIntent();
        if(!(getCategoryType().equals("Empty")))
        {
            category=getCategoryType();
        }

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOptions();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        num_25=0;
        num_50=0;
    }

    private void gatherControls()
{
    tvType=(TextView)findViewById(R.id.typeTv);
    tvNum=(TextView)findViewById(R.id.questionNumTv);
    revOption=(RadioButton)findViewById(R.id.reviewOption);
    testOption=(RadioButton)findViewById(R.id.testOption);
    num25=(RadioButton)findViewById(R.id.num25Option);
    num50=(RadioButton)findViewById(R.id.num50Option);
    startBtn =(Button)findViewById(R.id.startBtn);
    typesGroup =(RadioGroup)findViewById(R.id.typesId);
    numGroup=(RadioGroup)findViewById(R.id.numId);
}

    public String getCategoryType()
    {
        category=intent.getExtras().getString("lawEnfo");
        if(category!=null)
        {
            setTitle("Law Enforcement Administration");
            return category;
        }
        category=intent.getExtras().getString("crimin");
        if(category!=null)
        {
            setTitle("Criminalistics");
            return category;
        }
        category=intent.getExtras().getString("crinJur");
        if(category!=null)
        {
            setTitle("Criminal Jurisprudence");
            return category;
        }
        category=intent.getExtras().getString("crimDet");
        if(category!=null)
        {
            setTitle("Crime Detection");
            return category;
        }
        category=intent.getExtras().getString("socOfCrimes");
        if(category!=null)
        {
            setTitle("Sociology of Crimes");
            return category;
        }
        category=intent.getExtras().getString("CorrAdmin");
        if(category!=null)
        {
            setTitle("Correctional Administration");
            return category;
        }
        return "Empty";
    }

    public void getOptions()
    {
        int typesSelectedId=typesGroup.getCheckedRadioButtonId();
        int numSelectedId=numGroup.getCheckedRadioButtonId();
        if(typesSelectedId==-1||numSelectedId==-1)
        {
            Toast.makeText(OptionsActivity.this,"Please Select Items !!!",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(typesSelectedId==R.id.reviewOption&&numSelectedId==R.id.num25Option)
            {
                Intent intent1=new Intent(OptionsActivity.this,ReviewActivity.class);
                intent1.putExtra("type","review");
                intent1.putExtra("category",category);
                intent1.putExtra("questionNum",num_25);
                OptionsActivity.this.finish();
                startActivity(intent1);
            }
            else if(typesSelectedId==R.id.reviewOption&&numSelectedId==R.id.num50Option)
            {
                Intent intent2=new Intent(OptionsActivity.this,ReviewActivity.class);
                intent2.putExtra("type","review");
                intent2.putExtra("category",category);
                intent2.putExtra("questionNum",num_50);
                OptionsActivity.this.finish();
                startActivity(intent2);
            }
            else if(typesSelectedId==R.id.testOption&&numSelectedId==R.id.num25Option)
            {
                final Intent intent3 =new Intent(OptionsActivity.this,ReviewActivity.class);
                intent3.putExtra("type","test");
                intent3.putExtra("category",category);
                intent3.putExtra("questionNum",num_25);

                AlertDialog.Builder builder=new AlertDialog.Builder(OptionsActivity.this);
                builder.setMessage("Do you ready for the Test ?")
                        .setTitle("Test Confirmation").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OptionsActivity.this.finish();
                        startActivity(intent3);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();




            }
            else if(typesSelectedId==R.id.testOption&&numSelectedId==R.id.num50Option)
            {
                final Intent intent4=new Intent(OptionsActivity.this,ReviewActivity.class);
                intent4.putExtra("type","test");
                intent4.putExtra("category",category);
                intent4.putExtra("questionNum",num_50);

                AlertDialog.Builder builder=new AlertDialog.Builder(OptionsActivity.this);
                builder.setMessage("Do you ready for the Test ?")
                        .setTitle("Test Confirmation").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OptionsActivity.this.finish();
                        startActivity(intent4);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();


            }

        }

    }
}
