package com.will_russell.timemanager;

import java.util.ArrayList;

public class Task {

    private String name;
    private Integer length;

    public static ArrayList<Task> tasksList = new ArrayList<>();

    public Task(String name, Integer length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public static Integer getTotalLength() {
        Integer totalLength = 0;
        for (int i = 0; i < tasksList.size(); i++) {
            totalLength += tasksList.get(i).length;
        }
        return totalLength;
    }
}
