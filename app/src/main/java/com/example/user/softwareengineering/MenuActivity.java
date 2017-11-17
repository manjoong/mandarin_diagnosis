package com.example.user.softwareengineering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 *  어떤 검색을 할 것인지 선택하는 액티비티
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnImageSearch = (Button) findViewById(R.id.btnImageSearch);
        btnImageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), VisualSearchActivity.class);
                startActivity(myIntent);
            }
        });

        Button btnKeySearch = (Button) findViewById(R.id.btnKeySearch);
        btnKeySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), KeySearchActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
