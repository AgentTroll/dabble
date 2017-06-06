/*
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
package com.gmail.woodyc40.dabble.parsing;

import com.gmail.woodyc40.dabble.context.Context;
import com.gmail.woodyc40.dabble.context.Contextual;
import com.gmail.woodyc40.dabble.tags.PosTags;
import com.gmail.woodyc40.dabble.tags.UserInputted;
import lombok.Getter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gmail.woodyc40.dabble.util.UtilityMethods.strip;

@NotThreadSafe
public class Sentence implements Contextual {
    @Getter private final String input;
    @Getter private final List<String> individualWords = new ArrayList<>();
    @Getter private final Map<Class<? extends Context<?>>, Context<?>> contexts =
            new HashMap<>();

    public Sentence(String input) {
        this.input = input;

        String[] individualWords = input.split(" ");

        for (String individualWord : individualWords) {
            String s = individualWord.toLowerCase().trim();
            if (s.startsWith("-")) {
                continue;
            }

            s = strip(s);
            if (!s.isEmpty()) {
                this.individualWords.add(s);
            }
        }

        this.contexts.put(PosTags.class, new PosTags(this.individualWords.size()));
        this.contexts.put(UserInputted.class, new UserInputted());
    }

    @Override
    public <T, R extends Context<T>> R get(Class<? extends Context<T>> cls) {
        return (R) this.contexts.get(cls);
    }
}