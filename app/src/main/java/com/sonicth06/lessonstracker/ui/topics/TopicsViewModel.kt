package com.sonicth06.lessonstracker.ui.topics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonicth06.lessonstracker.data.LessonTrackerRepository
import com.sonicth06.lessonstracker.data.Topic
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TopicsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: LessonTrackerRepository
) : ViewModel() {

    private val subjectId: Int = checkNotNull(savedStateHandle[TopicsDestination.subjectIdArg])

    val uiState: StateFlow<TopicsUiState> =
        repository.getTopicsBySubjectStream(subjectId)
            .map { TopicsUiState(topicList = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TopicsUiState()
            )

    var entryUiState by mutableStateOf(TopicEntryUiState())
        private set

    fun updateEntryName(newName: String) {
        entryUiState = entryUiState.copy(
            name = newName,
            isValid = newName.isNotBlank()
        )
    }

    fun updateEntryDescription(newDescription: String) {
        entryUiState = entryUiState.copy(
            description = newDescription
        )
    }

    suspend fun saveTopic() {
        if (!entryUiState.isValid) return
        repository.insertTopic(entryUiState.toTopic(subjectId))
        entryUiState = TopicEntryUiState()
    }

    fun deleteTopic(topic: Topic) {
        viewModelScope.launch {
            repository.deleteTopic(topic)
        }
    }

    fun onCoveredChange(topic: Topic, covered: Boolean) {
        viewModelScope.launch {
            repository.updateTopic(topic.copy(covered = covered))
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TopicUiState(
    val topicList: List<Topic> = emptyList()
)

data class TopicEntryUiState(
    val name: String = "",
    val description: String = "",
    val isValid: Boolean = false
)

fun TopicEntryUiState.toTopic(subjectId: Int): Topic = Topic(
    name = name.trim(),
    description = description.trim(),
    subjectId = subjectId
)