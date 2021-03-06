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
package com.gmail.woodyc40.dabble.dictionary;

import lombok.Getter;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Immutable
public enum PartOfSpeech {
    NOUN("n."),
    ADJECTIVE("adj."),
    ADVERB("adv."),
    PRONOUN("pron."),
    PREPOSITION("prep."),
    INTERJECTION("int."),
    VERB("v."),
    CONJUNCTION("conj."),

    // These are tenses...
    SYMBOL("symb."),
    REL("rel."),
    ABBREVIATION("abbr."),
    PAST("past"),
    VARIANT("var."),
    DEMONSTRATIVE("demons."),
    PLURAL("pl."),
    PREDICATE("predic."),
    INTERROGATIVE("interrog."),
    NOUN_PLURAL("n.pl."),
    AUXILLARY_VERB("v.aux."),
    ATTRIB("attrib."),
    POSSESSIVE("poss."), // most of these aren't a real part of speech, lmao
    COMB("comb."),
    LOOK_SOMEWHERE_ELSE("see"),
    SUPERLATIVE("superl."),
    SINGULAR("sing."),
    PRESENT("pres."),
    OBJECTIVE("objective"),
    COMPARATIVE("compar."),
    PREFIX("prefix"),
    SUFFIX("suffix"),
    ABBR("Abbr."),
    CONTRADICTION("contr."),
    UNKNOWN("");

    @Getter private static final Map<PartOfSpeech, List<PartOfSpeech>> paradigms =
            new HashMap<>();
    static {
        paradigms.put(NOUN, Arrays.asList(NOUN, ADJECTIVE, VERB));
        paradigms.put(ADJECTIVE, Arrays.asList(ADJECTIVE));
    }

    private final String dict;

    PartOfSpeech(String dict) {
        this.dict = dict;
    }
    @Override public String toString() {
        return this.dict;
    }
}