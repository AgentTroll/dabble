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

import com.gmail.woodyc40.dabble.brain.Brain;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.lexing.Sentence;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.*;

@NotThreadSafe
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextBuilder {
    private final String word;
    private final Sentence sentence;

    @Getter private final List<WordDefinition> definitions =
            new LinkedList<>();

    public static void recurse(Sentence sentence) {
        for (String w : sentence.getIndividualWords()) {
            if (Brain.getInstance().getMemory().isDefined(w))
                return;

            for (WordDefinition d : Brain.getInstance().define(w)) {
                recurse(d.getDefinition());
            }
        }
    }

    public static ContextBuilder forWord(String word, Sentence sentence) {
        return new ContextBuilder(word, sentence);
    }

    public ContextBuilder defineWord() {
        this.definitions.addAll(Brain.getInstance().define(this.word));
        return this;
    }

    public ContextBuilder buildContext() {
        Map<Double, WordDefinition> defs = new TreeMap<>();
        for (WordDefinition definition : this.definitions) {
            ContextProcessor processor = new ContextProcessor(this.sentence);

            this.recursiveBuildContext(definition, processor);
            defs.put(processor.getRelevance(), definition);
        }

        this.definitions.addAll(defs.values());

        return this;
    }

    private void recursiveBuildContext(WordDefinition definition, ContextProcessor processor) {
        definition.indexWith(processor);

        for (String w : definition.getDefinition().getIndividualWords()) {
            processor.step();
            for (WordDefinition d : Brain.getInstance().define(w)) {
                this.recursiveBuildContext(d, processor);
            }
        }
    }
}