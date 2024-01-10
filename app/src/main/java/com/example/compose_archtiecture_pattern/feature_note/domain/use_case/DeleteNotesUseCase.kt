package com.example.compose_archtiecture_pattern.feature_note.domain.use_case

import com.example.compose_archtiecture_pattern.feature_note.domain.model.Note
import com.example.compose_archtiecture_pattern.feature_note.domain.repository.NoteRepository

class DeleteNotesUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}