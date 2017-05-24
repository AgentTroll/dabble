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
package com.gmail.woodyc40.dabble.parsing;

import com.gmail.woodyc40.dabble.context.ContextBuilder;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.List;

@Immutable
public class Parser {
    private static final Sentence NO_DEF = new Sentence("No definition found...");

    public List<WordDefinition> parse(Sentence sentence) {
        ContextBuilder.recurse(sentence);

        List<WordDefinition> definitions = new ArrayList<>();
        for (String word : sentence.getIndividualWords()) {
            List<WordDefinition> defs = ContextBuilder.
                    forWord(word, sentence).
                    defineWord().
                    buildContext().
                    getDefinitions();

            if (defs.isEmpty()) {
                definitions.add(new WordDefinition(word, NO_DEF, PartOfSpeech.LOOK_SOMEWHERE_ELSE));
            } else {
                definitions.add(defs.get(0));
            }
        }

        return definitions;
    }
}