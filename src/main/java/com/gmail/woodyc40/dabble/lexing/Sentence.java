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

public class Sentence {
    @Getter
    private final String input;
    @Getter
    private final String[] individualWords;

    public Sentence(String input) {
        this.input = input;
        this.individualWords = input.split(" ");

        for (int i = 0; i < this.individualWords.length; i++) {
            this.individualWords[i] = this.individualWords[i].trim();
        }
    }
}