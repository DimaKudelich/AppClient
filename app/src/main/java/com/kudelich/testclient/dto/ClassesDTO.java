package com.kudelich.testclient.dto;

public class ClassesDTO {
    private long id;
    private String subject;
    private String classroom;
    private int dayOfWeek;
    private char type;
    private long groupId;
    private String startTime;
    private String endTime;

    public ClassesDTO(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public static String classesToString(ClassesDTO[]classesDTOS){
        String result = new String();

        for (int i = 0; i < classesDTOS.length; i++) {
            result+= classesDTOS[i].dayOfWeek + " "
                    + classesDTOS[i].getSubject() + " "
                    + classesDTOS[i].getType() + " "
                    + classesDTOS[i].getClassroom() + "\n";
        }

        return result;
    }

    public static String[][]classesToStringArray(ClassesDTO[]classesDTOS){
        String[][]result = new String[classesDTOS.length][6];
        String[]days = {"Пнд.","Втр.","Срд.","Чтв.","Птн.","Сбт.",};

        for (int i = 0; i < classesDTOS.length; i++) {
            result[i][0] = days[classesDTOS[i].getDayOfWeek()-1];
            result[i][1] = classesDTOS[i].getSubject();
            result[i][2] =Character.toString(classesDTOS[i].getType());
            result[i][3] =classesDTOS[i].getClassroom();
            result[i][4] = classesDTOS[i].getStartTime();
            result[i][5] = classesDTOS[i].getEndTime();
        }


        return result;
    }
}
