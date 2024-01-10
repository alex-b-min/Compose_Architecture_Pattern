package com.example.compose_archtiecture_pattern.feature_note.presentation.notes

import com.example.compose_archtiecture_pattern.feature_note.domain.model.Note
import com.example.compose_archtiecture_pattern.feature_note.domain.util.NoteOrder
import com.example.compose_archtiecture_pattern.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Title(orderType = OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
) {
}