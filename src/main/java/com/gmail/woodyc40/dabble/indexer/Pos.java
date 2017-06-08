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

import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.PosTags;

import javax.annotation.concurrent.Immutable;
import java.util.List;

@Immutable
public class Pos implements RelevanceIndexer {
    @Override public double index(Sentence base, WordDefinition toIndex, ContextProcessor processor, List<WordDefinition> cache, List<WordDefinition> accepted) {
        if (base != processor.getBase()) {
            return 0;
        }

        PosTags posList = base.get(PosTags.class);

        if (!toIndex.isChangesPos()) {
            posList.getValue().set(processor.getWordIdx(), toIndex.getPartOfSpeech());
            return 0.5;
        }

        if (!accepted.isEmpty()) {
            for (int i = 0; i < accepted.size(); i++) {
                posList.getValue().set(i, accepted.get(i).getPartOfSpeech());
            }
        }

        if (processor.getWordIdx() > 0) {
            PartOfSpeech speech = posList.getValue().get(processor.getWordIdx() - 1);
            List<PartOfSpeech> combos = PartOfSpeech.getParadigms().get(toIndex.getPartOfSpeech());

            if (combos != null && combos.contains(speech)) {
                return 1;
            }
        }

        return 0;
    }
}