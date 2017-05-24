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
package com.gmail.woodyc40.dabble.parsing;

import com.gmail.woodyc40.dabble.context.Context;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.woodyc40.dabble.util.UtilityMethods.strip;

public class Sentence implements Context<List<String>> {
    @Getter private final String input;
    @Getter private final List<String> individualWords = new ArrayList<>();

    public Sentence(String input) {
        this.input = input;

        String[] individualWords = input.split(" ");

        for (String individualWord : individualWords) {
            String s = individualWord.toLowerCase().trim();
            if (s.startsWith("-")) {
                continue;
            }

            s = strip(s);
            if (!s.isEmpty()) {
                this.individualWords.add(s);
            }
        }
    }

    @Override public List<String> value() {
        return this.individualWords;
    }

    @Override public void setValue(List<String> val) {
        this.individualWords.addAll(val);
    }
}