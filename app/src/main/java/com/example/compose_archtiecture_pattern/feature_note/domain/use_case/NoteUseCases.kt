package com.example.compose_archtiecture_pattern.feature_note.domain.use_case

import javax.inject.Inject

data class NoteUseCases @Inject constructor(
    val getNotes: GetNotesUseCase,
    val deleteNotesUseCase: DeleteNotesUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
)