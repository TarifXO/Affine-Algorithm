private lateinit var r : CharArray

fun getSize4(str1: String ): Int {
    val lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray()
    val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
    val space = " ".toCharArray()
    val n = when {
        str1.all { it.isLowerCase() } -> lowercase
        str1.all { it.isUpperCase() } -> uppercase
        str1.all { it.isWhitespace() } -> space
        str1.all { it.isLowerCase() || it.isWhitespace() } -> space + lowercase
        str1.all { it.isUpperCase() || it.isWhitespace() } -> space + uppercase
        str1.all { it.isLowerCase() || it.isUpperCase() } -> lowercase + uppercase
        else -> space + lowercase + uppercase
    }
    val output = n.size
    r = n
    return output
}
private var alphabetd : CharArray = charArrayOf()

fun alphapetOfDecrypt(option : String) {
    var alphabet: CharArray = charArrayOf()
    when (option) {
        1.toString() -> alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        2.toString() -> alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        3.toString() -> alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        4.toString() -> alphabet = " abcdefghijklmnopqrstuvwxyz".toCharArray()
        5.toString() -> alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        6.toString() -> alphabet = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        else -> println("Invalid option.")
    }
    alphabetd = alphabet
}

fun affineCipher4(str: String, a: Int, b: Int, status: String): String {
    var result = ""
    val modSize = getSize4(str)
    for (char in str) {
        val p = r.indexOf(char)
        val d = alphabetd.indexOf(char)
        if (p == -1) {
            result += char
            continue
        }
        if (status == "encrypt") {
            val c = (a * p + b) % modSize
            result += r[c]
        }
        if (status == "decrypt") {
            val aInverse = modInverse4(a, alphabetd.size)
            if (aInverse == -1) {
                println("Invalid key: Modular inverse of 'a' does not exist.")
                return ""
            }
            val c = (aInverse * (d - b + alphabetd.size)) % alphabetd.size
            result += alphabetd[c]
        }
    }
    return result
}

fun gcd4(x: Int, y: Int) : Int {
    var a = x
    var m = y
    while (a !=0) {
        val reminder = a
        a = m % a
        m = reminder
    }
    return m
}

fun modInverse4(a: Int, m: Int): Int {
    for (x in 1..<m) {
        if ((a * x) % m == 1) {
            return x
        }
    }
    return -1
}

fun main() {
    println("Enter the text:")
    var inputText = readlnOrNull()?.takeWhile { it.isLetter() || it.isWhitespace() } ?: ""
    while (inputText.isBlank()) {
        println("Invalid input. Please enter valid text.")
        println("Enter the text:")
        inputText = readlnOrNull()?.takeWhile { it.isLetter() || it.isWhitespace() } ?: ""
    }
    var aValue: Int? = null
    while (aValue == null || gcd4(aValue, getSize4(inputText)) != 1) {
        print("Enter the 'a' value (an integer coprime to ${getSize4(inputText)}): ")
        aValue = readlnOrNull()?.toIntOrNull()
        if (aValue == null || gcd4(aValue, getSize4(inputText)) != 1) {
            println("Invalid input. Please enter a valid integer coprime to ${getSize4(inputText)}.")
        }
    }
    var bValue: Int? = null
    while (bValue == null) {
        print("Enter the 'b' value (an integer): ")
        bValue = readlnOrNull()?.toIntOrNull()
        if (bValue == null) {
            println("Invalid input. Please enter a valid integer.")
        }
    }
    println("Choose operation (encrypt/decrypt):")
    val operation = readlnOrNull()?.lowercase() ?: ""
    if (operation == "decrypt") {
        println("Choose Number of the Following:")
        println("1) LowerCase ")
        println("2) UpperCase ")
        println("3) Lower + UpperCase ")
        println("4) Space + LowerCase ")
        println("5) Space + UpperCase ")
        println("6) Space + LowerCase + UpperCase ")
        val option = readlnOrNull()
        alphapetOfDecrypt(option.toString())
    }
    val result = affineCipher4(inputText, aValue, bValue, operation)
    println("Result: $result")
}