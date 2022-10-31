package function

import java.util.Collections

fun main(args: Array<String>) {
    val collection = arrayListOf(1, 2, 3)

    val map = mapOf(1 to "one", 7 to "seven",)

    println(joinToString(collection)) // [1, 2, 3]
    println(joinToString(collection, " - ")) // [1 - 2 - 3]
    println(joinToString(collection, prefix = "{", postfix = "}")) // {1, 2, 3}
    println(joinToString(collection, "{", "}")) // }1{2{3]
}

fun<T> joinToString(
    collection: Collection<T>,
    separator: String = ", ",
    prefix: String = "[",
    postfix: String = "]"
): String {
    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}

fun<T> printCollection(collection: Collection<T>) {

}