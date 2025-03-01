package com.app.school.model;

import lombok.Data;

@Data
public class Holiday extends BaseEntity {

    private String day;
    private String reason;
    private Type type;

    public enum Type {
        FESTIVAL, FEDERAL
    }

//    public Holiday(String day, String reason, Type type) {
//        this.day = day;
//        this.reason = reason;
//        this.type = type;
//    }

    public Type getType() {
        return type;
    }
}