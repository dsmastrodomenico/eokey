package com.dsmastrodomenico.eokey;

public class Category {
    private String IDCategory;
    private String Name;
    private String Description;

    public Category(String IDCategory, String name, String description) {
        this.IDCategory = IDCategory;
        Name = name;
        Description = description;
    }

    public Category() {
    }

    public String getIDCategory() {
        return IDCategory;
    }

    public void setIDCategory(String IDCategory) {
        this.IDCategory = IDCategory;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
