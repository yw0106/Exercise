package com.example.exercise.model;

import java.util.List;

public class GroupedItem implements Comparable<GroupedItem> {

    private final int id; // list id
    private final List<Item> items;
    private boolean isExpanded;

    public GroupedItem(int id, List<Item> items) {
        this.id = id;
        this.items = items;
        this.isExpanded = false;
    }

    public int getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    @Override
    public int compareTo(GroupedItem o) {
        return id - o.id;
    }

}
