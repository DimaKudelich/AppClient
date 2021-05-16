package com.kudelich.testclient.integraterecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kudelich.testclient.Profile;
import com.kudelich.testclient.R;
import com.kudelich.testclient.strings.Strings;
import com.kudelich.testclient.adapters.ScheduleAdapter;
import com.kudelich.testclient.dto.ClassesDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class Schedule extends AppCompatActivity {
    String extraAddress = "group_id";
    ClassesDTO[]classesDTOS;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Long info = getIntent().getLongExtra(extraAddress,0);
        recyclerView = findViewById(R.id.list);

        HttpRequestTask task = new HttpRequestTask();
        task.setUrl(Strings.HOST+"/faculties/courses/groups/schedule/"+Long.toString(info));

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
                        break;
                    case R.id.action_my_schedule:
                        startActivity(new Intent(Schedule.this, MySchedule.class));
                        finish();
                        break;
                    case R.id.action_profile:
                        startActivity(new Intent(Schedule.this, Profile.class));
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
                Log.e("MeSchedule activity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ClassesDTO[] result) {
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}