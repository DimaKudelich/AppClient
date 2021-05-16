package com.kudelich.testclient.integraterecycler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kudelich.testclient.R;
import com.kudelich.testclient.strings.Strings;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {
    private SharedPreferences preferences;
    private final String SAVED_ID = "SAVED_ID";

    EditText login;
    EditText password;
    Button login_with_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        preferences = getSharedPreferences(SAVED_ID, MODE_PRIVATE);
        long savedId = preferences.getLong(SAVED_ID, 0);

        if (savedId!=0){
            startActivity(new Intent(Login.this, Profile.class));
            finish();
        }

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        login_with_data = (Button) findViewById(R.id.login_with_data);

        login_with_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_value = login.getText().toString();
                String password_value = password.getText().toString();

                HttpRequestTask requestTask = new HttpRequestTask();
                requestTask.setUrl(Strings.HOST+"/students/"+login_value+"/"+password_value);
                long current_login = 0;

                try {
                    current_login = requestTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (current_login!=0){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong(SAVED_ID, current_login);
                    editor.apply();

                    startActivity(new Intent(Login.this, Profile.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Long> {
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(url, Long.class);
            } catch (Exception e) {
                Log.e("Login activity error", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {
        }
    }
}