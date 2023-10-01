package com.example.foodplanner.data.models;


public class Tag<T> {
    String title;
    T ResourcesData;

    public String getTitle() {
        return title;
    }

    public T getResourcesData() {
        return ResourcesData;
    }

    public Tag(String title, T resourcesData) {
        this.title = title;
        ResourcesData = resourcesData;
    }
}
