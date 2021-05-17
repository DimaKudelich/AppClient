package com.kudelich.testclient.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kudelich.testclient.R;
import com.kudelich.testclient.adapters.ScheduleAdapter;
import com.kudelich.testclient.dto.ClassesDTO;
import com.kudelich.testclient.strings.Strings;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class MySchedule extends AppCompatActivity {
    private SharedPreferences preferences;
    private final String SAVED_ID = "SAVED_ID";
    ClassesDTO[]classesDTOS;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);
        recyclerView = findViewById(R.id.list);

        preferences = getSharedPreferences(SAVED_ID, MODE_PRIVATE);
        long savedId = 1;// preferences.getLong(SAVED_ID, 1);

        HttpRequestTask task = new HttpRequestTask();
        task.setUrl(Strings.HOST+"/faculties/courses/groups/student/schedule/"+Long.toString(savedId));

        try {
            classesDTOS =  task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[][]classes = ClassesDTO.classesToStringArray(classesDTOS);

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this,classes);
        recyclerView.setAdapter(scheduleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_schedule:
                        startActivity(new Intent(MySchedule.this, Faculties.class));
                        finish();
                        break;
                    case R.id.action_my_schedule:
                        break;
                    case R.id.action_profile:
                        startActivity(new Intent(MySchedule.this, Profile.class));
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void,Void, ClassesDTO[]> {
        private String url;
        ClassesDTO[]facultiesDTOS;

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected ClassesDTO[] doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                ResponseEntity<ClassesDTO[]> responseEntity = restTemplate.getForEntity(url,ClassesDTO[].class);
                facultiesDTOS = responseEntity.getBody();

                return facultiesDTOS;
            } catch (Exception e) {
                Log.e("MySchedule error", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ClassesDTO[] result) {
        }
    }
}