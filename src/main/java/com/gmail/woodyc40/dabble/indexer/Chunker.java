package com.gmail.woodyc40.dabble.indexer;

import com.gmail.woodyc40.dabble.brain.Brain;
import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;

import java.util.List;

public class Chunker implements RelevanceIndexer {
    @Override
    public double index(Sentence base, WordDefinition toIndex, ContextProcessor processor, List<WordDefinition> accepted) {
        if (toIndex.getPartOfSpeech() == PartOfSpeech.NOUN) {
            if (processor.getWordIdx() < base.getIndividualWords().size() - 1) {
                String chunk = toIndex.getWord() + " " +
                        base.getIndividualWords().get(processor.getWordIdx() + 1);
                List<WordDefinition> chunked = Brain.
                        getInstance().
                        define(chunk);

                if (chunked.isEmpty()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        }

        return 0;
    }
}
