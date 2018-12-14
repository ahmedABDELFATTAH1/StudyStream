package com.abdelfattah.study.Materials;

public class MaterialData {
    private String mTitle;
    private int mCourseCode;
    private int mMaterialNum;
    public MaterialData(String title ,int courseCode,int materialNum)
    {
        mTitle = title;
        mCourseCode = courseCode;
        mMaterialNum = materialNum;
    }

    public int GetCourseCode()
    {
        return mCourseCode;
    }
    public int GetMaterialNum()

    {
        return  mMaterialNum;
    }
    public String GetTitle()
    {
        return mTitle;
    }


}
