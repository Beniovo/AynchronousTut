package com.example.aynchronoustut;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText etNum;
    Button btinputNum;
    TextView tvShowResult;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum = (EditText)findViewById(R.id.etNum);
        tvShowResult = (TextView)findViewById(R.id.tvShowResult);
        tvShowResult.setVisibility(View.GONE);
        btinputNum = (Button)findViewById(R.id.btinputNum);

        btinputNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int numberSet = Integer.parseInt(etNum.getText().toString().trim());

                new AsynchronousLoader().execute(numberSet);


            }
        });


    }

    public  class AsynchronousLoader extends AsyncTask<Integer, Integer, String>{
       ProgressDialog progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(MainActivity.this);
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setMax(Integer.parseInt(etNum.getText().toString().trim()));
            progressBar.show();
        }


        @Override
        protected String doInBackground(Integer... integers) {
            int one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, randomNum;

            String result;

            //helps calculate the stages shiwn in the bar: no so useful except to reduce cost of
            //showing the bar
            double currentstatus = 0;
            double previousstatus = 0;



            Random random = new Random();

            for(int i = 0; i<integers[0]; i++){

                currentstatus = (double)i / integers[0];

                if(currentstatus - previousstatus >= 0.03){
                    publishProgress(i);
                    previousstatus = currentstatus;
                }

                randomNum = random.nextInt(6) + 1;

                switch (randomNum){

                    case 1 : one++;
                        break;
                    case 2 : two++;
                        break;
                    case 3 : three++;
                        break;
                    case 4 : four++;
                        break;
                    case 5 : five++;
                        break;
                    default:  six++;
                        break;
                }

            }

            result = "Result: \n1: " + one + "\n2: " + two + "\n3: " + three + "\n4: " + four
                    + "\n5: " + five + "\n6: " + six;


            return result;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //this sets the progress update tp the value in currentstatus
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.dismiss();

            tvShowResult.setText(s);
            tvShowResult.setVisibility(View.VISIBLE);

        }


    }
}
