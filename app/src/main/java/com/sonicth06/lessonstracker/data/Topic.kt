package com.sonicth06.lessonstracker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "topics",
    foreignKeys = [
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
    /**
     * A topic belongs to a subject. The foreign key uses CASCADE, so deleting
     * a subject also deletes its topics and the database never keeps orphans.
     */
)
data class Topic(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subjectId: Int,
    //val sessionId: Int? = null,
    val name: String,
    val description: String = "",
    val covered: Boolean = false,
)