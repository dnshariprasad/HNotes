package com.htrack.htrack.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String? = "",
    var info: String? = "",
    var link: String? = "",
    var type: String? = "",
    val tags: String = ""
): Parcelable

