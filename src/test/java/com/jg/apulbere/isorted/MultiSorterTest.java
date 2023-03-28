package com.jg.apulbere.isorted;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class MultiSorterTest {

    IteratorMultiSorter iteratorMultiSorter = new IteratorMultiSorter();

    @Test
    void shouldSortMultipleIterators() {
        var a1 = List.of(6, 8, 19, 21, 32, 66, 77, 89).iterator();
        var a2 = List.of(1, 3, 5, 24, 33, 45, 57, 59, 89).iterator();
        var a3 = List.of(2, 4, 9, 18, 22, 44, 46, 89, 89).iterator();

        var expectedResult = List.of(1, 2, 3, 4, 5, 6, 8, 9, 18, 19, 21, 22, 24,
            32, 33, 44, 45, 46, 57, 59, 66, 77, 89, 89, 89, 89);

        var sortedIteratorResult = iteratorMultiSorter.sort(a1, a2, a3);

        var result = new LinkedList<>();
        sortedIteratorResult.forEachRemaining(result::add);

        assertEquals(expectedResult, result);
    }

    @Test
    void shouldFailWhenNoElements() {
        var a1 = List.of(42).iterator();

        var sortedIterator = iteratorMultiSorter.sort(a1);
        sortedIterator.next();

        assertThrows(NoSuchElementException.class, sortedIterator::next);
    }

    @Test
    void shouldFindNoElementWhenEmpty() {
        var a1 = List.<Integer>of().iterator();
        var sortedIterator = iteratorMultiSorter.sort(a1);

        assertFalse(sortedIterator.hasNext());
    }

    @Test
    void shouldSortDifferentSizeIterators() {
        var a1 = List.of(4, 5).iterator();
        var a2 = List.of(3).iterator();

        var expectedResult = List.of(3, 4, 5);

        var sortedIteratorResult = iteratorMultiSorter.sort(a1, a2);

        var result = new LinkedList<>();
        sortedIteratorResult.forEachRemaining(result::add);

        assertEquals(expectedResult, result);
    }
}