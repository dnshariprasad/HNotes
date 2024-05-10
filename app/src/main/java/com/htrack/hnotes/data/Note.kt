package com.htrack.hnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String? = "",
    var info: String? = "",
    var link: String? = "",
    var type: String? = "",
    val tags: String = ""
)

