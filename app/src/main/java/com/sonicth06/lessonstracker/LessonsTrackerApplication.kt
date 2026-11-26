package com.sonicth06.lessonstracker

import android.app.Application
import com.sonicth06.lessonstracker.data.LessonTrackerContainer
import com.sonicth06.lessonstracker.data.LessonTrackerDataContainer

class LessonTrackerApplication : Application() {
    lateinit var container: LessonTrackerContainer

    override fun onCreate() {
        super.onCreate()
        container = LessonTrackerDataContainer(this)
    }
}