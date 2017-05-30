package com.gmail.woodyc40.dabble.indexer;

import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;

import java.util.List;

public class Chunker implements RelevanceIndexer {
    @Override
    public double index(Sentence base, WordDefinition toIndex, List<WordDefinition> accepted, ContextProcessor processor) {
        if (toIndex.getPartOfSpeech() == PartOfSpeech.NOUN) {
        }

        return 0; // TODO
    }
}
