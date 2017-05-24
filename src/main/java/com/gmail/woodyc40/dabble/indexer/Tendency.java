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

import com.gmail.woodyc40.dabble.context.RelevanceIndexer;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.TimesDefined;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class Tendency implements RelevanceIndexer {
    private int count;

    @Override
    public double index(Sentence base, WordDefinition toIndex) {
        this.count++;
        return toIndex.get(TimesDefined.class).value() / (3.0 * this.count);
    }
}