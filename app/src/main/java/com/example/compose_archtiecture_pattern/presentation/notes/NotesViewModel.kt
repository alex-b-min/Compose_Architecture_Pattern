package com.example.compose_archtiecture_pattern.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_archtiecture_pattern.domain.model.Note
import com.example.compose_archtiecture_pattern.domain.use_case.NoteUseCases
import com.example.compose_archtiecture_pattern.domain.util.NoteOrder
import com.example.compose_archtiecture_pattern.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<NotesState>(NotesState())
    val state: StateFlow<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private val getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder == event.noteOrder &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                } else {
                    getNotes(noteOrder = event.noteOrder)
                }
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotesUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.update { currentState ->
                    currentState.copy(
                        isOrderSectionVisible = !currentState.isOrderSectionVisible
                    )
                }
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()

        noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.update { currentState ->
                    currentState.copy(
                        notes = notes,
                        noteOrder = noteOrder
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}