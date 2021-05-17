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
import com.kudelich.testclient.dto.GroupsDTO;
import com.kudelich.testclient.strings.Strings;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class Groups extends AppCompatActivity {
    RecyclerView recyclerView;
    String extraAddress = "course_id";
    String extraPut = "group_id";
    String[]objects;
    GroupsDTO[] groupsDTOS;
    long[]allId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);
        recyclerView = findViewById(R.id.list);

        Long info = getIntent().getLongExtra(extraAddress,0);

        HttpRequestTask task = new HttpRequestTask();
        task.setUrl(Strings.HOST+"/faculties/courses/groups/"+Long.toString(info));

        try {
            groupsDTOS =  task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        objects = GroupsDTO.convertToStringArray(groupsDTOS);
        allId = GroupsDTO.getAllId(groupsDTOS);

        SearchAdapter searchAdapter = new SearchAdapter(this, objects, extraPut,allId, Schedule.class);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.findViewById(R.id.action_schedule).setEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_schedule:
                        break;
                    case R.id.action_my_schedule:
                        startActivity(new Intent(Groups.this, MySchedule.class));
                        finish();
                        break;
                    case R.id.action_profile:
                        startActivity(new Intent(Groups.this, Profile.class));
                        finish();
                        break;
                }
                return false;
            }
        });
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

    private class HttpRequestTask extends AsyncTask<Void,Void, GroupsDTO[]> {
        private String url;
        GroupsDTO[]facultiesDTOS;

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected GroupsDTO[] doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                ResponseEntity<GroupsDTO[]> responseEntity = restTemplate.getForEntity(url,GroupsDTO[].class);
                facultiesDTOS = responseEntity.getBody();

                return facultiesDTOS;
            } catch (Exception e) {
                Log.e("MeSchedule activity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(GroupsDTO[] result) {
        }
    }
}