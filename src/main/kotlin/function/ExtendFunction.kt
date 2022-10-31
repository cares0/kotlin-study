package function

fun String.lastChar(): Char = this[this.length - 1]

fun main(args: Array<String>) {
    println("Test".lastChar()) // t
}