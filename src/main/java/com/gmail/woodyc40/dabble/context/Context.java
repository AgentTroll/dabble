package com.gmail.woodyc40.dabble.context;

public interface Context<T> {
    String name();

    T value();
}
