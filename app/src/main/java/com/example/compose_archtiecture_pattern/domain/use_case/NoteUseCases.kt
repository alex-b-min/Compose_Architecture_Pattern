package com.example.compose_archtiecture_pattern.domain.use_case

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNotesUseCase: DeleteNotesUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
)