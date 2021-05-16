package com.kudelich.testclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kudelich.testclient.strings.Strings;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Schedule extends AppCompatActivity {
    Button mySchedule;
    Button myProfile;
    Button enterId;
    Button backToLast;
    EditText chooseId;
    TextView textView;

    String[]urls = {
            Strings.HOST+"/faculties/get",
            Strings.HOST+"/faculties/courses/",
            Strings.HOST+"/faculties/courses/groups/",
            Strings.HOST+"/faculties/courses/groups/schedule/"
    };
    int tmp;
    long lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mySchedule = (Button)findViewById(R.id.mySchedule);
        mySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Schedule.this,MySchedule.class));
                finish();
            }
        });
        tmp = 0;

        myProfile = (Button)findViewById(R.id.myProfile);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Schedule.this,Profile.class));
                finish();
            }
        });

        chooseId = (EditText)findViewById(R.id.chooseId);
        enterId = (Button)findViewById(R.id.enterId);
        enterId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseId.getText().toString().length()!=0 && tmp<=3){
                    HttpRequestTask task = new HttpRequestTask();
                    task.setUrl(urls[tmp]+chooseId.getText().toString());
                    task.execute();
                    tmp++;
                    lastId = Long.parseLong(chooseId.getText().toString());
                }else{
                    textView.setText(chooseId.getText().toString());
                }
            }
        });

        backToLast = (Button)findViewById(R.id.backToLast);
        backToLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tmp>=0){
                    HttpRequestTask task = new HttpRequestTask();
                    task.setUrl(urls[tmp-1]+Long.toString(lastId));
                    task.execute();
                    tmp--;
                }else{
                    textView.setText(chooseId.getText().toString());
                }
            }
        });

        textView = (TextView)findViewById(R.id.scheduleData);

        HttpRequestTask task = new HttpRequestTask();
        task.setUrl(Strings.HOST+"/faculties/get");
        task.execute();
    }
    private class HttpRequestTask extends AsyncTask<Void,Void,String> {
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                return restTemplate.getForObject(url,String.class);
            } catch (Exception e) {
                Log.e("MeSchedule activity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }
}