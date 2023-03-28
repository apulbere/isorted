package com.jg.apulbere.isorted;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class SortedIterator implements Iterator<Integer> {

    private final Iterator<Integer>[] iterators;
    private final Integer[] iteratorsHeadElements;

    public SortedIterator(Iterator<Integer>[] iterators) {
        this.iterators = iterators;
        this.iteratorsHeadElements = new Integer[this.iterators.length];
        populateHeaders();
    }

    @Override
    public boolean hasNext() {
        return stream(iteratorsHeadElements).anyMatch(Objects::nonNull);
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        var minPair = findMinPair().orElseThrow(NoSuchElementException::new);
        iteratorsHeadElements[minPair.index()] = null;
        populateHeaders();
        return minPair.value();
    }

    private void populateHeaders() {
        for (int i = 0; i < iterators.length; i++) {
            if (iteratorsHeadElements[i] == null && iterators[i].hasNext()) {
                iteratorsHeadElements[i] = iterators[i].next();
            }
        }
    }

    record MinPair(Integer value, int index) {}

    private Optional<MinPair> findMinPair() {
        MinPair minPair = null;
        for (int i = 0; i < iteratorsHeadElements.length; i++) {
            var currentValue = iteratorsHeadElements[i];
            if (currentValue == null) {
                continue;
            }
            if (minPair == null) {
                minPair = new MinPair(currentValue, i);
                continue;
            }
            if (currentValue < minPair.value()) {
                minPair = new MinPair(currentValue, i);
            }
        }
        return ofNullable(minPair);
    }
}
