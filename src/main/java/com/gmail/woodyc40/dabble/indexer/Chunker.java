package com.gmail.woodyc40.dabble.indexer;

import com.gmail.woodyc40.dabble.brain.Brain;
import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.util.UtilityMethods;

import java.util.List;

public class Chunker implements RelevanceIndexer {
    @Override
    public double index(Sentence base, WordDefinition toIndex, ContextProcessor processor, List<WordDefinition> cache, List<WordDefinition> accepted) {
        if (toIndex.getPartOfSpeech() == PartOfSpeech.NOUN) {
            List<String> words = base.getIndividualWords();
            if (processor.getWordIdx() < words.size() - 1) {
                String chunk = words.get(processor.getWordIdx()) + ' ' +
                        words.get(processor.getWordIdx() + 1);
                UtilityMethods.pl(chunk);
                List<WordDefinition> chunked = Brain.
                        getInstance().
                        define(chunk);

                if (chunked.isEmpty()) {
                    return 0;
                } else {
                    cache.addAll(chunked);
                    return -1;
                }
            }
        }

        return 0;
    }
}
