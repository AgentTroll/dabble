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
package com.gmail.woodyc40.dabble.training;

import com.gmail.woodyc40.dabble.parsing.Parser;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.concurrent.Immutable;

import static com.gmail.woodyc40.dabble.util.UtilityMethods.p;
import static com.gmail.woodyc40.dabble.util.UtilityMethods.pl;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Immutable
public final class Trainer {
    public static void init() {
        p("Building definition cache (this might take a while)... ");
        Parser.parse(new Sentence("large fly come in to eat food like apple " +
                "and banana which will help them live through tough time " +
                "by having extra energy in future"));
        pl("Done.");
    }
}