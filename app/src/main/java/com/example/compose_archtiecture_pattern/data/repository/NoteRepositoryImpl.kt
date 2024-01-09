package com.example.compose_archtiecture_pattern.data.repository

import androidx.room.Dao
import com.example.compose_archtiecture_pattern.data.data_source.NoteDao
import com.example.compose_archtiecture_pattern.domain.model.Note
import com.example.compose_archtiecture_pattern.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id = id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note = note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note = note)
    }
}