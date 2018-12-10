package com.example.fahri.firstdb.ListView;

public class Words {

    private String mDay,mHomework, mLecture, mDeadline;
    private int mId;

    public Words (int id, String day, String homework, String lecture, String deadline ){

        this.mId = id;
        this.mDay = day;
        this.mHomework = homework;
        this.mLecture = lecture;
        this.mDeadline = deadline;
    }

    public int getId() {
        return mId;
    }
    public String getDay(){
        return mDay;
    }
    public String getHomework(){
        return mHomework;
    }
    public String getLecture(){
        return mLecture;
    }
    public String getDeadline(){
        return mDeadline;
    }

    private void setId(int id){
        this.mId = id;
    }

    public void setDay (String day){
        this.mDay = day;
    }

    public void setHomework (String homework){
        this.mHomework = homework;
    }

    public void setLecture (String lecture){
        this.mLecture = lecture;
    }

    public void setDeadline (String deadline){
        this.mDeadline = deadline;
    }

}
