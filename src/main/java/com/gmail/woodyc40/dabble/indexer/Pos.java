package com.gmail.woodyc40.dabble.indexer;

import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.PosTags;

import javax.annotation.concurrent.Immutable;
import java.util.List;

@Immutable
public class Pos implements RelevanceIndexer {
    @Override
    public double index(Sentence base, WordDefinition toIndex, ContextProcessor processor, List<WordDefinition> accepted) {
        String word = toIndex.getWord();

        if (toIndex.isChangesPos()) {
            return 0;
        }

        PosTags posList = base.get(PosTags.class);

        if (posList.value().isEmpty()) {
            posList.value().add(toIndex.getPartOfSpeech());
            return 0;
        }

        for (int i = 0; i < base.getIndividualWords().size(); i++) {
            String w0 = base.getIndividualWords().get(i);

            // TODO
        }

        return 0;
    }
}