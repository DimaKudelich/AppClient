package com.kudelich.testclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kudelich.testclient.strings.Strings;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static android.content.SharedPreferences.*;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private final String SAVED_ID = "SAVED_ID";

    EditText loginEdit;
    EditText passwordEdit;
    Button loginButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //super.onStart();
//        startActivity(new Intent(MainActivity.this, NewLogin.class));
//        finish();

        textView = (TextView) findViewById(R.id.text);
        loginEdit = (EditText) findViewById(R.id.loginEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequestTask task = new HttpRequestTask();
                task.setUrl(Strings.HOST+"/students/" + loginEdit.getText().toString() + "/" + passwordEdit.getText().toString());
                task.execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        startActivity(new Intent(MainActivity.this, NewLogin.class));
//        finish();


        preferences = getSharedPreferences(SAVED_ID, MODE_PRIVATE);
        long savedId = preferences.getLong(SAVED_ID, 0);

        if (savedId != 0) {
            startActivity(new Intent(MainActivity.this, Profile.class));
            finish();
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(url, String.class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                long id = Long.parseLong(result);

                if (id != 0) {
                    Editor editor = preferences.edit();
                    editor.putLong(SAVED_ID, id);
                    editor.apply();

                    startActivity(new Intent(MainActivity.this, Profile.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect login or password", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Bad connection to the server", Toast.LENGTH_LONG).show();
            }
        }
    }
}