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
import com.gmail.woodyc40.dabble.lexing.Sentence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public Map<String, List<WordDefinition>> parse(Sentence sentence) {
        Map<String, List<WordDefinition>> definitions =
                new HashMap<>();
        for (String word : sentence.getIndividualWords()) {
            List<WordDefinition> defs =
                    ContextBuilder.
                            forWord(word).
                            in(sentence).
                            recursiveDefine().
                            getDefinitions();
            definitions.put(word, defs);
        }

        return definitions;
    }
}