package com.example.compose_archtiecture_pattern.domain.use_case

import com.example.compose_archtiecture_pattern.domain.model.Note
import com.example.compose_archtiecture_pattern.domain.repository.NoteRepository

class DeleteNotesUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}