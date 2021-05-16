package com.kudelich.testclient.dto;

public class GroupsDTO {
    private long id;
    private long courseId;
    private String curator;
    private int groupNumber;

    public GroupsDTO(){

    }

    public long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getCurator() {
        return curator;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public static String[]convertToStringArray(GroupsDTO[]groupsDTOS){
        String[]result = new String[groupsDTOS.length];

        for (int i = 0; i < groupsDTOS.length; i++) {
            result[i] = Integer.toString(groupsDTOS[i].getGroupNumber()) + " группа";
        }

        return result;
    }

    public static long[]getAllId(GroupsDTO[]groupsDTOS){
        long[]result = new long[groupsDTOS.length];

        for (int i = 0; i < result.length ; i++) {
            result[i] = groupsDTOS[i].getId();
        }

        return result;
    }
}
