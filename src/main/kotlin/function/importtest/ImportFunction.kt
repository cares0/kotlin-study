package function.importtest

import function.joinToString
import function.lastChar

fun main(args: Array<String>) {
    val arrayList = arrayListOf("a", "b", "c", "d")
    joinToString(arrayList)

    val postArray = arrayOf(5, 6, 7, 8)
    val list = listOf(postArray)

    for (i in list){
        println(i)
    }

    "test".lastChar();
}
