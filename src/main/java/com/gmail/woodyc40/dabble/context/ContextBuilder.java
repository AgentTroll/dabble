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
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.*;

@NotThreadSafe
public class ContextBuilder {
    private static final Sentence NO_DEF = new Sentence("No definition found...");

    @Setter @Getter private String skip;
    @Getter private final int idx;
    private final String word;
    @Getter private final Sentence sentence;
    @Getter private final List<WordDefinition> accepted;

    @Getter private final List<WordDefinition> definitions =
            new ArrayList<>();

    @Getter private final Set<String> recursed = new HashSet<>();

    public static void recurse(Sentence sentence) {
        for (String w : sentence.getIndividualWords()) {
            if (Brain.getInstance().getMemory().isDefined(w))
                return;

            for (WordDefinition d : Brain.getInstance().define(w)) {
                recurse(d.getDefinition());
            }
        }
    }

    public static ContextBuilder forWord(int word, Sentence sentence, List<WordDefinition> accepted) {
        return new ContextBuilder(word, sentence, accepted);
    }

    private ContextBuilder(int word, Sentence sentence, List<WordDefinition> accepted) {
        this.idx = word;
        this.word = sentence.getIndividualWords().get(word);
        this.sentence = sentence;
        this.accepted = accepted;
    }

    public ContextBuilder defineWord() {
        this.definitions.addAll(Brain.getInstance().define(this.word));
        return this;
    }

    public ContextBuilder buildContext() {
        // Go high to low
        Map<Double, WordDefinition> defs = new TreeMap<>(Comparator.reverseOrder());
        for (int i = 0; i < this.definitions.size(); i++) {
            WordDefinition definition = this.definitions.get(i);
            ContextProcessor processor = new ContextProcessor(this);

            this.recursiveBuildContext(this.sentence, definition, processor);
            defs.put(processor.getRelevance(), definition);
        }

        this.definitions.clear();
        this.definitions.addAll(defs.values());

        return this;
    }

    public WordDefinition getDefinition() {
        if (this.definitions.isEmpty()) {
            return new WordDefinition(this.word, NO_DEF, PartOfSpeech.UNKNOWN, false);
        }

        return this.definitions.get(0);
    }

    private void recursiveBuildContext(Sentence sent, WordDefinition definition, ContextProcessor processor) {
        definition.indexWith(sent, processor);
        processor.step();

        Sentence def = definition.getDefinition();
        for (String w : def.getIndividualWords()) {
            for (WordDefinition d : Brain.getInstance().define(w)) {
                if (!this.recursed.add(d.getWord())) {
                    continue;
                }

                this.recursiveBuildContext(def, d, processor);
            }
        }
    }
}