package com.sonicth06.lessonstracker.ui.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonicth06.lessonstracker.data.LessonTrackerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SubjectsViewModel(repository: LessonTrackerRepository) : ViewModel() {

    val uiState: StateFlow<SubjectsUiState> =
        repository.getAllSubjectsStream()
            .map { SubjectsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SubjectsUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}