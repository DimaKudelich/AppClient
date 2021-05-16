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

import com.kudelich.testclient.dto.ClassesDTO;
import com.kudelich.testclient.strings.Strings;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MySchedule extends AppCompatActivity {
    private SharedPreferences preferences;
    private final String SAVED_ID = "SAVED_ID";

    TextView textView;
    Button profile;
    Button schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        profile = (Button) findViewById(R.id.myProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MySchedule.this, Profile.class));
                finish();
            }
        });

        schedule = (Button) findViewById(R.id.schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MySchedule.this, Schedule.class));
                finish();
            }
        });

        textView = (TextView) findViewById(R.id.myScheduleData);

        preferences = getSharedPreferences(SAVED_ID, MODE_PRIVATE);
        long savedId = preferences.getLong(SAVED_ID, 0);

        HttpRequestTask task = new HttpRequestTask();
        task.setUrl(Strings.HOST+"/faculties/courses/groups/student/schedule/" + Long.toString(savedId));
        task.execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, ClassesDTO[]> {
        private String url;
        ClassesDTO[] classesDTOS;

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected ClassesDTO[] doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                ResponseEntity<ClassesDTO[]> responseEntity = restTemplate.getForEntity(url, ClassesDTO[].class);
                classesDTOS = responseEntity.getBody();

                return classesDTOS;
            } catch (Exception e) {
                Log.e("MySchedule activity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ClassesDTO[] classesDTO) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < classesDTO.length; i++) {
                stringBuilder.append(classesDTOS[i].toString() + "\n");
            }
            textView.setText(stringBuilder.toString());
        }
    }
}