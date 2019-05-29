package com.dsmastrodomenico.eokey;

public class Message {
    private String IDMessage;
    private String Name;
    private String Description;

    public Message() {
    }

    public Message(String IDMessage, String name, String description) {
        this.IDMessage = IDMessage;
        Name = name;
        Description = description;
    }

    public String getIDMessage() {
        return IDMessage;
    }

    public void setIDMessage(String IDMessage) {
        this.IDMessage = IDMessage;
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
