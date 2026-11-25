package com.sonicth06.lessonstracker.data

import kotlinx.coroutines.flow.Flow

class OfflineLessonTrackerRepository(
    private val subjectDao: SubjectDao,
    private val topicDao: TopicDao
): LessonTrackerRepository {
    override fun getTopicsBySubjectStream(subjectId: Int): Flow<List<Topic>> =
        topicDao.getTopicsBySubject(subjectId)
    override fun getTopicStream(id: Int): Flow<Topic?> =
        topicDao.getTopic(id)
    override suspend fun insertTopic(topic: Topic) =
        topicDao.insert(topic)
    override suspend fun updateTopic(topic: Topic) =
        topicDao.update(topic)
    override suspend fun deleteTopic(topic: Topic) =
        topicDao.delete(topic)

    override fun getAllSubjectsStream(): Flow<List<Subject>> =
        subjectDao.getAllSubjects()
    override fun getSubjectStream(id: Int): Flow<Subject?> =
        subjectDao.getSubject(id)
    override suspend fun insertSubject(subject: Subject) =
        subjectDao.insert(subject)
    override suspend fun updateSubject(subject: Subject) =
        subjectDao.update(subject)
    override suspend fun deleteSubject(subject: Subject) =
        subjectDao.delete(subject)
}
