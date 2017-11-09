package com.example.user.softwareengineering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 *  애플리케이션을 실행하면 제일 처음에 보이는 액티비티
 *  최상위 레이아웃의 UI를 설정해야 함.
 *  원래는 약 3~5초간 해당 화면을 보여주고 MenuActivity로 넘어가려고 했으나,
 *  개발단계에서는 버튼을 클릭함으로써 MenuActivity로 전환
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //개발단계에서는 버튼을 이용한 화면전환(임시)
        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
