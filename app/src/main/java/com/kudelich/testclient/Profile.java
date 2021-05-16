package com.kudelich.testclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kudelich.testclient.dto.StudentDTO;
import com.kudelich.testclient.strings.Strings;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Profile extends AppCompatActivity {
    private SharedPreferences preferences;
    private final String SAVED_ID = "SAVED_ID";

    TextView textView;
    Button mySchedule;
    Button schedule;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView = (TextView)findViewById(R.id.profileData);
        mySchedule = (Button)findViewById(R.id.mySchedule);
        mySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,MySchedule.class));
                finish();
            }
        });

        schedule = (Button)findViewById(R.id.schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Schedule.class));
                finish();
            }
        });

        logOut = (Button)findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(SAVED_ID, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong(SAVED_ID, 0);
                editor.apply();

                startActivity(new Intent(Profile.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView = (TextView)findViewById(R.id.profileData);

        preferences = getSharedPreferences(SAVED_ID,MODE_PRIVATE);
        long savedId = preferences.getLong(SAVED_ID, 0);

        HttpRequestTask task = new HttpRequestTask();
        task.setUrl(Strings.HOST+"/students/" + Long.toString(savedId));
        task.execute();
    }

    private class HttpRequestTask extends AsyncTask<Void,Void, StudentDTO>{
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected StudentDTO doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(url, StudentDTO.class);
            } catch (Exception e) {
                Log.e("ProfileActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(StudentDTO studentDTO){
            String result = studentDTO.toString();

            textView.setVisibility(View.VISIBLE);
            textView.setText(result);
        }
    }
}