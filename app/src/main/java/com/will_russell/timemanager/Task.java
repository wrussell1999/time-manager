package com.will_russell.timemanager;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private String name;
    private Integer length;
    private boolean expanded;

    public static List<Task> tasksList = new ArrayList<>();

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

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
