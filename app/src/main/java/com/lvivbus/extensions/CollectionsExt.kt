package com.lvivbus.extensions

import java.util.*

inline fun <T, reified R> Iterable<T>.mapSafe(transform: (T) -> R): List<R> {
    return mapSafe(ArrayList<R>(), transform)
}

inline fun <T, reified R, C : MutableCollection<in R>> Iterable<T>.mapSafe(destination: C, transform: (T) -> R): C {
    for (item in this)
        if (item is R) destination.add(transform(item))
    return destination
}