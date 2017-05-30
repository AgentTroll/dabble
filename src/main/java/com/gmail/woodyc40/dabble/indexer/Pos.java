package com.gmail.woodyc40.dabble.indexer;

import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.PosTags;

import java.util.ArrayList;
import java.util.List;

public class Pos implements RelevanceIndexer {
    @Override
    public double index(Sentence base, WordDefinition toIndex, List<WordDefinition> accepted, ContextProcessor processor) {
        String word = toIndex.getWord();
        PosTags posList = base.get(PosTags.class);
        if (posList == null) {
            posList = new PosTags();
            posList.setValue(new ArrayList<>(base.getIndividualWords().size()));
        }

        if (posList.value().isEmpty()) {
            posList.value().add(toIndex.getPartOfSpeech());
            return 0;
        }

        for (int i = 0; i < base.getIndividualWords().size(); i++) {
            String w0 = base.getIndividualWords().get(i);
            PartOfSpeech speech = posList.value().get(i);

            // TODO
        }

        return 0;
    }
}