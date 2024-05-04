package com.htrack.hnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String? = "",
    val info: String? = "",
//    val type: String? = "",
//    val time: String? = "",
//    val tags: List<Tag>? = emptyList()
)

