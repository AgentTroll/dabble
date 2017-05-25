package com.gmail.woodyc40.dabble.indexer;

import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.PosTags;

import java.util.List;

public class Pos implements RelevanceIndexer {
    @Override
    public double index(Sentence base, WordDefinition toIndex, List<WordDefinition> accepted) {
        String word = toIndex.getWord();
        PosTags posList = base.get(PosTags.class);
        for (int i = 0; i < base.getIndividualWords().size(); i++) {
            String w0 = base.getIndividualWords().get(i);

        }

        return 0;
    }
}