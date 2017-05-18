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
package com.gmail.woodyc40.dabble.lexing;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
    @Getter
    private final String input;
    @Getter
    private final List<String> individualWords = new ArrayList<>();

    public Sentence(String input) {
        this.input = input;
        String[] individualWords = input.split(" ");

        for (int i = 0; i < individualWords.length; i++) {
            String s = individualWords[i].toLowerCase().trim();
            if (s.startsWith("-")) {
                continue;
            }

            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt(j);
                if (c >= 'a' && c <= 'z') {
                    builder.append(c);
                }
            }

            s = builder.toString().trim();
            if (!s.isEmpty()) {
                this.individualWords.add(s);
            }
        }
    }
}