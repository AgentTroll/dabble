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
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

@NotThreadSafe
@RequiredArgsConstructor
public class ContextBuilder {
    private final String word;

    private Sentence sentence;
    @Getter private List<WordDefinition> definitions;

    public static ContextBuilder forWord(String word) {
        return new ContextBuilder(word);
    }

    public static ContextBuilder forDefinition(WordDefinition def) {
        ContextBuilder builder = new ContextBuilder("");
        builder.sentence = new Sentence(def.getDefinition());

        return builder;
    }

    public ContextBuilder in(Sentence sentence) {
        this.sentence = sentence;
        return this;
    }

    public ContextBuilder recursiveDefine() {
        for (String w : this.sentence.getIndividualWords()) {
            if (w.equals(this.word)) {
                this.definitions = Brain.getInstance().define(w);
                continue;
            }
        }

        ContextProcessor processor = new ContextProcessor(this.sentence);
        for (WordDefinition definition : this.definitions) {
            definition.indexAgainst(processor);
        }

        return this;
    }
}