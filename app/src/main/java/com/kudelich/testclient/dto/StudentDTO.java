package com.kudelich.testclient.dto;

import androidx.annotation.NonNull;

public class StudentDTO {
    private long id;
    private String name;
    private long groupId;
    private String login;
    private String password;

    public StudentDTO(){

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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return new StringBuilder().append("{id: ").append(Long.toString(id)).append(";name: ").append(name).append("}").toString();
    }
}
