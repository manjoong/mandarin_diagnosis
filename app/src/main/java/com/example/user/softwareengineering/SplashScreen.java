package com.example.user.softwareengineering;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Random;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class SplashScreen extends Activity {

    Thread splashTread;
    ImageView imageView;

    String bugName_eg, bugName_ko, symptom, content, management;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        imageView = (ImageView)findViewById(R.id.splash);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        int[] ids = new int[]{R.drawable.s_img};

        Random randomGenerator = new Random();
        int rg = randomGenerator.nextInt(ids.length);

        this.imageView.setImageDrawable(getResources().getDrawable(ids[rg]));
        try {
            createDataBase();

            int row = 0, column = 0;

            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("bugs.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);

            Sheet sh = workbook.getSheet("Sheet1");
            row = sh.getRows();
            column = sh.getColumns();

            for(int r = 1; r < row; r++) {
                for(int c = 0; c < column; c++) {
                    Cell sheetCell = sh.getCell(c,r);

                    switch(c) {
                        case 1:
                            bugName_eg = sheetCell.getContents();
                            break;
                        case 2:
                            bugName_ko = sheetCell.getContents();
                            break;
                        case 3:
                            symptom = r + "번째 증상";
                            break;
                        case 4:
                            content = sheetCell.getContents();
                            break;
                        case 5:
                            management = sheetCell.getContents();
                            break;
                    }
                }
                insertData(bugName_eg, bugName_ko, symptom, content, management);

/*
                    Cell sheetCell = sh.getCell(2, r);
                    bugName_ko = sheetCell.getContents();

                    txtSample.append(bugName_ko + "\n");
                    updateDate(bugName_ko, r);*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }

            }
        };
        splashTread.start();
    }

    private void createDataBase() {
        db = openOrCreateDatabase("bugs.db", MODE_PRIVATE, null);

        if(db != null) {
            String sql = "create table bug (bugNum integer PRIMARY KEY autoincrement, bugName_eg text, bugName_ko text, symptom text, content text, management text);";
            db.execSQL(sql);
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
    }

}