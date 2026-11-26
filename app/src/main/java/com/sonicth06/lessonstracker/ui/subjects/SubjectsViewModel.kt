package com.sonicth06.lessonstracker.ui.subjects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonicth06.lessonstracker.data.LessonTrackerRepository
import com.sonicth06.lessonstracker.data.Subject
import com.sonicth06.lessonstracker.data.Topic
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SubjectsViewModel(
    private val repository: LessonTrackerRepository
) : ViewModel() {

    var entryUiState by mutableStateOf(SubjectEntryUiState())
        private set

    val uiState: StateFlow<SubjectsUiState> =
        repository.getAllSubjectsStream()
            .map { SubjectsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SubjectsUiState()
            )

    fun updateEntryName(newName: String) {
        entryUiState = SubjectEntryUiState(
            name = newName,
            isValid = newName.trim().isNotEmpty())
    }

    suspend fun saveSubject() {
        val trimmedName = entryUiState.name.trim()
        if (trimmedName.isEmpty()) return
        repository.insertSubject(Subject(name = trimmedName))
        entryUiState = SubjectEntryUiState()
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            repository.deleteSubject(subject)
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class SubjectEntryUiState(
    val name: String = "",
    val isValid: Boolean = false
)

data class SubjectsUiState(
    val subjects: List<Subject> = emptyList()
)