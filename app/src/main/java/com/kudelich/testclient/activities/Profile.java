package com.kudelich.testclient.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kudelich.testclient.R;
import com.kudelich.testclient.strings.Strings;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class Profile extends AppCompatActivity {
    TextView name;
    TextView faculty;
    TextView course;
    TextView group;
    Button log_out;

    private SharedPreferences preferences;
    private final String SAVED_ID = "SAVED_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        name = findViewById(R.id.name);
        faculty = findViewById(R.id.faculty);
        course = findViewById(R.id.course);
        group = findViewById(R.id.group);
        log_out = findViewById(R.id.log_out);

        HttpRequestTask requestTask = new HttpRequestTask();
        requestTask.setUrl(Strings.HOST+"/student/info/1");
        String[]res = new String[0];

        try {
            res = requestTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        name.setText(res[0]);
        faculty.setText(res[3]);
        course.setText(res[2]);
        group.setText(res[1]);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences(SAVED_ID,MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong(SAVED_ID, 0);
                editor.apply();

                startActivity(new Intent(Profile.this,Login.class));
                finish();
            }
        });

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.findViewById(R.id.action_profile).setEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_schedule:
                        startActivity(new Intent(Profile.this, Faculties.class));
                        finish();
                        break;
                    case R.id.action_my_schedule:
                        startActivity(new Intent(Profile.this, MySchedule.class));
                        finish();
                        break;
                    case R.id.action_profile:
                        break;
                }
                return false;
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void,Void, String[]> {
        private String url;
        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(url,String[].class);
                String[] result = responseEntity.getBody();

                return result;
            } catch (Exception e) {
                Log.e("MeSchedule activity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
        }
    }
}