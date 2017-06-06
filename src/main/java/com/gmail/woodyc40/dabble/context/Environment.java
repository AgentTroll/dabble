package com.gmail.woodyc40.dabble.context;

import com.gmail.woodyc40.dabble.tags.SettingTags;
import lombok.Getter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.HashMap;
import java.util.Map;

@NotThreadSafe
public class Environment implements Contextual {
    @Getter private static final Environment instance = new Environment();

    @Getter private final Map<Class<? extends Context<?>>, Context<?>> contexts =
            new HashMap<Class<? extends Context<?>>, Context<?>>() {{
                this.put(SettingTags.Classroom.class, new SettingTags.Classroom());
                this.put(SettingTags.Course.class, new SettingTags.Course("AP Computer Science"));
            }};

    @Override public <T, R extends Context<T>> R get(Class<? extends Context<T>> cls) {
        return (R) contexts.get(cls);
    }
}