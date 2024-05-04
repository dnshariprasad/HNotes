package com.htrack.hnotes.data

import androidx.room.Entity

@Entity
data class Tag(
    val id: String? = "",
    val name: String? = "",
    val about: String? = ""
)