package com.abdelfattah.study.Materials;

public class MaterialData {
    private String mTitle;
    private String mContent;
    private int mCourseCode;
    private int mMaterialNum;
    public MaterialData(String title,String content ,int courseCode,int materialNum)
    {
        mTitle = title;
        mContent = content;
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
    public String GetContent(){return mContent;}




}
