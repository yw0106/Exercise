package com.example.exercise.model;

import androidx.lifecycle.ViewModel;

public final class Item extends ViewModel implements Comparable<Item>{

    private final int listId;
    private final int id;
    private final String name;

    public Item(int id, int listId, String name) {
        this.id = id;
        this.name = name;
        this.listId = listId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getListId() {
        return listId;
    }

    @Override
    public int compareTo(Item o) {
        return name.compareTo(o.name);
    }

}