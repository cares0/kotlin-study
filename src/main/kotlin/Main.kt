import java.io.BufferedReader
import java.io.IOException
import java.io.Reader
import java.io.StringReader
import java.util.IllegalFormatCodePointException
import java.util.TreeMap

fun main(args: Array<String>) {
}

fun readNumber(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    } catch (e: NumberFormatException) {
        return
    } finally {
        println("exit")
    }
}

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A' .. 'Z'
fun isNumber(num: Int) = num in 1..100
fun isNotDigit(c: Char) = c !in '0' .. '9'

data class Test(val name: String, val age: Int)