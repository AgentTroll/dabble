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
        int count = 1;
        int reps = 1;
        for (Context<?> tag : Environment.getInstance().getContexts().values()) {
            Context<String> st = (SettingTags) tag;
            if (st.getValue().toLowerCase().contains(toIndex.getWord().toLowerCase())) {
                count += 5;
            }

            for (String word : toIndex.getDefinition().getIndividualWords()) {
                reps++;
                if (word.equalsIgnoreCase(st.getValue())) {
                    count++;
                }
            }
        }
        return count / reps;
    }
}
