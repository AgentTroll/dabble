package com.gmail.woodyc40.dabble.indexer;

import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.PosTags;

import javax.annotation.concurrent.Immutable;
import java.util.List;

@Immutable
public class Pos implements RelevanceIndexer {
    @Override
    public double index(Sentence base, WordDefinition toIndex, ContextProcessor processor, List<WordDefinition> cache, List<WordDefinition> accepted) {
        if (base != processor.getBase()) {
            return 0;
        }

        PosTags posList = base.get(PosTags.class);

        if (!toIndex.isChangesPos()) {
            posList.value().set(processor.getWordIdx(), toIndex.getPartOfSpeech());
            return 0.5;
        }

        if (!accepted.isEmpty()) {
            for (int i = 0; i < accepted.size(); i++) {
                posList.value().set(i, accepted.get(i).getPartOfSpeech());
            }
        }

        if (processor.getWordIdx() > 0) {
            PartOfSpeech speech = posList.value().get(processor.getWordIdx() - 1);
            List<PartOfSpeech> combos = PartOfSpeech.getParadigms().get(toIndex.getPartOfSpeech());

            if (combos != null && combos.contains(speech)) {
                return 1;
            }
        }

        return 0;
    }
}