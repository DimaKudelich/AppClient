package com.kudelich.testclient.integraterecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.kudelich.testclient.R;
import com.kudelich.testclient.strings.Strings;
import com.kudelich.testclient.adapters.SearchAdapter;
import com.kudelich.testclient.dto.GroupsDTO;

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
        setContentView(R.layout.activity_recycler_view);
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