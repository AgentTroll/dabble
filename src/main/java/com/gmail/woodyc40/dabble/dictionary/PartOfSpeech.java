package com.gmail.woodyc40.dabble.dictionary;

public enum PartOfSpeech {
    NOUN("n"),
    ADJECTIVE("adj"),
    ADVERB("adv"),
    PRONOUN("pron"),
    PREPOSITION("prep"),
    INTERJECTION("int"),
    VERB("v"),
    CONJUNCTION("conj");

    private final String dict;

    PartOfSpeech(String dict) {
        this.dict = dict;
    }

    @Override public String toString() {
        return this.dict;
    }
}