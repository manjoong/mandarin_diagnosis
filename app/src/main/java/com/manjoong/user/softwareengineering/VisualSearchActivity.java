package com.manjoong.user.softwareengineering;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.softwareengineering.R;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *  임시적으로 UI를 구현.
 *
 *  카메라 애플리케이션으로 사진을 찍고 그 사진을 받아오거나, 갤러리에서 이미지를 찾을 수 있음.
 *  받아온 사진은 화면에 있는 이미지뷰에 보이고, 추후 텐서플로우와의 통신할 때 이 사진을 보내면 될 듯하다.
 *
 *  imageLoad 변수를 사용해서 이미지가 업로드 되지 않은 채로 검색 버튼을 누르면 경고 다이얼로그가 실행됨
 *
 *  이미지를 업로드하고 검색을 할 때, 텐서플로우로 데이터를 넘기고 결과를 받아오는 방법을 결정해야함.
 *  1. 이 액티비티에서 텐서플로우로 데이터를 보내고 결과를 받은 후 ListViewActivity에 결과를 전달하는 방법
 *  2. 이 액티비티에서 데이터를 ListViewActivity로 전달하고, ListViewActivity에서 텐서플로우로 데이터를 보내고,
 *     결과를 ListViewActivity에서 받아 처리하는 방법
 */

public class VisualSearchActivity extends AppCompatActivity {
    String[] bug = null;
    String[] firstbug = null;
    String transfer = null;
    private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";

    private static final String MODEL_FILE = "file:///android_asset/optimized_graph_new.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/output_labels_new.txt";
    private Button btnCamera;
    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult;
    /////////////////////////////////////////////////////////////////////

    ImageView imageView;




    File file;
    boolean imageLoad;  // 이미지를 업로드 했는지 확인하는 변수

    private static int CAMERA_REQUEST_CODE = 101;   // 카메라 요청 코드
    private static int GALLERY_REQUEST_CODE = 201;    // 갤러리 요청 코드

    public VisualSearchActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_search);

        textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());
        btnCamera = (Button) findViewById(R.id.btnCamera);

        File sdcard = Environment.getExternalStorageDirectory();
        file = new File(sdcard, "capture.jpg");

        imageView = (ImageView) findViewById(R.id.imageView);

        Button btnCamera = (Button) findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });
        initTensorFlowAndLoadModel();

        Button btnGallery = (Button) findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageLoad == true) {
                    // 사진을 업로드 한 경우 결과를 ListViewActivity에서 확인한다.

                    // 사진을 ListViewActivity에서 보낼지, VisualSearchActivity에서 보낼지 결정한 후 다음을 구현한다.

                    // ListViewActivity에서 보내면 결과를 ListViewActivity에서 받아서 myIntent에 추가하고,
                    // startActivity(myIntent);를 startActivityForResult(myIntent, 요청코드); 로 바꾼다.

                    // VisualSearchActivity에서 보내면 사진을 myIntent에 추가하고,
                    // startActivity(myIntent);를 startActivityForResult(myIntent, 요청코드); 로 바꾼다.
                    // VisualSearchActivity에서 사진을 보내므로 결과 또한 해당 액티비티에서 받아서 사용한다.

                    Intent myIntent = new Intent(getApplicationContext(), DiseaseActivity.class);
                    myIntent.putExtra("BUG_NAME_EG", firstbug[0]);
                    startActivity(myIntent);
                }
                else    // 사진을 업로드 하지 않은 경우 경고 메시지 출력
                    showMessage();
            }
        });

    }

    private void showMessage() {    // 이미지를 업로드하지 않은 경우 경고 메시지 출력
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("안내");
        builder.setIcon(android.R.drawable.ic_dialog_alert);    // 경고 이미지 설정

        // 확인버튼 설정
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        builder.setMessage("이미지가 업로드되지 않았습니다.");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void load() {   // 갤러리에서 사진을 불러오는 경우
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    private void capture() {    // 카메라 애플리케이션으로 사진을 찍는 경우
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 카메라 애플리케이션으로 사진을 찍고 결과를 받는 경우

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 8;

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            imageView.setImageBitmap(bitmap);
            recognize_bitmap(bitmap);
            imageLoad = true;
        }
        else if(requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // 갤러리에서 사진을 선택하고 결과를 받는 경우

            Uri uri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                imageView.setImageBitmap(scaled);
                recognize_bitmap(bitmap);
                imageLoad = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void recognize_bitmap(Bitmap bitmap) {
        // 비트맵을 처음에 정의된 INPUT SIZE에 맞춰 스케일링 (상의 왜곡이 일어날수 있는데, 이건 나중에 따로 설명할게요)
        bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

// classifier 의 recognizeImage 부분이 실제 inference 를 호출해서 인식작업을 하는 부분입니다.
        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
        // 결과값은 Classifier.Recognition 구조로 리턴되는데, 원래는 여기서 결과값을 배열로 추출가능하지만,
        // 여기서는 간단하게 그냥 통째로 txtResult에 뿌려줍니다.
        // imgResult에는 분석에 사용된 비트맵을 뿌려줍니다.
        imageView.setImageBitmap(bitmap);

        bug=results.get(0).toString().split("] ");
        firstbug=bug[1].split(" \\(");
        transfer=firstbug[0].substring(0,1);
        transfer= transfer.toUpperCase();
        transfer+=firstbug[0].substring(1); //first bug[0] 가장 확률 높은 병충
         textViewResult.setText(results.get(0).toString()+"\n"+transfer); //transfer은 앞에 대문자
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }
    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_FILE,
                            LABEL_FILE,
                            INPUT_SIZE,
                            IMAGE_MEAN,
                            IMAGE_STD,
                            INPUT_NAME,
                            OUTPUT_NAME);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }
    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnCamera.setVisibility(View.VISIBLE);
            }
        });
    }
}
