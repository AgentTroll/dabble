package com.gmail.woodyc40.dabble.tags;

import com.gmail.woodyc40.dabble.context.Context;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;

@NotThreadSafe
public class PosTags implements Context<List<PartOfSpeech>> {
    private final List<PartOfSpeech> posList = new ArrayList<>();

    public PosTags(int size) {
        for (int i = 0; i < size; i++) {
            this.posList.add(PartOfSpeech.UNKNOWN);
        }
    }

    @Override
    public List<PartOfSpeech> value() {
        return this.posList;
    }

    @Override
    public void setValue(List<PartOfSpeech> val) {
        this.posList.clear();
        this.posList.addAll(val);
    }
}