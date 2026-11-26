package com.sonicth06.lessonstracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Subject::class, Topic::class],
    version = 2,
    exportSchema = false,
)
abstract class LessonTrackerDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun topicDao(): TopicDao

    companion object {
        @Volatile
        private var Instance: LessonTrackerDatabase? = null

        fun getDatabase(context: Context): LessonTrackerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    LessonTrackerDatabase::class.java,
                    "lesson_tracker_database",
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
