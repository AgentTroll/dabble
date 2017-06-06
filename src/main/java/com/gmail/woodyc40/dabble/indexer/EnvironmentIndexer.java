package com.gmail.woodyc40.dabble.indexer;

import com.gmail.woodyc40.dabble.context.Context;
import com.gmail.woodyc40.dabble.context.ContextProcessor;
import com.gmail.woodyc40.dabble.context.Environment;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.SettingTags;

import javax.annotation.concurrent.Immutable;
import java.util.List;

@Immutable
public class EnvironmentIndexer implements RelevanceIndexer {
    @Override
    public double index(Sentence base, WordDefinition toIndex, ContextProcessor processor, List<WordDefinition> cache, List<WordDefinition> accepted) {
        for (Context<?> tag : Environment.getInstance().getContexts().values()) {
            Context<String> st = (SettingTags) tag;
            if (st.value().toLowerCase().contains(toIndex.getWord().toLowerCase())) {
                return 500;
            }

            for (String word : toIndex.getDefinition().getIndividualWords()) {
                if (word.equalsIgnoreCase(st.value())) {
                    return 2;
                }
            }
        }
        return 0;
    }
}
