package com.abdelfattah.study.TopStudents;

public class TopStudents_Data {
    private String mStudentID;
    private int mRate;

    public TopStudents_Data(String mStudentID, int mRate) {
        this.mStudentID = mStudentID;
        this.mRate = mRate;
    }

    public String getmStudentID() {
        return mStudentID;
    }
    public int getmRate() {
        return mRate;
    }

}
