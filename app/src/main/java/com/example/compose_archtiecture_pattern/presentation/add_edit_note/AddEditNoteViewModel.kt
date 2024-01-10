package com.example.compose_archtiecture_pattern.presentation.add_edit_note

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_archtiecture_pattern.domain.model.InvalidNoteException
import com.example.compose_archtiecture_pattern.domain.model.Note
import com.example.compose_archtiecture_pattern.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = MutableStateFlow<NoteTextFieldState>(
        NoteTextFieldState(
            hint = "제목을 입력해주세요."
        )
    )
    val noteTitle: StateFlow<NoteTextFieldState> = _noteTitle

    private val _noteContent = MutableStateFlow<NoteTextFieldState>(
        NoteTextFieldState(
            hint = "내용을 입력해주세요."
        )
    )
    val noteContent: StateFlow<NoteTextFieldState> = _noteContent

    private val _noteColor = MutableStateFlow<Int>(Note.noteColors.random().toArgb())
    val noteColor: StateFlow<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            viewModelScope.launch {
                if(noteId != -1) {
                    noteUseCases.getNoteByIdUseCase(noteId)?.also { note ->
                        _noteTitle.update { title ->
                            title.copy(
                                text = note.title,
                                isHintVisible = false
                            )
                        }
                        _noteContent.update { content ->
                            content.copy(
                                text = note.content,
                                isHintVisible = false
                            )
                        }
                        _noteColor.update { color ->
                            note.color
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.update { title ->
                    title.copy(
                        text = event.value
                    )
                }
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.update { title ->
                    title.copy(
                        isHintVisible = !event.focusState.isFocused
                                && title.text.isBlank()
                    )
                }

            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.update { content ->
                    content.copy(
                        text = event.value
                    )
                }
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.update { content ->
                    content.copy(
                        isHintVisible = !event.focusState.isFocused
                                && content.text.isBlank()
                    )
                }
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.update { color ->
                    event.color
                }
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnacbar(
                                message = "노트를 저장 할 수 없습니다."
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnacbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}