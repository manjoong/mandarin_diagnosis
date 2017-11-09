package com.example.user.softwareengineering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 *  병충해를 직접적으로 검색하는 액티비티
 *  UI구성해야함.
 *  현재 구현되어 있는 버튼으로 인해 ListViewActivity로 화면을 전환하게 된다.
 */

public class KeySearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_search);

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ListViewActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
