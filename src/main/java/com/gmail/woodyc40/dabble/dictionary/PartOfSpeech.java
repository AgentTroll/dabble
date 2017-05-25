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
    WTF("rel."),
    ABBREVIATION("abbr."),
    PAST("past"),
    VARIANT("var."),
    DEMONSTRATIVE("demons."),
    PLURAL("pl."),
    PREDICATE("predic."),
    INTERROGATIVE("interrog."),
    NOUN_PLURAL("n.pl."),
    AUXILLARY_VERB("v.aux."),
    WTF_2("attrib."),
    POSSESSIVE("poss."), // most of these aren't a real part of speech, lmao
    WTF_3("comb."),
    LOOK_SOMEWHERE_ELSE("see"),
    SUPERLATIVE("superl."),
    SINGULAR("sing."),
    PRESENT("pres."),
    OBJECTIVE("objective"),
    COMPARATIVE("compar."),
    PREFIX("prefix"),
    SUFFIX("suffix"),
    ABBR_2("Abbr."),
    CONTRADICTION("contr.");

    private final String dict;

    PartOfSpeech(String dict) {
        this.dict = dict;
    }

    @Override public String toString() {
        return this.dict;
    }
}