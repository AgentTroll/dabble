/*
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
package com.gmail.woodyc40.dabble.brain;

import com.gmail.woodyc40.dabble.dictionary.OxfordDictionary;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import lombok.Getter;

import java.util.List;

public final class Brain {
    @Getter private static final Brain instance = new Brain();

    @Getter private final Memory memory;

    private Brain() {
        this.memory = new Memory();
    }

    public List<WordDefinition> define(String word) {
        return this.memory.define(word, OxfordDictionary::lookup);
    }
}