package com.kudelich.testclient.integraterecycler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kudelich.testclient.R;
import com.kudelich.testclient.strings.Strings;
import com.kudelich.testclient.adapters.SearchAdapter;
import com.kudelich.testclient.dto.CoursesDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class Courses extends AppCompatActivity {
    String extraAddress = "faculty_id";
    String extraPut = "course_id";
    RecyclerView recyclerView;
    String[] objects;
    CoursesDTO[]coursesDTOS;
    long[]allId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = findViewById(R.id.list);

        //putExtraInfo = findViewById(R.id.putExtraInfo);
        Long info = getIntent().getLongExtra(extraAddress,0);
        //putExtraInfo.setText(Integer.toString(info));

        HttpRequestTask task = new HttpRequestTask();
        task.setUrl(Strings.HOST+"/faculties/courses/"+Long.toString(info));

        try {
            coursesDTOS =  task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        objects = CoursesDTO.convertToStringArray(coursesDTOS);
        allId = CoursesDTO.getAllId(coursesDTOS);

        SearchAdapter searchAdapter = new SearchAdapter(this, objects, extraPut,allId, Groups.class);
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

    private class HttpRequestTask extends AsyncTask<Void,Void, CoursesDTO[]> {
        private String url;
        CoursesDTO[] coursesDTOS;
        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected CoursesDTO[] doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                ResponseEntity<CoursesDTO[]> responseEntity = restTemplate.getForEntity(url,CoursesDTO[].class);
                coursesDTOS = responseEntity.getBody();

                return coursesDTOS;
            } catch (Exception e) {
                Log.e("MeSchedule activity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(CoursesDTO[] result) {
        }
    }
}