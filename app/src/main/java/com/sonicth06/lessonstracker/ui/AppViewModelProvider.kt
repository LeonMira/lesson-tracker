package com.sonicth06.lessonstracker.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sonicth06.lessonstracker.LessonTrackerApplication
import com.sonicth06.lessonstracker.ui.subjects.SubjectsViewModel
import com.sonicth06.lessonstracker.ui.topics.TopicsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SubjectsViewModel(
                lessonTrackerApplication().container.lessonTrackerRepository
            )
        }
        initializer {
            TopicsViewModel(
                this.createSavedStateHandle(),
                lessonTrackerApplication().container.lessonTrackerRepository,
            )
        }
    }
}

fun CreationExtras.lessonTrackerApplication(): LessonTrackerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as? LessonTrackerApplication)
        ?: throw IllegalStateException("Application must be an instance of LessonTrackerApplication")