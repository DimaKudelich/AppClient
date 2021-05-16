package com.kudelich.testclient.dto;

public class ClassesDTO {
    private long id;
    private String subject;
    private String classroom;
    private int dayOfWeek;
    private char type;
    private long groupId;

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
        String[][]result = new String[classesDTOS.length][4];
        String[]days = {"Пнд.","Втр.","Срд.","Чтв.","Птн.","Сбт.",};

        for (int i = 0; i < classesDTOS.length; i++) {
            result[i][0] = days[classesDTOS[i].getDayOfWeek()-1];
            result[i][1] = classesDTOS[i].getSubject();
            result[i][2] =Character.toString(classesDTOS[i].getType());
            result[i][3] =classesDTOS[i].getClassroom();
        }


        return result;
    }
}
