package com.example.user.softwareengineering;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 *  애플리케이션을 실행하면 제일 처음에 보이는 액티비티
 *  원래는 약 3~5초간 해당 화면을 보여주고 MenuActivity로 넘어가려고 했으나,
 *  개발단계에서는 버튼을 클릭함으로써 MenuActivity로 전환
 */

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    ScrollView scroll;

    String bugName_eg, bugName_ko, symptom, content, management;

    Button btnView;
    TextView txtSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        scroll = (ScrollView) findViewById(R.id.scroll);

        txtSample = (TextView) findViewById(R.id.txtSample);

        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "select* from bug";
                Cursor cursor = db.rawQuery(sql, null);

                for(int i=0; i<cursor.getCount(); i++) {
                    cursor.moveToNext();
                    txtSample.append(cursor.getInt(0) + " / " +
                            cursor.getString(1) + " / " + cursor.getString(2)
                             + " / " +  cursor.getString(3) + " / " + cursor.getString(4)
                             + " / " + cursor.getString(5));
                }
            }
        });*/

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
/*
                createDataBase();


                try {
                    int row = 0, column = 0;
                    AssetManager assetManager = getAssets();
                    InputStream inputStream = assetManager.open("bugs.xls");
                    Workbook workbook = Workbook.getWorkbook(inputStream);

                    Sheet sh = workbook.getSheet("Sheet1");
                    row = sh.getRows();
                    column = sh.getColumns();

                    for(int r = 1; r < row; r++) {
                        Cell sheetCell = sh.getCell(2, r);
                        bugName_ko = sheetCell.getContents();

                        //txtSample.append(bugName_ko + "\n");
                        updateDate(bugName_ko, r);
                        //insertData(bugName_eg, bugName_eg, symptom, content, management);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BiffException e) {
                    e.printStackTrace();
                }

*/
            }
        });
    }
/*
    private void createDataBase() {
        db = openOrCreateDatabase("bugs.db", MODE_PRIVATE, null);

        if(db != null) {
            String sql = "delete bug;";
            //db.execSQL(sql);
        }
    }

    private void insertData(String bugName_eg, String bugName_ko, String symptom, String content, String management) {
        if(db != null) {
            String sql = "insert into bug (bugName_eg, bugName_ko, symptom, content, management) values(?, ?, ?, ?, ?);";
            Object[] params = {bugName_eg, bugName_ko, symptom, content, management};

            db.execSQL(sql, params);
        }
    }

    private void updateDate(String bugName_ko, int bugNum) {
        if(db != null) {
            String sql = "update bug set bugName_ko = \"" + bugName_ko + "\" where bugNum = " + bugNum + ";";
            db.execSQL(sql);
        }
    }*/
}
