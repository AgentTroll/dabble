/**
 * Dabble contextual dictionary - Copyright 2017 Johnny Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        return (R) this.contexts.get(cls);
    }
}