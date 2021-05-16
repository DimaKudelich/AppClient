package com.kudelich.testclient.dto;

public class FacultiesDTO{
    private long id;
    private String address;
    private String name;

    public FacultiesDTO(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static String[]convertToStringArray(FacultiesDTO[]facultiesDTOS){
        String[]result = new String[facultiesDTOS.length];

        for (int i = 0; i < facultiesDTOS.length; i++) {
            result[i] = facultiesDTOS[i].getName();
        }

        return result;
    }

    public static long[]getAllId(FacultiesDTO[]facultiesDTOS){
        long[]result = new long[facultiesDTOS.length];

        for (int i = 0; i < result.length ; i++) {
            result[i] = facultiesDTOS[i].getId();
        }

        return result;
    }
}
