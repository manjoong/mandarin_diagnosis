package com.example.user.softwareengineering;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 *  ListViewActivity에서 선택된 진단에 대한 정보를 보여주는 액티비티
 *  UI 구현해야 함.
 *  (일단 어떤 데이터를 보여줄지 정해져야 UI 구성이 편할듯함)
 *
 *  대처방안 버튼을 만들고, 클릭하면 ManagementActivity로 화면을 전환한다.
 */

public class DiseaseActivity extends AppCompatActivity {

    int num;
    String name_ko;
    String name_eg;
    String symptom;
    String content;
    String management;
    ScrollView scrollView;

    TextView txtDiseaseNameKorean, txtDiseaseNameEnglish, txtSymptom, txtContent, txtManagement;
    ImageView imageView;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

        txtSymptom = (TextView) findViewById(R.id.txtSymptom);
        txtContent = (TextView) findViewById(R.id.txtExplanation);
        txtManagement = (TextView) findViewById(R.id.txtManagement);

        imageView = (ImageView) findViewById(R.id.imageView2);

        txtDiseaseNameKorean = (TextView) findViewById(R.id.txtDiseaseNameKorean);
        txtDiseaseNameEnglish = (TextView) findViewById(R.id.txtDiseaseNameEnglish);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        Intent intent = getIntent();
        name_ko = intent.getStringExtra("BUG_NAME");

        setTextView();
    }

    private void setTextView() {
        db = openOrCreateDatabase("bugs.db", MODE_PRIVATE, null);

        if(db != null) {
            String sql = "select bugNum, bugName_eg, bugName_ko, symptom, content, management from bug where bugName_ko = \"" + name_ko + "\";";

            Cursor cursor = db.rawQuery(sql, null);

            for(int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                num = cursor.getInt(0);
                name_ko = cursor.getString(2);
                name_eg = cursor.getString(1);
                symptom = cursor.getString(3);
                content = cursor.getString(4);
                management = cursor.getString(5);
            }

            txtDiseaseNameKorean.setText(name_ko);
            txtDiseaseNameEnglish.setText(name_eg);
            txtSymptom.setText(symptom);
            txtContent.setText(content);
            txtManagement.setText(management);

            imageView.setImageResource(findResID(num));
        }
    }

    private int findResID(int id) {
        switch (id) {
            case 1:
                return R.drawable.bug1;
            case 2:
                return R.drawable.bug2;
            case 3:
                return R.drawable.bug3;
            case 4:
                return R.drawable.bug4;
            case 5:
                return R.drawable.bug5;
            case 6:
                return R.drawable.bug6;
            case 7:
                return R.drawable.bug7;
            case 8:
                return R.drawable.bug8;
            case 9:
                return R.drawable.bug9;
            case 10:
                return R.drawable.bug10;
            case 11:
                return R.drawable.bug11;
            case 12:
                return R.drawable.bug12;
            case 13:
                return R.drawable.bug13;
            case 14:
                return R.drawable.bug14;
            case 15:
                return R.drawable.bug15;
        }

        return R.mipmap.ic_launcher;
    }
}