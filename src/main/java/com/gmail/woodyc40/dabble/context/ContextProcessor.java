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
import com.gmail.woodyc40.dabble.indexer.Depth;
import com.gmail.woodyc40.dabble.indexer.Repetition;
import com.gmail.woodyc40.dabble.indexer.Tendency;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import lombok.Getter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;

@NotThreadSafe
public class ContextProcessor {
    @Getter private static final Class<?>[] INDEXERS = {
            Depth.class,
            Repetition.class,
            Tendency.class };

    @Getter private double relevance;

    private final Sentence base;
    private final List<RelevanceIndexer> indexers = new ArrayList<>(INDEXERS.length);

    public ContextProcessor(Sentence sentence) {
        this.base = sentence;

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

    public void process(WordDefinition toIndex) {
        for (RelevanceIndexer indexer : this.indexers) {
            this.relevance += indexer.index(this.base, toIndex);
        }
    }
}