package com.gmail.woodyc40.dabble.tags;

import com.gmail.woodyc40.dabble.context.Context;

public class UserInputted implements Context<Boolean> {
    private boolean userInputted;

    @Override
    public Boolean getValue() {
        return this.userInputted;
    }

    @Override
    public void setValue(Boolean val) {
        this.userInputted = val;
    }
}
