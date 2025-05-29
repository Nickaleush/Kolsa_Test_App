package ru.kolsa.core.ui.extensions

inline fun <reified R> Iterable<*>.containsInstance(): Boolean {
    for (element in this) {
        if (element is R) return true
    }
    return false
}

inline fun <reified R> Iterable<*>.firstInstanceOrNull(): R? {
    for (element in this) {
        if (element is R) return element
    }
    return null
}

inline fun <reified R> Iterable<*>.firstInstanceIndexedOrNull(): IndexedValue<R>? {
    for (element in this.withIndex()) {
        val value = element.value
        if (value is R) {
            return IndexedValue(element.index, value)
        }
    }
    return null
}

inline fun <reified R> Iterable<*>.firstInstance(): R {
    return firstInstanceOrNull<R>()
        ?: throw NoSuchElementException("Element of provided type was not found in the collection")
}

inline fun <reified R> Iterable<*>.firstInstanceIndexed(): IndexedValue<R> {
    return firstInstanceIndexedOrNull<R>()
        ?: throw NoSuchElementException("Element of provided type was not found in the collection")
}

inline fun <reified T, reified R> Iterable<T>.flatMapNotNull(transform: (T) -> Iterable<R>?): Iterable<R> {
    return flatMapNotNullTo(ArrayList(), transform)
}

inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapNotNullTo(
    destination: C,
    transform: (T) -> Iterable<R>?
): C {
    for (element in this) {
        val list = transform(element)
        if (list != null) {
            destination.addAll(list)
        }
    }
    return destination
}

inline fun <reified T> Iterable<T>.findIndexed(predicate: (Int, T) -> Boolean): T? {
    var index = 0
    return find { element ->
        val result = predicate(index, element)
        index++
        result
    }
}

fun <T> Iterable<T>.notContain(element: T): Boolean = !contains(element)
