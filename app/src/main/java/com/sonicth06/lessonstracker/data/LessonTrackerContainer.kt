package com.sonicth06.lessonstracker.data

import android.content.Context
import com.sonicth06.lessonstracker.data.LessonTrackerDatabase

interface LessonTrackerContainer {
    val lessonTrackerRepository: LessonTrackerRepository
}

class LessonTrackerDataContainer(private val context: Context) : LessonTrackerContainer {
    override val lessonTrackerRepository: LessonTrackerRepository by lazy {
        OfflineLessonTrackerRepository(
            LessonTrackerDatabase.getDatabase(context).subjectDao(),
            LessonTrackerDatabase.getDatabase(context).topicDao()
        )
    }
}
