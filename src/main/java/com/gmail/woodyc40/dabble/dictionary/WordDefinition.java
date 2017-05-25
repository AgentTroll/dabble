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
package com.gmail.woodyc40.dabble.dictionary;

import com.gmail.woodyc40.dabble.context.Context;
import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.context.Contextual;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.TimesDefined;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.HashMap;
import java.util.Map;

@NotThreadSafe
@EqualsAndHashCode(of = "word")
@AllArgsConstructor
public class WordDefinition implements Contextual {
    @Getter private final String word;
    @Getter private final Sentence definition;
    @Getter private final PartOfSpeech partOfSpeech;
    @Getter private final boolean changesPos;
    private final Map<Class<? extends Context<?>>, Context<?>> contexts = new HashMap<Class<? extends Context<?>>, Context<?>>() {{
        this.put(TimesDefined.class, new TimesDefined());
    }};

    public void indexWith(ContextProcessor proc) {
        proc.process(this);
    }

    @Override
    public <T, R extends Context<T>> R get(Class<? extends Context<T>> cls)  {
        return (R) this.contexts.get(cls);
    }
}