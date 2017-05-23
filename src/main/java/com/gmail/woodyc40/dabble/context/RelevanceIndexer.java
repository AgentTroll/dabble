package com.gmail.woodyc40.dabble.context;

import com.gmail.woodyc40.dabble.lexing.Sentence;

@FunctionalInterface
public interface RelevanceIndexer {
    double index(Sentence base, Sentence toIndex);
}