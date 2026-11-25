package com.sonicth06.lessonstracker.ui.topics

import androidx.lifecycle.ViewModel
import com.sonicth06.lessonstracker.data.Topic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TopicsViewModel : ViewModel() {

    //MutableStateFlow is the observable container holding the state and telling the display
    //when it changes. Acts as an intermediary between ViewModel and the UI
    private val _uiState = MutableStateFlow(TopicsUiState(
        topicList = listOf(
            Topic(id = 1, subjectId = 1, name = "Variables"),
            Topic(id = 2, subjectId = 1, name = "Condicionales"),
        )
        //MutableStateFlow is the observable container holding the state and telling the display
        //when it changes. Acts as an intermediary between ViewModel and the UI
    ))
    val uiState: StateFlow<TopicsUiState> = _uiState.asStateFlow()

    fun onCoveredChange(topic: Topic, covered: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                topicList = currentState.topicList.map { item ->
                    if (item.id == topic.id) item.copy(covered = covered) else item
                }
            )
        }
    }
}