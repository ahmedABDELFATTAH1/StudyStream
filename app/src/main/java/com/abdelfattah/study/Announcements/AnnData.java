package com.abdelfattah.study.Announcements;

public class AnnData {
    private String mTitle;
    private int mCourseCode;
    private int mAnnouncementNum;
    private  String mContent;
    public AnnData(String title ,String content,int courseCode,int announcementNum)
    {
        mTitle = title;
        mCourseCode = courseCode;
        mAnnouncementNum = announcementNum;
        mContent = content;

    }
    public String GetTitle()
    {
        return  mTitle;
    }
    public String GetContent() { return mContent;}
    public int GetCourseCode()
    {
        return mCourseCode;
    }
    public int GetAnnouncementNum()
    {
        return  mAnnouncementNum;
    }
}

