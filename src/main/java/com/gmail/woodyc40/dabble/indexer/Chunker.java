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
package com.gmail.woodyc40.dabble.indexer;

import com.gmail.woodyc40.dabble.brain.Brain;
import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;

import javax.annotation.concurrent.Immutable;
import java.util.List;

@Immutable
public class Chunker implements RelevanceIndexer {
    @Override public double index(Sentence base, WordDefinition toIndex, ContextProcessor processor, List<WordDefinition> cache, List<WordDefinition> accepted) {
        if (toIndex.getPartOfSpeech() == PartOfSpeech.NOUN) {
            List<String> words = base.getIndividualWords();
            if (processor.getWordIdx() < words.size() - 1) {
                String chunk = words.get(processor.getWordIdx()) + ' ' +
                        words.get(processor.getWordIdx() + 1);
                if (!processor.getParent().getRecursed().add(chunk)) {
                    return 0;
                }

                List<WordDefinition> chunked = Brain.
                        getInstance().
                        define(chunk);

                if (chunked.isEmpty()) {
                    return 0;
                } else {
                    cache.addAll(chunked);
                    return -1;
                }
            }
        }

        return 0;
    }
}