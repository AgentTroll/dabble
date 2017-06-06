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

import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.indexer.*;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.UserInputted;
import lombok.Getter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;

@NotThreadSafe
public class ContextProcessor {
    @Getter private static final Class<?>[] INDEXERS = {
            Chunker.class,
            Depth.class,
            Repetition.class,
            Tendency.class,
            Pos.class,
            EnvironmentIndexer.class };

    @Getter private double relevance;

    @Getter private final int wordIdx;
    @Getter private final Sentence base;
    @Getter private final List<WordDefinition> cache = new ArrayList<>();

    @Getter private final ContextBuilder parent;
    private final List<WordDefinition> accepted;
    private final List<RelevanceIndexer> indexers = new ArrayList<>(INDEXERS.length);

    public ContextProcessor(ContextBuilder builder) {
        this.base = builder.getSentence();
        this.accepted = builder.getAccepted();
        this.wordIdx = builder.getIdx();
        this.parent = builder;

        for (Class<?> c : INDEXERS) {
            try {
                this.indexers.add((RelevanceIndexer) c.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void step() {
        for (RelevanceIndexer indexer : this.indexers) {
            indexer.step();
        }
    }

    public void process(Sentence sentence, WordDefinition toIndex) {
        for (RelevanceIndexer indexer : this.indexers) {
            double index = indexer.index(sentence, toIndex, this, this.cache, this.accepted);
            if (index < 0 && sentence.get(UserInputted.class).value()) {
                this.parent.getDefinitions().addAll(this.cache);
                this.parent.setSkip(this.cache.get(0).getWord());
                this.cache.clear();

                index = -index;
            }

            this.relevance += index;
        }
    }
}