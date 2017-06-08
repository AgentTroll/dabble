package com.gmail.woodyc40.dabble.tags;

import com.gmail.woodyc40.dabble.context.Context;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SettingTags implements Context<String> {
    @Getter @Setter private String value;

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