package com.gabriel.lunala.project.utils.flaging

enum class Priority(val id: Int) {

    SEVERE(3),
    MODERATED(2),
    LOW(1);

    fun isHigher(priority: Priority) =
            id > priority.id

    fun isLower(priority: Priority) =
            id < priority.id

}


val Priority.role: String
get() = when {
    this == Priority.SEVERE -> "Owner"
    this == Priority.MODERATED -> "Administrator"
    else -> "Member"
}
