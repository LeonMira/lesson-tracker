package com.sonicth06.lessonstracker.data

import kotlinx.coroutines.flow.Flow

interface LessonTrackerRepository {

    fun getTopicsBySubjectStream(subjectId: Int): Flow<List<Topic>>
    fun getTopicStream(id: Int): Flow<Topic?>
    suspend fun insertTopic(topic: Topic)
    suspend fun updateTopic(topic: Topic)
    suspend fun deleteTopic(topic: Topic)

    fun getAllSubjectsStream(): Flow<List<Subject>>
    fun getSubjectStream(id: Int): Flow<Subject?>
    suspend fun insertSubject(subject: Subject)
    suspend fun updateSubject(subject: Subject)
    suspend fun deleteSubject(subject: Subject)
}