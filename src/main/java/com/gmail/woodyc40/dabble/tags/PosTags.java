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
package com.gmail.woodyc40.dabble.tags;

import com.gmail.woodyc40.dabble.context.Context;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import lombok.Getter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;

@NotThreadSafe
public class PosTags implements Context<List<PartOfSpeech>> {
    @Getter private final List<PartOfSpeech> value = new ArrayList<>();

    public PosTags(int size) {
        for (int i = 0; i < size; i++) {
            this.value.add(PartOfSpeech.UNKNOWN);
        }
    }

    @Override public void setValue(List<PartOfSpeech> val) {
        this.value.clear();
        this.value.addAll(val);
    }
}