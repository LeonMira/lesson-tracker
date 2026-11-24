package com.sonicth06.lessonstracker.ui.home

import androidx.lifecycle.ViewModel
import com.sonicth06.lessonstracker.data.Topic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(
        topicList = listOf(
            Topic(id = 1, subjectId = 1, name = "Variables"),
            Topic(id = 2, subjectId = 1, name = "Condicionales"),
        )
        //MutableStateFlow is the observable container holding the state and telling the display
        //when it changes. Acts as an intermediary between ViewModel and the UI
    ))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onCoveredChange(topic: Topic, covered; Boolean) {

    }
}