package com.jg.apulbere.isorted;

import java.util.Iterator;

public class IteratorMultiSorter {

    public Iterator<Integer> sort(Iterator<Integer>... iterators) {
        return new SortedIterator(iterators);
    }
}
