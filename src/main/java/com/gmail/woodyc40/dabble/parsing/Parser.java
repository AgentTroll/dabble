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
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Immutable
public final class Parser {
    public static List<WordDefinition> parse(Sentence sentence) {
        ContextBuilder.recurse(sentence);

        List<WordDefinition> accepted = new ArrayList<>();
        List<String> words = sentence.getIndividualWords();
        for (int i = 0, size = words.size(); i < size; i++) {
            ContextBuilder cb = ContextBuilder.
                    forWord(i, sentence, accepted).
                    defineWord().
                    buildContext();

            WordDefinition definition = cb.getDefinition();
            if (definition.getWord().equals(cb.getSkip())) {
                i += cb.getSkip().split(" ").length - 1;
            }

            accepted.add(definition);
        }

        return accepted;
    }
}