package com.htrack.hnotes.data

data class Note(
    val id: String? = "",
    val title: String? = "",
    val info: String? = "",
    val tags: List<Tag>? = emptyList()
)

