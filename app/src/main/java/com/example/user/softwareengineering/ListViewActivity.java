package com.example.user.softwareengineering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 *  화면에 리스트뷰만 존재.
 *  이전의 화면에서 데이터를 텐스플로우로 전달하고 받는 방법을 결정했을 테니, 여기서는 그 방식대로 한 후
 *  데이터를 리스트뷰에 추가한다.
 *
 *  이 화면을 프래그먼트로 바꾸는것도 좋은 수지만, 아래 주석이 달려있는 곳에서 구현상의 어려움이 있어서
 *  현재 상태로 만들어둠.
 */

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        final ListView listView = (ListView) findViewById(R.id.listView);

        DiagnosisAdapter adapter = new DiagnosisAdapter();

        // 임의적으로 adapter에 데이터를 추가했으나,
        // 이미지 검색을 하고 결과를 받으면 일치하는 갯수만큼 for 반복문으로 adapter에 각 진단 객체 추가

        final DiagnosisItem[] diagnosisItems = new DiagnosisItem[10];

        for(int i=0; i<10; i++) {
            diagnosisItems[i] = new DiagnosisItem("병" + i, (90-i*10) + "%", R.mipmap.ic_launcher);
            adapter.addItem(diagnosisItems[i]);
        }

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = diagnosisItems[position].getName();

                Intent myIntent = new Intent(getApplicationContext(), DiseaseActivity.class);
                myIntent.putExtra("name", name);
                startActivity(myIntent);
            }
        });
    }

    class DiagnosisAdapter extends BaseAdapter {
        ArrayList<DiagnosisItem> items = new ArrayList<DiagnosisItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        private void addItem(DiagnosisItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DiagnosisItemView view = new DiagnosisItemView(getApplicationContext());
            // 현재 액티비티를 프래그먼트로 바꾸게 되면 getApplicationContext()부분에서 에러가 발생
            // 아직까지 처리하기 어려워 그냥 액티비티 상태로 놔둠

            DiagnosisItem item = items.get(position);
            view.setName(item.getName());
            view.setPercent(item.getPercent());
            view.setImage(item.getResId());

            return view;
        }
    }
}
