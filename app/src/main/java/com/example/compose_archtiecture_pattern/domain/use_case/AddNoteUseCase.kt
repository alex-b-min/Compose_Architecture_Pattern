package com.example.compose_archtiecture_pattern.domain.use_case

import com.example.compose_archtiecture_pattern.domain.model.InvalidNoteException
import com.example.compose_archtiecture_pattern.domain.model.Note
import com.example.compose_archtiecture_pattern.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("제목이 비어 있습니다.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("내용이 비어 있습니다.")
        }
        repository.insertNote(note)
    }
}