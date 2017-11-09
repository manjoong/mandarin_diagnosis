package com.example.user.softwareengineering;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 *  ListViewActivity에서 선택된 진단에 대한 정보를 보여주는 액티비티
 *  UI 구현해야 함.
 *  (일단 어떤 데이터를 보여줄지 정해져야 UI 구성이 편할듯함)
 *
 *  대처방안 버튼을 만들고, 클릭하면 ManagementActivity로 화면을 전환한다.
 */

public class DiseaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

    }
}
