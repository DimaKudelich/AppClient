package com.kudelich.testclient.dto;

public class CoursesDTO {
    private long id;
    private long courseNumber;
    private long facultyId;

    public CoursesDTO(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(long courseNumber) {
        this.courseNumber = courseNumber;
    }

    public long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(long facultyId) {
        this.facultyId = facultyId;
    }

    public static String[]convertToStringArray(CoursesDTO[]coursesDTOS){
        String[]result = new String[coursesDTOS.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = Long.toString(coursesDTOS[i].getCourseNumber()) + " курс";
        }

        return result;
    }

    public static long[]getAllId(CoursesDTO[]coursesDTOS){
        long[]result = new long[coursesDTOS.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = coursesDTOS[i].getId();
        }

        return result;
    }
}
