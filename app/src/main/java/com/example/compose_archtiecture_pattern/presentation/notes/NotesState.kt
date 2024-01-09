package com.example.compose_archtiecture_pattern.presentation.notes

import com.example.compose_archtiecture_pattern.domain.model.Note
import com.example.compose_archtiecture_pattern.domain.util.NoteOrder
import com.example.compose_archtiecture_pattern.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Title(orderType = OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
) {
}