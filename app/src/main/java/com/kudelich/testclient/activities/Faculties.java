package com.kudelich.testclient.activities;

import android.content.Intent;
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
import com.kudelich.testclient.adapters.SearchAdapter;
import com.kudelich.testclient.dto.FacultiesDTO;
import com.kudelich.testclient.strings.Strings;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class Faculties extends AppCompatActivity {
    String[] objects;
    RecyclerView recyclerView;
    String extraAddress = "faculty_id";
    FacultiesDTO[]facultiesDTOS;
    long[]allId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);
        recyclerView = findViewById(R.id.list);

        HttpRequestTask task = new HttpRequestTask();
        task.setUrl(Strings.HOST+"/faculties/get");

        try {
             facultiesDTOS =  task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        objects = FacultiesDTO.convertToStringArray(facultiesDTOS);
        allId = FacultiesDTO.getAllId(facultiesDTOS);

        SearchAdapter searchAdapter = new SearchAdapter(this, objects, extraAddress,allId, Courses.class);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_schedule:
                        break;
                    case R.id.action_my_schedule:
                        startActivity(new Intent(Faculties.this, MySchedule.class));
                        finish();
                        break;
                    case R.id.action_profile:
                        startActivity(new Intent(Faculties.this, Profile.class));
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void,Void, FacultiesDTO[]> {
        private String url;
        FacultiesDTO[]facultiesDTOS;

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected FacultiesDTO[] doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                ResponseEntity<FacultiesDTO[]> responseEntity = restTemplate.getForEntity(url,FacultiesDTO[].class);
                facultiesDTOS = responseEntity.getBody();

                return facultiesDTOS;
            } catch (Exception e) {
                Log.e("MeSchedule activity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(FacultiesDTO[] result) {
        }
    }
}