package com.khajana.setting.entity.procedure;

public class HouseKeeping {

    private Long id;
    private String type;
    private String name;

    private String namebn;

    private String paramExtra1;
    private String paramExtra2;

    public String getNamebn() {
        return namebn;
    }

    public void setNamebn(String namebn) {
        this.namebn = namebn;
    }
// Getters and setters for id, type, and name

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
