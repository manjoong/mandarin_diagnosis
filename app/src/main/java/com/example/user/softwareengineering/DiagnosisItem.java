package com.example.user.softwareengineering;

/**
 * Created by user on 2017-11-07.
 * ListViewActivity에서 ListView에 추가할 객체에 대한 정보
 */

public class DiagnosisItem {

    String name;        // 진단 or 병충해 이름
    String percent;     // 진단의 경우 정확도
    int resId;          // 진단의 사례 사진

    public DiagnosisItem(String name, String percent, int resId) {
        this.name = name;           //  생성자
        this.percent = percent;
        this.resId = resId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "DiagnosisItem{" + "name='" + name + '\'' + ", percent='" + percent + '\'' + '}';

    }   // 객체의 데이터를 스트링화
}
