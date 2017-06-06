package com.gmail.woodyc40.dabble.tags;

import com.gmail.woodyc40.dabble.context.Context;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SettingTags implements Context<String> {
    private String value;

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public void setValue(String val) {
        this.value = val;
    }

    public static class Classroom extends SettingTags {
        public Classroom() {
            super("Classroom");
        }
    }

    public static class Course extends SettingTags {
        public Course(String value) {
            super(value);
        }
    }
}