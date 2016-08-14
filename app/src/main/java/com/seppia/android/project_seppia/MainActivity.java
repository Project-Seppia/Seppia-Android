package com.seppia.android.project_seppia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView temp_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String intentMsg = intent.getStringExtra(LoginActivity.TAG);
        temp_display = (TextView) findViewById(R.id.textView_MA_temp);
        temp_display.setText(intentMsg);

    }
}
