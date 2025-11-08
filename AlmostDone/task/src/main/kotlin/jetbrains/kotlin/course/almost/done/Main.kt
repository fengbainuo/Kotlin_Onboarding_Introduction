package jetbrains.kotlin.course.almost.done

fun main() {
    // Uncomment this code on the last step of the game

    photoshop()
}

fun trimPicture(picture: String): String = picture.trimIndent()

fun applyBordersFilter(picture: String): String {
    val pictureWidth = getPictureWidth(picture)
    val firstLine = StringBuilder()
    repeat(pictureWidth+4) {
        firstLine.append(borderSymbol)
    }
    val topAndBottomBorder = firstLine.toString()
    val picLines = picture.lines()
    val newPic = StringBuilder()
    for (line in picLines) {
        val newLine = StringBuilder()
        newLine.append("$borderSymbol$separator")
        newLine.append(line)
        if (line.length < pictureWidth) {
            repeat(pictureWidth - line.length) {
                newLine.append(separator)
            }
        }
        newLine.append("$separator$borderSymbol")
        newLine.toString()
        newPic.append("$newLine$newLineSymbol")
    }
    newPic.toString()
    val borderPicture = "$topAndBottomBorder$newLineSymbol$newPic$topAndBottomBorder"
    return borderPicture
}

fun applySquaredFilter(picture: String): String {
    val pictureWidth = getPictureWidth(picture)
    val firstLine = StringBuilder()
    repeat(pictureWidth+4) {
        firstLine.append(borderSymbol)
    }
    val topAndBottomBorder = firstLine.toString()
    val picLines = picture.lines()
    val topPic = StringBuilder()
    for (line in picLines) {
        val newLine = StringBuilder()
        repeat(2) {
            newLine.append("$borderSymbol$separator")
            newLine.append(line)
            if (line.length < pictureWidth) {
                repeat(pictureWidth - line.length) {
                    newLine.append(separator)
                }
            }
            newLine.append("$separator$borderSymbol")
        }
        newLine.toString()
        topPic.append("$newLine$newLineSymbol")
    }
    topPic.toString()
    val squarePic = "$topAndBottomBorder$topAndBottomBorder$newLineSymbol$topPic$topAndBottomBorder$topAndBottomBorder$newLineSymbol$topPic$topAndBottomBorder$topAndBottomBorder"
    return squarePic
}

fun applyFilter(picture: String, filter: String): String {
    return when (filter) {
        "borders" -> applyBordersFilter(picture)
        "squared" -> applySquaredFilter(picture)
        else -> error("Unexpected filter")
    }
}

fun safeReadLine(): String {
    val input = readlnOrNull()
    return input ?: error("Null value detected")
}

fun chooseFilter(): String {
    println("Please choose the filter: 'borders' or 'squared'.")
    while (true) {
        when (val choice = safeReadLine()) {
            "borders", "squared" -> {
                return choice
            }
            else -> {
                println("Please input 'borders' or 'squared'")
            }
        }
    }
}

fun choosePicture(): String {
    while (true) {
        println("Please choose a picture. The possible options are: ${allPictures().joinToString(", ")}")
        val choicePic = safeReadLine()
        if (choicePic in allPictures()) {
            println(getPictureByName(choicePic))?.let {
                return getPictureByName(choicePic).toString()
            }
        }
    }
}

fun getPicture(): String {
    while (true) {
        println("Do you want to use a predefined picture or a custom one? Please input 'yes' for a predefined image or 'no' for a custom one")
        val type = safeReadLine()
        if (type == "yes") {
            return choosePicture()
        } else if (type == "no") {
            println("Please input a custom picture")
            val customPic = safeReadLine()
            return customPic
        }
        println("Please input 'yes' or 'no'")
    }
}

fun photoshop(): Unit {
    val picture = getPicture()
    val filter = chooseFilter()
    println("The old image:")
    println(trimPicture(picture))
    println("The transformed picture:")
    println(applyFilter(trimPicture(picture), filter))
}