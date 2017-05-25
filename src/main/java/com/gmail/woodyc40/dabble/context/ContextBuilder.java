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
import com.gmail.woodyc40.dabble.parsing.Sentence;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.*;

@NotThreadSafe
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextBuilder {
    private static final Sentence NO_DEF = new Sentence("No definition found...");

    private final String word;
    private final Sentence sentence;
    private final List<WordDefinition> accepted;

    private final List<WordDefinition> definitions =
            new LinkedList<>();

    private final Set<WordDefinition> recursed = new HashSet<>();

    public static void recurse(Sentence sentence) {
        for (String w : sentence.getIndividualWords()) {
            if (Brain.getInstance().getMemory().isDefined(w))
                return;

            for (WordDefinition d : Brain.getInstance().define(w)) {
                recurse(d.getDefinition());
            }
        }
    }

    public static ContextBuilder forWord(String word, Sentence sentence, List<WordDefinition> accepted) {
        return new ContextBuilder(word, sentence, accepted);
    }

    public ContextBuilder defineWord() {
        this.definitions.addAll(Brain.getInstance().define(this.word));
        return this;
    }

    public ContextBuilder buildContext() {
        // Go high to lown
            Map<Double, WordDefinition> defs = new TreeMap<>(Comparator.reverseOrder());
            for (WordDefinition definition : this.definitions) {
                ContextProcessor processor = new ContextProcessor(this.sentence, this.accepted);

            this.recursiveBuildContext(definition, processor);
            defs.put(processor.getRelevance(), definition);
        }

        this.definitions.clear();
        this.definitions.addAll(defs.values());

        return this;
    }

    public WordDefinition getDefinition() {
        WordDefinition def = this.definitions.get(0);
        if (def == null) {
            return new WordDefinition(this.word, NO_DEF, null, false);
        }

        return def;
    }

    private void recursiveBuildContext(WordDefinition definition, ContextProcessor processor) {
        definition.indexWith(processor);

        for (String w : definition.getDefinition().getIndividualWords()) {
            processor.step();
            for (WordDefinition d : Brain.getInstance().define(w)) {
                if (this.recursed.contains(d)) {
                    continue;
                }

                this.recursed.add(d);
                this.recursiveBuildContext(d, processor);
            }
        }
    }
}