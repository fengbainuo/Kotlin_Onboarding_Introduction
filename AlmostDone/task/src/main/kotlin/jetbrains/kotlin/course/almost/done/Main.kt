package jetbrains.kotlin.course.almost.done

fun main() {
    // Uncomment this code on the last step of the game

    // photoshop()
}

fun trimPicture(picture: String): String = picture.trimIndent()

fun applyBordersFilter(picture: String): String = TODO("Not implemented yet")

fun applySquaredFilter(picture: String): String = TODO("Not implemented yet")

fun applyFilter(picture: String, filter: String): String {
    return when (filter) {
        "borders" -> applyBordersFilter(picture)
        "squared" -> applySquaredFilter(picture)
        else -> error("Unexpected filter")
    }
}