package com.example.compose_archtiecture_pattern.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose_archtiecture_pattern.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)

abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
}