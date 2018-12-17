package com.abdelfattah.study.Announcements;

public class AnnouncementData {
    private String mTitle;
    private int mCourseCode;
    private int mAnnouncementNum;
    public AnnouncementData(String title ,int courseCode,int announcementNum)
    {
        mTitle = title;
        mCourseCode = courseCode;
        mAnnouncementNum = announcementNum;
    }
    public String GetTitle()
    {
        return  mTitle;
    }
    public int GetCourseCode()
    {
        return mCourseCode;
    }
    public int GetAnnouncementNum()
    {
        return  mAnnouncementNum;
    }
}

