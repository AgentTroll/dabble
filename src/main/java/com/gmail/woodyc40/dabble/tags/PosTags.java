package com.gmail.woodyc40.dabble.tags;

import com.gmail.woodyc40.dabble.context.Context;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;
import lombok.Getter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;

@NotThreadSafe
public class PosTags implements Context<List<PartOfSpeech>> {
    @Getter private final List<PartOfSpeech> value = new ArrayList<>();

    public PosTags(int size) {
        for (int i = 0; i < size; i++) {
            this.value.add(PartOfSpeech.UNKNOWN);
        }
    }

    @Override public void setValue(List<PartOfSpeech> val) {
        this.value.clear();
        this.value.addAll(val);
    }
}