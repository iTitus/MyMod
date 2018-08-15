package io.github.ititus.mymod.util;

public interface ICyclable<T> {

    T next(int i);

    T prev(int i);

    default T next() {
        return next(1);
    }

    default T prev() {
        return prev(1);
    }

}
