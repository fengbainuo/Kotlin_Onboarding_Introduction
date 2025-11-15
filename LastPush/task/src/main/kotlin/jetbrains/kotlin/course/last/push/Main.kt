package jetbrains.kotlin.course.last.push

import org.intellij.lang.annotations.Pattern

// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }
            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }
            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }
            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun main() {
    // Uncomment this code on the last step of the game

    // val pattern = getPattern()
    // val generatorName = chooseGenerator()
    // println("Please input the width of the resulting picture:")
    // val width = safeReadLine().toInt()
    // println("Please input the height of the resulting picture:")
    // val height = safeReadLine().toInt()

    // println("The pattern:$newLineSymbol${pattern.trimIndent()}")

    // println("The generated image:")
    // println(applyGenerator(pattern, generatorName, width, height))
}

fun getPatternHeight(pattern: String): Int = pattern.lines().size

fun fillPatternRow(patternRow: String, patternWidth: Int): String {
    val builder = StringBuilder()
    builder.append(patternRow)
    if (patternRow.length > patternWidth) {
        error("IllegalStateException")
    }
    repeat(patternWidth - patternRow.length) {
        builder.append("$separator")
    }
    val newPattern = builder.toString()
    return newPattern
}

fun repeatHorizontally(pattern: String, n: Int, patternWidth: Int): String {
    val lines = pattern.lines()
    val builder = StringBuilder()
    for (line in lines) {
        val newLine = if (line.length < patternWidth) {
            fillPatternRow(line, patternWidth)
        } else {
            line
        }
        repeat(n) {
            builder.append(newLine)
        }
        builder.append("$newLineSymbol")
    }
    val newPattern = builder.toString()
    return newPattern
}

fun dropTopLine(image: String, width: Int, patternHeight: Int, patternWidth: Int): String {
    if (patternHeight == 1) {
        return image
    }
    val lines = image.lines()
    val newImage = lines.drop(1)
    return newImage.joinToString("$newLineSymbol")
}

fun canvasGenerator(pattern: String, width: Int, height: Int): String {
    val patternWidth = getPatternWidth(pattern)
    val patternHeight = getPatternHeight(pattern)
    if (height == 1) {
        return repeatHorizontally(pattern, width, patternWidth)
    }
    val builder = StringBuilder()
    builder.append(pattern)
    repeat(height-1) {
        val dropTop = dropTopLine(pattern, patternWidth, patternHeight, patternWidth)
        builder.append("$newLineSymbol")
        builder.append(dropTop)
    }
    val newColumn = builder.toString()
    return repeatHorizontally(newColumn, width, patternWidth)
}

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int): String {
    if (height == 1) {
        return repeatHorizontallyWithGaps(pattern, width).dropLast(1)
    } else if (width == 1) {
        val columnBuilder = StringBuilder()
        repeat (height) {
            columnBuilder.append("$pattern$newLineSymbol")
        }
        val newColumn = columnBuilder.toString()
        return newColumn.dropLast(1)
    }
    val newPatternBuilder = StringBuilder()
    var i = height
    while (i > 0) {
        if (i % 2 != 0) {
            newPatternBuilder.append(repeatHorizontallyWithGaps(pattern, width))
        } else {
            newPatternBuilder.append(repeatHorizontallyWithGapsEvenLine(pattern, width))
        }
        i -= 1
    }
    val newPattern = newPatternBuilder.toString()
    return newPattern
}

fun createEmptyPattern(pattern: String): String {
    val patternWidth = getPatternWidth(pattern)
    val patternHeight = getPatternHeight(pattern)
    val builder = StringBuilder()
    repeat(patternWidth) {
        builder.append(" ")
    }
    builder.append("$newLineSymbol")
    val newLine = builder.toString()
    val patternBuilder = StringBuilder()
    repeat(patternHeight) {
        patternBuilder.append(newLine)
    }
    val emptyPattern = patternBuilder.toString()
    return emptyPattern
}

fun repeatHorizontallyWithGaps(pattern: String, n: Int): String {
    if (n == 1) {
        return "$pattern$newLineSymbol"
    }
    val repeated = StringBuilder()
    repeated.append(pattern)
    var i = n-1
    while (i > 0) {
        if (i % 2 != 0) {
            repeated.append(pattern)
        } else {
            repeated.append(createEmptyPattern(pattern))
        }
        i -= 0
    }
    repeated.append("$newLineSymbol")
    val oddRow = repeated.toString()
    return oddRow
}

fun repeatHorizontallyWithGapsEvenLine(pattern: String, n: Int): String {
    if (n == 1) {
        return "${createEmptyPattern(pattern)}$newLineSymbol"
    }
    val repeated = StringBuilder()
    var i = n
    while (i > 0) {
        if (i % 2 == 0) {
            repeated.append(pattern)
        } else {
            repeated.append(createEmptyPattern(pattern))
        }
        i -= 1
    }
    repeated.append("$newLineSymbol")
    val evenRow = repeated.toString()
    return evenRow
}