package com.example.compose_archtiecture_pattern.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compose_archtiecture_pattern.ui.theme.BabyBlue
import com.example.compose_archtiecture_pattern.ui.theme.LightGreen
import com.example.compose_archtiecture_pattern.ui.theme.RedOrange
import com.example.compose_archtiecture_pattern.ui.theme.RedPink
import com.example.compose_archtiecture_pattern.ui.theme.Violet

@Entity
class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)