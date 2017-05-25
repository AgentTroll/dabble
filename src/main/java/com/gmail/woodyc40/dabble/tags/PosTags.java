package com.gmail.woodyc40.dabble.tags;

import com.gmail.woodyc40.dabble.context.Context;
import com.gmail.woodyc40.dabble.dictionary.PartOfSpeech;

import java.util.ArrayList;
import java.util.List;

public class PosTags implements Context<List<PartOfSpeech>> {
    private final List<PartOfSpeech> posList = new ArrayList<>();

    @Override
    public List<PartOfSpeech> value() {
        return null;
    }

    @Override
    public void setValue(List<PartOfSpeech> val) {
    }
}